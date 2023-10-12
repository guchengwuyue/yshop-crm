<template>
  <div class="panelBoxWarp">
    <div class="panelBox" :class="{ on: sidebarShow }">
      <div class="componentList">
        <ul>
          <li
            v-for="(item, index) of componentList"
            :key="index"
            :class="{ on: componentActive == index }"
            @click="componentActive = index"
          >
            <i class="iconfont icon-fangkuai"></i>
            <span>{{ item }}</span>
          </li>
        </ul>
      </div>
      <div class="categoryList">
        <div class="itemBox" v-for="(item, index) of categoryList" :key="index">
          <h3>{{ item.title }}</h3>
          <div class="childList">
            <draggable
              class="dragArea list-group"
              :list="item.classList"
              :clone="cloneItem"
              :group="{ name: 'pageEdit', pull: 'clone', put: false }"
              :options="{ sort: false }"
            >
              <div
                @mouseover="hoverItem(classItem)"
                @mouseout="hoverItemOut"
                class="childItem list-group-item"
                v-for="(classItem, index) of item.classList"
                :key="index"
              >
                <div class="contentBox">
                  <img
                    :src="
                      isHover === classItem.title
                        ? classItem.iconH
                        : classItem.icon
                    "
                    alt=""
                  />
                  <span>{{ classItem.title }}</span>
                </div>
                <div class="cloneText">组件放置区域</div>
              </div>
            </draggable>
          </div>
        </div>
      </div>
    </div>
    <!-- 是否展开 -->
    <div
      class="btnToggle"
      @click="sidebarShow = !sidebarShow"
      :class="{ on: sidebarShow }"
    >
      <i class="el-icon-arrow-right iconfont icon-arrow-right"></i>
      <i class="el-icon-arrow-left iconfont icon-arrow-left"></i>
    </div>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import { mapGetters } from 'vuex'
// 默认配置文件
import panelList from './panelList.js'
export default {
  name: 'panel',
  components: {
    draggable
  },
  data () {
    return {
      sidebarShow: true,
      componentActive: 0,
      componentList: ['组件'],
      isHover: ''
    }
  },
  methods: {
    hoverItem (classItem) {
      this.isHover = classItem.title
    },
    hoverItemOut () {
      this.isHover = ''
    },
    cloneItem (item) {
      return JSON.parse(JSON.stringify(item))
    }
  },
  computed: {
    ...mapGetters([
      'terminal'
    ]),
    categoryList () {
      if (this.terminal === 3) {
        return panelList[0]
      } else if (this.terminal === 4) {
        return panelList[1]
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.panelBoxWarp {
  position: relative;
  height: 100%;
}

.panelBox {
  width: 0px;
  height: 100%;
  display: flex;
  overflow: hidden;
  transition: 0.2s width ease;

  &.on {
    width: 250px;
  }

  .componentList {
    width: 64px;
    padding: 17px 0;
    text-align: center;
    ul{
      margin: 0;
      padding: 0;
    }
    li {
      list-style: none;
      position: relative;
      margin-bottom: 18px;

      .iconfont {
        font-size: 24px;
      }

      span {
        font-size: 12px;
        display: block;
      }

      &.on,
      &:hover {
        color: $mainColor;

        :after {
          content: "";
          position: absolute;
          left: 0;
          top: 0;
          width: 2px;
          height: 100%;
          background-color: $mainColor;
        }
      }
    }
  }

  .categoryList {
    border-left: 1px solid #f0f3f4;
    width: 186px;
    padding: 20px 0;
    height: auto;
    overflow-y: auto;
    padding-bottom:40px;

    .itemBox {
      h3 {
        padding-left: 20px;
        height: 35px;
        line-height: 35px;
        font-size: 16px;
        color: #333333;
        position: relative;

        &:before {
          content: "";
          border-left: 5px solid $mainColor;
          border-top: 4px solid transparent;
          border-bottom: 4px solid transparent;
          position: absolute;
          left: 10px;
          top: 50%;
          margin-top: -4px;
        }
      }

      .childList {
        font-size: 14px;
        color: #333333;
        padding: 20px 10px;

        .list-group {
          display: flex;
          flex-wrap: wrap;

          :hover {
            background: #ff7800;
            color: #ffffff;
            border-radius: 3px;
            cursor: move;
          }

          .childItem {
            height: 80px;
            width: 50%;
            cursor: pointer;

            .contentBox {
              height: 100%;
              display: flex;
              flex-flow: column;
              align-items: center;
              justify-content: center;

              img {
                width: 20px;
                height: 20px;
              }

              span {
                margin-top: 10px;
              }
            }

            .cloneText {
              display: none;
            }
          }
        }
      }
    }
  }
}

.btnToggle {
  width: 20px;
  height: 66px;
  background-color: #fff;
  border-radius: 0 20px 20px 0;
  position: absolute;
  right: -20px;
  top: 50%;
  margin-top: -33px;
  display: flex;
  align-items: center;
  cursor: pointer;
  z-index: 9;

  .icon-arrow-left {
    display: none;
  }

  &.on {
    .icon-arrow-left {
      display: block;
    }

    .icon-arrow-right {
      display: none;
    }
  }
}
</style>
