<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />

      <add />
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="模板ID" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="模板名称" />
        <el-table-column v-if="columns.visible('type')" prop="type" label="计费方式">
          <template slot-scope="scope">
            <span v-if="scope.row.type == 1">按件数</span>
            <span v-else-if="scope.row.type == 2">按重量</span>
            <span v-else>按体积</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('appoint')" prop="appoint" label="指定包邮开关">
          <template slot-scope="scope">
            <span v-if="scope.row.appoint == 1">开启</span>
            <span v-else>关闭</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="添加时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('sort')" prop="sort" label="排序" />
        <el-table-column v-permission="['admin','yxShippingTemplates:edit','yxShippingTemplates:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudYxShippingTemplates from '@/api/yxShippingTemplates'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import MaterialList from "@/components/material";
import add from './form'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '运费模板', url: 'api/yxShippingTemplates', sort: 'id,desc', crudMethod: { ...crudYxShippingTemplates }})
const defaultForm = { id: null, name: null, type: null, regionInfo: null, appoint: null, appointInfo: null, createTime: null, updateTime: null, isDel: null, sort: null }
export default {
  name: 'YxShippingTemplates',
  components: { pagination, crudOperation, rrOperation, udOperation ,MaterialList ,add},
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      
      permission: {
        add: ['admin', 'yxShippingTemplates:add'],
        edit: ['admin', 'yxShippingTemplates:edit'],
        del: ['admin', 'yxShippingTemplates:del']
      },
      rules: {
      }    }
  },
  watch: {
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }, // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
    },
  }
}



</script>

<style scoped>
  .table-img {
    display: inline-block;
    text-align: center;
    background: #ccc;
    color: #fff;
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    vertical-align: middle;
    width: 32px;
    height: 32px;
    line-height: 32px;
  }
</style>
