<template>
  <div>
    <el-upload
      :action="qiNiuUploadApi"
      :headers="headers"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :file-list="imageList"
      :before-upload="beforeAvatarUpload"
    >
      <i class="el-icon-plus" />
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
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
      dialogImageUrl: '',
      dialogVisible: false,
      resourcesUrl: '',
      headers: {
        'Authorization': getToken()
      }
    }
  },
  computed: {
    ...mapGetters([
      'qiNiuUploadApi'
    ]),
    imageList() {
      const res = []
      if (this.value) {
        const imageArray = this.value.split(',')
        for (let i = 0; i < imageArray.length; i++) {
          res.push({ url: this.resourcesUrl + imageArray[i], response: imageArray[i] })
        }
      }
      this.$emit('input', this.value)
      return res
    }
  },
  methods: {
    // 图片上传
    handleUploadSuccess(response, file, fileList) {
      console.log(file)
      console.log(fileList)
      const pics = fileList.map(file => {
        if ((typeof file.response) === 'object') {
          return file.response.data[0]
        } else {
          return file.response
        }
      }).join(',')
      console.log(pics)
      this.$emit('input', pics)
    },
    // 限制图片上传大小
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    },
    handleRemove(file, fileList) {
      const pics = fileList.map(file => {
        return file.response
      }).join(',')
      this.$emit('input', pics)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    }
  }
}
</script>

<style lang="scss">
</style>
