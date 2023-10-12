<template>
  <div class="canvanProductHot">
    <product-slot
      :obj="obj">
      <div class="hotContainer">
        <div class="left">
            <div class="title">{{ componentContent.product[0].storeName }}</div>
            <div class="price">
              {{ `￥${componentContent.product[0].price}` }}
              <div class="putCart">
                <svg
                  t="1618572221481" class="icon" viewBox="0 0 1170 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1604" width="16" height="16"><path d="M1119.085714 138.313143H269.165714L239.177143 42.861714a60.928 60.928 0 0 0-58.514286-42.715428H40.96a40.594286 40.594286 0 1 0 0 81.115428h124.342857l229.668572 727.04h656.822857a40.521143 40.521143 0 1 0 0-81.115428H455.68l-33.645714-105.398857 705.828571-118.637715a51.2 51.2 0 0 0 42.422857-50.029714V189.001143a51.2 51.2 0 0 0-51.2-50.688z m-587.337143 707.876571a88.795429 88.795429 0 1 0 89.965715 88.795429 89.380571 89.380571 0 0 0-89.965715-88.795429z m430.08 0a88.795429 88.795429 0 1 0 89.965715 88.795429 89.380571 89.380571 0 0 0-89.965715-88.795429z" fill="#333333" p-id="1605"></path></svg>
              </div>
            </div>
            <img
              class="imgPro"
              :src="componentContent.product[0].pic"
              alt="商品图片">
        </div>
        <!-- 人气爆款右边部分 -->
        <div class="right" v-if="componentContent.product.length > 1">
          <div v-for="(i, index2) in componentContent.product" :key="index2">
            <div v-if="index2 !== 0"><ProductItem class="wrapItem" :obj="i" :type="0"/></div>
          </div>
        </div>
      </div>
    </product-slot>
  </div>
</template>

<script>
import ProductSlot from '@/views/theme/components/canvasShow/pc/components/IndexSlot.vue'
import ProductItem from '@/views/theme/components/canvasShow/pc/components/ProductItem.vue'
export default {
  components: {ProductSlot, ProductItem},
  props: {
    componentContent: Object
  },
  data () {
    return {
      obj: {
        'title': '人气爆款',
        'bcText': 'POPULAR STYLE',
        'interface': '/indexpage/productlist'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.canvanProductHot{
  .hotContainer{
    display: flex;
    margin: 30px 0;
    .left{
      width: 500px;
      height: 695px;
      margin: 0 15px;
      background-color: #EEE;
      display: flex;
      flex-direction: column;
      .title{
        margin: 50px 10px 0 50px;
        text-align: left;
        font-size: 30px;
        overflow: hidden;
        white-space:nowrap;
        text-overflow: ellipsis;
      }
      .price{
        margin: 30px 50px;
        color: #333333;
        font-size: 22px;
        font-weight: 500;
        .putCart{
          float: right;
          cursor: pointer;
        }
      }
      .imgPro{
        width: 375px;
        height: 375px;
        margin: auto;
        cursor: pointer;
      }
    }
    .right{
      width: 650px;
      height: 675px;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      .wrapItem{
          width: 315px;
          background-color: #EEE;
          padding-bottom: 10px;
          margin-bottom: 20px;
      }
    }
  }
}
</style>
