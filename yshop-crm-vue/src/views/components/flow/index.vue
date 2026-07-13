<script lang="ts" setup>
import { formatDate } from '@/utils/formatTime'

const props = defineProps({
  flowAdmins: [],
})
</script>

<template>
  <div>
    <el-timeline>
      <el-timeline-item v-for="(item,index) in flowAdmins" :key="index">
        <template #dot>
          <div style="display:flex;flex-direction:column;">
            <div style="text-align:center">第{{item.relation}}级</div>
            <div v-if="item.type == 2"><el-tag type="danger">1人或签</el-tag></div>
            <div v-else-if="item.type == 3"><el-tag type="danger">{{item.userRespVOList.length}}人会签</el-tag></div>
            <div v-if="item.status >= 0">
              <span v-if="item.status == 0" style="display:flex;align-items: center;"><Icon icon="ep:question-filled" color="#409EFF" class="mr-2px" />待审核</span>
              <span v-if="item.status == 1" style="display:flex;align-items: center;"><Icon icon="ep:success-filled" color="#67C23A" class="mr-2px" />审核通过</span>
              <span v-if="item.status == 2" style="display:flex;align-items: center;"><Icon icon="ep:circle-close-filled" color="#F56C6C" class="mr-2px" />审核拒绝</span>
              <span v-if="item.status == 5" style="display:flex;align-items: center;"><Icon icon="ep:help-filled" color="#F56C6C" class="mr-2px" />审核中</span>
            </div>
          </div>
        </template>
        <div style="padding-left:70px;display:flex;">
          <div style="display:flex;flex-direction:column;margin-right:10px" v-for="(user,index2) in item.userRespVOList" :key="index2">
            <div style="display:flex;justify-content: center;align-items: center;"><el-avatar :size="50" :src="user.avatar" /></div>
            <div style="text-align:center">{{user.nickname}}</div>
            <div style="display:flex;justify-content: center;align-items: center;">
              <Icon v-if="user.flowCheckStatus == 0" icon="ep:question-filled" color="#409EFF" class="mr-2px" />
              <Icon v-if="user.flowCheckStatus == 1" icon="ep:success-filled" color="#67C23A" class="mr-2px" />
              <Icon v-if="user.flowCheckStatus == 2" icon="ep:circle-close-filled" color="#F56C6C" class="mr-2px" />
              {{formatDate(user.flowCheckTime)}}
            </div>
            <div v-if="user.flowCheckRemark" style="display:flex;align-items: center;flex-wrap: wrap;background-color:#f7f8fa;border-radius: 5px"><span style="width:180px;padding:10px 10px">{{user.flowCheckRemark}}</span></div>
          </div>
        </div>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<style  scoped>
   div:deep(.el-timeline-item__tail) {
      display: none !important ;
    }
</style>
