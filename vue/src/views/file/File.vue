<template>
  <div>
    <div style="margin: 10px 0">
      <el-form :inline="true" :model="filters" ref="filters">
        <el-form-item prop="name">
          <el-input v-model="filters.name" style="width: 150px" placeholder="请输入姓名" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="ml-5" @click="load" icon="el-icon-search">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button class="ml-5" @click="restLoad('filters')" icon="el-icon-refresh-left">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div style="margin: 10px 0">
      <el-upload action="http://localhost:80/system/file/upload" :show-file-list="false" :on-success="handleFileUploadSuccess">
        <el-button type="primary" class="ml-5">点击上传</el-button>
      </el-upload>
    </div>

    <el-table :data="tableData"
              border stripe max-height="70%"
              :header-cell-class-name="headerBG">
      <el-table-column prop="name" align="center" label="名称" >
      </el-table-column>
      <el-table-column prop="type" align="center" label="类型" >
      </el-table-column>
      <el-table-column prop="size" align="center" label="文件大小" >
      </el-table-column>
      <el-table-column align="center" label="下载链接" >
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" @change="changeStatus(scope.row)" active-color="#13ce66" inactive-color="#ccc"></el-switch>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="60px">
        <template slot-scope="scope">
          <el-popconfirm
            class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='取消'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm="remove(scope.row.id)"
          >
            <el-button type="text" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0; float: right">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5,10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>

export default {
  name: "File",
  data(){
    return{
      filters: {
        name: ""
      },
      form:{},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      headerBG: 'headerBG',
      url: {
        pageList: "/system/file/pageList",
        update: "/system/file/update",
        remove: "/system/file/delete/",
      },
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get(this.url.pageList,{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.filters.name
        }
      }).then(res=>{
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    restLoad(form){
      this.$refs[form].resetFields()
      this.load()
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    remove(id){
      this.request.post(this.url.remove + id).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },
    download(url){
      window.open(url)
    },
    changeStatus(row){
      this.request.post(this.url.update,row).then(res => {
        if(res.code === '200'){
          this.$message.success("更新成功")
        }
      })
    },
    handleFileUploadSuccess(){
      this.load()
    }

  }
}
</script>

<style scoped>
.headerBG{
  background: #eeeeee!important;
}
</style>
