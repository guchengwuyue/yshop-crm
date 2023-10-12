<template>
  <div class="videoTool">
    <h3 class="toolTit">视频</h3>
    <div class="toolBox">
      <div class="itemBox">
        <label>视频地址</label>
        <el-input v-model="activeComponent.componentContent.videoUrl" placeholder="请输入内容"></el-input>
      </div>
<!--      <div class="itemBox">-->
<!--        <label>文本</label>-->
<!--        <el-input-->
<!--          type="textarea"-->
<!--          :rows="2"-->
<!--          placeholder="请输入内容"-->
<!--          resize="none"-->
<!--          v-model="textInfo">-->
<!--        </el-input>-->
<!--      </div>-->
      <div class="itemBox">
        <label>标题</label>
        <el-input v-model="activeComponent.componentContent.title" placeholder="请输入内容"></el-input>
      </div>
      <div class="itemBox">
        <label>正文</label>
        <quill-editor
          v-model="activeComponent.componentContent.mainBody"
          ref="myQuillEditor"
          :options="editorOption"
          @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
          @change="onEditorChange($event)">
        </quill-editor>
      </div>
    </div>
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
import { toolMixin } from '@/mixins/tool.js'
export default {
  mixins: [toolMixin],
  name: 'videoTool',
  components: {
    quillEditor
  },
  data() {
    return {
      editorOption: {
        placeholder: '请输入',
        modules: {
          toolbar: [
            ['bold', 'italic', 'link'] // toggled buttons
          ]
        }
      }
    }
  },
  methods: {
    onEditorBlur() { // 失去焦点事件
    },
    onEditorFocus() { // 获得焦点事件
    },
    onEditorChange() { // 内容改变事件
      console.log(this.mainBody)
    }
  }
}
</script>

<style lang="scss" scoped>
.videoTool {
  padding: 20px 20px 0 20px;
  h3 {
    font-size: 18px;
    font-weight: 500;
    height: 35px;
    line-height: 35px;
    color: #333333;
    margin-bottom: 20px;
  }
  .toolBox {
    padding-bottom: 10px;
    .itemBox {
      label {
        font-size: 14px;
        color: #666666;
        height: 40px;
        line-height: 40px;
      }
      margin-bottom: 15px;
    }
  }
  >>> .ql-container {
    height: 200px;
  }
}
</style>
