<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
          <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
        </el-select>
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" width="75px" />
        <el-table-column v-if="columns.visible('nickname')" prop="nickname" label="昵称" width="100px" />
        <el-table-column v-if="columns.visible('orderId')" prop="orderId" label="订单号" />
        <el-table-column v-if="columns.visible('price')" prop="price" label="充值金额" width="100px" />
        <el-table-column v-if="columns.visible('rechargeType')" prop="rechargeType" label="充值类型" width="100px" />
        <el-table-column v-if="columns.visible('paid')" prop="paid" label="是否支付" width="100px">
          <template slot-scope="scope">
            <span v-if="scope.row.paid == 1">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('payTime')" prop="payTime" label="支付时间" >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.payTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('addTime')" prop="addTime" label="充值时间" >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudYxUserRecharge from '@/api/yxUserRecharge'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '充值管理', url: 'api/yxUserRecharge', sort: 'id,desc', crudMethod: { ...crudYxUserRecharge }, optShow: { add: false, edit: false, del: true, download: true}})
const defaultForm = { id: null, uid: null, orderId: null, price: null, rechargeType: null, paid: null, payTime: null, addTime: null, refundPrice: null, nickname: null }
export default {
  name: 'YxUserRecharge',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'yxUserRecharge:add'],
        edit: ['admin', 'yxUserRecharge:edit'],
        del: ['admin', 'yxUserRecharge:del']
      },
      rules: {
      },
      queryTypeOptions: [
        { key: 'nickname', display_name: '昵称' }
      ]
    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      const query = this.query
      if (query.type && query.value) {
        this.crud.params[query.type] = query.value
      }
      return true
    }
  }
}
</script>

<style scoped>

</style>
