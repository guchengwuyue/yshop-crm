<template>
  <div>
    <div ref="editor" style="text-align:left;margin: 5px;width: 700px" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import E from 'wangeditor'
import { getToken } from '@/utils/auth'
export default {
  name: 'Editor',
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    value: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      headers: {
        'Authorization': getToken()
      },
      info: null,
      editor: null
    }
  },
  computed: {
    ...mapGetters([
      'uploadApi'
    ])
  },
  watch: {
    value: function(val) {
      this.editor.txt.html(val)
      // this.editor.txt.html(val)
    }
  },
  mounted() {
    this.editor = new E(this.$refs.editor)
    this.editor.customConfig = this.editor.customConfig ? this.editor.customConfig : this.editor.config
    this.editor.customConfig.uploadImgShowBase64 = true // 使用 base64 保存图片
    // 不可修改
    this.editor.customConfig.uploadImgHeaders = this.headers
    // 自定义文件名，不可修改，修改后会上传失败
    this.editor.customConfig.uploadFileName = 'file'
    this.editor.customConfig.uploadImgServer = this.uploadApi // 上传图片到服务器
    this.editor.customConfig.uploadImgHooks = {
      customInsert: function(insertImg, result, editor) {
        var url = result.link
        insertImg(url)
      }
    }
    this.editor.customConfig.onchange = (html) => {
      this.info = html
      this.$emit('change', this.info)
      this.$emit('input', this.info)
    }
    this.editor.create()
    this.editor.txt.html(this.value)
  }
}
</script>

<style scoped>
  .editor-content{
    padding-left: 5px;
  }
</style>
