<template>
  <div v-if="type == 'image'">
    <ul v-for="(item,index) in value" :key="index" class="el-upload-list el-upload-list--picture-card">
      <li tabindex="0" class="el-upload-list__item is-ready" :style="'width: '+width+'px;height: '+height+'px'">
        <div>
          <img :src="item" alt="" class="el-upload-list__item-thumbnail">
          <span class="el-upload-list__item-actions">
            <span v-if="index != 0" class="el-upload-list__item-preview" @click="moveMaterial(index,'up')">
              <i class="el-icon-back" />
            </span>
            <span class="el-upload-list__item-preview" @click="zoomMaterial(index)">
              <i class="el-icon-view" />
            </span>
            <span class="el-upload-list__item-delete" @click="deleteMaterial(index)">
              <i class="el-icon-delete" />
            </span>
            <span v-if="index != value.length-1" class="el-upload-list__item-preview" @click="moveMaterial(index,'down')">
              <i class="el-icon-right" />
            </span>
          </span>
        </div>
      </li>
    </ul>
    <div v-if="num > value.length" tabindex="0" class="el-upload el-upload--picture-card" :style="'width: '+width+'px;height: '+height+'px;'+'line-height:'+height+'px;'" @click="toSeleteMaterial">
      <i class="el-icon-plus" />
    </div>

    <el-dialog
      append-to-body
      :visible.sync="dialogVisible"
      width="35%"
    >
      <img :src="url" alt="" style="width: 100%">
    </el-dialog>

    <el-dialog
      title="图片素材库"
      append-to-body
      :visible.sync="listDialogVisible"
      width="70%"
    >
      <el-container>
        <el-aside width="unset">
          <div style="margin-bottom: 10px">
            <el-button
              class="el-icon-plus"
              size="small"
              @click="materialgroupAdd()"
            >
              添加分组
            </el-button>
          </div>
          <el-tabs v-model="materialgroupObjId" v-loading="materialgroupLoading" tab-position="left" @tab-click="tabClick">
            <el-tab-pane
              v-for="(item,index) in materialgroupList"
              :key="item.id"
              :name="item.id"
            >
              <span slot="label"> {{ item.name }}</span>
            </el-tab-pane>
          </el-tabs>
        </el-aside>
        <el-main>
          <el-card>
            <div slot="header">
              <el-row>
                <el-col :span="12">
                  <span>{{ materialgroupObj.name }}</span>
                  <span v-if="materialgroupObj.id != '-1'">
                    <el-button size="small" type="text" class="el-icon-edit" style="margin-left: 10px;" @click="materialgroupEdit(materialgroupObj)">重命名</el-button>
                    <el-button size="small" type="text" class="el-icon-delete" style="margin-left: 10px;color: red" @click="materialgroupDelete(materialgroupObj)">删除</el-button>
                  </span>
                </el-col>
                <el-col :span="12" style="text-align: right;">
                  <el-upload
                    :action="uploadApi"
                    :headers="headers"
                    :file-list="[]"
                    :on-progress="handleProgress"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess"
                    :data="{type: 1}"
                    multiple
                  >
                    <el-button size="small" type="primary">批量上传</el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </div>
            <div v-loading="tableLoading">
              <el-alert
                v-if="tableData.length <= 0"
                title="暂无数据"
                type="info"
                :closable="false"
                center
                show-icon
              />
              <el-row :gutter="5">
                <el-checkbox-group v-model="urls" :max="num - value.length">
                  <el-col v-for="(item,index) in tableData" :key="index" :span="4">
                    <el-card :body-style="{ padding: '5px' }">
                      <el-image
                        style="width: 100%;height: 100px"
                        :src="item.url"
                        fit="contain"
                        :preview-src-list="[item.url]"
                        :z-index="9999"
                      />
                      <div>
                        <el-checkbox class="material-name" :label="item.url">
                          选择
                        </el-checkbox>
                        <el-row>
                          <el-col :span="24" class="col-do">
                            <el-button type="text" size="medium" @click="materialDel(item)">删除</el-button>
                          </el-col>
                        </el-row>

                      </div>
                    </el-card>
                  </el-col>
                </el-checkbox-group>
              </el-row>
              <el-pagination
                :current-page.sync="page.currentPage"
                :page-sizes="[12, 24]"
                :page-size="page.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="page.total"
                class="pagination"
                @size-change="sizeChange"
                @current-change="pageChange"
              />
            </div>
          </el-card>
        </el-main>
      </el-container>
      <span slot="footer" class="dialog-footer">
        <el-button @click="listDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="sureUrls">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
