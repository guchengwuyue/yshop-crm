<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="title" width="900px">
    <el-form v-show="hidden == false" ref="form" :model="form" :inline="true" :rules="rules" label-width="80px">
      <el-form-item label="规则名称">
        <el-row :gutter="10">
          <el-col :span="10"><el-button type="primary" @click="hiddenBool">添加新规则</el-button></el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <el-form v-show="hidden == true" ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="规则名称">
        <el-row :gutter="10">
          <el-col
            v-for="(item, index) in items"
            :key="index"
            :span="5"
            style="position: relative;margin-right: 6px"
          >
            <el-input v-model="item.value" style="width: 150px;" placeholder="设置名称" />
            <el-button v-show="item.attrHidden == true" type="text" style="position: absolute;top:-6px;right:17px;margin-top:1px;border: none;font-size: 14px;font-weight:bold;line-height: 1.8" icon="el-icon-close" @click="handleRemove(index)" />
            <el-button v-show="item.attrHidden == false" type="text" style="position: absolute;top:-6px;right:17px;margin-top:1px;border: none;font-size: 14px;font-weight:bold;line-height: 1.8" icon="el-icon-check" @click="attrHiddenBool(item)" />
          </el-col>
          <el-col :span="5"><el-button type="primary" @click="handleAdd">添加新规则</el-button></el-col>
        </el-row>
      </el-form-item>
      <el-form-item
        v-for="(item, index) in items"
        v-show="item.attrHidden == true"
        :key="index"
        :label="''+item.value+':'"
      >
        <el-row :gutter="13">
          <el-col
            v-for="(attr,k) in item.detail"
            :key="attr"
            :span="3"
            :name="attr"
          >
            <el-tag closable @close="attrRemove(item,k)">{{ attr }}</el-tag>
          </el-col>
          <el-col :span="5">
            <el-input v-model="item.detailValue" style="width: 150px;" placeholder="设置属性" />
          </el-col>
          <el-col :span="5">
            <el-button type="primary" @click="attrAdd(item)">添加</el-button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item v-show="hidden == true">
        <el-row :gutter="24">
          <el-col :span="24"><el-button :loading="loading" type="primary" @click="addGoods(true)">生成</el-button></el-col>
        </el-row>
      </el-form-item>

      <template v-if="items[0].value!='' && items[0].detail.length>0 && attrs.length">
        <template v-for="(attr,index) in attrs">
          <el-form-item>
            <el-row :gutter="24">
              <template v-for="(item,index) in attr.detail">
                <el-col :span="3" style="margin-right: 2px">
                  {{ index }}:{{ item }}
                </el-col>
              </template>
              <el-col :span="4">
                <span :class="attr.check ? 'check':''">金额:</span>&nbsp;
                <el-input v-model="attr.price" placeholder="金额" style="width: 60%" :number="true" />
              </el-col>
              <el-col :span="4">
                <span :class="attr.check ? 'check':''">库存:</span>&nbsp;
                <el-input v-model="attr.sales" placeholder="库存" style="width: 60%" :number="true" maxlength="7"/>
              </el-col>
              <el-col :span="5">
                <span :class="attr.check ? 'check':''">成本价:</span>&nbsp;
                <el-input v-model="attr.cost" placeholder="成本价" style="width: 60%" :number="true" />
              </el-col>
              <el-col :span="3" style="margin-right: 2px">
                <div class="demo-upload">
                  <!--<img :src="attr.pic">-->
                  <pic-upload-two v-model="attr.pic" />
                </div>
              </el-col>
              <el-col :span="2" style="margin-right: 3px">
                <el-button type="primary" @click="removeGoods(index)">删除</el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </template>
        <el-form-item>
          <el-row :gutter="24">
            <el-col :span="2">
              <el-button type="primary" :loading="loading" @click="submit">提交</el-button>
            </el-col>
            <el-col :span="2">
              <el-button type="error" @click="clear">清空所有属性</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </template>

    </el-form>
  </el-dialog>

</template>

