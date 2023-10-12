const container = {
  state: {
    terminal: 3, // 画布选择的设备
    activeComponent: {}, // 选中模板数据
    componentsData: [] // 模板组件数据
  },
  mutations: {
    SET_TERMINAL: (state, terminal) => {
      state.terminal = terminal
    },
    SET_ACTIVECOMPONENT: (state, activeComponent) => {
      state.activeComponent = activeComponent
    },
    SET_COMPONENTSDATA: (state, componentsData) => {
      state.componentsData = componentsData
    }
  }
}

export default container
