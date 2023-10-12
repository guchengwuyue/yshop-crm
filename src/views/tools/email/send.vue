<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;margin-bottom:46px" size="small" label-width="100px">
      <el-form-item label="邮件标题" prop="subject">
        <el-input v-model="form.subject" style="width: 646px"/>
      </el-form-item>
      <el-form-item
          v-for="(domain, index) in tos"
          :key="domain.key"
          :label="'收件邮箱' + (index === 0 ? '': index)"
      >
        <el-input v-model="domain.value" style="width: 550px"/>
        <el-button icon="el-icon-plus" @click="addDomain"/>
        <el-button style="margin-left:0;" icon="el-icon-minus" @click.prevent="removeDomain(domain)"/>
      </el-form-item>
      <el-form-item label="邮件内容">
        <ueditor-wrap v-model="form.content" :config="myConfig" @beforeInit="addCustomDialog"
                      style="width: 90%;"></ueditor-wrap>
      </el-form-item>

      <!-- <div ref="editor" class="editor"/> -->
      <el-form-item label=""> 
      <el-button :loading="loading"  size="medium" type="primary" @click="doSubmit">发送邮件
      </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {send} from '@/api/tools/email'
import {upload} from '@/utils/upload'
import {validEmail} from '@/utils/validate'
import {mapGetters} from 'vuex'
import UeditorWrap from 'vue-ueditor-wrap';

export default {
  name: 'Index',
  data() {
    return {
      myConfig: {
        autoHeightEnabled: false, // 编辑器不自动被内容撑高
        initialFrameHeight: 500, // 初始容器高度
        initialFrameWidth: '100%', // 初始容器宽度
        UEDITOR_HOME_URL: '/UEditor/',
        serverUrl: ''
      },
      loading: false, form: {subject: '', tos: [], content: ''},
      tos: [{
        value: ''
      }],
      rules: {
        subject: [
          {required: true, message: '标题不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  components: {
    UeditorWrap
  },
  computed: {
    ...mapGetters([
      'imagesUploadApi'
    ])
  },
  mounted() {
    const _this = this
    var editor = new E(this.$refs.editor)
    // 自定义菜单配置
    editor.customConfig.zIndex = 10
    // 文件上传
    editor.customConfig.customUploadImg = function (files, insert) {
      // files 是 input 中选中的文件列表
      // insert 是获取图片 url 后，插入到编辑器的方法
      files.forEach(image => {
        files.forEach(image => {
          upload(_this.imagesUploadApi, image).then(data => {
            insert(data.data.url)
          })
        })
      })
    }
    editor.customConfig.onchange = (html) => {
      this.form.content = html
    }
    editor.create()
  },
  methods: {
    addCustomDialog () {
      window.UE.registerUI('yshop', function (editor, uiName) {
        let dialog = new window.UE.ui.Dialog({
          iframeUrl: '/yshop/materia/index',
          editor: editor,
          name: uiName,
          title: '上传图片',
          cssRules: 'width:1200px;height:500px;padding:20px;'
        });
        this.dialog = dialog;

        var btn = new window.UE.ui.Button({
          name: 'dialog-button',
          title: '上传图片',
          cssRules: `background-image: url(../../../assets/images/icons.png);background-position: -726px -77px;`,
          onclick: function () {
            dialog.render();
            dialog.open();
          }
        });

        return btn;
      }, 37);
    },
    removeDomain(item) {
      var index = this.tos.indexOf(item)
      if (index !== -1 && this.tos.length !== 1) {
        this.tos.splice(index, 1)
      } else {
        this.$message({
          message: '请至少保留一位联系人',
          type: 'warning'
        })
      }
    },
    addDomain() {
      this.tos.push({
        value: '',
        key: Date.now()
      })
    },
    doSubmit() {
      const _this = this
      this.$refs['form'].validate((valid) => {
        this.form.tos = []
        if (valid) {
          let sub = false
          this.tos.forEach(function (data, index) {
            if (data.value === '') {
              _this.$message({
                message: '收件邮箱不能为空',
                type: 'warning'
              })
              sub = true
            } else if (validEmail(data.value)) {
              _this.form.tos.push(data.value)
            } else {
              _this.$message({
                message: '收件邮箱格式错误',
                type: 'warning'
              })
              sub = true
            }
          })
          if (sub) {
            return false
          }
          this.loading = true
          send(this.form).then(res => {
            this.$notify({
              title: '发送成功',
              type: 'success',
              duration: 2500
            })
            this.loading = false
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
.editor {
  text-align: left;
  margin: 20px;
  width: 730px;
}

::v-deep .w-e-text-container {
  height: 360px !important;
}
</style>
