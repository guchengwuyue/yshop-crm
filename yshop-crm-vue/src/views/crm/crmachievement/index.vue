<template>
  <ContentWrap>
    <el-menu
    :default-active="activeIndex"
    style="margin-bottom:10px"
    mode="horizontal"
    @select="handleSelect"
  >
      <!-- <el-menu-item index="my">团队业绩</el-menu-item> -->
      <el-menu-item index="sub">成员业绩</el-menu-item>
    </el-menu>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="业绩方式" prop="type">
        <el-select
          v-model="queryParams.config"
          placeholder="请选择"
          clearable
          class="!w-240px"
        >
          <el-option label="合同金额" :value="1" />
          <el-option label="回款金额" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="年度" prop="type">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          value-format="YYYY"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['crm:achievement:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 批量设置
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap v-if="activeIndex == 'my'">
    <!-- <el-table v-loading="loading"  row-key="id" :data="deptList"  :default-expand-all="isExpandAll">
      <el-table-column prop="name" label="部门名称" width="200" />
      <el-table-column label="年目标" align="center" prop="yeartarget">
        <template #default="scope">
          {{scope.row.achievementRespVO.yeartarget}}
        </template>
      </el-table-column>
      <el-table-column label="一月" align="center" prop="january">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.january"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.january}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="二月" align="center" prop="february">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.february"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.february}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="三月" align="center" prop="march">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.march"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.march}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="四月" align="center" prop="april">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.april"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.april}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="五月" align="center" prop="may">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.may"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.may}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="六月" align="center" prop="june">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.june"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.june}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="七月" align="center" prop="july">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.july"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.july}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="八月" align="center" prop="august">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.august"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.august}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="九月" align="center" prop="september">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.september"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.september}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="十月" align="center" prop="october">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.october"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.october}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="十一月" align="center" prop="november">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.november"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.november}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="十二月" align="center" prop="december">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.december"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.december}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
    </el-table> -->
  
  </ContentWrap>

  <ContentWrap v-else>
    <el-table v-loading="loading"  row-key="id" :data="list"  :default-expand-all="isExpandAll">
      <el-table-column prop="nickname" label="成员名称" width="200" />
      <el-table-column label="年目标" align="center" prop="yeartarget">
        <template #default="scope">
          {{scope.row.achievementRespVO.yeartarget}}
        </template>
      </el-table-column>
      <el-table-column label="一月" align="center" prop="january">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.january"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.january}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="二月" align="center" prop="february">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.february"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.february}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="三月" align="center" prop="march">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.march"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.march}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="四月" align="center" prop="april">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.april"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.april}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="五月" align="center" prop="may">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.may"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.may}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="六月" align="center" prop="june">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.june"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.june}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="七月" align="center" prop="july">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.july"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.july}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="八月" align="center" prop="august">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.august"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.august}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="九月" align="center" prop="september">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.september"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.september}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="十月" align="center" prop="october">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.october"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.october}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="十一月" align="center" prop="november">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.november"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.november}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="十二月" align="center" prop="december">
        <template #default="scope">
          <el-popover trigger="click" :visible="(thisIndex == scope.$index) && (scope.column.property = thisColumn)" placement="top" :width="260">
            <div>
              <el-input-number
                v-model="scope.row.achievementRespVO.december"
                style="width: 120px"
                placeholder="Please input"
                :controls="false"
                :min="0"
              />
               <el-button
                  type="success"
                  plain
                  style="margin-left:5px"
                  @click = "doSubmit(scope.row,scope.column.property)"
                >
                  <Icon icon="ep:check"/>
                </el-button>
                <el-button
                  type="danger"
                  plain
                  @click = "doClose()"
                >
                  <Icon icon="ep:close"/>
                </el-button>
            </div>
            <template #reference>
              <el-text type="primary" style="cursor:pointer" @click="visibleFuc(scope.$index,scope.column.property)">{{scope.row.achievementRespVO.december}}</el-text>
            </template>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <AchievementForm ref="formRef" @success="getListDept" />
  <AchievementForm2 ref="formRef2" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter,formatDate } from '@/utils/formatTime'
import download from '@/utils/download'
import { AchievementApi, AchievementVO } from '@/api/crm/crmachievement'
import AchievementForm from './AchievementForm.vue'
import AchievementForm2 from './AchievementForm2.vue'
import { handleTree } from '@/utils/tree'
//const visible = ref(false)
const thisIndex = ref(-1)
const thisColumn = ref('')
const deptType = ref(3)
const activeIndex = ref('sub')



/** 业绩目标 列表 */
defineOptions({ name: 'CrmAchievement' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<AchievementVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  config: 1,
  year: formatDate(new Date(),'YYYY'),
  
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const deptList = ref([])
const isExpandAll = ref(true) // 是否展开，默认全部展开

const getListDept = async () => {
  loading.value = true
  try {
    const data = await AchievementApi.getAchievementDept(queryParams)
    deptList.value = handleTree(data)
  } finally {
    loading.value = false
  }
}

const handleSelect = (key) => {
  activeIndex.value = key
  getList()

}


const visibleFuc = (index,column) => {
  thisIndex.value = index
  thisColumn.value = column
  console.log(index,column)
}

const doClose = () => {
  thisIndex.value = -1
  thisColumn.value = ''
}

const doSubmit = async(row,column) => {
  row.achievementRespVO.config = queryParams.config
  row.achievementRespVO.type = activeIndex.value == 'my' ? 3 : 2
  row.achievementRespVO.year = queryParams.year
  console.log('row:',row)
  //return
  await AchievementApi.createAchievement(row)
  message.success(t('common.createSuccess'))
  if(activeIndex.value == 'my'){
    getListDept()
  }else{
    getList()
  }
  doClose()
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await AchievementApi.getAchievementPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  if(activeIndex.value == 'my'){
    getListDept()
  }else{
    getList()
  }
 // getList()
  //getListDept()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const formRef2 = ref()
const openForm = (type: string, id?: number) => {
  if(activeIndex.value == 'my'){
    formRef.value.open(type, id)
  }else{
    formRef2.value.open(type, id)
  }
  
}




/** 初始化 **/
onMounted(() => {
  //getListDept()
  getList()
})
</script>