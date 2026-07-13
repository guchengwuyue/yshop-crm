<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="800">
    
    <div id="printMe">
      <table border="0" cellspacing="10" width="700"  >
        <tbody>
        <tr>
            <td width="743" valign="center" colspan="8" >
                <p  style="text-align:center;vertical-align:middle;">
                <b><span style="color:#0070C0;font-size:18pt;">合 &nbsp;&nbsp;&nbsp;同</span></b>
                    <br/>
                <b><span style="color:#0070C0;font-size:16pt;">{{formData.name}}</span></b>
                </p>
            </td>

        </tr>

        <tr>
            <td valign="center" colspan="5" >
                <p  style="vertical-align:middle;">
                    <b>&nbsp;合同编号：{{formData.number}} </b>
                </p>
            </td>
            <td >
                <p  style="vertical-align:middle;">
                <b>下单日期：</b>
            </p>
            </td>
            <td colspan="2" >
                <p  style="vertical-align:middle;">
                <b>{{formatDate(formData.orderTime)}}</b>
                </p>
            </td>

        </tr>

        <tr>
            <td class="tdborder">
                <p class="tdtext" >
                    <b>客户名称</b>
                </p>
            </td>
            <td valign="center" colspan="3" class="tdborder">
                <p class="tdtext" >
                    {{formData.customerName}}
                </p>
            </td>
            <td valign="center" class="tdborder">
                <p class="tdtext">
                    <b>联系人</b>

                </p>
            </td>
            <td   colspan="3" class="tdborder">
                <p class="tdtext" >
                  
                </p>
            </td>

        </tr>

        <tr>
            <td class="tdborder">
                <p class="tdtext">
                    <b>项目经理</b>
                </p>
            </td>
            <td colspan="3" class="tdborder" >
                <p class="tdtext" >

                </p>
            </td>
            <td class="tdborder">
                <p class="tdtext" >
                <b>签单人</b>
                </p>
            </td>
            <td  colspan="3" class="tdborder">
                <p class="tdtext" >
                  {{formData.orderAdminName}}
                </p>
            </td>


        </tr>


        <tr>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    <b>开始日期</b></p>
            </td>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                  {{formatDate(formData.startTime)}}
                </p>
            </td>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    <b>结束日期</b></p>
            </td>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                  {{formatDate(formData.endTime)}}
                </p>
            </td>
        </tr>


        <tr>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    <b>合同金额</b></p>
            </td>
            <td  colspan="2" class="tdborder">
                <b><span style="color:#0070C0;font-size:16pt;">{{formData.money}}</span></b>

            </td>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    <b>已收款</b></p>
            </td>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                  {{formData.returnMoney}}
                </p>
            </td>
        </tr>
        <tr>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    <b>备注</b>
                  </p>
            </td>
            <td  colspan="6" class="tdborder">
                <p class="tdtext" >
                  {{formData.remark}}
                </p>
            </td>

        </tr>


        <tr>
            <td valign="center" :rowspan="rowspan" class="tdborder" >
                <p >
                    <b>
                        <div style="width: 20px;margin-left: 15px;">购买产品或服务</div>
                    </b>
                </p>
            </td>
            <td  class="tdborder title">

                <b>名称</b>
            </td>
            <td class="tdborder title" >
                <b>数量</b>
            </td>
            <td class="tdborder title" >
                <b>商品属性</b>
            </td>
            <td  class="tdborder title">
                <b>单价</b>
            </td>
            <td class="tdborder title">
                <b>小计</b>
            </td>
            <td class="tdborder title">
                <b>备注</b>
            </td>
        </tr>

  
        <tr v-for="(item,index) in contactsProducts" :key="index">
            <td  class="tdborder" >
                <p>
                <b>{{item.name}}</b>
                </p>
            </td>
            <td  class="tdborder pcontent">
              {{item.nums}}
            </td>
            <td  class="tdborder pcontent">
              {{item.sku}}
            </td>
            <td  class="tdborder pcontent">
                ￥{{item.price}}
            </td>
            <td  class="tdborder pcontent">
                ￥{{item.subtotal}}
            </td>
            <td  class="tdborder pcontent">
                  {{item.remarks}}
            </td>
        </tr>
  

        <tr>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    <b>产品金额</b></p>
            </td>
            <td  colspan="2" class="tdborder">
                <b><span style="">{{totalPrice}}</span></b>

            </td>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    <b>折扣</b></p>
            </td>
            <td  colspan="2" class="tdborder">
                <p class="tdtext" >
                    {{discountPrice}}
                </p>
            </td>
        </tr>



        <tr>
            <td colspan="4" >
                <p  style="text-align:left;vertical-align:middle;">


                    <b><span style="font-size:14pt;">甲方</span></b>
                    <br/>
                    <br/>
                    <b><span >盖章签名：_____________</span></b>
                    <br/>
                    <br/>
                    <b><span >日期：</span></b>
                </p>
            </td>

            <td colspan="4" >
                <p  style="text-align:left;vertical-align:middle;">


                    <b><span style="font-size:14pt;">乙方</span></b>
                    <br/>
                    <br/>
                    <b><span >盖章签名：_____________</span></b>
                    <br/>
                    <br/>
                    <b><span >日期"：</span></b>
                </p>
            </td>
        </tr>

        </tbody>

    </table>
    <br/>
    <br/>
    <br/>
    </div>

    <button v-print="'#printMe'">打印合同</button>
  </Dialog>
