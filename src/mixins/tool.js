import { mapGetters, mapMutations } from 'vuex'

export const toolMixin = {
  computed: {
    ...mapGetters([
      'activeComponent',
      'componentsData'
    ])
  },
  methods: {
    ...mapMutations({
      setComponentsData: 'SET_COMPONENTSDATA'
    })
  }
  // watch: {
  //   'activeComponent.componentContent': {
  //     handler(newVal, oldVal) {
  //       console.log(newVal)
  //       // localStorage.setItem('componentsData', JSON.stringify(this.componentsData))
  //     },
  //     deep: true
  //   }
  // }
}

/**
 * 删除类别中的空 Child
 * @param {className} class名称
 * @param {tagName} 标签名称
 */
export function checkEmptyChild(arr) {
  for (let i = 0; i < arr.length; i++) {
    if (arr[i].childs.length === 0) {
      arr[i].childs = ''
    } else {
      checkEmptyChild(arr[i].childs)
    }
  }
}
