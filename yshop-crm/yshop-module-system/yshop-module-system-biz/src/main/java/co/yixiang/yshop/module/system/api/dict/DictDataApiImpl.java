package co.yixiang.yshop.module.system.api.dict;

import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.system.api.dict.dto.DictDataRespDTO;
import co.yixiang.yshop.module.system.dal.dataobject.dict.DictDataDO;
import co.yixiang.yshop.module.system.service.dict.DictDataService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 字典数据 API 实现类
 *
 * @author yshop
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
    }

    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        DictDataDO dictData = dictDataService.getDictData(dictType, value);
        return BeanUtils.toBean(dictData, DictDataRespDTO.class);
    }

    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        DictDataDO dictData = dictDataService.parseDictData(dictType, label);
        return BeanUtils.toBean(dictData, DictDataRespDTO.class);
    }

    @Override
    public List<DictDataRespDTO> getDictDataList(String dictType) {
        List<DictDataDO> list = dictDataService.getDictDataListByDictType(dictType);
        return BeanUtils.toBean(list, DictDataRespDTO.class);
    }

}
