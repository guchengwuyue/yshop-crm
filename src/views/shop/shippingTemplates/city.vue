<template>
  <el-dialog :close-on-click-modal="false"
             :visible.sync="addressView"
             append-to-body
             class="modal"
             title="选择城市" width="950px">
    <el-row :gutter="24" type="flex">
      <el-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24" class="item">
        <div class="acea-row row-right row-middle">
          <el-checkbox v-model="iSselect" @change="allCheckbox">全选</el-checkbox>
          <div class="empty" @click="empty">清空</div>
        </div>
      </el-col>
    </el-row>
    <el-row  :gutter="24"  :loading="loading" >
      <el-col  :xl="6" :lg="6" :md="6" :sm="8" :xs="6" class="item"  v-for="(item,index) in cityList" :key="index">

        <div @mouseenter="enter(index)" @mouseleave="leave()">
          <el-checkbox v-model="item.checked" :label="item.name" @change="checkedClick(index)">{{item.name}}</el-checkbox>
          <span class="red">({{(item.count || 0) + '/' + item.children.length}})</span>
          <div class="city" v-show="activeCity===index">
            <div class="checkBox">
              <div class="arrow"></div>
              <div>
                <el-checkbox v-model="city.checked" :label="city.name" @change="primary(index,indexn)"
                class="itemn" v-for="(city,indexn) in item.children"
                :key="indexn">{{city.name}}</el-checkbox>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <div slot="footer">
      <el-button @click="close">取消</el-button>
      <el-button type="primary" @click="confirm">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import crudYxShippingTemplates from '@/api/yxShippingTemplates'
  import CRUD, {presenter, header, form, crud} from '@crud/crud'
  import rrOperation from '@crud/RR.operation'
  import crudOperation from '@crud/CRUD.operation'
  import udOperation from '@crud/UD.operation'
  import MaterialList from "@/components/material";
  export default {
    name: 'city',
    props: {
      type: {
        type: Number,
        default: 0
      }
    },
    components: {crudOperation, rrOperation, udOperation, MaterialList},
    mixins: [header(), crud()],
    data () {
      return {
        iSselect: false,
        addressView: false,
        cityList: [],
        activeCity: -1,
        loading: false
      }
    },
    methods: {
      enter (index) {
        this.activeCity = index;
      },
      leave () {
        this.activeCity = null;
      },
      getCityList () {
        this.loading = true;
        crudYxShippingTemplates.getCity().then(res => {
          this.loading = false;
          this.cityList = res;
          console.log("jjj:"+res)
        })
      },
      /**
       * 全选或者反选
       * @param checked
       */
      allCheckbox: function () {
        let that = this, checked = this.iSselect;
        that.cityList.forEach(function (item, key) {
          that.$set(that.cityList[key], 'checked', checked);
          if (checked) {
            that.$set(that.cityList[key], 'count', that.cityList[key].children.length);
          } else {
            that.$set(that.cityList[key], 'count', 0);
          }
          that.cityList[key].children.forEach(function (val, k) {
            that.$set(that.cityList[key].children[k], 'checked', checked);
          })
        });
      },
      // 清空；
      empty () {
        let that = this;
        that.cityList.forEach(function (item, key) {
          that.$set(that.cityList[key], 'checked', false);
          that.cityList[key].children.forEach(function (val, k) {
            that.$set(that.cityList[key].children[k], 'checked', false);
          });
          that.$set(that.cityList[key], 'count', 0);
        });
        this.iSselect = false;
      },
      /**
       * 点击省
       * @param index
       */
      checkedClick: function (index) {
        let that = this;
        if (that.cityList[index].checked) {
          that.$set(that.cityList[index], 'count', that.cityList[index].children.length);
          that.cityList[index].children.forEach(function (item, key) {
            that.$set(that.cityList[index].children[key], 'checked', true);
          });
        } else {
          that.$set(that.cityList[index], 'count', 0);
          that.$set(that.cityList[index], 'checked', false);
          that.cityList[index].children.forEach(function (item, key) {
            that.$set(that.cityList[index].children[key], 'checked', false);
          });
          that.iSselect = false;
        }
      },
      /**
       * 点击市区
       * @param index
       * @param ind
       */
      primary: function (index, ind) {
        let checked = false, count = 0;
        this.cityList[index].children.forEach(function (item, key) {
          console.log("item:"+item.checked)
          if (item.checked) {
            checked = true;
            count++;
          }
        });
        this.$set(this.cityList[index], 'count', count);
        this.$set(this.cityList[index], 'checked', checked);
      },
      // 确定;
      confirm () {
        let that = this;
        // 被选中的省市；
        let selectList = [];
        console.log("city:"+that.cityList[0].children)
        that.cityList.forEach(function (item, key) {
          let data = {};
          if (item.checked) {
            data = {
              name: item.name,
              city_id: item.city_id,
              children: []
            };

          }
          console.log("data:"+data)
          that.cityList[key].children.forEach(function (i, k) {
            if (i.checked) {
              data.children.push({
                city_id: i.city_id
              })
            }
          });
          if (data.city_id !== undefined) {
            selectList.push(data);
          }
        });
        console.log(selectList);
        if (selectList.length === 0) {
          return this.$message({
            message:'至少选择一个省份或者城市',
            type: 'error'
          });
        } else {
          this.$emit('selectCity', selectList, this.type);
          that.addressView = false;
          this.cityList = []
        }
      },
      close () {
        this.addressView = false;
        this.cityList = []
      }
    },
    mounted () {
    }
  }
</script>

<style scoped>
  .modal .item {
    position: relative;
    margin-bottom: 20px;
  }

  .modal .item .city {
    position: absolute;
    z-index: 9;
    top: 17px;
    width: 100%;
    padding-top: 18px;
  }

  .modal .item .city .checkBox {
    width: 97%;
    padding: 10px;
    border: 1px solid #eee;
    background-color: #fff;
    max-height: 100px;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .modal .item .city .checkBox .arrow {
    position: absolute;
    top: 3px;
    width: 0;
    height: 0;
    border: 8px solid transparent;
    border-bottom-color: #ddd;
  }

  .modal .item .city .checkBox .arrow:before {
    position: absolute;
    bottom: -8px;
    right: -7px;
    content: "";
    width: 0;
    height: 0;
    border: 7px solid transparent;
    border-bottom-color: #fff;
  }

  .modal .item .city .checkBox .itemn {
    margin-bottom: 10px;
  }

  .radio {
    padding: 5px 0;
    font-size: 14px !important;
  }

  .red {
    color: #ff0000;
  }

  .empty {
    cursor: pointer;
    margin-left:10px
  }
</style>
