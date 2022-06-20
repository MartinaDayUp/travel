<template>

  <div class="backg">
    <el-backtop target=".backg" ></el-backtop>
    <Header></Header>

    <div class="mblog">
      <h2 style="display: inline-block"> {{ blog.title }}</h2>
      <p class="blog-collect">收藏：{{blog.collect}}</p>
      <div class="fix" >
        <el-button class="el-icon-star-off" @click="addcollect(blog.collect,blog.id)"></el-button>
<!--        <i class="el-icon-star-off" @click="addcollect"></i>-->
      </div>
      <el-link icon="el-icon-edit" v-if="ownBlog" style="display: block">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >
          编辑
        </router-link>
      </el-link>
      <el-divider></el-divider>
      <el-button icon="el-icon-delete" v-if="ownBlog" type="text" @click="deletel(blog.id)">删除</el-button>
      <div class="markdown-body" v-html="blog.content"></div>

    </div>
  </div>
</template>

<script>
import 'github-markdown-css'
import Header from "../components/Header";

export default {
  name: "BlogDetail.vue",
  components: {Header},
  data() {
    return {
      blog: {
        id: "",
        title: "",
        content: "",
        collect:""
      },
      ownBlog: false
    }
  },
  methods:{
    deletel(id) {
      this.$message({
        message: '删除成功',
        type: 'success'
      });
      console.log(id)
      const _this = this
      _this.$axios.get("/blog/delete" ,{
        params: {
          id:id}
      }).then(res => {
        _this.$router.push("/blogs")
      })
    },
    addcollect(collect,id){
      this.$message({
        message: '恭喜你，收藏成功',
        type: 'success'
      });
      console.log(collect)
      if(collect===null){
        collect=0
      }
      const _this = this
      _this.$axios.get("/collectsubmit" ,{
        params: {collect: collect,
        id:id}
      }).then(res => {
        location.reload()
        // _this.$router.replace({
        //   path: '/Blank',
        //   name: 'Blank'
        // })
      })
    }
  },
  created() {
    const blogId = this.$route.params.blogId
    console.log(blogId)
    const _this = this
    this.$axios.get('/blog/' + blogId).then(res => {
      const blog = res.data.data
      _this.blog.id = blog.id
      _this.blog.title = blog.title
      _this.blog.collect = blog.collect
      var MardownIt = require("markdown-it")
      var md = new MardownIt()

      var result = md.render(blog.content)
      _this.blog.content = result
      _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)

    })
  }
}
</script>

<style scoped>
  .backg{
    /*background: url(https://s1.ax1x.com/2022/05/31/XGVQFP.jpg) center center /*/
    /*contain ;*/
    background: linear-gradient(to right,#FEF4EB,#F0F8DE);
    opacity: 90%;
    overflow-x: hidden;
    z-index: -10;
  }
  .mblog {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.25);
    border-radius: 4px;
    width: 80%;
    min-height: 700px;
    padding: 20px 30px;
    margin: 10px auto;
    background: url("http://img1.qunarzz.com/travel/d1/1610/e7/c1b16fa3064be1b5.jpg");

  }
  .markdown-body{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.15);
    padding: 20px;
  }
  .blog-collect{
    display: inline-block;
    background-color: #ffffff;
    border-radius: 5px;
    position: absolute;
    top: 250px;
    right: 200px;
    padding: 5px;
    font-size: 16px;
  }
  .fix{
    position: fixed;
    right: 33px;
    bottom: 80px;
  }

</style>