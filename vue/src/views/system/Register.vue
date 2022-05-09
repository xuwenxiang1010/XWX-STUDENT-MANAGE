<template>
  <div class="login">
    <div class="login_box">

      <!-- 输入表单 -->
      <el-form label-width="0" :rules="rules" class="login_form" :model="user" ref="user">
        <div style="margin: 20px 0;text-align: center; font-size: 20px">注册</div>
        <!-- name -->
        <el-form-item prop="userName">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.userName"></el-input>
        </el-form-item>
        <!-- password -->
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" type="password" show-password v-model="user.password"></el-input>
        </el-form-item>
        <!--button -->
        <el-form-item class="submit">
          <el-button type="primary" @click="login('user')" style="margin-right: 50px">注册</el-button>
          <el-button type="info" @click="$router.push('/')">返回登录页</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      user:{
        userName: "",
        password: ""
      },
      rules: {
        userName: [
          {required:true,message: '请输入用户名'}
        ],
        password: [
          {required:true,message: '请输入密码'}
        ]
      }
    }
  },
  methods:{
    login(form){
      this.$refs[form].validate((valid) =>{
        if(valid){ //表单校验合法

          this.request.post("/register",this.user).then(res => {
            if (res.code === '200'){
              this.$message.success("注册成功 请及时完善个人信息")
              this.request.post("/login",this.user).then(res =>{
                if(res.code === '200'){
                  localStorage.setItem("user",JSON.stringify(res.data)) //存储用户信息
                  this.$router.push('/system')
                }else {
                  this.$message.error(res.message)
                }
              })

            }else {
              this.$message.error(res.message)
            }
          })

        }
      })

    },
    clean(form){
      this.$refs[form].resetFields()
    }
  }
}
</script>

<style scoped>

.login {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  position: fixed;
  background: url('../../assets/login.png');
  background-size: 100% 100%;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 25%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.login_form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
.submit{
  display: flex;
  justify-content: flex-end;
}


</style>
