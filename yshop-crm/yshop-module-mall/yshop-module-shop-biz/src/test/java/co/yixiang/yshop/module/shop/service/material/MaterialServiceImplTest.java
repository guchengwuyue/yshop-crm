package co.yixiang.yshop.module.shop.service.material;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import co.yixiang.yshop.framework.test.core.ut.BaseDbUnitTest;

import co.yixiang.yshop.module.shop.controller.admin.material.vo.*;
import co.yixiang.yshop.module.shop.dal.dataobject.material.MaterialDO;
import co.yixiang.yshop.module.shop.dal.mysql.material.MaterialMapper;
import co.yixiang.yshop.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static co.yixiang.yshop.module.shop.enums.ErrorCodeConstants.*;
import static co.yixiang.yshop.framework.test.core.util.AssertUtils.*;
import static co.yixiang.yshop.framework.test.core.util.RandomUtils.*;
import static co.yixiang.yshop.framework.common.util.date.LocalDateTimeUtils.*;
import static co.yixiang.yshop.framework.common.util.object.ObjectUtils.*;
import static co.yixiang.yshop.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link MaterialServiceImpl} 的单元测试类
*
* @author yshop
*/
@Import(MaterialServiceImpl.class)
public class MaterialServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MaterialServiceImpl materialService;

    @Resource
    private MaterialMapper materialMapper;

    @Test
    public void testCreateMaterial_success() {
        // 准备参数
        MaterialCreateReqVO reqVO = randomPojo(MaterialCreateReqVO.class);

        // 调用
        Long materialId = materialService.createMaterial(reqVO);
        // 断言
        assertNotNull(materialId);
        // 校验记录的属性是否正确
        MaterialDO material = materialMapper.selectById(materialId);
        assertPojoEquals(reqVO, material);
    }

    @Test
    public void testUpdateMaterial_success() {
        // mock 数据
        MaterialDO dbMaterial = randomPojo(MaterialDO.class);
        materialMapper.insert(dbMaterial);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MaterialUpdateReqVO reqVO = randomPojo(MaterialUpdateReqVO.class, o -> {
            o.setId(dbMaterial.getId()); // 设置更新的 ID
        });

        // 调用
        materialService.updateMaterial(reqVO);
        // 校验是否更新正确
        MaterialDO material = materialMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, material);
    }

    @Test
    public void testUpdateMaterial_notExists() {
        // 准备参数
        MaterialUpdateReqVO reqVO = randomPojo(MaterialUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> materialService.updateMaterial(reqVO), MATERIAL_NOT_EXISTS);
    }

    @Test
    public void testDeleteMaterial_success() {
        // mock 数据
        MaterialDO dbMaterial = randomPojo(MaterialDO.class);
        materialMapper.insert(dbMaterial);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbMaterial.getId();

        // 调用
        materialService.deleteMaterial(id);
       // 校验数据不存在了
       assertNull(materialMapper.selectById(id));
    }

    @Test
    public void testDeleteMaterial_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> materialService.deleteMaterial(id), MATERIAL_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMaterialPage() {
       // mock 数据
       MaterialDO dbMaterial = randomPojo(MaterialDO.class, o -> { // 等会查询到
           o.setCreateTime(null);
           o.setType(null);
           o.setGroupId(null);
           o.setName(null);
           o.setUrl(null);
       });
       materialMapper.insert(dbMaterial);
       // 测试 createTime 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setCreateTime(null)));
       // 测试 type 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setType(null)));
       // 测试 groupId 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setGroupId(null)));
       // 测试 name 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setName(null)));
       // 测试 url 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setUrl(null)));
       // 准备参数
       MaterialPageReqVO reqVO = new MaterialPageReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setType(null);
       reqVO.setGroupId(null);
       reqVO.setName(null);
       reqVO.setUrl(null);

       // 调用
       PageResult<MaterialDO> pageResult = materialService.getMaterialPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMaterial, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMaterialList() {
       // mock 数据
       MaterialDO dbMaterial = randomPojo(MaterialDO.class, o -> { // 等会查询到
           o.setCreateTime(null);
           o.setType(null);
           o.setGroupId(null);
           o.setName(null);
           o.setUrl(null);
       });
       materialMapper.insert(dbMaterial);
       // 测试 createTime 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setCreateTime(null)));
       // 测试 type 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setType(null)));
       // 测试 groupId 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setGroupId(null)));
       // 测试 name 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setName(null)));
       // 测试 url 不匹配
       materialMapper.insert(cloneIgnoreId(dbMaterial, o -> o.setUrl(null)));
       // 准备参数
       MaterialExportReqVO reqVO = new MaterialExportReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setType(null);
       reqVO.setGroupId(null);
       reqVO.setName(null);
       reqVO.setUrl(null);

       // 调用
       List<MaterialDO> list = materialService.getMaterialList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbMaterial, list.get(0));
    }

}
