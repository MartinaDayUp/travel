<template>
  <div class="mcontaner">
    <Header></Header>

    <div class="m-content">

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <div class="cat">
          <p style="display: inline-block">类别：</p>
          <el-select v-model="ruleForm.categeory" clearable placeholder="请选择类别">
            <el-option
                    v-for="item in catoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
          </el-select>
        </div>

        <div class="province">
          <p style="display: inline-block">所属省份：</p>
          <el-select v-model="ruleForm.province" placeholder="请选择所属省份">
            <el-option-group
                    v-for="group in options"
                    :key="group.label"
                    :label="group.label">
              <el-option
                      v-for="item in group.options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-option-group>
          </el-select>
        </div>

        <el-form-item label="封面图链接" prop="img">
          <el-input type="textarea" v-model="ruleForm.img"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>

        <el-button class="crefix" type="primary" @click="submitForm('ruleForm')"><p>立即创建</p></el-button>

      </el-form>

    </div>



  </div>
</template>

<script>
import Header from "../components/Header";
export default {
  name: "BlogEdit.vue",
  components: {Header},
  data() {
    return {
      catoptions: [{
        value: 0,
        label: '健康和观光：大多数，舞蹈俱乐部、游泳池、健身房、面包房、美容和水疗中心、咖啡馆、景点、纪念碑和花园等'
      }, {
        value: 1,
        label: '食物与艺术类：如酒店和果汁吧等'
      }, {
        value: 2,
        label: '自然与人文类：如海滩和公园，博物馆和剧院等'
      }, {
        value: 3,
        label: '当地服务：如动物园，商场，高档餐厅和酒吧等'
      }],
      options: [{
        label: '热门城市',
        options: [{
          value: '上海',
          label: '上海'
        }, {
          value: '北京',
          label: '北京'
        }]
      }, {
        label: '城市名',
        options: [{
          value: '成都',
          label: '成都'
        }, {
          value: '杭州',
          label: '杭州'
        }, {
          value: '广州',
          label: '广州'
        }, {
          value: '重庆',
          label: '重庆'
        }, {
          value: '西安',
          label: '西安'
        }, {
          value: '长沙',
          label: '长沙'
        }, {
          value: '三亚',
          label: '三亚'
        }, {
          value: '青岛',
          label: '青岛'
        }, {
          value: '深圳',
          label: '深圳'
        }]
      }],
      ruleForm: {
        id: '',
        title: '',
        description: '',
        categeory: '',
        province: '',
        img: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 3, max: 30, message: '长度在 3 到 30 个字符', trigger: 'blur' }
        ],
        category: [
          { required: true, message: '请输入类别', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入摘要', trigger: 'blur' }
        ],
        content: [
          { trequired: true, message: '请输入内容', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          console.log(this.ruleForm)
          this.$axios.post('/blog/edit', this.ruleForm, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            console.log(res)
            _this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                _this.$router.push("/blogs")
              }
            });

          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    const blogId = this.$route.params.blogId
    console.log(blogId)
    const _this = this
    if(blogId) {
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.ruleForm.id = blog.id
        _this.ruleForm.title = blog.title
        _this.ruleForm.categeory = blog.categeory
        _this.ruleForm.province = blog.province
        _this.ruleForm.img = blog.img
        _this.ruleForm.description = blog.description
        _this.ruleForm.content = blog.content
      })
    }
  }
}
</script>

<style scoped>
  .mcontaner{
    background: linear-gradient(to right,#FEF4EB,#F0F8DE);
  }
.m-content {
  text-align: center;
  margin: 0 auto;
  width: 85%;
}
.cat{
  margin: 5px 20px 25px;
  width: 36%;
  display: inline-block;
}
  .province{
    margin: 5px 20px 25px;
    width: 36%;
    display: inline-block;
  }
  .crefix{
    position: fixed;
    top: 50%;
    right: 10px;
    text-align: center;

  }
</style>