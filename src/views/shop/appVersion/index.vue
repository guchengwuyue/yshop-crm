<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission"/>
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0"
                 :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="版本名称">
            <el-input v-model="form.versionName" style="width: 370px;"/>
          </el-form-item>
          <el-form-item label="版本描述">
            <el-input v-model="form.versionInfo" style="width: 370px;"/>
          </el-form-item>
          <el-form-item label="版本code">
            <el-input v-model="form.versionCode" style="width: 370px;"/>
          </el-form-item>
          <el-form-item label="ios应用商店链接">
            <el-input v-model="form.iosUrl" style="width: 370px;"/>
          </el-form-item>
          <el-form-item label="安卓下载链接">
            <el-input v-model="form.androidUrl" style="width: 370px;"/>
          </el-form-item>
          <el-form-item label="是否强制升级">
            <el-radio v-for="item in dict.force_update" :key="item.id" v-model="form.forceUpdate" :label="item.value">
              {{ item.label }}
            </el-radio>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;"
                @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55"/>
        <el-table-column v-if="columns.visible('versionName')" prop="versionName" label="版本名称"/>
        <el-table-column v-if="columns.visible('versionInfo')" prop="versionInfo" label="版本描述"/>
        <el-table-column v-if="columns.visible('versionCode')" prop="versionCode" label="版本code"/>
        <el-table-column v-if="columns.visible('iosUrl')" prop="iosUrl" label="ios store应用商店链接"/>
        <el-table-column v-if="columns.visible('androidUrl')" prop="androidUrl" label="安卓下载链接"/>
        <el-table-column v-if="columns.visible('forceUpdate')" prop="forceUpdate" label="是否强制升级">
          <template slot-scope="scope">
            {{ dict.label.force_update[scope.row.forceUpdate] }}
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','yxAppVersion:edit','yxAppVersion:del']" label="操作" width="150px"
                         align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination/>
    </div>
  </div>
</template>

<script>
import crudYxAppVersion from '@/api/yxAppVersion'
import CRUD, {presenter, header, form, crud} from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import MaterialList from "@/components/material";

// crud交由presenter持有
const defaultCrud = CRUD({
  title: 'app版本控制',
  url: 'api/yxAppVersion',
  sort: 'id,desc',
  crudMethod: {...crudYxAppVersion}
})
const defaultForm = {
  id: null,
  isDel: null,
  createTime: null,
  updateTime: null,
  versionCode: null,
  versionName: null,
  versionInfo: null,
  iosUrl: null,
  androidUrl: null,
  forceUpdate: null
}
export default {
  name: 'YxAppVersion',
  components: {pagination, crudOperation, rrOperation, udOperation, MaterialList},
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  dicts: ['force_update'],
  data() {
    return {

      permission: {
        add: ['admin', 'yxAppVersion:add'],
        edit: ['admin', 'yxAppVersion:edit'],
        del: ['admin', 'yxAppVersion:del']
      },
      rules: {}
    }
  },
  watch: {},
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
