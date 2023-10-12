<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.value"
          clearable
          placeholder="输入搜索内容"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-select
          v-model="query.type"
          clearable
          placeholder="类型"
          class="filter-item"
          style="width: 130px"
        >
          <el-option
            v-for="item in queryTypeOptions"
            :key="item.key"
            :label="item.display_name"
            :value="item.key"
          />
        </el-select>
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission">
        <el-tooltip
          slot="right"
          class="item"
          effect="dark"
          content="数据库中表字段变动时使用该功能"
          placement="top-start"
        >
          <el-button
            class="filter-item"
            size="mini"
            type="success"
            icon="el-icon-refresh"
            :loading="syncLoading"
            :disabled="crud.selections.length === 0"
            @click="sync"
            >同步</el-button
          >
        </el-tooltip>
      </crudOperation>
      <!--表单组件-->
      <el-dialog
        :close-on-click-modal="false"
        :before-close="crud.cancelCU"
        :visible.sync="crud.status.cu > 0"
        :title="crud.status.title"
        width="800px"
      >
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          size="small"
          label-width="140px"
        >
          <el-form-item label="选择商品" prop="coverImgeUrl">
            <cgood v-model="form.good" :disabled="isdisabled"></cgood>
          </el-form-item>
          <el-form-item label="商品封面图：" prop="coverImgeUrl">
            <single-pic
              v-model="form.coverImgeUrl"
              type="image"
              :num="1"
              :width="150"
              :height="150"
            />
            <p style="color: #cf0f0f">
              图片规则：图片尺寸最大300像素*300像素；
            </p>
          </el-form-item>

          <el-form-item label="商品小程序路径" prop="url">
            <el-input
              v-model="form.url"
              style="width: 370px;"
              :disabled="true"
            />
          </el-form-item>
          <!--          1：一口价（只需要传入price，price2不传）-->
          <!--          2：价格区间（price字段为左边界，price2字段为右边界，price和price2必传）-->
          <!--          3：显示折扣价（price字段为原价，price2字段为现价， price和price2必传）-->
          <el-form-item label="商品名称" prop="name">
            <el-input
              v-model="form.name"
              style="width: 370px;"
              :disabled="isdisabled"
            />
          </el-form-item>
          <el-form-item label="价格类型" prop="priceType">
            <el-radio-group v-model="form.priceType">
              <el-radio :label="'1'" class="radio">一口价</el-radio>
              <el-radio :label="'2'" class="radio">价格区间</el-radio>
              <el-radio :label="'3'" class="radio">显示折扣价</el-radio>
            </el-radio-group>
            <p v-if="isdisabled" style="color: #cf0f0f">
              商品审核通过已入库，只能修改价格
            </p>
          </el-form-item>
          <el-col v-if="form.priceType == '1'" v-bind="grid">
            <el-form-item label="一口价" prop="price">
              <el-input v-model="form.price" style="width: 200px;" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.priceType == '2'" v-bind="grid">
            <el-form-item label="最低价格" prop="price">
              <el-input v-model="form.price" style="width: 200px;" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.priceType == '2'" v-bind="grid">
            <el-form-item label="最高价格" prop="price2">
              <el-input v-model="form.price2" style="width: 200px;" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.priceType == '3'" v-bind="grid">
            <el-form-item label="市场价" prop="price">
              <el-input v-model="form.price" style="width: 200px;" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.priceType == '3'" v-bind="grid">
            <el-form-item label="现价" prop="price2">
              <el-input v-model="form.price2" style="width: 200px;" />
            </el-form-item>
          </el-col>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button
            :loading="crud.status.cu === 2"
            type="primary"
            @click="crud.submitCU"
            >确认</el-button
          >
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
        <el-table-column
          v-if="columns.visible('goodsId')"
          prop="goodsId"
          label="直播商品id"
        />
        <el-table-column
          v-if="columns.visible('name')"
          prop="name"
          label="商品名称"
        />
        <el-table-column
          v-if="columns.visible('productId')"
          prop="productId"
          label="关联商品id"
        />
        <el-table-column
          v-if="columns.visible('coverImgeUrl')"
          prop="coverImgUrl"
          label="商品图片"
        >
          <template slot-scope="scope">
            <a
              :href="scope.row.coverImgeUrl"
              style="color: #42b983"
              target="_blank"
              ><img
                :src="scope.row.coverImgeUrl"
                alt="点击打开"
                class="el-avatar"
            /></a>
          </template>
        </el-table-column>
        <el-table-column
          v-if="columns.visible('url')"
          prop="url"
          label="商品小程序路径"
        />
        <el-table-column
          v-if="columns.visible('priceType')"
          prop="priceType"
          label="价格类型"
        >
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.priceType == 1" :type="''">一口价</el-tag>
              <el-tag v-else-if="scope.row.priceType == 2" :type="''"
                >价格区间</el-tag
              >
              <el-tag v-else-if="scope.row.priceType == 3" :type="''"
                >显示折扣价</el-tag
              >
            </div>
          </template>
        </el-table-column>
        <!--        <el-table-column v-if="columns.visible('price')" prop="price" label="price" />-->
        <!--        <el-table-column v-if="columns.visible('price2')" prop="price2" label="price2" />-->
        <el-table-column
          v-if="columns.visible('auditStatus')"
          prop="auditStatus"
          label="审核状态"
        >
          <!--          //0：未审核，1：审核中，2:审核通过，3审核失败-->
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.auditStatus === 0" :type="''"
                >未审核</el-tag
              >
              <el-tag v-else-if="scope.row.auditStatus === 1" :type="''"
                >审核中</el-tag
              >
              <el-tag v-else-if="scope.row.auditStatus === 2" :type="''"
                >审核通过</el-tag
              >
              <el-tag v-else-if="scope.row.auditStatus === 3" :type="''"
                >审核失败</el-tag
              >
            </div>
          </template>
        </el-table-column>
        <!--        1, 2：表示是为api添加商品，否则是直播控制台添加的商品-->
        <el-table-column
          v-if="columns.visible('thirdPartyTag')"
          prop="thirdPartyTag"
          label="添加途径"
        >
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.thirdPartyTag == 0" :type="''"
                >api添加</el-tag
              >
              <el-tag v-else-if="scope.row.thirdPartyTag == 2" :type="''"
                >控制台</el-tag
              >
            </div>
          </template>
        </el-table-column>

        <el-table-column
          v-permission="[
            'admin',
            'yxWechatLiveGoods:edit',
            'yxWechatLiveGoods:del'
          ]"
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
import { sync } from "@/api/yxWechatLiveGoods";
import crudYxWechatLiveGoods from "@/api/yxWechatLiveGoods";
import CRUD, { presenter, header, form, crud } from "@crud/crud";
import rrOperation from "@crud/RR.operation";
import crudOperation from "@crud/CRUD.operation";
import udOperation from "@crud/UD.operation";
import pagination from "@crud/Pagination";
import cgood from "@/views/components/good";
import singlePic from "@/components/singlematerial";
// crud交由presenter持有
const defaultCrud = CRUD({
  title: "直播商品",
  url: "api/yxWechatLiveGoods",
  sort: "goods_id,desc",
  crudMethod: { ...crudYxWechatLiveGoods }
});
const defaultForm = {
  good: {
    productId: null,
    storeName: null,
    image: null,
    price: null,
    otPrice: null
  },
  goodsId: null,
  productId: null,
  coverImgeUrl: "",
  url: null,
  priceType: null,
  price: null,
  price2: null,
  name: null,
  thirdPartyTag: null,
  auditId: null,
  auditStatus: null
};
export default {
  name: "YxWechatLiveGoods",
  components: {
    pagination,
    crudOperation,
    rrOperation,
    udOperation,
    cgood,
    singlePic
  },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      isdisabled: false,
      syncLoading: false,
      grid: {
        xl: 8,
        lg: 12,
        md: 12,
        sm: 24,
        xs: 24
      },
      permission: {
        add: ["admin", "yxWechatLiveGoods:add"],
        edit: ["admin", "yxWechatLiveGoods:edit"],
        del: ["admin", "yxWechatLiveGoods:del"]
      },
      rules: {
        coverImgUrl: [
          { required: true, message: "商品图片不能为空", trigger: "blur" }
        ],
        priceType: [
          { required: true, message: "价格类型不能为空", trigger: "blur" }
        ],
        price: [{ required: true, message: "不能为空", trigger: "blur" }],
        name: [{ required: true, message: "商品名称不能为空", trigger: "blur" }]
      },
      queryTypeOptions: [{ key: "name", display_name: "商品名称" }]
    };
  },
  watch: {
    "form.good": {
      handler(val, oldVal) {
        this.form.productId = val.productId;
        this.form.name = val.storeName;
        this.form.price = val.price;
        this.form.price2 = val.otPrice;
        this.form.priceType = "3";
        if (val.productId) {
          this.form.url = "pages/shop/GoodsCon/index?id=" + val.productId;
        }
        this.isdisabled = false;
      },
      deep: true //对象内部的属性监听，也叫深度监听
    }
  },
  computed: {
    disable() {
      if (this.form.auditStatus == "2") {
        return (this.isdisabled = true);
      } else {
        return (this.isdisabled = false);
      }
    }
  },
  methods: {
    sync() {
      const ids = [];
      this.crud.selections.forEach(val => {
        ids.push(val.goodsId);
      });
      this.crud.selections.forEach(val => {});
      this.syncLoading = true;
      sync(ids)
        .then(() => {
          this.crud.refresh();
          this.crud.notify("同步成功", CRUD.NOTIFICATION_TYPE.SUCCESS);
          this.syncLoading = false;
        })
        .then(() => {
          this.syncLoading = false;
        });
    },
    /**
     * 执行删除
     * @param {*} data 数据项
     */
    doDelete(data) {
      let delAll = false;
      let dataStatus;
      const ids = [];
      if (data instanceof Array) {
        delAll = true;
        data.forEach(val => {
          ids.push(val.goodsId);
        });
      } else {
        ids.push(data.goodsId);
        dataStatus = crud.getDataStatus(data.goodsId);
      }
      if (!callVmHook(crud, CRUD.HOOK.beforeDelete, data)) {
        return;
      }
      if (!delAll) {
        dataStatus.delete = CRUD.STATUS.PROCESSING;
      }
      return crud.crudMethod
        .del(ids)
        .then(() => {
          if (delAll) {
            crud.delAllLoading = false;
          } else dataStatus.delete = CRUD.STATUS.PREPARED;
          crud.dleChangePage(1);
          crud.delSuccessNotify();
          callVmHook(crud, CRUD.HOOK.afterDelete, data);
          crud.refresh();
        })
        .catch(() => {
          if (delAll) {
            crud.delAllLoading = false;
          } else dataStatus.delete = CRUD.STATUS.PREPARED;
        });
    },
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      const query = this.query;
      if (query.type && query.value) {
        this.crud.params[query.type] = query.value;
      } else {
        delete this.crud.params.name;
      }
      return true;
    }, // 新增与编辑前做的操作
    [CRUD.HOOK.beforeToCU](crud, form) {
      this.form.good.productId = form.productId;
      this.form.good.storeName = form.name;
      this.form.good.image = form.coverImgeUrl;
      this.form.good.price = form.price;
      this.form.good.otPrice = form.price2;
      this.form.good.auditStatus = form.auditStatus;
    }
  }
};
</script>

<style scoped>
.table-img {
  display: inline-block;
  text-align: center;
  background: #ccc;
  color: #fff;
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  vertical-align: middle;
  width: 32px;
  height: 32px;
  line-height: 32px;
}
</style>
