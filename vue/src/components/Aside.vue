<template>
  <el-menu style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           :unique-opened="true"
           router
  >
    <div style=" line-height: 60px; text-align: center">
      <img src="../assets/图标.png" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
      <b style="color: cornflowerblue" v-show="logoTextShow">后台管理系统</b>
    </div>

    <div v-for="item in menuList" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"/>
          <span slot="title">{{item.name}}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon"/>
            <span slot="title">{{item.name}}</span>
          </template>
          <div v-for="child in item.children" :key="child.id">
            <el-menu-item :index="child.path">
              <i :class="child.icon"/>
              <span slot="title">{{child.name}}</span>
            </el-menu-item>
          </div>

        </el-submenu>
      </div>
    </div>
<!--    <el-menu-item index="/manage/home" v-if="!isCollapse">
      <template slot="title">
        <i class="el-icon-s-home"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>

    <el-submenu v-for="menu in menuList" :index="menu.name" >
      <template slot="title">
        <i :class="menu.icon"></i>
        <span>{{ menu.title }}</span>
      </template>
      <el-menu-item-group v-for="item in menu.children">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.title }}</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>-->

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      menuList: localStorage.getItem("menuList") ? JSON.parse(localStorage.getItem("menuList")) : []
    }
  },
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },



}
</script>

<style scoped>

</style>
