<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
    <div style="flex: 1; ">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>

      <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item >{{ this.$route.name }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 100px; cursor: pointer">
      <span>{{user.userName}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item><el-button type="text" @click="userInfo">个人信息</el-button></el-dropdown-item>
        <el-dropdown-item><el-button type="text" @click="logout">退出</el-button></el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  props: {
    collapseBtnClass: String,
    collapse: Function
  },
  methods:{
    logout(){
      this.$store.commit("logout")
      this.$message.success("已退出登录")
    },
    userInfo(){
      this.$router.push('/UserInfo')
    }

  }

}
</script>

<style scoped>

</style>
