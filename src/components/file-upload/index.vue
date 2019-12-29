<template>
  <div>
    <el-upload
      :action="fileUploadApi"
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
  data () {
    return {
      resourcesUrl: '',
      headers: {
        'Authorization': 'Bearer ' + getToken()
      }
    }
  },
  props: {
    value: {
      default: '',
      type: String
    }
  },
  computed: {
    ...mapGetters([
      'fileUploadApi'
    ]),
    fileList () {
      let res = []
      if (this.value) {
        // let fileArray = this.value.split(',')
        // for (let i = 0; i < fileArray.length; i++) {
        //   res.push({name: fileArray[i], url: this.resourcesUrl + fileArray[i], response: fileArray[i]})
        // }
        console.log("aaa"+this.value)
        res.push({name: this.value, url: this.resourcesUrl + this.value, response: this.value})
      }
      this.$emit('input', this.value)
      console.log("res"+res)
      return res
    }
  },
  methods: {
    // 图片上传
    handleUploadSuccess (response, file, fileList) {
      this.$emit('input', file.response.data[0])
    },
    handleRemove (file, fileList) {
      this.$emit('change', file)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}
</script>

<style lang="scss">
</style>
