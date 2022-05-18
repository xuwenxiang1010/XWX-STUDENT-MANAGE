<template>
  <div>
    <div style="margin: 10px 0">
      <el-form :inline="true" :model="filters" ref="filters">
        <el-form-item prop="code">
          <el-input v-model="filters.code" style="width: 150px" placeholder="请输入索引名称" suffix-icon="el-icon-set-up" class="ml-5" clearable></el-input>
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
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table :data="tableData"
              border stripe max-height="70%"
              :header-cell-class-name="headerBG">
      <el-table-column type="selection" width="55" align="center">
      </el-table-column>
      <el-table-column prop="code" align="center" label="索引">
      </el-table-column>
      <el-table-column prop="description" align="center" label="描述">
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="60px">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-edit" @click="genItem(scope.row.code)">字典值</el-button>
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

    <el-dialog title="新增索引" :visible.sync="dialogAdd" width="30%">
      <el-form label-width="70px">
        <el-form-item label="索引">
          <el-input v-model="form.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAdd = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="编辑索引" :visible.sync="dialogEdit" width="30%">
      <el-form label-width="70px">
        <el-form-item label="索引">
          <el-input v-model="form.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelUpdate">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="字典值" :visible.sync="dialogItem" width="40%">
      <el-table :data="items">
        <el-table-column prop="text" align="center" label="名称" width="150"></el-table-column>
        <el-table-column prop="value" align="center" label="值" width="200"></el-table-column>
        <el-table-column align="center" label="操作" min-width="60px">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="editItem(scope.row)">编辑</el-button>
            <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="removeItem(scope.row.id)"
            >
              <el-button type="text" icon="el-icon-delete" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addItem()">新增</el-button>
        <el-button >取 消</el-button>
        <el-button type="primary">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="新增字典" :visible.sync="dialogItemAdd" width="30%">
      <el-form label-width="70px">
        <el-form-item label="字典名称">
          <el-input v-model="formItem.text" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="字典值">
          <el-input v-model="formItem.value" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="">取 消</el-button>
        <el-button type="primary" >确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="编辑字典" :visible.sync="dialogItemEdit" width="30%">
      <el-form label-width="70px">
        <el-form-item label="字典名称">
          <el-input v-model="formItem.text" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="字典值">
          <el-input v-model="formItem.value" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button >取 消</el-button>
        <el-button type="primary" >确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {
  name: "Dict",
  data(){
    return{
      filters: {
        code: ""
      },
      form:{},
      formItem:{},
      dialogAdd: false,
      dialogEdit: false,
      dialogItem: false,
      dialogItemAdd: false,
      dialogItemEdit: false,
      tableData: [],
      items: [],

      total: 0,
      pageNum: 1,
      pageSize: 5,
      multipleSelection: [],
      headerBG: 'headerBG',
      url: {
        pageList: "/system/dict/pageList",
        add: "/system/dict/add",
        update: "/system/dict/update",
        remove: "/system/dict/delete/",
        getItem: "/system/dict/getItem/",
        addItem: "/system/dict/addItem"
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
          code: this.filters.code
        }
      }).then(res=>{
        console.log(res)
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

    /* todo:新增字典、编辑字典、删除字典方法
    */
    genItem(code){
      this.request.post(this.url.getItem + code).then(res => {
        if (res.code === '200'){

          this.dialogItem = true
          this.items = res.data
          this.formItem.itemCode = code
          console.log(code)
        }
      })
    },
    addItem(){
      this.dialogItemAdd = true
      this.formItem = {}
    },
    editItem(row){
      this.dialogItemEdit = true
      this.formItem = row
    }

  }
}
</script>

<style scoped>
.headerBG{
  background: #eeeeee!important;
}
</style>
