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
          <el-form-item label="商城用户" prop="uid">
            <cuser v-model="form.user"></cuser>
          </el-form-item>
          <el-form-item label="所属门店" prop="storeId">
            <el-select v-model="form.storeId" style="width: 178px" placeholder="请先选择门店">
              <el-option
                v-for="(item, index) in mystores"
                :key="item.name + index"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="店员名称" prop="staffName">
            <el-input v-model="form.staffName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="核销开关" prop="verifyStatus">
            <el-radio-group v-model="form.verifyStatus" style="width: 178px">
              <el-radio :label="1">开启</el-radio>
              <el-radio :label="0">关闭</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" width="55" />
        <el-table-column v-if="columns.visible('nickname')" prop="nickname" label="微信昵称" />
        <el-table-column v-if="columns.visible('avatar')" prop="avatar" label="店员头像" >
          <template slot-scope="scope">
            <a :href="scope.row.avatar" style="color: #42b983" target="_blank"><img :src="scope.row.avatar" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('staffName')" prop="staffName" label="店员名称" />
        <el-table-column v-if="columns.visible('storeName')" prop="storeName" label="所属门店" />
        <el-table-column v-if="columns.visible('verifyStatus')" prop="verifyStatus" label="核销开关" >
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.verifyStatus === 1" :type="''">开启</el-tag>
              <el-tag v-else :type=" 'info' ">关闭</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('addTime')" prop="addTime" label="添加时间" >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','yxSystemStoreStaff:edit','yxSystemStoreStaff:del']" label="操作" width="150px" align="center">
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
import crudYxSystemStoreStaff from '@/api/yxSystemStoreStaff'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import cuser from '@/views/components/user'
import crudYxSystemStore from '@/api/yxSystemStore'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '门店店员', url: 'api/yxSystemStoreStaff', sort: 'id,desc', crudMethod: { ...crudYxSystemStoreStaff }})
const defaultForm = { user: {uid: null,nickname: null,avatar: null}, id: null, uid: null, avatar: null, storeId: null, staffName: null, phone: null, verifyStatus: 1, status: null, addTime: null, nickname: null, storeName: null }
export default {
  name: 'YxSystemStoreStaff',
  components: { pagination, crudOperation, rrOperation, udOperation, cuser },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      mystores: [],
      permission: {
        add: ['admin', 'yxSystemStoreStaff:add'],
        edit: ['admin', 'yxSystemStoreStaff:edit'],
        del: ['admin', 'yxSystemStoreStaff:del']
      },
      rules: {
        // uid: [
        //   { required: true, message: '微信用户id不能为空', trigger: 'blur' }
        // ],
        // avatar: [
        //   { required: true, message: '店员头像不能为空', trigger: 'blur' }
        // ],
        // storeId: [
        //   { required: true, message: '门店id不能为空', trigger: 'blur' }
        // ],
        // staffName: [
        //   { required: true, message: '店员名称不能为空', trigger: 'blur' }
        // ],
        // phone: [
        //   { required: true, message: '手机号码不能为空', trigger: 'blur' }
        // ],
        // verifyStatus: [
        //   { required: true, message: '核销开关不能为空', trigger: 'blur' }
        // ],
        // nickname: [
        //   { required: true, message: '微信昵称不能为空', trigger: 'blur' }
        // ]
      },
      queryTypeOptions: [
        { key: 'staffName', display_name: '店员名称' },
        { key: 'nickname', display_name: '微信昵称' }
      ]
    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      const query = this.query
      console.log('query:'+query.value)
      if (query.type && query.value) {
        this.crud.params[query.type] = query.value
      }else{
        delete this.crud.params.staffName
        delete this.crud.params.nickname
      }
      return true
    },
    //新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      crudYxSystemStore.get().then(res => {
         this.mystores= res.content
      })
    },
    // 编辑前
    [CRUD.HOOK.beforeToEdit](crud, form) {
      this.form.user.uid = form.uid
      this.form.user.nickname = form.nickname
      this.form.user.avatar = form.avatar
    },
    [CRUD.HOOK.beforeSubmit]() {
      this.form.uid = this.form.user.uid
      this.form.nickname = this.form.user.nickname
      this.form.avatar = this.form.user.avatar
    }
  }
}
</script>

<style scoped>

</style>