import { getList as materialgroupPage, addObj as materialgroupAdd, delObj as materialgroupDel, putObj as materialgroupEdit } from '@/api/tools/materialgroup'
import { getPage, addObj, delObj, putObj } from '@/api/tools/material'
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'

export default {
  name: 'MaterialList',
  props: {
    // 素材数据
    value: {
      type: Array,
      default() {
        return []
      }
    },
    // 素材类型
    type: {
      type: String
    },
    // 素材限制数量，默认5个
    num: {
      type: Number,
      default() {
        return 5
      }
    },
    // 宽度
    width: {
      type: Number,
      default() {
        return 150
      }
    },
    // 宽度
    height: {
      type: Number,
      default() {
        return 150
      }
    }
  },
  data() {
    return {
      headers: {
        Authorization: getToken()
      },
      dialogVisible: false,
      url: '',
      listDialogVisible: false,
      materialgroupList: [],
      materialgroupObjId: '',
      materialgroupObj: {},
      materialgroupLoading: false,
      tableData: [],
      resultNumber: 0,
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 12, // 每页显示多少条
        ascs: [], // 升序字段
        descs: 'create_time'// 降序字段
      },
      tableLoading: false,
      groupId: null,
      urls: []
    }
  },
  computed: {
    ...mapGetters([
      'uploadApi'
    ])
  },
  methods: {
    moveMaterial(index, type) {
      if (type == 'up') {
        const tempOption = this.value[index - 1]
        this.$set(this.value, index - 1, this.value[index])
        this.$set(this.value, index, tempOption)
      }
      if (type == 'down') {
        const tempOption = this.value[index + 1]
        this.$set(this.value, index + 1, this.value[index])
        this.$set(this.value, index, tempOption)
      }
    },
    zoomMaterial(index) {
      this.dialogVisible = true
      this.url = this.value[index]
    },
    deleteMaterial(index) {
      const that = this
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        that.value.splice(index, 1)
        that.urls = []
      })
    },
    toSeleteMaterial() {
      this.listDialogVisible = true
      if (this.tableData.length <= 0) {
        this.materialgroupPage()
      }
    },
    materialgroupPage() {
      this.materialgroupLoading = true
      materialgroupPage({
        total: 0, // 总页数
        page: 1, // 当前页数
        size: 100, // 每页显示多少条
        ascs: [], // 升序字段
        sort: 'create_time,desc'// 降序字段
      }).then(response => {
        this.materialgroupLoading = false
        const materialgroupList = response
        materialgroupList.unshift({
          id: '-1',
          name: '全部分组'
        })
        this.materialgroupList = materialgroupList
        this.tabClick({
          index: 0
        })
      })
    },
    materialgroupDelete(materialgroupObj) {
      const that = this
      this.$confirm('是否确认删除该分组？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        materialgroupDel(materialgroupObj.id)
          .then(function() {
            that.$delete(that.materialgroupList, materialgroupObj.index)
          })
      })
    },
    materialgroupEdit(materialgroupObj) {
      const that = this
      this.$prompt('请输入分组名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: materialgroupObj.name
      }).then(({ value }) => {
        materialgroupEdit({
          id: materialgroupObj.id,
          name: value
        }).then(function() {
          materialgroupObj.name = value
          that.$set(that.materialgroupList, materialgroupObj.index, materialgroupObj)
        })
      }).catch(() => {

      })
    },
    materialgroupAdd() {
      const that = this
      this.$prompt('请输入分组名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        materialgroupAdd({
          name: value
        }).then(function() {
          that.materialgroupPage()
        })
      }).catch(() => {

      })
    },
    tabClick(tab, event) {
      this.urls = []
      const index = Number(tab.index)
      const materialgroupObj = this.materialgroupList[index]
      materialgroupObj.index = index
      this.materialgroupObj = materialgroupObj
      this.materialgroupObjId = materialgroupObj.id
      this.page.currentPage = 1
      this.page.total = 0
      if (materialgroupObj.id != '-1') {
        this.groupId = materialgroupObj.id
      } else {
        this.groupId = null
      }
      this.getPage(this.page)
    },
    getPage(page, params) {
      this.tableLoading = true
      getPage(Object.assign({
        page: page.currentPage - 1,
        size: page.pageSize,
        descs: this.page.descs,
        ascs: this.page.ascs,
        sort: 'create_time,desc'
      }, {
        groupId: this.groupId
      })).then(response => {
        const tableData = response.content
        this.page.total = response.totalElements
        this.page.currentPage = page.currentPage
        this.page.pageSize = page.pageSize
        this.tableData = tableData
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    sizeChange(val) {
      console.log(val)
      this.page.currentPage = 1
      this.page.pageSize = val
      this.getPage(this.page)
    },
    pageChange(val) {
      console.log(val)
      this.page.currentPage = val
      // this.page.pageSize = val
      this.getPage(this.page)
    },
    materialRename(item) {
      const that = this
      this.$prompt('请输入素材名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: item.name
      }).then(({ value }) => {
        putObj({
          id: item.id,
          name: value
        }).then(function() {
          that.getPage(that.page)
        })
      }).catch(() => {

      })
    },
    materialUrl(item) {
      const that = this
      this.$prompt('素材链接', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: item.url
      }).then(({ value }) => {

      }).catch(() => {

      })
    },
    materialDel(item) {
      const that = this
      this.$confirm('是否确认删除该素材？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        delObj(item.id)
          .then(function() {
            that.getPage(that.page)
          })
      })
    },
    handleCommand(command) {
      const that = this
      const s = command.split('-')
      putObj({
        id: s[0],
        groupId: s[1]
      }).then(function() {
        that.getPage(that.page)
      })
    },
    handleProgress(event, file, fileList) {
      console.log(event)
      // let uploadProgress = file.percentage.toFixed(0)
      // this.uploadProgress = uploadProgress
    },
    handleSuccess(response, file, fileList) {
      const that = this
      this.uploadProgress = 0
      addObj({
        type: '1',
        groupId: this.groupId != '-1' ? this.groupId : null,
        name: file.name,
        url: response.link
      }).then(() => {
        this.resultNumber++
        if (fileList.length === this.resultNumber) {
          that.getPage(that.page)
          this.resultNumber = 0
        }
      })
    },
    beforeUpload(file) {
      const isPic =
        file.type === 'image/jpeg' ||
        file.type === 'image/png' ||
        file.type === 'image/gif' ||
        file.type === 'image/jpg'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isPic) {
        this.$message.error('上传图片只能是 JPG、JPEG、PNG、GIF 格式!')
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isPic && isLt2M
    },
    sureUrls() {
      this.urls.forEach(item => {
        this.$set(this.value, this.value.length, item)
      })
      this.listDialogVisible = false
    }
  }
}
</script>

<style lang="scss" scoped>
  ::v-deep .el-icon-circle-close{
    color: red;
  }
  .material-name{
    padding: 8px 0px;
  }
  .col-do{
    text-align: center;
  }
  .button-do{
    padding: unset!important;
    font-size: 12px;
  }
</style>

