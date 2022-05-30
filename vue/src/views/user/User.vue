<template>
  <div>
    <div style="margin: 10px 0">
      <el-form :inline="true" :model="filters" ref="filters">
        <el-form-item prop="userName">
          <el-input v-model="filters.userName" style="width: 150px" placeholder="请输入姓名" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="ml-5" @click="load" icon="el-icon-search">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button class="ml-5" @click="restLoad('filters')" icon="el-icon-refresh-left">重置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button class="ml-5" @click="getRoles()" >重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
        class="ml-5"
        confirm-button-text='确定'
        cancel-button-text='取消'
        icon="el-icon-info"
        icon-color="red"
        title="确定删除这些数据吗？"
        @confirm="batchRemove"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-button type="primary">导入 <i class="el-icon-download"></i></el-button>
      <el-button style="float: right">导出 <i class="el-icon-upload2"></i></el-button>
    </div>


    <el-table :data="tableData"
              border stripe max-height="70%"
              :header-cell-class-name="headerBG">
      <el-table-column type="selection" width="55" align="center">
      </el-table-column>
      <el-table-column prop="userName" align="center" label="用户名" width="140">
      </el-table-column>
      <el-table-column prop="userAge" align="center" label="年龄" width="140">
      </el-table-column>
      <el-table-column prop="phone" align="center" label="电话号码" width="120">
      </el-table-column>
      <el-table-column prop="email" align="center" label="邮箱" width="200">
      </el-table-column>
      <el-table-column prop="address" align="center" label="地址">
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="20px">
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

    <el-dialog title="新增用户" :visible.sync="dialogAdd" width="30%">
      <el-form label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.userAge" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAdd = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑用户" :visible.sync="dialogEdit" width="30%">
      <el-form label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.userAge" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox-button v-for="role in roleList" :label="role.id" :key="role.id">
            {{role.roleName}}
          </el-checkbox-button>
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
        userName: ""
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
      roleList: [],
      url: {
        pageList: "/system/user/pageList",
        add: "/system/user/add",
        update: "/system/user/update",
        remove: "/system/user/delete/",
        batchRemove: "/system/user/batchDelete",
        getUserRole: "/system/user/getUserInfo",
        roleList: "/system/role/list"
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
          userName: this.filters.userName
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
    },
    batchRemove(){
      let ids = this.multipleSelection.map(v => v.id)
      if(ids.length == 0){
        this.$message.error("无数据")
      }else {
        this.request.post(this.url.batchRemove,ids).then(res =>{
          if (res){
            this.$message.success("批量删除成功")
            this.load()
          }else {
            this.$message.error("批量删除失败")
          }
        })
      }
    },
    getRoles(){
      this.request.get(this.url.roleList).then(res => {
        console.log(res.data)
        this.roleList = res.data
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
