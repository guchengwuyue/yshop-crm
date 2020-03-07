<template>
  <div>
    <el-upload
      :action="uploadApi"
      :headers="headers"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :before-remove="beforeRemove"
      :file-list="fileList"
      multiple
      :limit="1"
    >
      <el-button size="small" type="primary">点击上传</el-button>
    </el-upload>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
export default {
  props: {
    value: {
      default: '',
      type: String
    }
  },
  data() {
    return {
      resourcesUrl: '',
      headers: {
        'Authorization': getToken()
      }
    }
  },
  computed: {
    ...mapGetters([
      'uploadApi'
    ]),
    fileList() {
      const res = []
      if (this.value) {
        res.push({ name: this.value, url: this.resourcesUrl + this.value, response: this.value })
      }
      this.$emit('input', this.value)
      console.log('res' + res)
      return res
    }
  },
  methods: {
    // 图片上传
    handleUploadSuccess(response, file, fileList) {
      this.$emit('input', file.response.link)
    },
    handleRemove(file, fileList) {
      this.$emit('change', file)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}
</script>

<style lang="scss">
</style>
