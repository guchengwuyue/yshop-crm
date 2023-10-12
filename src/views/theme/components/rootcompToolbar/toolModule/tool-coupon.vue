<template>
<div class="tool-coupon">
  <div class="selectMode">
    <div>添加优惠券</div>
    <el-radio-group class="modeRight" v-model="modeValue" @change="modeChange">
      <el-radio label="1">手动添加</el-radio>
      <div class="mode2">
        <el-radio label="2">自动获取</el-radio>
        <el-tooltip class="item" effect="dark" content="系统自动获取仅设置为“公开领取”的店铺优惠券，新创建的券排在前面" placement="bottom">
        <span class="iconfont">&#xe60a;</span>
        </el-tooltip>
      </div>
    </el-radio-group>
  </div>
  <div class="addCouponBox" v-show="couponBtnVisible">
    <div class="addCouponBtn" @click="addCoupon">
      <i class="iconfont">&#xe685;</i><span>添加优惠券</span>
    </div>
  </div>
  <el-dialog title="选择优惠券" :visible.sync="couponDialogVisible" width="1000">
    <coupon-select ref="couponSelect"></coupon-select>
    <span slot="footer" class="dialog-footer">
      <el-button @click="couponDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="couponChanged">确 定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import CouponSelect from '@/views/theme/components/rootcompToolbar/toolModule/coupon-select.vue'
  export default {
    name: 'tool-coupon',
    components: { CouponSelect },
    data () {
      return {
        modeValue: '1', // 添加优惠券方式
        couponBtnVisible: true,
        selectedCoupon: [],
        couponDialogVisible: false
      }
    },
    methods: {
      // 添加优惠券
      addCoupon () {
        this.couponDialogVisible = true
        this.$refs.couponSelect.multipleSelection = []
      },
      couponChanged () {
        this.selectedCoupon = this.$refs.couponSelect.multipleSelection
        this.couponDialogVisible = false
        console.log(this.selectedCoupon)
      },
      modeChange (val) {
        this.couponBtnVisible = val === '1'
      }
    }
  }
</script>

<style lang="scss" scoped>
.tool-coupon{
  padding-bottom: 20px;
}
.selectMode {
  display: flex;
  justify-content: space-between;
  align-items: center;
  .modeRight {
    display: flex;
    .iconfont{
      cursor: pointer;
    }
  }
  >>> .el-radio {
    margin-right: 0;
  }
  >>> .el-radio__label {
    padding-left: 5px;
  }
  .mode2 {
    margin-left: 10px;
    span {
      font-size: 14px;
      color: #999999;
    }
  }
}
.addCouponBox {
  background: #F6F7F9;
  padding: 10px 13px;
  margin: 20px 0 0;
  .addCouponBtn {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 40px;
    line-height: 40px;
    background: #ffffff;
    font-size: 14px;
    color: $mainColor;
    border: 1px solid $mainColor;
    box-sizing: border-box;
    cursor: pointer;
    i {
      margin-right: 10px;
    }
  }
}
</style>
