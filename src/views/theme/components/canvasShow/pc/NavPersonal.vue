/**
    顶部个人中心组件
*/
<template>
  <div class="canvasNavPersonal">
    <div class="TPNleft" >
      <svg t="1618193747145" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1380" width="13" height="13"><path d="M494.808475 0.002204a59.729531 59.729531 0 0 1 59.729531 59.729531v241.0487a59.729531 59.729531 0 0 1-119.385595 0V59.731735A59.656063 59.656063 0 0 1 494.808475 0.002204zM106.823659 723.22177h810.499619a59.729531 59.729531 0 0 1 0 119.385594H106.823659a59.729531 59.729531 0 0 1 0-119.385594z m394.890813-541.82693a307.684228 307.684228 0 0 1 307.684228 307.684228v234.142702H194.103712V489.0056a307.61076 307.61076 0 0 1 307.61076-307.61076zM343.317338 964.270469h303.055741a29.828032 29.828032 0 0 1 0 59.729531H343.317338a29.828032 29.828032 0 0 1 0-59.729531z" fill="#333333" p-id="1381"></path></svg>
      <div class="warpper">通知公告</div>
    </div>
    <div class="TPNright">
      <div class="content"
        v-for="(item,index) in componentContent.txtRoute"
        :key="index">
        <div class="personalText" v-if="item.des != ''">
          {{ item.des }}<Badge class="zxbadge" :num="item.notice" :size="'regular'"/>
        </div>
        <div class="container" v-if="index != componentContent.txtRoute.length -1">/</div>
      </div>
      <!-- icon部分 -->
      <div class="personalText iconLeft">
        <svg t="1618194176792" class="icon" viewBox="0 0 1170 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1815" width="16" height="16"><path d="M1119.085714 138.313143H269.165714L239.177143 42.861714a60.928 60.928 0 0 0-58.514286-42.715428H40.96a40.594286 40.594286 0 1 0 0 81.115428h124.342857l229.668572 727.04h656.822857a40.521143 40.521143 0 1 0 0-81.115428H455.68l-33.645714-105.398857 705.828571-118.637715a51.2 51.2 0 0 0 42.422857-50.029714V189.001143a51.2 51.2 0 0 0-51.2-50.688z m-587.337143 707.876571a88.795429 88.795429 0 1 0 89.965715 88.795429 89.380571 89.380571 0 0 0-89.965715-88.795429z m430.08 0a88.795429 88.795429 0 1 0 89.965715 88.795429 89.380571 89.380571 0 0 0-89.965715-88.795429z" fill="#333333" p-id="1816"></path></svg><Badge class="zxbadge" :num="this.componentContent.cartNum" :size="'regular'"/>
      </div>
      <div class="personalText">
        <svg t="1618196116210" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1480" width="16" height="16"><path d="M980.114286 277.942857c-29.257143-51.2-65.828571-102.4-109.714286-146.285714-51.2-43.885714-102.4-73.142857-160.914286-95.085714C585.142857-14.628571 438.857143-14.628571 314.514286 36.571429c-58.514286 21.942857-117.028571 51.2-160.914286 95.085714-51.2 36.571429-87.771429 87.771429-109.714286 146.285714-29.257143 51.2-43.885714 109.714286-43.885714 175.542857 0 160.914286 87.771429 299.885714 226.742857 373.028572 21.942857 14.628571 51.2 29.257143 73.142857 36.571428l175.542857 146.285715c14.628571 7.314286 21.942857 14.628571 36.571429 14.628571s21.942857-7.314286 36.571429-14.628571l153.6-138.971429c87.771429-29.257143 168.228571-87.771429 226.742857-160.914286 58.514286-73.142857 95.085714-168.228571 95.085714-263.314285 0-58.514286-14.628571-117.028571-43.885714-168.228572zM665.6 614.4c-87.771429 73.142857-219.428571 73.142857-314.514286 0-36.571429-36.571429-58.514286-87.771429-58.514285-138.971429 0-21.942857 21.942857-36.571429 36.571428-36.571428s29.257143 14.628571 29.257143 29.257143c14.628571 80.457143 95.085714 138.971429 175.542857 124.342857 65.828571-7.314286 117.028571-58.514286 124.342857-117.028572 7.314286-21.942857 21.942857-36.571429 43.885715-36.571428 14.628571 0 29.257143 14.628571 29.257142 29.257143 0 58.514286-21.942857 109.714286-65.828571 146.285714z" fill="#333333" p-id="1481"></path></svg><Badge class="zxbadge" :num="0" :size="'regular'"/>
      </div>
      <div class="personalText el-dropdown-link">
        <svg t="1618194347551" class="icon" viewBox="0 0 1054 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2126" width="16" height="16"><path d="M527.058824 0c150.588235 0 271.058824 120.470588 271.058823 271.058824S677.647059 542.117647 527.058824 542.117647 256 421.647059 256 271.058824 376.470588 0 527.058824 0zM0 1024v-225.882353c0-82.823529 67.764706-150.588235 150.588235-150.588235h752.941177c82.823529 0 150.588235 67.764706 150.588235 150.588235v225.882353H0z" fill="#333333" p-id="2127"></path></svg><Badge class="zxbadge" :num="0" :size="'regular'"/>
      </div>
    </div>
  </div>
</template>

<script>
import Badge from '@/views/theme/components/canvasShow/pc/components/Badge.vue'
export default {
  components: { Badge },
  name: 'TopPersonalNav',
  props: {
    componentContent: Object
  }
}
</script>

<style lang="scss" scoped>
.canvasNavPersonal{
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  background-color: #F6F6F8;
  div{
    display: inline-block;
  }
  .TPNleft{
    cursor: pointer;
    margin-left: 135px;
    line-height: 40px;
  }
  .TPNright{
    float: right;
    margin-right: 135px;
    cursor: pointer;
    .content{
        margin-right: 10px;
        .container{
            padding-left: 5px;
        }
    }
    .iconLeft{
        margin-left: 20px;
    }
    .zxbadge{
        display: inline-block;
        height: 20px;
        position: relative;
        left: -5px;
    }
    .personalText{
        margin-right: 15px;
    }
  }
  >>>.el-dropdown-menu__item:focus, .el-dropdown-menu__item:not(.is-disabled):hover{
    background-color: #F6F6F6;
    color: #333333;
  }
}
</style>
