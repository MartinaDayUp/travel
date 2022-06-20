<template>
  <div class="mcontaner">
<!--    <el-color-picker v-model="color"></el-color-picker>-->
      <Header></Header>
      <div class="m-search" style="width: 600px; margin: 0 auto">
          <el-button icon="el-icon-search" @click="submitkey(input),page(1)" style="display: inline-block"></el-button>
          <el-input v-model="input" placeholder="请输入关键字" style="display: inline-block; width: 500px; border-radius: 30px;"></el-input>
      </div>

      <div class="tag">
          搜索
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
<!--                      <div  v-html="toolTipTitle"></div>-->

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
        name: "Search.vue",
        components: {Header},
        data() {
            return {
                blogs: {},
                currentPage: 1,
                total: 0,
                pageSize: 5,
                input: ''
            }
        },
        methods: {
            submitkey(input){
                 console.log(input)
                const _this = this
                _this.$axios.get("/searchsubmit" ,{
                    params: {input: input}
                }).then(res => {

                })
            },
            page(currentPage) {
                // console.log(input)
                const _this = this
                _this.$axios.get("/searchblogs?currentPage=" + currentPage).then(res => {
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.current
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size

                })
            }
        },
        created() {
            this.page(1)
        }
    // ,
    // computed: {
    //     toolTipTitle() {
    //         let  titleIdCodeText =h4.text()//props传入的,不管怎么样，拿到这个显示的标题就是了
    //         // titleIdCodeText = titleIdCodeText.replace(/<font color=red>/g, '')
    //         // titleIdCodeText = titleIdCodeText.replace(/<\/font>/g, '')
    //         //this.searchValue为搜索框的关键词，也可以props传入
    //         titleIdCodeText = titleIdCodeText.split(this.input).join(`<font color=red>${this.searchValue}</font>`)
    //         return titleIdCodeText
    //     }

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