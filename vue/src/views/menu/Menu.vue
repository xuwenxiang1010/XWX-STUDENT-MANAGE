<template>
  <div>
    <div style="margin: 10px 0">
      <el-form :inline="true" :model="filters" ref="filters">
        <el-form-item prop="name">
          <el-input v-model="filters.name" style="width: 150px" placeholder="请输入名称" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
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
              row-key="id"
              :header-cell-class-name="headerBG">
      <el-table-column prop="name" align="center" label="菜单名称"></el-table-column>
      <el-table-column prop="path" align="center" label="路径"></el-table-column>
      <el-table-column prop="pagePath" align="center" label="页面路径"></el-table-column>
      <el-table-column prop="icon" align="center" label="图标">
        <template slot-scope="scope">
          <i :class="scope.row.icon" style="font-size: 20px"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300px">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-edit" v-if="!scope.row.parentId" @click="handleAdd(scope.row.id)">新增子菜单</el-button>
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


    <el-dialog title="新增菜单" :visible.sync="dialogAdd" width="30%">
      <el-form label-width="70px">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in options" :key="item.text" :label="item.text" :value="item.value">
              <i :class="item.value"/>{{item.text}}
            </el-option>
          </el-select>
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
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in options" :key="item.text" :label="item.text" :value="item.value">
              <i :class="item.value"/>{{item.value}}
            </el-option>
          </el-select>
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
  name: "User",
  data(){
    return{
      filters: {
        name: ""
      },
      form:{},
      dialogAdd: false,
      dialogEdit: false,
      tableData: [],
      multipleSelection: [],
      headerBG: 'headerBG',
      url: {
        findAll: "/system/menu/findAll",
        add: "/system/menu/add",
        update: "/system/menu/update",
        remove: "/system/menu/delete/",
        icons: "/system/menu/icons",
      },
      options: []
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get(this.url.findAll,{
        params: {
          name: this.filters.name
        }
      }).then(res=>{
        this.tableData = res.data
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
    handleAdd(pid){
      this.dialogAdd = true
      this.form = {}
      if(pid){
        this.form.parentId = pid
      }

      //获得图标
      this.request.get(this.url.icons).then(res => {
        this.options = res.data
      })
    },
    handleEdit(row){
      this.dialogEdit = true
      this.form = row

      //获得图标
      this.request.get(this.url.icons).then(res => {
        this.options = res.data
      })
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
      this.request.post(this.url.remove + id).then(res =>{
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
