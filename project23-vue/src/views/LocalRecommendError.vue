<template>
  <div class="mcontaner">
    <!--    <el-color-picker v-model="color"></el-color-picker>-->
    <Header></Header>
    <div style="width:180px; margin: 0 auto">
      <span ><el-link href="/login" style="font-size: 20px">您未登录，点此登录</el-link></span>
    </div>

    <div class="block">
      <el-timeline>

        <el-timeline-item :timestamp="blog.created" :color="blog.color" placement="top" v-for="blog in blogs">
          <el-card class="card">
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
              <p class="blog-collect">收藏：{{blog.collect}}</p>
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
    name: "LikeBlogsError.vue",
    components: {Header},
    methods: {
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
</style>