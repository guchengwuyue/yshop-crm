package co.yixiang.yshop.module.product.service.storeproduct;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductCreateReqVO;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductExportReqVO;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductPageReqVO;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductUpdateReqVO;
import co.yixiang.yshop.module.product.convert.storeproduct.StoreProductConvert;
import co.yixiang.yshop.module.product.convert.storeproductrule.StoreProductRuleConvert;
import co.yixiang.yshop.module.product.dal.dataobject.storeproduct.StoreProductDO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrresult.StoreProductAttrResultDO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue.StoreProductAttrValueDO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductrule.StoreProductRuleDO;
import co.yixiang.yshop.module.product.dal.mysql.storeproduct.StoreProductMapper;
import co.yixiang.yshop.module.product.enums.product.SpecTypeEnum;
import co.yixiang.yshop.module.product.service.category.ProductCategoryService;
import co.yixiang.yshop.module.product.service.storeproduct.dto.*;
import co.yixiang.yshop.module.product.service.storeproductattr.StoreProductAttrService;
import co.yixiang.yshop.module.product.service.storeproductattrresult.StoreProductAttrResultService;
import co.yixiang.yshop.module.product.service.storeproductattrvalue.StoreProductAttrValueService;
import co.yixiang.yshop.module.product.service.storeproductrule.StoreProductRuleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.product.enums.ErrorCodeConstants.*;

