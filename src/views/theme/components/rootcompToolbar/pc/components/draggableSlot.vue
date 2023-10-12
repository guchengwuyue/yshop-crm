/**
    画布编辑器插槽
 */
<template>
  <div class="draggableSlot">
    <div class="draggableItem">
        <div class="listItemBox">
            <!-- 标题 -->
            <div class="addImgTit" @click="openAddImg(obj, index)">
                <div class="titLeft">
                    <span class="iconfont">&#xe703;</span>
                    <span class="iconfont">&#xe64a;</span>
                    <span>{{ obj.title }}</span>
                </div>
                <div class="titRight">
                    <span class="iconfont"
                    @click.stop="deleteItem(obj, index)"
                    >&#xe633;</span>
                    <span
                    v-html="imgCurrent === index ? '&#xe660;' : '&#xe695;'"
                    class="iconfont"
                    ></span>
                </div>
            </div>
            <!-- 编辑部分 -->
            <div class="addBox" v-show="imgCurrent === index">
                <slot></slot>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    props: {
        obj: Object,
        index: Number
    },
    data () {
        return {
            imgCurrent: null
        }
    },
    methods: {
        openAddImg (item, index) {
            if (this.imgCurrent === index) {
                this.imgCurrent = null
                return false
            }
            this.imgCurrent = index
        },
        // 删除内容
        deleteItem (item, index) {
            this.$confirm('确定删除此项？')
            .then((_) => {
                this.$emit('deleteIt', index)
            })
            .catch((_) => {})
        }
    }
}
</script>

<style lang="scss">
.draggableSlot{
    .listItemBox {
        .addImgTit {
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            background: #f6f7f9;
            cursor: pointer;
            .titLeft {
            display: flex;
            align-items: center;
            span {
                color: #7d7e80;
            }
            span:nth-child(1) {
                font-size: 28px;
            }
            span:nth-child(2) {
                font-size: 25px;
                margin: 0 6px;
            }
            span:nth-child(3) {
                font-size: 14px;
            }
            }
            .titRight {
            display: flex;
            align-items: center;
                span:nth-child(1) {
                    width: 40px;
                    text-align: center;
                    display: block;
                    height: 30px;
                    line-height: 30px;
                }
            }
        }
        .addContent {
            margin-top: 10px;
            padding: 5px 13px;
            .imgIsShow {
            display: flex;
            justify-content: space-between;
            margin: 18px 0 22px 0;
                span {
                    font-size: 14px;
                    color: #666666;
                }
            }
            .titleBox{
                margin: 10px 0;
            }
            .deleteItem {
                border-radius: 4px;
                background: $mainColor;
                text-align: center;
                height: 36px;
                color: #ffffff;
                font-size: 14px;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                margin-bottom: 10px;
                span {
                    font-size: 18px;
                    color: #ffffff;
                    margin-right: 5px;
                }
            }
        }
        .deleteItem {
            margin-top: 10px;
            padding: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #f6f7f9;
            cursor: pointer;
            color: $mainColor;
            font-size: 14px;
            span {
                font-size: 16px;
                margin-right: 5px;
            }
            &:hover{
                color: #f6f7f9;
                background: $mainColor;;
            }
        }
    }
}
</style>
