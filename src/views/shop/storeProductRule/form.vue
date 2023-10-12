<template>
  <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU"
             :visible.sync="crud.status.cu > 0"
             :title="crud.status.title" width="950px">
    <el-form ref="formData" :model="formData" class="attrFrom"  :rules="rules" label-width="92px" :inline="true">
      <el-row :gutter="24">
        <el-col :span="24">
          <el-col :span="9" class="mb15">
            <el-form-item label="标题名称：" prop="ruleName">
              <el-input placeholder="请输入标题名称" v-model="formData.ruleName"/>
            </el-form-item>
          </el-col>
        </el-col>
        <el-col :span="23" class="noForm" v-for="(item, index) in formData.ruleValue" :key="index">
          <el-form-item label=" ">
            <div class="acea-row row-middle"><span class="mr5">{{item.value}}</span>
              <i class="el-icon-circle-close"  @click="handleRemove(index)"></i>
            </div>
            <div class="rulesBox">
              <el-tag   class="mb5" style="margin: 2px 4px 2px 0;" closable
              v-for="(j, indexn) in item.detail" :key="indexn"
              :name="j" @close="handleRemove2(item.detail,indexn)">{{j}}
              </el-tag>
              <el-input placeholder="请输入属性名称" v-model="item.detail.attrsVal"
                        style="width: 170px">
                <el-button slot="append" type="primary" @click="createAttr(item.detail.attrsVal,index)">添加</el-button>
              </el-input>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="isBtn" class="mt10">
          <el-col :span="9" class="mr15">
            <el-form-item label="规格：">
              <el-input placeholder="请输入规格" v-model="attrsName"/>
            </el-form-item>
          </el-col>
          <el-col :span="9" class="mr20">
            <el-form-item label="规格值：">
              <el-input v-model="attrsVal" placeholder="请输入规格值"/>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="createAttrName">确定</el-button>
          </el-col>
          <el-col :span="2">
            <el-button @click="offAttrName">取消</el-button>
          </el-col>
        </el-col>
      </el-row>
      <el-button type="primary" icon="md-add" @click="addBtn" v-if="!isBtn" class="ml95 mt10">添加新规格</el-button>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="modal_loading" type="primary" @click="handleSubmit('formData')">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import crudYxStoreProductRule from '@/api/storeProductRule'
  import CRUD, {presenter, header, form, crud} from '@crud/crud'
  import rrOperation from '@crud/RR.operation'
  import crudOperation from '@crud/CRUD.operation'
  import udOperation from '@crud/UD.operation'
  import MaterialList from "@/components/material";

  const defaultForm = {id: null, ruleName: null, ruleValue: null, createTime: null, updateTime: null, isDel: null}
  export default {
    name: 'ruleForm',
    components: {crudOperation, rrOperation, udOperation, MaterialList},
    mixins: [header(), form(defaultForm), crud()],
    data() {
      return {

        permission: {
          add: ['admin', 'yxStoreProductRule:add'],
          edit: ['admin', 'yxStoreProductRule:edit'],
          del: ['admin', 'yxStoreProductRule:del']
        },
        rules: {
          ruleName: [
            {required: true, message: '规格名称不能为空', trigger: 'blur'}
          ]
        },

        modal_loading: false,
        index: 1,
        formData: {
          ruleName: '',
          ruleValue: []
        },
        attrsName: '',
        attrsVal: '',
        formDataNameData: [],
        isBtn: false,
        formDataName: [],
        ids: 0

      }
    },
    watch: {},
    methods: {
      // 获取数据前设置好接口地址
      [CRUD.HOOK.beforeRefresh]() {
        return true
      }, // 新增与编辑前做的操作
      [CRUD.HOOK.afterToCU](crud, form) {
          this.formData.ruleName = form.ruleName || ''
          this.formData.ruleValue = form.ruleValue || []
          this.ids = form.id || 0
      },

      onCancel () {
        this.clear();
      },
      // 添加按钮
      addBtn () {
        this.isBtn = true;
      },
      // 提交
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            if (!this.formData.ruleValue) {
              return this.$message.warning('请至少添加一条商品规格！');
            }
            if (this.formData.ruleValue.length === 0) {
              return this.$message.warning('请至少添加一条商品规格！');
            }
            this.modal_loading = true;

            setTimeout(() => {
              crudYxStoreProductRule.add(this.formData, this.ids).then(res => {
                setTimeout(() => {
                  if(this.ids){
                    this.crud.status.edit = CRUD.STATUS.NORMAL
                    this.crud.editSuccessNotify()
                  }else{
                    this.crud.status.add = CRUD.STATUS.NORMAL
                    this.crud.addSuccessNotify()
                  }

                  this.crud.resetForm()
                  this.crud.toQuery()
                  this.modal_loading = false;
                }, 500);
                setTimeout(() => {
                  this.clear();
                }, 600);
              }).catch(res => {
                this.modal_loading = false;
                this.$message({
                  message:res.msg,
                  type: 'error'
                });
              });
            }, 1200);
          } else {
            return false
          }
        })
      },
      clear () {
        this.$refs['formData'].resetFields();
        this.formData.ruleValue = [];
        this.isBtn = false;
        this.attrsName = '';
        this.attrsVal = '';
      },
      // 取消
      offAttrName () {
        this.isBtn = false;
      },
      // 删除
      handleRemove (index) {
        this.formData.ruleValue.splice(index, 1);
      },
      // 删除属性
      handleRemove2 (item, index) {
        item.splice(index, 1);
      },
      // 添加规则名称
      createAttrName () {
        if (this.attrsName && this.attrsVal) {
          let data = {
            value: this.attrsName,
            detail: [
              this.attrsVal
            ]
          };
          this.formData.ruleValue.push(data);
          var hash = {};
          this.formData.ruleValue = this.formData.ruleValue.reduce(function (item, next) {
            /* eslint-disable */
            hash[next.value] ? '' : hash[next.value] = true && item.push(next);
            return item
          }, [])
          this.attrsName = '';
          this.attrsVal = '';
          this.isBtn = false;
        } else {
          return this.$message.warning('请添加规格名称！');
        }
      },
      // 添加属性
      createAttr (num, idx) {
        if (num) {
          this.formData.ruleValue[idx].detail.push(num);
          var hash = {};
          this.formData.ruleValue[idx].detail = this.formData.ruleValue[idx].detail.reduce(function (item, next) {
            hash[next] ? '' : hash[next] = true && item.push(next);
            return item
          }, [])
        } else {
          return this.$message.warning('请添加属性！');
        }
      }


    }
  }


</script>

<style scoped>

</style>