/**
 * 商品 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class StoreProductServiceImpl extends ServiceImpl<StoreProductMapper,StoreProductDO> implements StoreProductService {

    @Resource
    private StoreProductMapper storeProductMapper;
    @Resource
    private StoreProductAttrValueService storeProductAttrValueService;
    @Resource
    private StoreProductAttrService storeProductAttrService;
    @Resource
    private StoreProductRuleService storeProductRuleService;
    @Resource
    private StoreProductAttrResultService storeProductAttrResultService;
    @Resource
    private ProductCategoryService productCategoryService;

    @Override
    public Long createStoreProduct(StoreProductCreateReqVO createReqVO) {
        // 插入
        StoreProductDO storeProduct = StoreProductConvert.INSTANCE.convert(createReqVO);
        storeProductMapper.insert(storeProduct);
        // 返回
        return storeProduct.getId();
    }

    @Override
    public void updateStoreProduct(StoreProductUpdateReqVO updateReqVO) {
        // 校验存在
        validateStoreProductExists(updateReqVO.getId());
        // 更新
        StoreProductDO updateObj = StoreProductConvert.INSTANCE.convert(updateReqVO);
        storeProductMapper.updateById(updateObj);
    }

    @Override
    public void deleteStoreProduct(Long id) {
        // 校验存在
        validateStoreProductExists(id);
        // 删除
        storeProductMapper.deleteById(id);
    }

    private void validateStoreProductExists(Long id) {
        if (storeProductMapper.selectById(id) == null) {
            throw exception(STORE_PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public StoreProductDO getStoreProduct(Long id) {
        return storeProductMapper.selectById(id);
    }

    @Override
    public List<StoreProductDO> getStoreProductList(Collection<Long> ids) {
        return storeProductMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StoreProductDO> getStoreProductPage(StoreProductPageReqVO pageReqVO) {
//        if(StrUtil.isNotEmpty(pageReqVO.getCateId())) {
//            ProductCategoryDO productCategoryDO = productCategoryService.getCategory(Convert.toLong(pageReqVO.getCateId()));
//            if(productCategoryDO != null) {
//                List<Long> catIds = new ArrayList<>();
//                if (Objects.equals(productCategoryDO.getParentId(), ProductCategoryDO.PARENT_ID_NULL)) {
//                    catIds = productCategoryService.list((Wrappers.<ProductCategoryDO>lambdaQuery()
//                            .eq(ProductCategoryDO::getParentId, productCategoryDO.getId())))
//                            .stream().map(ProductCategoryDO::getId).collect(Collectors.toList());
//                } else {
//                    catIds.add(Convert.toLong(pageReqVO.getCateId()));
//                }
//                pageReqVO.setCatIds(catIds);
//            }
      //  }
        return storeProductMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StoreProductDO> getStoreProductList(StoreProductExportReqVO exportReqVO) {
        return storeProductMapper.selectList(exportReqVO);
    }

    /**
     * 获取生成的属性
     *
     * @param id      商品id
     * @param jsonStr jsonStr
     * @return map
     */
    @Override
    public Map<String, Object> getFormatAttr(Long id, String jsonStr, boolean isActivity) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map<String, Object> resultMap = new LinkedHashMap<>(3);

        if (jsonObject == null || jsonObject.get("attrs") == null || jsonObject.getJSONArray("attrs").isEmpty()) {
            resultMap.put("attr", new ArrayList<>());
            resultMap.put("value", new ArrayList<>());
            resultMap.put("header", new ArrayList<>());
            return resultMap;
        }


        List<FromatDetailDto> fromatDetailDTOList = JSON.parseArray(jsonObject.get("attrs").toString(),
                FromatDetailDto.class);

        //fromatDetailDTOList
        DetailDto detailDto = this.attrFormat(fromatDetailDTOList);

        List<Map<String, Object>> headerMapList = null;
        List<Map<String, Object>> valueMapList = new ArrayList<>();
        String align = "center";
        Map<String, Object> headerMap = new LinkedHashMap<>();
        for (Map<String, Map<String, String>> map : detailDto.getRes()) {
            Map<String, String> detail = map.get("detail");
            String[] detailArr = detail.values().toArray(new String[]{});
            Arrays.sort(detailArr);

            String sku = String.join(",", detailArr);

            Map<String, Object> valueMap = new LinkedHashMap<>();

            List<String> detailKeys =
                    detail.entrySet()
                            .stream()
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

            int i = 0;
            headerMapList = new ArrayList<>();
            for (String title : detailKeys) {
                headerMap.put("title", title);
                headerMap.put("minWidth", "130");
                headerMap.put("align", align);
                headerMap.put("key", "value" + (i + 1));
                headerMap.put("slot", "value" + (i + 1));
                headerMapList.add(ObjectUtil.clone(headerMap));
                i++;
            }

            String[] detailValues = detail.values().toArray(new String[]{});
            for (int j = 0; j < detailValues.length; j++) {
                String key = "value" + (j + 1);
                valueMap.put(key, detailValues[j]);
            }
//            /** 拼团属性对应的金额 */
//            private BigDecimal pinkPrice;
//
//            /** 秒杀属性对应的金额 */
//            private BigDecimal seckillPrice;
//            /** 拼团库存属性对应的库存 */
//            private Integer pinkStock;
//
//            private Integer seckillStock;
            valueMap.put("detail", detail);
            valueMap.put("sku", "");
            valueMap.put("pic", "");
            valueMap.put("price", 0);
            valueMap.put("cost", 0);
            valueMap.put("ot_price", 0);
            valueMap.put("stock", 0);
            valueMap.put("bar_code", "");
            valueMap.put("weight", 0);
            valueMap.put("volume", 0);
            valueMap.put("brokerage", 0);
            valueMap.put("brokerage_two", 0);
            valueMap.put("pink_price", 0);
            valueMap.put("seckill_price", 0);
            valueMap.put("pink_stock", 0);
            valueMap.put("seckill_stock", 0);
            valueMap.put("integral", 0);
            valueMap.put("unique", "");
            if (id > 0) {
                StoreProductAttrValueDO storeProductAttrValue = storeProductAttrValueService
                        .getOne(Wrappers.<StoreProductAttrValueDO>lambdaQuery()
                                .eq(StoreProductAttrValueDO::getProductId, id)
                                .eq(StoreProductAttrValueDO::getSku, sku));
                if (storeProductAttrValue != null) {
                    valueMap.put("sku",storeProductAttrValue.getSku());
                    valueMap.put("pic", storeProductAttrValue.getImage());
                    valueMap.put("price", storeProductAttrValue.getPrice());
                    valueMap.put("cost", storeProductAttrValue.getCost());
                    valueMap.put("ot_price", storeProductAttrValue.getOtPrice());
                    valueMap.put("stock", storeProductAttrValue.getStock());
                    valueMap.put("bar_code", storeProductAttrValue.getBarCode());
                    valueMap.put("weight", storeProductAttrValue.getWeight());
                    valueMap.put("volume", storeProductAttrValue.getVolume());
                    valueMap.put("brokerage", storeProductAttrValue.getBrokerage());
                    valueMap.put("brokerage_two", storeProductAttrValue.getBrokerageTwo());
                    valueMap.put("pink_price", storeProductAttrValue.getPinkPrice());
                    valueMap.put("seckill_price", storeProductAttrValue.getSeckillPrice());
                    valueMap.put("pink_stock", storeProductAttrValue.getPinkStock());
                    valueMap.put("seckill_stock", storeProductAttrValue.getSeckillStock());
                    valueMap.put("integral", storeProductAttrValue.getIntegral());
                    valueMap.put("unique", storeProductAttrValue.getUnique());
                }
            }

            valueMapList.add(ObjectUtil.clone(valueMap));

        }

        this.addMap(headerMap, headerMapList, align, isActivity);


        resultMap.put("attr", fromatDetailDTOList);
        resultMap.put("value", valueMapList);
        resultMap.put("header", headerMapList);

        return resultMap;
    }

    /**
     * 增加表头
     *
     * @param headerMap     headerMap
     * @param headerMapList headerMapList
     * @param align         align
     */
    private void addMap(Map<String, Object> headerMap, List<Map<String, Object>> headerMapList, String align, boolean isActivity) {
        headerMap.put("title", "图片");
        headerMap.put("slot", "pic");
        headerMap.put("align", align);
        headerMap.put("minWidth", 80);
        headerMapList.add(ObjectUtil.clone(headerMap));

        headerMap.put("title", "售价");
        headerMap.put("slot", "price");
        headerMap.put("align", align);
        headerMap.put("minWidth", 120);
        headerMapList.add(ObjectUtil.clone(headerMap));

        headerMap.put("title", "成本价");
        headerMap.put("slot", "cost");
        headerMap.put("align", align);
        headerMap.put("minWidth", 140);
        headerMapList.add(ObjectUtil.clone(headerMap));

        headerMap.put("title", "原价");
        headerMap.put("slot", "ot_price");
        headerMap.put("align", align);
        headerMap.put("minWidth", 140);
        headerMapList.add(ObjectUtil.clone(headerMap));

        headerMap.put("title", "库存");
        headerMap.put("slot", "stock");
        headerMap.put("align", align);
        headerMap.put("minWidth", 140);
        headerMapList.add(ObjectUtil.clone(headerMap));

        headerMap.put("title", "产品编号");
        headerMap.put("slot", "bar_code");
        headerMap.put("align", align);
        headerMap.put("minWidth", 140);
        headerMapList.add(ObjectUtil.clone(headerMap));

        headerMap.put("title", "重量(KG)");
        headerMap.put("slot", "weight");
        headerMap.put("align", align);
        headerMap.put("minWidth", 140);
        headerMapList.add(ObjectUtil.clone(headerMap));

        headerMap.put("title", "体积(m³)");
        headerMap.put("slot", "volume");
        headerMap.put("align", align);
        headerMap.put("minWidth", 140);
        headerMapList.add(ObjectUtil.clone(headerMap));


        if (isActivity) {
            headerMap.put("title", "拼团价");
            headerMap.put("slot", "pink_price");
            headerMap.put("align", align);
            headerMap.put("minWidth", 140);
            headerMapList.add(ObjectUtil.clone(headerMap));

            headerMap.put("title", "拼团活动库存");
            headerMap.put("slot", "pink_stock");
            headerMap.put("align", align);
            headerMap.put("minWidth", 140);
            headerMapList.add(ObjectUtil.clone(headerMap));

            headerMap.put("title", "秒杀价");
            headerMap.put("slot", "seckill_price");
            headerMap.put("align", align);
            headerMap.put("minWidth", 140);
            headerMapList.add(ObjectUtil.clone(headerMap));

            headerMap.put("title", "秒杀活动库存");
            headerMap.put("slot", "seckill_stock");
            headerMap.put("align", align);
            headerMap.put("minWidth", 140);
            headerMapList.add(ObjectUtil.clone(headerMap));
        }

        headerMap.put("title", "操作");
        headerMap.put("slot", "action");
        headerMap.put("align", align);
        headerMap.put("minWidth", 70);
        headerMapList.add(ObjectUtil.clone(headerMap));
    }



    /**
     * 组合规则属性算法
     *
     * @param fromatDetailDTOList
     * @return DetailDto
     */
    private DetailDto attrFormat(List<FromatDetailDto> fromatDetailDTOList) {

        List<String> data = new ArrayList<>();
        List<Map<String, Map<String, String>>> res = new ArrayList<>();

        fromatDetailDTOList.stream()
                .map(FromatDetailDto::getDetail)
                .forEach(i -> {
                    if (i == null || i.isEmpty()) {
                        throw exception(STORE_PRODUCT_RULE_NEED);
                    }
                    String str = ArrayUtil.join(i.toArray(), ",");
                    if (str.contains("-")) {
                        throw exception(STORE_PRODUCT_RULE_RE);
                    }
                });

        if (fromatDetailDTOList.size() > 1) {
            for (int i = 0; i < fromatDetailDTOList.size() - 1; i++) {
                if (i == 0) {
                    data = fromatDetailDTOList.get(i).getDetail();
                }
                List<String> tmp = new LinkedList<>();
                for (String v : data) {
                    for (String g : fromatDetailDTOList.get(i + 1).getDetail()) {
                        String rep2 = "";
                        if (i == 0) {
                            rep2 = fromatDetailDTOList.get(i).getValue() + "_" + v + "-"
                                    + fromatDetailDTOList.get(i + 1).getValue() + "_" + g;
                        } else {
                            rep2 = v + "-"
                                    + fromatDetailDTOList.get(i + 1).getValue() + "_" + g;
                        }

                        tmp.add(rep2);

                        if (i == fromatDetailDTOList.size() - 2) {
                            Map<String, Map<String, String>> rep4 = new LinkedHashMap<>();
                            Map<String, String> reptemp = new LinkedHashMap<>();
                            for (String h : Arrays.asList(rep2.split("-"))) {
                                List<String> rep3 = Arrays.asList(h.split("_"));
                                if (rep3.size() > 1) {
                                    reptemp.put(rep3.get(0), rep3.get(1));
                                } else {
                                    reptemp.put(rep3.get(0), "");
                                }
                            }
                            rep4.put("detail", reptemp);

                            res.add(rep4);
                        }
                    }

                }

                if (!tmp.isEmpty()) {
                    data = tmp;
                }
            }
        } else {
            List<String> dataArr = new ArrayList<>();
            for (FromatDetailDto fromatDetailDTO : fromatDetailDTOList) {
                for (String str : fromatDetailDTO.getDetail()) {
                    Map<String, Map<String, String>> map2 = new LinkedHashMap<>();
                    dataArr.add(fromatDetailDTO.getValue() + "_" + str);
                    Map<String, String> map1 = new LinkedHashMap<>();
                    map1.put(fromatDetailDTO.getValue(), str);
                    map2.put("detail", map1);
                    res.add(map2);
                }
            }
            String s = StrUtil.join("-", dataArr);
            data.add(s);
        }

        DetailDto detailDto = new DetailDto();
        detailDto.setData(data);
        detailDto.setRes(res);

        return detailDto;
    }

    /**
     * 新增/保存商品
     *
     * @param storeProductDto 商品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAndEditYxStoreProduct(StoreProductDto storeProductDto) {
        //storeProductDto.setDescription(RegexUtil.converProductDescription(storeProductDto.getDescription()));
        ProductResultDto resultDTO = this.computedProduct(storeProductDto.getAttrs());

        //添加商品
        StoreProductDO yxStoreProduct = new StoreProductDO();
        BeanUtil.copyProperties(storeProductDto, yxStoreProduct, "sliderImage");
        if (storeProductDto.getSliderImage().isEmpty()) {
            throw exception(STORE_PRODUCT_SLIDER_ERROR);
        }
        yxStoreProduct.setPrice(BigDecimal.valueOf(resultDTO.getMinPrice()));
        yxStoreProduct.setOtPrice(BigDecimal.valueOf(resultDTO.getMinOtPrice()));
        yxStoreProduct.setCost(BigDecimal.valueOf(resultDTO.getMinCost()));
        yxStoreProduct.setIntegral(resultDTO.getMinIntegral());
        yxStoreProduct.setStock(resultDTO.getStock());
        yxStoreProduct.setSliderImage(String.join(",", storeProductDto.getSliderImage()));


        this.saveOrUpdate(yxStoreProduct);

        //属性处理
        //处理单sKu
        if (SpecTypeEnum.TYPE_0.getValue().equals(storeProductDto.getSpecType())) {
            FromatDetailDto fromatDetailDto = FromatDetailDto.builder()
                    .value("规格")
                    .detailValue("")
                    .attrHidden("")
                    .detail(ListUtil.toList("默认"))
                    .build();
            List<ProductFormatDto> attrs = storeProductDto.getAttrs();
            ProductFormatDto productFormatDto = attrs.get(0);
            productFormatDto.setValue1("规格");
            Map<String, String> map = new HashMap<>();
            map.put("规格", "默认");
            productFormatDto.setDetail(map);
            storeProductAttrService.insertYxStoreProductAttr(ListUtil.toList(fromatDetailDto),
                    ListUtil.toList(productFormatDto), yxStoreProduct.getId());
        } else {
            storeProductAttrService.insertYxStoreProductAttr(storeProductDto.getItems(),
                    storeProductDto.getAttrs(), yxStoreProduct.getId());
        }


    }

    @Override
    public Map<String, Object> getProductInfo(Long id) {
        Map<String,Object> map = new LinkedHashMap<>(3);

        ArrayUtil.newArray(String.class, 3);
        //运费模板
        //todo
        //shippingTemplatesService.list()



        //商品规格
        List<StoreProductRuleDO> list = storeProductRuleService.getStoreProductRuleList(CollUtil.newArrayList());
        map.put("ruleList", StoreProductRuleConvert.INSTANCE.convertList(list));
        if (id == 0 ) {
            return map;
        }

        //处理商品详情
        StoreProductDO storeProduct = storeProductMapper.selectById(id);
        ProductDto productDto = new ProductDto();
        BeanUtil.copyProperties(storeProduct,productDto,"sliderImage");
        productDto.setSliderImage(Arrays.asList(storeProduct.getSliderImage().split(",")));
        StoreProductAttrResultDO storeProductAttrResult = storeProductAttrResultService
                .getOne(Wrappers.<StoreProductAttrResultDO>lambdaQuery()
                        .eq(StoreProductAttrResultDO::getProductId,id).last("limit 1"));
        JSONObject result = JSON.parseObject(storeProductAttrResult.getResult());
        List<StoreProductAttrValueDO> attrValues = storeProductAttrValueService.list(new LambdaQueryWrapper<StoreProductAttrValueDO>().eq(StoreProductAttrValueDO::getProductId, id));
        List<ProductFormatDto> productFormatDtos =attrValues.stream().map(i ->{
            ProductFormatDto productFormatDto = new ProductFormatDto();
            BeanUtils.copyProperties(i,productFormatDto);
            productFormatDto.setPic(i.getImage());
            return productFormatDto;
        }).collect(Collectors.toList());
        if(SpecTypeEnum.TYPE_1.getValue().equals(storeProduct.getSpecType())){
            productDto.setAttr(new ProductFormatDto());
            productDto.setAttrs(productFormatDtos);
            productDto.setItems(result.getObject("attr",ArrayList.class));
        }else{
           // this.productFromat(productDto, result);
            this.productFromatNew(productDto, attrValues.get(0));
        }

        map.put("productInfo",productDto);

        return map;

    }

    /**
     * 商品上架下架
     *
     * @param id     商品id
     * @param status ShopCommonEnum
     */
    @Override
    public void onSale(Long id, Integer status) {
        if (ShopCommonEnum.SHOW_1.getValue().equals(status)) {
            status = ShopCommonEnum.SHOW_0.getValue();
        } else {
            status = ShopCommonEnum.SHOW_1.getValue();
        }
        storeProductMapper.updateOnsale(status, id);
    }


    /**
     * 获取商品属性
     * @param productDto
     * @param result
     */
    private void productFromatNew(ProductDto productDto, StoreProductAttrValueDO result) {
        //Map<String,Object> mapAttr = (Map<String,Object>) result.getObject("value",ArrayList.class).get(0);
        ProductFormatDto productFormatDto = ProductFormatDto.builder()
                .pic(result.getImage())
                .price(result.getPrice().doubleValue())
                .cost(result.getCost().doubleValue())
                .otPrice(result.getOtPrice().doubleValue())
                .stock(result.getStock())
                .barCode(result.getBarCode())
                .weight(result.getWeight().doubleValue())
                .volume(result.getVolume().doubleValue())
                .value1("规格")
                .integral(result.getIntegral())
                .brokerage(result.getBrokerage().doubleValue())
                .brokerageTwo(result.getBrokerageTwo().doubleValue())
                .pinkPrice(result.getPinkPrice().doubleValue())
                .pinkStock(result.getPinkStock())
                .seckillPrice(result.getSeckillPrice().doubleValue())
                .seckillStock(result.getSeckillStock())
                .unique(result.getUnique())
                .sku(result.getSku())
                .build();
        productDto.setAttr(productFormatDto);
    }


    /**
     * 获取商品属性 已经废弃
     * @param productDto
     * @param result
     */
    @Deprecated
    private void productFromat(ProductDto productDto, JSONObject result) {
        Map<String,Object> mapAttr = (Map<String,Object>) result.getObject("value",ArrayList.class).get(0);
        ProductFormatDto productFormatDto = ProductFormatDto.builder()
                .pic(mapAttr.get("pic").toString())
                .price(Double.valueOf(mapAttr.get("price").toString()))
                .cost(Double.valueOf(mapAttr.get("cost").toString()))
                .otPrice(Double.valueOf(mapAttr.get("otPrice").toString()))
                .stock(Integer.valueOf(mapAttr.get("stock").toString()))
                .barCode(mapAttr.get("barCode").toString())
                .weight(Double.valueOf(mapAttr.get("weight").toString()))
                .volume(Double.valueOf(mapAttr.get("volume").toString()))
                .value1(mapAttr.get("value1").toString())
                .integral(mapAttr.get("integral") !=null ? Integer.valueOf(mapAttr.get("integral").toString()) : 0)
                .brokerage(Double.valueOf(mapAttr.get("brokerage").toString()))
                .brokerageTwo(Double.valueOf(mapAttr.get("brokerageTwo").toString()))
                .pinkPrice(Double.valueOf(mapAttr.get("pinkPrice").toString()))
                .pinkStock(Integer.valueOf(mapAttr.get("pinkStock").toString()))
                .seckillPrice(Double.valueOf(mapAttr.get("seckillPrice").toString()))
                .seckillStock(Integer.valueOf(mapAttr.get("seckillStock").toString()))
                .build();
        productDto.setAttr(productFormatDto);
    }


    /**
     * 计算产品数据
     *
     * @param attrs attrs
     * @return ProductResultDto
     */
    private ProductResultDto computedProduct(List<ProductFormatDto> attrs) {
        //取最小价格
        Double minPrice = attrs
                .stream()
                .map(ProductFormatDto::getPrice)
                .min(Comparator.naturalOrder())
                .orElse(0d);

        //取最小积分
        Integer minIntegral = attrs
                .stream()
                .map(ProductFormatDto::getIntegral)
                .min(Comparator.naturalOrder())
                .orElse(0);

        Double minOtPrice = attrs
                .stream()
                .map(ProductFormatDto::getOtPrice)
                .min(Comparator.naturalOrder())
                .orElse(0d);

        Double minCost = attrs
                .stream()
                .map(ProductFormatDto::getCost)
                .min(Comparator.naturalOrder())
                .orElse(0d);
        //计算库存
        Integer stock = attrs
                .stream()
                .map(ProductFormatDto::getStock)
                .reduce(Integer::sum)
                .orElse(0);

        if (stock <= 0) {
            throw exception(STORE_PRODUCT_STOCK_ERROR);
        }

        return ProductResultDto.builder()
                .minPrice(minPrice)
                .minOtPrice(minOtPrice)
                .minCost(minCost)
                .stock(stock)
                .minIntegral(minIntegral)
                .build();
    }




}
