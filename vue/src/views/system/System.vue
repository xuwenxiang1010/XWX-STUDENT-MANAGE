<template>
    <el-container style="min-height: 100vh">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); min-height: 100%; overflow: hidden">
        <Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #cccccc;">
          <Header :collapse-btn-class="collapseBtnClass" :collapse="collapse"/>
        </el-header>

        <el-main>
<!--          当前页面的子路由-->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
</template>

<script>

import Aside from "../../components/Aside";
import Header from "../../components/Header";

export default {
  data () {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
    }
  },
  components: {
    Aside,
    Header
  },
  methods:{
    collapse(){//点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){ //收缩
        this.sideWidth = 54
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else { //展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },


  }
}
</script>

<style>

</style>
