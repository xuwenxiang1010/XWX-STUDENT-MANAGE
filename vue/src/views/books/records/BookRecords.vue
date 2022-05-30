<template>
  <div>
    <div style="margin: 10px 0">
      <el-form :inline="true" :model="filters" ref="filters">
        <el-form-item prop="bookCode">
          <el-input v-model="filters.bookCode" style="width: 150px" placeholder="请输入图书编号" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
        </el-form-item>
        <el-form-item prop="bookName">
          <el-input v-model="filters.bookName" style="width: 150px" placeholder="请输入图书名称" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
        </el-form-item>
        <el-form-item prop="bookNature">
          <el-select v-model="filters.bookNature" style="width: 150px" placeholder="请选择图书性质" suffix-icon="el-icon-set-up" class="ml-5" clearable>
            <el-option value="">请选择</el-option>
            <el-option value="1">儿童读物</el-option>
            <el-option value="2">成人读物</el-option>
            <el-option value="3">R18读物</el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="bookCategory">
          <el-select v-model="filters.bookCategory" style="width: 150px" placeholder="请选择图书分类" suffix-icon="el-icon-set-up" class="ml-5" clearable>
            <el-option value="">请选择</el-option>
            <el-option value="1">科幻</el-option>
            <el-option value="2">玄幻</el-option>
            <el-option value="3">奇幻</el-option>
            <el-option value="4">武侠</el-option>
            <el-option value="5">仙侠</el-option>
            <el-option value="6">都市</el-option>
            <el-option value="7">游戏</el-option>
            <el-option value="8">灵异</el-option>
            <el-option value="9">历史</el-option>
            <el-option value="10">军事</el-option>
            <el-option value="11">职场</el-option>
            <el-option value="12">体育</el-option>
            <el-option value="13">同人</el-option>
            <el-option value="14">轻小说</el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="start">
          <el-date-picker v-model="filters.start" style="width: 150px" placeholder="请选择开始时间" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-date-picker>
        </el-form-item>
        <el-form-item prop="end">
          <el-date-picker v-model="filters.end" style="width: 150px" placeholder="请选择结束时间" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-date-picker>
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
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>


    <el-table :data="tableData"
              border stripe max-height="70%"
              :header-cell-class-name="headerBG">
      <el-table-column prop="bookCode" align="center" label="图书编号">
      </el-table-column>
      <el-table-column prop="bookName" align="center" label="图书名称">
      </el-table-column>
      <el-table-column prop="position" align="center" label="图书位置">
      </el-table-column>
      <el-table-column prop="natureName" align="center" label="图书性质">
      </el-table-column>
      <el-table-column prop="categoryName" align="center" label="图书类别">
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
      <el-form label-width="120px">
        <el-form-item label="图书编号">
          <el-input v-model="form.bookCode" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="请输入图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="请选择图书性质">
          <el-select v-model="form.bookNature" placeholder="请选择图书性质">
            <el-option v-for="item in natureList"
                       :label="item.text"
                       :key="item.value"
                       :value="item.value">
              <i :class="item.text"/> {{item.text}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请选择图书类别">
          <el-select v-model="form.bookCategory" placeholder="请选择图书分类">
            <el-option v-for="item in categoryList"
                       :label="item.text"
                       :key="item.value"
                       :value="item.value">
              <i :class="item.text"/> {{item.text}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图书馆">
          <el-select clearable v-model="form.libId" placeholder="请选择图书馆" @change="getOther(form.libId)">
            <el-option v-for="item in librariesList"
                       :label="item.name"
                       :key="item.id"
                       :value="item.id">
              <i :class="item.name"/> {{item.name}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请选择楼层">
            <el-select v-model="form.flower" autocomplete="off">
              <el-option v-for="item in floorList"
                         :label="item.label"
                         :key="item.code"
                         :value="item.code"><i :class="item.label"/> {{item.label}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请选择房间">
            <el-select v-model="form.room" autocomplete="off">
              <el-option v-for="item in roomList"
                         :label="item.label"
                         :key="item.code"
                         :value="item.code"><i :class="item.label"/> {{item.label}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请选择书架">
            <el-select v-model="form.bookShelf" autocomplete="off">
              <el-option v-for="item in shelfList"
                         :label="item.label"
                         :key="item.code"
                         :value="item.code"><i :class="item.label"/> {{item.label}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请选择书架层数">
            <el-select v-model="form.layer" autocomplete="off">
              <el-option v-for="item in layerList"
                         :label="item.label"
                         :key="item.code"
                         :value="item.code"><i :class="item.label"/> {{item.label}}
              </el-option>
            </el-select>
          </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAdd = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑图书" :visible.sync="dialogEdit" width="30%">
      <el-form label-width="120px">
        <el-form-item label="图书编号">
          <el-input v-model="form.bookCode" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="请输入图书名称">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="请选择图书性质">
          <el-select v-model="form.bookNature" placeholder="请选择图书性质">
            <el-option v-for="item in natureList"
                       :label="item.text"
                       :key="item.value"
                       :value="item.value">
              <i :class="item.text"/> {{item.text}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请选择图书类别">
          <el-select v-model="form.bookCategory" placeholder="请选择图书分类">
            <el-option v-for="item in categoryList"
                       :label="item.text"
                       :key="item.value"
                       :value="item.value">
              <i :class="item.text"/> {{item.text}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图书馆">
          <el-select clearable v-model="form.libId" placeholder="请选择图书馆" @change="getOther(form.libId)">
            <el-option v-for="item in librariesList"
                       :label="item.name"
                       :key="item.id"
                       :value="item.id">
              <i :class="item.name"/> {{item.name}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请选择楼层">
          <el-select v-model="form.flower" autocomplete="off">
            <el-option v-for="item in floorList"
                       :label="item.label"
                       :key="item.code"
                       :value="item.code"><i :class="item.label"/> {{item.label}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请选择房间">
          <el-select v-model="form.room" autocomplete="off">
            <el-option v-for="item in roomList"
                       :label="item.label"
                       :key="item.code"
                       :value="item.code"><i :class="item.label"/> {{item.label}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请选择书架">
          <el-select v-model="form.bookShelf" autocomplete="off">
            <el-option v-for="item in shelfList"
                       :label="item.label"
                       :key="item.code"
                       :value="item.code"><i :class="item.label"/> {{item.label}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请选择书架层数">
          <el-select v-model="form.layer" autocomplete="off">
            <el-option v-for="item in layerList"
                       :label="item.label"
                       :key="item.code"
                       :value="item.code"><i :class="item.label"/> {{item.label}}
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
    import request from "../../../utils/request";

    export default {
        name: "BookRecords",
        data(){
            return{
                filters: {
                    bookCode: "",
                    bookName:"",
                    position:"",
                    natureName:"",
                    categoryName:""
                },
                form:{},
                librariesList:[],
                floorList:[],
                roomList:[],
                shelfList:[],
                layerList:[],
                natureList:[],
                categoryList:[],
                dialogAdd: false,
                dialogEdit: false,
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 5,
                multipleSelection: [],
                headerBG: 'headerBG',
                url: {
                    list: "/books/records/list",
                    add: "/books/records/add",
                    edit: "/books/records/update",
                    remove: "/books/records/delete/",
                    getCode:"/books/records/getCode",
                    getLibId:"/books/records/getLibId",
                    getOther:"/books/records/getOther/",
                    getDict:"/system/dict/getItem/",
                },
                props:{
                    label : 'name',
                },
                expands:[],
                checks:[],
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
                        bookCode: this.filters.bookCode,
                        bookName:this.filters.bookName,
                        bookNature:this.filters.bookNature,
                        bookCategory:this.filters.bookCategory,
                        start:this.filters.start,
                        end:this.filters .end,
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
                request.get(this.url.getCode).then(res => {
                    if (res){
                        this.form = {}
                        this.form.bookCode = res.message
                        request.get(this.url.getLibId).then(lib =>{
                            if (lib){
                                this.librariesList = lib.data
                            }
                        })
                        request.post(this.url.getDict + "book_nature").then(nature=>{
                            if(nature){
                                  this.natureList = nature.data
                            }
                        })
                        request.post(this.url.getDict + "book_category").then(cate=>{
                            if(cate){
                                this.categoryList = cate.data
                            }
                        })
                        this.dialogAdd = true
                    }
                })
            },
            handleEdit(row){
                this.dialogEdit = true
                this.form = row
                request.get(this.url.getLibId).then(lib =>{
                    if (lib){
                        this.librariesList = lib.data
                    }
                })
                request.post(this.url.getDict + "book_nature").then(nature=>{
                    if(nature){
                        this.natureList = nature.data
                    }
                })
                request.post(this.url.getDict + "book_category").then(cate=>{
                    if(cate){
                        this.categoryList = cate.data
                    }
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
                this.request.delete(this.url.remove + id).then(res =>{
                    if (res){
                        this.$message.success("删除成功")
                        this.load()
                    }else {
                        this.$message.error("删除失败")
                    }
                })
            },
            getOther(libId){
                this.request.post(this.url.getOther + libId).then(res=>{
                    if (res){
                        this.floorList = res.data.floorList
                        this.roomList = res.data.roomList
                        this.shelfList = res.data.shelfList
                        this.layerList = res.data.layerList
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
