<template>
  <div>
    <div style="margin: 10px 0">
      <el-form :inline="true" :model="filters" ref="filters">
        <el-form-item prop="name">
          <el-input v-model="filters.name" style="width: 200px" placeholder="请输入图书馆名称" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
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
      <el-button type="primary" @click="handleAdd()">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table :data="tableData"
              border stripe max-height="70%"
              :header-cell-class-name="headerBG">

      <el-table-column prop="name" align="center" label="图书馆名称">
      </el-table-column>

      <el-table-column prop="flower" align="center" label="图书馆层数">
      </el-table-column>

      <el-table-column prop="room" align="center" label="每层房间数">
      </el-table-column>

      <el-table-column prop="bookShelf" align="center" label="房间书架数">
      </el-table-column>

      <el-table-column prop="layer" align="center" label="书架层数">
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
            @confirm="remove(scope.row.id)">
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


    <el-dialog title="新增图书馆" :visible.sync="dialogAdd" width="40%">
      <el-form label-width="100px">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书馆层数">
          <el-input v-model="form.flower" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="每层房间数">
          <el-input v-model="form.room" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="房间书架数">
          <el-input v-model="form.bookShelf" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="书架层数">
          <el-input v-model="form.layer" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAdd = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑菜单" :visible.sync="dialogEdit" width="30%">
      <el-form label-width="70px">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书馆层数">
          <el-input v-model="form.flower" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="每层房间数">
          <el-input v-model="form.room" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="房间书架数">
          <el-input v-model="form.bookShelf" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="书架层数">
          <el-input v-model="form.layer" autocomplete="off"></el-input>
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
  name: "Libraries",
  data(){
    return{
      filters: {
        name: "",
          flower:"",
          room:"",
          bookShelf:"",
          layer:"",
      },
      form:{},
      dialogAdd: false,
      dialogEdit: false,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      multipleSelection: [],
      headerBG: 'headerBG',
      url: {
        list: "/books/libraries/list",
        add: "/books/libraries/add",
        edit: "/books/libraries/edit",
        remove: "/books/libraries/delete/",
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
          name: this.filters.name,
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
      this.request.put(this.url.edit,this.form).then(res =>{
        if (res){
          this.$message.success("编辑成功")
          this.dialogEdit = false
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
    remove(id){
        debugger
      this.request.delete(this.url.remove + id).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    }
  }
}
</script>

<style scoped>
.headerBG{
  background: #eeeeee!important;
}
</style>
