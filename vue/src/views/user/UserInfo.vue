<template>
  <div>
    <el-descriptions title="个人信息" :column="7" direction="vertical" border>

      <el-descriptions-item label="用户名">{{this.user.userName}}</el-descriptions-item>
      <el-descriptions-item label="年龄">{{this.user.userAge}}</el-descriptions-item>
      <el-descriptions-item label="电话号码">{{this.user.phone}}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{this.user.email}}</el-descriptions-item>
      <el-descriptions-item label="地址">{{this.user.address}}</el-descriptions-item>
      <el-descriptions-item label="角色">{{this.roleName}}</el-descriptions-item>
      <el-descriptions-item label="操作">
        <el-button type="primary" style="margin: 10px 0" @click="handleEdit">编辑</el-button>
      </el-descriptions-item>
    </el-descriptions>

    <el-dialog title="编辑个人信息" :visible.sync="dialogEdit" width="30%">
      <el-form label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.userName" ></el-input>
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
        <el-button @click="cancelUpdate">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  data(){
    return{
      dialogEdit: false,
      dialogReset: false,
      form:{},
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      list:{},
      roleName: "",
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/system/user/getUserInfo",{
        params: {
          id: this.user.id
        }
      }).then(res => {
        this.user = res.data
        this.list = res.data.list
        for(var i=0;i < this.list.length;i++){
          this.roleName += this.list[i].roleName+","
          if(i = this.list.length-1){
            this.roleName += this.list[i].roleName
          }
        }
      })
    },
    handleEdit(){
      this.dialogEdit = true
      this.form = this.user
    },
    update(){
      this.request.post("/system/user/update",this.form).then(res =>{
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
    }
  }
}
</script>

<style scoped>

</style>
