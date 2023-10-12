<template>
  <div class="productItem">
      <img class="imgPro" :src="obj.pic || ''" alt="商品图片" @click="toDetail(obj.id,type)">
      <div class="title">{{ obj.storeName }}</div>
      <div class="price">
        {{ `￥${obj.price}` }}
        <div class="putCart" @click="toDetail(obj.id, type)">
          <svg
            t="1618572221481" class="icon" viewBox="0 0 1170 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1604" width="16" height="16">
            <path d="M1119.085714 138.313143H269.165714L239.177143 42.861714a60.928 60.928 0 0 0-58.514286-42.715428H40.96a40.594286 40.594286 0 1 0 0 81.115428h124.342857l229.668572 727.04h656.822857a40.521143 40.521143 0 1 0 0-81.115428H455.68l-33.645714-105.398857 705.828571-118.637715a51.2 51.2 0 0 0 42.422857-50.029714V189.001143a51.2 51.2 0 0 0-51.2-50.688z m-587.337143 707.876571a88.795429 88.795429 0 1 0 89.965715 88.795429 89.380571 89.380571 0 0 0-89.965715-88.795429z m430.08 0a88.795429 88.795429 0 1 0 89.965715 88.795429 89.380571 89.380571 0 0 0-89.965715-88.795429z" fill="#333333" p-id="1605"></path>
            </svg>
        </div>
      </div>
  </div>
</template>

<script>
export default {
  props: {
    obj: Object,
    type: Number
  },
  methods: {
    toDetail (id, type) {
      this.$router.push({
        path: '/productDetail',
        query: {
          productId: id,
          type: type
        }
      })
    }
  }
}
</script>

<style lang="scss">
.productItem{
  width: 278px;
  .imgPro{
    width: 100%;
    height: 250px;
    cursor: pointer;
  }
  .title{
    margin: 15px 20px;
    overflow: hidden;
    white-space:nowrap;
    text-overflow: ellipsis;
  }
  .price{
    margin: 0 20px;
    color: #333333;
    font-size: 22px;
    font-weight: 500;
    .putCart{
        float: right;
        cursor: pointer;
    }
  }
}
</style>
