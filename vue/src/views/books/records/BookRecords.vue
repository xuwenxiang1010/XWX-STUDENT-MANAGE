<template>
  <div>
    <div style="margin: 10px 0">
      <el-form :inline="true" :model="filters" ref="filters">
        <el-form-item prop="roleName">
          <el-input v-model="filters.bookCode" style="width: 150px" placeholder="请输入图书编号" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
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
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    </div>


    <el-table :data="tableData"
              border stripe max-height="70%"
              :header-cell-class-name="headerBG">
      <el-table-column prop="bookCode" align="center" label="图书编号">
      </el-table-column>
      <el-table-column prop="bookName" align="center" label="图书名称">
      </el-table-column>
      <el-table-column align="center" label="操作" width="300px">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
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

    <el-dialog title="新增图书" :visible.sync="dialogAdd" width="30%">
      <el-form label-width="70px">
        <el-form-item label="图书编号">
          <el-input v-model="getbefor.bookCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAdd = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑图书" :visible.sync="dialogEdit" width="30%">
      <el-form label-width="70px">
        <el-form-item label="图书编号">
        <el-input v-model="form.bookCode" autocomplete="off"></el-input>
      </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书编号">
          <el-input v-model="form.bookCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书编号">
          <el-input v-model="form.bookCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书编号">
          <el-input v-model="form.bookCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelUpdate">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "BookRecords",
        data(){
            return{
                filters: {
                    bookCode: ""
                },
                form:{},
                id: 0,
                dialogAdd: false,
                dialogEdit: false,
                dialogMenu: false,
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 5,
                multipleSelection: [],
                headerBG: 'headerBG',
                url: {
                    list: "/books/records/list",
                    add: "/books/records/add",
                    update: "/books/records/update",
                    remove: "/books/records/delete/"
                },
                props:{
                    label : 'name',
                },
                expands:[],
                checks:[]
            }
        },
        created() {
            this.load()
        },
        methods:{
            load(){
                this.request.get(this.url.list,{
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        bookCode: this.filters.bookCode
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
            handleAdd(){
                this.dialogAdd = true
                this.form = {}
            },
            handleEdit(row){
                this.dialogEdit = true
                this.form = row
            },
            save(){
                this.request.post(this.url.add,this.form).then(res =>{
                    if (res.code === '200'){
                        this.$message.success("保存成功")
                        this.dialogAdd = false
                        this.load()
                    }else {
                        this.$message.error(res.message)
                    }
                })
            },
            update(){
                this.request.post(this.url.update,this.form).then(res =>{
                    if (res){
                        this.$message.success("编辑成功")
                        this.dialogEdit = false
                        this.dialogMenu = false
                        this.load()
                    }else {
                        this.$message.error("编辑失败")
                    }
                })
            },
            cancelUpdate(){
                this.dialogEdit = false
                this.load()
            },
            cancelSelect(){
                this.dialogMenu = false
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
        }
    }
</script>

<style scoped>
  .headerBG{
    background: #eeeeee!important;
  }
</style>
