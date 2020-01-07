<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.cateName" clearable size="small" placeholder="输入部门名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <crudOperation :permission="permission" />
    </div>
    <!--表单组件-->
    <el-dialog append-to-body :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="form.cateName" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="分类图片">
          <pic-upload v-model="form.pic" style="width: 500px;" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.isShow" style="width: 178px">
            <el-radio :label="1">显示</el-radio>
            <el-radio :label="0">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;" label="上级分类" prop="pid">
          <treeselect v-model="form.pid" :options="depts" style="width: 370px;" placeholder="选择上级分类" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
    <!--表格渲染-->
    <el-table ref="table" v-loading="crud.loading" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" default-expand-all :data="crud.data" row-key="id" @select="crud.selectChange" @select-all="crud.selectAllChange" @selection-change="crud.selectionChangeHandler">
      <el-table-column :selectable="checkboxT" type="selection" width="55" />
      <el-table-column v-if="columns.visible('cateName')" label="名称" prop="cateName" />
      <el-table-column v-if="columns.visible('isShow')" label="状态" align="center" prop="isShow">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.isShow === 1" :type="''">显示</el-tag>
            <el-tag v-else :type=" 'info' ">隐藏</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.visible('sort')" label="排序" prop="sort" />
      <el-table-column v-permission="['admin','cate:edit','cate:del']" label="操作" width="130px" align="center" fixed="right">
        <template slot-scope="scope">
          <udOperation
            :data="scope.row"
            :permission="permission"
            :disabled-dle="scope.row.id === 1"
            msg="确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！"
          />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import crudDept from '@/api/yxStoreCategory'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import picUpload from '@/components/pic-upload'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '分类', url: 'api/yxStoreCategory', crudMethod: { ...crudDept }})
const defaultForm = { id: null, cateName: null, pid: 0, isShow: 1 }
export default {
  name: 'Dept',
  components: { Treeselect, crudOperation, rrOperation, udOperation, picUpload },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      depts: [],
      rules: {
        cateName: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      permission: {
        add: ['admin', 'cate:add'],
        edit: ['admin', 'cate:edit'],
        del: ['admin', 'cate:del']
      },
      enabledTypeOptions: [
        { key: 'true', display_name: '正常' },
        { key: 'false', display_name: '禁用' }
      ]
    }
  },
  methods: {
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      // 获取所有部门
      crudDept.getCates({ isShow: true }).then(res => {
        this.depts = []
        const dept = { id: 0, label: '顶级类目', children: [] }
        dept.children = res.content
        this.depts.push(dept)
      })
    },
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      return true
    },
    checkboxT(row, rowIndex) {
      return row.id !== 1
    }
  }
}
</script>

<style scoped>
</style>
