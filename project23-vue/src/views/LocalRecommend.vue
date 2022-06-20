<template>
  <div class="mcontaner">
    <el-backtop target=".mcontaner" visibility-height="100px"></el-backtop>
    <!--    <el-color-picker v-model="color"></el-color-picker>-->
    <Header></Header>

    <div class="tag">
      本地推荐
    </div>

    <div class="block">
      <el-timeline>

        <el-timeline-item :timestamp="blog.created" :color="blog.color" placement="top" v-for="blog in blogs">
          <el-card class="card" shadow="hover">
            <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}"  class="blog-img">
              <el-image
                      style="width: 300px; height: 200px"
                      :src=blog.img
                      :fit=cover>
              </el-image>
            </router-link>

            <div class="blog-content">

              <h4>
                <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}" class="blogtitile">
                  {{blog.title}}
                </router-link>

              </h4>
              <!--                  <el-button type="text" class="blog-collect">收藏：{{blog.collect}}</el-button>-->
              <div class="blog-collect" style="width: 70px; margin-top:10px">
                <el-button type="warning" icon="el-icon-star-off" circle @click="addcollect(blog.collect,blog.id)" style="display:inline-block; margin-right: 5px"></el-button>
                <p style="display:inline-block">{{blog.collect}}</p>
              </div>


              <hr>
              <p>途经：{{blog.province}}</p>
              <p class="blog-description">{{blog.description}}</p>
            </div>
          </el-card>
        </el-timeline-item>

      </el-timeline>

      <el-pagination class="mpage"
                     background
                     layout="prev, pager, next"
                     :current-page="currentPage"
                     :page-size="pageSize"
                     :total="total"
                     @current-change=page>
      </el-pagination>

    </div>
  </div>
</template>

<script>
  import Header from "../components/Header";

  export default {
    name: "Blogs.vue",
    components: {Header},
    data() {
      return {
        blogs: {},
        currentPage: 1,
        total: 0,
        pageSize: 5,
      }
    },
    methods: {
      addcollect(collect,id){
        console.log(collect)
        const _this = this
        _this.$axios.get("/collectsubmit" ,{
          params: {collect: collect,
            id:id}
        }).then(res => {
          location.reload()
          // _this.$router.replace({
          //     path: '/Blank',
          //     name: 'Blank'
          // })
        })
      },
      collect() {
        this.$message({
          showClose: true,
          message: '恭喜你，收藏成功！',
          type: 'success',
          duration: 1000
        });
      },
      page(currentPage) {
        const _this = this
        _this.$axios.get("/localblogs?currentPage=" + currentPage).then(res => {
          console.log(res)
          _this.blogs = res.data.data.records
          if(_this.blogs.length===0){
            _this.$router.push("/LocalRecommendError")
          }
          _this.currentPage = res.data.data.current
          _this.total = res.data.data.total
          _this.pageSize = res.data.data.size

        })
      }
    },

    created() {
      this.page(1)
    }
  }
</script>


<style scoped>
  .mpage {
    margin: 0 auto;
    text-align: center;
  }
  .mcontaner{
    background: linear-gradient(to right,#FEF4EB,#F0F8DE);
    overflow-x: hidden;
    z-index: -10;
  }
  .blogtitile{
    font-size: 24px;
    text-decoration: none;
  }
  .card{
    width: 1100px;
    height: 240px;
  }
  .blog-img{
    width: 300px;
    height: 200px;
    position: relative;
    float: left;
    background-color: aqua;
    display: inline-block;

  }
  image:hover{
    margin: 100px;
    transform: scale(2,2);
  }
  .blog-content{
    width: 700px;
    height: 100%;
    display: inline-block;
    position: relative;
    float: right;
  }
  h4{
    width: 600px;
    display: inline-block;
  }
  .blog-collect{
    display: inline-block;
    position: relative;
    float: right;;
  }
  .blog-description{
    font-size: 14px;
    line-height: 24px;
  }
  .tag{
    background-color: antiquewhite;
    font-size: 24px;
    writing-mode: vertical-lr;
    position: fixed;
    top: 0;
    left: 30px;
    padding: 10px 10px 15px 10px;
    border-radius: 5px;
    border: #8c939d 1px solid;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.15);
  }
</style>