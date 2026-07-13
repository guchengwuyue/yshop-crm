<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" height="60%" >
    <div style="height:500px">
      <iframe
      id="mapPage"
      width="100%"
      height="100%"
      frameborder="0"
      :src="'https://apis.map.qq.com/tools/locpicker?key='+ak+'&search=1&type=1&referer=myapp'"
    >
    </iframe>

    </div>

    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button icon="el-icon-place" type="primary" @click="confirm">确 定</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">

// const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const ak = ref(import.meta.env.VITE_QQ_MAP_AK)
const selectedLocation = ref({})

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('地图选择') // 弹窗的标题
const keyword = ref('')// 地区搜索的信息


/** 打开弹窗 */
const open = async () => {
  dialogVisible.value = true
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗



/** *
 * 确认
 */
const emit = defineEmits(['mapConfirm']) 
const confirm = () => {
  if(!selectedLocation.value.address || selectedLocation.value.address == '') {
    return message.error('请选择定位地址')
  }
  dialogVisible.value = false
    // 发送操作成功的事件
  emit('mapConfirm',selectedLocation.value)
}

const selectAddress = (loc) => {
 
 if (loc.poiname === '我的位置') {
   message.error('请移动地图选择位置')
   return
 }

 console.log('loc:',loc)

 selectedLocation.value = {
   latitude: loc.latlng?.lat,
   longitude: loc.latlng?.lng,
   address: loc.poiaddress,
   detailAddress: loc.poiname

 }



//  mapDialogVisible.value = false

}


onMounted(() => {
  window.selectAddress = selectAddress
 
  window.addEventListener('message', (event) => {

    const loc = event.data

    if (loc && loc.module === 'locationPicker') {
      window.parent.selectAddress(loc)

    }

  }, false)


})

</script>
<style scoped>
.map {
  width: 100%;
  height: 300px;
}
</style>
