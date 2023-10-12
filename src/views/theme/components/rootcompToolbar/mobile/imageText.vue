<template>
  <div class="imageTextTool">
    <h3 class="toolTit">图文</h3>
    <div class="toolBox">
      <tool-single-img :imageUrl.sync='activeComponent.componentContent.imageUrl'></tool-single-img>
      <tool-select-link :linkObj.sync='activeComponent.componentContent.linkObj' title="图片链接"></tool-select-link>
      <tool-select :linkValue.sync='activeComponent.componentContent.positionValue' title="PC图片位置" :options="positionOpt"></tool-select>
      <div class="textTit">文本设置</div>
      <div class="itemBox">
        <label>标题</label>
        <el-input v-model="activeComponent.componentContent.title" placeholder="请输入内容"></el-input>
      </div>
      <div class="itemBox">
        <label>正文</label>
        <quill-editor
          v-model="activeComponent.componentContent.content"
          ref="myQuillEditor"
          :options="editorOption"
          @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
          @change="onEditorChange($event)">
        </quill-editor>
      </div>
    </div>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
import { toolMixin } from '@/mixins/tool.js'
import ToolSelectLink from '@/views/theme/components/rootcompToolbar/toolModule/tool-select-link.vue'
import ToolSingleImg from '@/views/theme/components/rootcompToolbar/toolModule/tool-single-img.vue'
import ToolSelect from '@/views/theme/components/rootcompToolbar/toolModule/tool-select.vue'
export default {
  name: 'videoTool',
  mixins: [toolMixin],
  components: {
    ToolSelectLink,
    ToolSelect,
    ToolSingleImg,
    quillEditor
  },
  data () {
    return {
      // imageUrl: '',
      // title: '', // 标题内容
      // linkValue: '',
      // positionValue: '',
      // content: null,
      dialogVisible: false,
      dialogImageUrl: '',
      linkOptions: [
        {
          value: '/index',
          label: '首页'
        },
        {
          value: '/list',
          label: '列表页'
        },
        {
          value: '/detail',
          label: '详情页'
        },
        {
          value: '/about',
          label: '关于我们'
        }
      ],
      positionOpt: [
        {
          value: 'left',
          label: '左图右文'
        },
        {
          value: 'right',
          label: '右图左文'
        }
        // {
        //   value: 'top',
        //   label: '上图下文'
        // },
        // {
        //   value: 'bottom',
        //   label: '下图上文'
        // }
      ],
      editorOption: {
        placeholder: '请输入',
        modules: {
          toolbar: [
            ['bold', 'italic', 'link'] // toggled buttons
          ]
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.imageTextTool {
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
    .textTit {
      height: 35px;
      line-height: 35px;
      font-size: 16px;
      color: #333333;
      margin-top: 30px;
      font-weight: bold;
    }
    .itemBox {
      label {
        font-size: 14px;
        color: #666666;
        height: 40px;
        line-height: 40px;
      }
      margin-bottom: 15px;
      >>> .el-select {
        display: block;
      }
    }
    .imgBox{
      position: relative;
      .overlay{
        display: none;
      }
      &:hover .overlay{
        position: absolute;
        top:0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.3);
        color: #fff;
        display: flex;
        justify-content: center;
        align-items: center;
        .iconfont{
          padding: 5px;
          margin: 0 5px;
          font-size: 20px;
          cursor: pointer;
        }
      }

    }
  }
  >>> .ql-container {
    height: 150px;
  }
  >>> .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  >>> .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  >>> .el-upload__tip {
    height: auto;
    margin-top: 0;
    line-height: normal;
  }
  >>> .el-upload {
    width: 100%;
  }
  >>> .el-upload-dragger {
    width: 100%;
    position: relative;
    .avatar{
      max-width: 100%;
      height: 100%;
      max-height: 100%;
      position: absolute;
      margin: auto;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
    }
  }
  >>> .ql-snow .ql-tooltip{
    z-index: 8;
    left: 5px!important;
  }
  >>> .ql-snow .ql-tooltip::before{
    content: '链接';
  }
  >>> .ql-snow .ql-tooltip a.ql-action::after{
    content: '编辑';
  }
  >>> .ql-snow .ql-tooltip a.ql-remove::before{
    content: '移除';
  }
  >>> .ql-snow .ql-tooltip[data-mode=link]::before{
    content: '链接';
  }
  >>> .ql-snow .ql-tooltip.ql-editing a.ql-action::after{
    content: '确定';
  }
}
</style>
