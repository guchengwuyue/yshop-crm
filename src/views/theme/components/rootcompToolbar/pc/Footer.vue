/**
    编辑器_联系方式组件
*/
<template>
  <div class="editorFooter">
    <h2>联系方式</h2>
    <ul class="editorList">
      <draggable v-model="activeComponent.componentContent.publicList">
        <li
        class="item"
        v-for="(item, index) in activeComponent.componentContent.publicList"
        :key="index"
        >
          <DraggableSlot :obj="item" :index="index" @deleteIt="deleteIt">
            <div class="addContent">
              <ToolSingleImg
              v-if="item.pic !== undefined && item.pic !== null && item.pic !== ''"
              :imageUrl.sync="item.pic" />
              <div class="titleBox">{{ item.title }}</div>
              <el-input
                  placeholder="描述"
                  resize="none"
                  v-model="item.des"
              />
            </div>
          </DraggableSlot>
        </li>
      </draggable>
    </ul>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="deleteItem"
    >
      <span>点击确定删除此项</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog :visible.sync="dialogImageVisible">
        <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script>
import Draggable from 'vuedraggable'
import { toolMixin } from '@/mixins/tool.js'
import ToolSingleImg from '@/views/theme/components/rootcompToolbar/toolModule/tool-single-img.vue'
import DraggableSlot from '@/views/theme/components/rootcompToolbar/pc/components/draggableSlot.vue'
export default {
    name: 'EditorFooter',
    mixins: [toolMixin],
    components: {Draggable, ToolSingleImg, DraggableSlot},
    data () {
        return {
          dialogVisible: false,
          dialogImageVisible: false,
          dialogImageUrl: null
        }
    },
    methods: {
      // 添加图文
      addImgText () {
          this.activeComponent.componentContent.publicList.push({
              'title': '',
              'des': '',
              'linkTo': '',
              'pic': ''
          })
      },
      deleteItem (item, index) {
          this.$confirm('确定删除此项？')
          .then((_) => {
              this.activeComponent.componentContent.publicList.splice(index, 1)
          })
          .catch((_) => {})
      },
      deleteIt (index) {
        this.$message.error('该模块无法删除')
        // this.activeComponent.componentContent.publicList.splice(index, 1)
      }
    }
}
</script>

<style lang="scss">
.editorFooter{
  padding: 8px 12px;
  .editorList{
    .item {
        border: 1px solid #e8eaec;
        border-radius: 4px;
        margin: 10px 0;
    }
    .addImgBtn {
      border-radius: 4px;
      background: $mainColor;
      text-align: center;
      height: 36px;
      color: #ffffff;
      font-size: 14px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      margin-bottom: 30px;
      span {
        font-size: 20px;
        margin-right: 5px;
      }
    }
  }
}
</style>