</template>
<script setup lang="ts">
import { ContractApi, ContractVO } from '@/api/crm/crmcontract'
import { useUserStore } from '@/store/modules/user'
import { formatDate } from '@/utils/formatTime'
import print from 'vue3-print-nb'
const userStore = useUserStore()

directives: {
    print   
}

/** 合同 表单 */
defineOptions({ name: 'ContractPrint' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('打印') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  customerId: undefined,
  businessId: undefined,
  contactsId: undefined,
  sourceId: undefined,
  name: undefined,
  number: undefined,
  orderTime: undefined,
  money: undefined,
  totalPrice: undefined,
  returnMoney: undefined,
  discountRate: undefined,
  checkStatus: undefined,
  flowId: undefined,
  stepId: undefined,
  checkAdminId: undefined,
  flowAdminId: undefined,
  startTime: undefined,
  endTime: undefined,
  orderAdminId: undefined,
  remark: undefined,
  ownerUserId: undefined,
  nextTime: undefined,
  expireHandle: undefined,
  invoiceMoney: undefined
})

const formRef = ref() // 表单 Ref
const contactsProducts = ref([])
const rowspan = ref(2)



/** 打开弹窗 */
const open = async (data) => {
  dialogVisible.value = true
  resetForm()
  formData.value = data
  console.log('formData.value :',formData.value )
  const list = await ContractApi.getContractProductListByContractId(data.id)
  contactsProducts.value = list
  rowspan.value = list.length + 1

}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗


const totalPrice = computed(() => {
  let money = 0
  contactsProducts.value.forEach(element => {
    money = money + element.subtotal
  })

  return money

})

const discountPrice = computed(() => {
  let money = 0
  contactsProducts.value.forEach(element => {
    money = money + element.discount
  })

  return money

})


/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    customerId: undefined,
    businessId: undefined,
    contactsId: undefined,
    sourceId: undefined,
    name: undefined,
    number: undefined,
    orderTime: undefined,
    money: undefined,
    totalPrice: 0,
    returnMoney: undefined,
    discountRate: 0,
    checkStatus: undefined,
    flowId: undefined,
    stepId: undefined,
    checkAdminId: undefined,
    flowAdminId: undefined,
    startTime: Date.parse(new Date()),
    endTime: undefined,
    orderAdminId: undefined,
    remark: undefined,
    ownerUserId: undefined,
    nextTime: undefined,
    expireHandle: undefined,
    invoiceMoney: undefined
  }
  formRef.value?.resetFields()
}
</script>

<style scope>
    table {
      border-collapse: collapse;
    }
    td {
        padding: 10px 5px;
    }

    p {
        margin: 0 0 0px;
    }
    table{
        border-spacing: 1px;
    }
    .tdborder{
        border:1px solid #000000;
       align-items:center;line-height:16px;
    }
    .tdtext{
        text-align:center;
        vertical-align:middle;
    }
    .title{
        padding: 0px;
        font-weight: bold;
        text-align: center;
    }
    .pcontent{
        padding: 0px;
        text-align: center;
    }
</style>