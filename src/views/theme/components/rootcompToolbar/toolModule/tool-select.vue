<template>
  <div class="module-box link-select">
    <div class="module-box__title">
      <label class="module-box__label">{{title}}</label>
    </div>
    <el-select class="link-select__select" :popper-append-to-body="false" v-model="selsectValue" placeholder="请选择跳转到的页面" @change="selectChanged">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
  </div>
</template>

<script>
export default {
  name: 'tool-select',
  data () {
    return {
      selsectValue: ''
    }
  },
  props: {
    title: {
      type: String,
      default: '标题'
    },
    linkValue: {
      type: String,
      default: ''
    },
    options: {
      type: Array,
      default: () => []
    }
  },
  mounted () {
    this.selsectValue = this.linkValue // props 不能直接修改
  },
  methods: {
    selectChanged (value) {
      this.$emit('update:linkValue', value)
    }
  }
}
</script>

<style lang="scss" scoped>
.link-select{
  &__select{
    width: 100%;
  }
}
</style>
