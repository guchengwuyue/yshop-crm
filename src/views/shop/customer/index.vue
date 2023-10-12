<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.nickName"
          clearable
          placeholder="输入用户昵称"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.remark"
          clearable
          placeholder="输入备注"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />

        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog
        :close-on-click-modal="false"
        :before-close="crud.cancelCU"
        :visible.sync="crud.status.cu > 0"
        :title="crud.status.title"
        width="750px"
      >
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
          <el-form-item label="用户昵称">
            <el-input v-model="form.nickName" style="width: 485px;" :disabled="true" />
          </el-form-item>
          <el-form-item label="openId">
            <el-input v-model="form.openId" style="width: 485px;" :disabled="true" />
          </el-form-item>
          <el-form-item label="扫码获取">
            <div class="qrcode_img">
              <el-image :src="gzhsrc" class="qrcode">
                <div slot="placeholder" class="image-slot">
                  加载中
                  <span class="dot">...</span>
                </div>
              </el-image>
              <div class="attention">
                <span class="demonstration">注:未关注公众号请先关注</span>
              </div>
            </div>
            <div class="qrcode_img">
              <el-image :src="src" class="qrcode">
                <div slot="placeholder" class="image-slot">
                  加载中
                  <span class="dot">...</span>
                </div>
              </el-image>
            </div>
          </el-form-item>
          <el-form-item label="备注" style="margin-top:40px">
            <el-input v-model="form.remark" style="width: 485px;" />
          </el-form-item>
          <el-form-item label="是否启用">
            <el-radio v-for="item in dict.is_enable" :key="item.id" v-model="form.isEnable" :label="item.value">{{ item.label }}</el-radio>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table
        ref="table"
        v-loading="crud.loading"
        :data="crud.data"
        size="small"
        style="width: 100%;"
        @selection-change="crud.selectionChangeHandler"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" />
        <el-table-column v-if="columns.visible('nickName')" prop="nickName" label="用户昵称" />
        <el-table-column v-if="columns.visible('openId')" prop="openId" label="用户标识" />
        <el-table-column v-if="columns.visible('remark')" prop="remark" label="备注" />
        <el-table-column v-if="columns.visible('isEnable')" prop="isEnable" label="是否启用">
          <template slot-scope="scope">
            {{ dict.label.is_enable[scope.row.isEnable] }}
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>

        <el-table-column
          v-permission="['admin','yzCustomer:edit','yzCustomer:del']"
          label="操作"
          width="150px"
          align="center"
        >
          <template slot-scope="scope">
            <udOperation :data="scope.row" :permission="permission" />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudYzCustomer from "@/api/yxStoreCustomer";
import CRUD, { presenter, header, form, crud } from "@crud/crud";
import rrOperation from "@crud/RR.operation";
import crudOperation from "@crud/CRUD.operation";
import udOperation from "@crud/UD.operation";
import pagination from "@crud/Pagination";
import { RandomNumber } from "@/utils/index";

// crud交由presenter持有
const defaultCrud = CRUD({
  title: "消息通知",
  url: "api/yxStoreCustomer",
  sort: "id,desc",
  crudMethod: { ...crudYzCustomer }
});
const defaultForm = {
  id: null,
  nickName: null,
  openId: null,
  remark: null,
  createTime: null,
  updateTime: null
};
export default {
  name: "yxStoreCustomer",
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  dicts: ['is_enable'],
  data() {
    return {
      timer: null, //定时器
      src: "",
      gzhsrc: "",
      permission: {
        add: ["admin", "yzCustomer:add"],
        edit: ["admin", "yzCustomer:edit"],
        del: ["admin", "yzCustomer:del"]
      },
      rules: {}
    };
  },
  created() {
    this.init();
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      const query = this.query;
      if (query.type && query.value) {
        this.crud.params[query.type] = query.value;
      }
      return true;
    },
    [CRUD.HOOK.beforeToAdd]() {
      this.randomStr = RandomNumber();
      this.src =
        process.env.VUE_APP_BASE_API + "/api/wxmp/qrcode?key=" + this.randomStr;
      this.setIntervaltimer();
      crudYzCustomer.getwechatCode()
        //getOpenId("FK14YV17TURrFdyWG4")
        .then(res => {
          if (res != "") {
            this.gzhsrc = res;
            console.log(res);
          }
        })
        .catch(err => {
          console.log(err.response.data.message);
        });

      return true;
    },
    //新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
    },
    [CRUD.HOOK.beforeToCU]() {
      this.randomStr = RandomNumber();
      this.src =
        process.env.VUE_APP_BASE_API + "/api/wxmp/qrcode?key=" + this.randomStr;
      this.setIntervaltimer();
      return true;
    },
    [CRUD.HOOK.afterAddCancel]() {
      clearInterval(this.timer);
      this.timer = null;
      return true;
    },
    [CRUD.HOOK.afterEditCancel]() {
      clearInterval(this.timer);
      this.timer = null;
      return true;
    },
    [CRUD.HOOK.afterAddCancel]() {
      clearInterval(this.timer);
      this.timer = null;
      return true;
    },

    init() {},
    setIntervaltimer() {
      if (this.timer != null) {
        clearInterval(this.timer);
        this.timer = null;
      }
      this.timer = setInterval(() => {
        crudYzCustomer.getOpenId(this.randomStr)
          //getOpenId("FK14YV17TURrFdyWG4")
          .then(res => {
            if (res != "") {
              this.form.nickName = res.nickName;
              this.form.openId = res.openId;
              clearInterval(this.timer);
              this.timer = null;
            }
          })
          .catch(err => {
            console.log(err.response.data.message);
          });
      }, 2000);
      return this.timer;
    }
  }
};
</script>

<style scoped>
.qrcode_img {
  width: 250px;
  height: 250px;
  float: left;
}

.qrcode_img .qrcode {
  width: 100%;
  height: 100%;
}

.attention {
  line-height: 0px;
  color: red;
  margin-left: 20px;
  font-size: 19px;
}
</style>
