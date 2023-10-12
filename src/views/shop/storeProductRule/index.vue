<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <add />
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" />
        <el-table-column v-if="columns.visible('ruleName')" prop="ruleName" label="规格名称" />
        <el-table-column v-if="columns.visible('ruleValue')" prop="ruleValue" label="规格值" >
          <template slot-scope="scope">
            <div v-for="(item, index) in scope.row.ruleValue">
              {{ item.value }} : {{ item.detail.join(',')}}
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','yxStoreProductRule:edit','yxStoreProductRule:del']" label="操作" width="150px" align="center">
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
import crudYxStoreProductRule from '@/api/storeProductRule'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import MaterialList from "@/components/material"
import add from './form'

// crud交由presenter持有
const defaultCrud = CRUD({ title: 'sku规则', url: 'api/yxStoreProductRule', sort: 'id,desc', crudMethod: { ...crudYxStoreProductRule }})
const defaultForm = { id: null, ruleName: null, ruleValue: null, createTime: null, updateTime: null, isDel: null }
export default {
  name: 'YxStoreProductRule',
  components: { pagination, crudOperation, rrOperation, udOperation ,MaterialList,add},
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
        permission: {
          add: ['admin', 'yxStoreProductRule:add'],
          edit: ['admin', 'yxStoreProductRule:edit'],
          del: ['admin', 'yxStoreProductRule:del']
        }
      }
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
