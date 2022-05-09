import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const routes = [
  //静态路由：全都能看到
  {
    path: '/',
    name: 'Login',
    component: () => import('../views/system/Login')
  },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/system/Login')
  },
  {
    path: '/Register',
    name: 'Register',
    component: () => import('../views/system/Register')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/system/404')
  }

]


const router = new Router({
  mode: 'history',
  routes
})

//重置路由方法
export const resetRouter= () => {
  router.matcher = new Router({
    mode: 'history',
    routes
  })
}

export const setRoutes = () => {
  const storeMenuList = localStorage.getItem("menuList");
  if (storeMenuList) {
    //获取当前的路由对象名称数组
    const currentRoute = router.getRoutes().map(v => v.name)
    if (!currentRoute.includes('system')){
      const systemRoute = {path: "/system" , name: 'system' , component:() => import('../views/system/System'), redirect: "/system/home", children: [
          {path: "/userInfo", name:'个人信息', component:() => import('../views/user/UserInfo')}
        ]}
      const menus = JSON.parse(storeMenuList)
      menus.forEach(item => {
        if(item.path){ //当path不为空时才去设置路由
          let itemMenu = {path: item.path , name: item.name, component:() => import('../views/'+item.pagePath+'.vue')}
          systemRoute.children.push(itemMenu)
        }else if(item.children.length){
          item.children.forEach(item => {
            if (item.path){
              let itemMenu = {path: item.path , name: item.name, component:() => import('../views/'+item.pagePath+'.vue')}
              systemRoute.children.push(itemMenu)
            }

          })
        }
      })
      //动态添加到现在的路由对象
      router.addRoute(systemRoute)
    }
  }
}

setRoutes()

router.beforeEach((to, from, next) => {


  //未找到路由
  if(!to.matched.length){
    const storeMenu = localStorage.getItem("menuList")
    if(storeMenu){
      next("/404")
      //清空缓存
    }else {
      //跳回登录页面
      next("/Login")
    }
  }
  //其他情况放行
  next()
})


export default router