<script>
import { getCates } from '@/api/yxStoreCategory'
import { add, edit, isFormatAttr, setAttr, clearAttr, getAttr } from '@/api/yxStoreProduct'
import editor from '../../components/Editor'
import picUploadTwo from '@/components/pic-upload-two'
import mulpicUpload from '@/components/mul-pic-upload'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { Message } from 'element-ui'
export default {
  components: { editor, picUploadTwo, mulpicUpload, Treeselect },
  props: {
    isAttr: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false, cates: [], title: '规则属性',
      form: {
        id: '',
        merId: '',
        image: '',
        sliderImage: '',
        storeName: '',
        storeInfo: '',
        keyword: '',
        barCode: '',
        cateId: 1,
        price: '',
        vipPrice: '',
        otPrice: '',
        postage: '',
        unitName: '',
        sort: '',
        sales: '',
        stock: '',
        isShow: '',
        isHot: '',
        isBenefit: '',
        isBest: '',
        isNew: '',
        description: '',
        addTime: '',
        isPostage: '',
        isDel: '',
        merUse: '',
        giveIntegral: '',
        cost: '',
        isSeckill: '',
        isBargain: '',
        isGood: '',
        ficti: '',
        browse: '',
        codePath: '',
        soureLink: ''
      },
      rules: {
      },
      items: [{
        value: '',
        detailValue: '',
        attrHidden: false,
        detail: []
      }],
      attrs: [],
      hidden: false,
      attrHidden: false,
      submiting: false
    }
  },
  mounted() {
    // console.log('items'+this.items)
    // console.log('attrs'+this.attrs)
    // if(this.items && this.attrs) this.hidden = true;

    // window.changeIMG = (index,pic)=>{
    //   _vm.setAttrPic(index,pic);
    // };
  },
  methods: {
    getAttrs(id) {
      getAttr(id).then(res => {
        console.log('res' + res)
        // this.items = JSON.parse(res.attr)
        if (res) {
          this.hidden = true
          this.items = res.attr
          this.attrs = res.value
        } else {
          this.hidden = false
          this.items = [{
            value: '',
            detailValue: '',
            attrHidden: false,
            detail: []
          }]
          this.attrs = []
        }
      })
    },
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      if (this.isAttr) {
        this.doAdd()
      } else this.doEdit()
    },
    doAdd() {
      add(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '添加成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    doEdit() {
      edit(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        id: '',
        merId: '',
        image: '',
        sliderImage: '',
        storeName: '',
        storeInfo: '',
        keyword: '',
        barCode: '',
        cateId: '',
        price: '',
        vipPrice: '',
        otPrice: '',
        postage: '',
        unitName: '',
        sort: '',
        sales: '',
        stock: '',
        isShow: '',
        isHot: '',
        isBenefit: '',
        isBest: '',
        isNew: '',
        description: '',
        addTime: '',
        isPostage: '',
        isDel: '',
        merUse: '',
        giveIntegral: '',
        cost: '',
        isSeckill: '',
        isBargain: '',
        isGood: '',
        ficti: '',
        browse: '',
        codePath: '',
        soureLink: ''
      }
    },
    setAttrPic(index, pic) {
      this.$set(this.attrs[index], 'pic', pic)
    },
    attrHiddenBool(item) {
      if (item.value == '') {
        Message({ message: '请填写规则名称', type: 'error' })
      } else {
        item.attrHidden = true
      }
    },
    hiddenBool() {
      this.hidden = true
    },
    handleAdd() {
      if (!this.checkAttr()) return
      this.items.push({
        value: '',
        detailValue: '',
        attrHidden: false,
        detail: []
      })
    },
    checkAttr() {
      var bool = true
      this.items.map(function(item) {
        if (!bool) return
        if (!item.value) {
          Message({ message: '请填写规则名称', type: 'error' })
          bool = false
        } else if (!item.detail.length) {
          Message({ message: '请设置规则属性', type: 'error' })
          bool = false
        }
      })
      return bool
    },
    attrAdd(item) {
      if (!item.detailValue) return false
      item.detail.push(item.detailValue)
      item.detailValue = ''
    },
    handleRemove(index) {
      if (this.items.length > 1) { this.items.splice(index, 1) } else { Message({ message: '请设置至少一个规则', type: 'error' }) }
    },
    attrRemove(item, k) {
      console.log('item:')
      if (item.detail.length == 1) {
        Message({ message: '请设置至少一个属性', type: 'error' })
        return false
      }
      item.detail.splice(k, 1)
    },
    removeGoods(index) {
      this.attrs.splice(index, 1)
    },
    checkGoods() {
      var bool = true
      this.attrs.map(function(attr) {
        if (!bool) return
        if (!Object.keys(attr.detail).length) {
          Message({ message: '请选择至少一个属性', type: 'error' })
          bool = false
        } else if (attr.price != parseFloat(attr.price) || attr.price < 0) {
          Message({ message: '请输入正确的商品价格', type: 'error' })
          bool = false
        } else if (attr.sales != parseInt(attr.sales) || attr.sales < 0) {
          Message({ message: '请输入正确的商品库存', type: 'error' })
          bool = false
        }
      })
      return bool
    },
    addGoods(type) {
      if (this.attrs.length) {
        if (!this.checkGoods()) return
      }
      var that = this
      isFormatAttr(this.form.id, { items: this.items, attrs: this.attrs }).then(res => {
        this.attrs = res
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    submit() {
      var that = this
      that.submiting = true
      if (!this.checkAttr() || !this.checkGoods()) return
      for (const attr in that.attrs) {
        that.attrs[attr].check = false
      }

      // console.log({items:this.items,attrs:this.attrs})
      this.loading = false
      setAttr(this.form.id, { items: this.items, attrs: this.attrs }).then(res => {
        this.attrs = res
        Message({ message: '操作成功', type: 'success' })
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
      this.dialog = false
    },
    clear() {
      this.$confirm(`确定要清空属性数据'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          clearAttr(this.form.id).then(({ data }) => {
            Message({ message: '操作成功', type: 'success' })
            // this.dialog = false
            this.getAttrs(this.form.id)
          })
        })
        .catch(() => { })
    }
  }
}
</script>

<style scoped>

  .demo-upload{
    display: block;
    /*//height: 50px;*/
    text-align: center;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 4px;
  }
  .demo-upload img{
    width: 100%;
    height: 100%;
    display: block;
  }

  .demo-upload-cover{
    display: block;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
  }
  .demo-upload:hover .demo-upload-cover{
    display: block;
  }
  .demo-upload-cover i{
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
  }

</style>
