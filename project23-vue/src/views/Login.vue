<template>
      <div class="login-container">
        <div class="login-card">
            <div class="register-tip" style="color:#666666; display: inline-block">
                没有账号？<router-link to="/Register" style="color:#c35655; text-decoration: none">注册</router-link>
            </div>
          <div class="login-scard">

            <div class="login-title">
  <!--        <el-header>-->
              <router-link to="/blogs">
                <img src="https://s1.ax1x.com/2022/06/08/Xrk0AK.png"
                     style="height: 30px; margin-top: 10px;">
              </router-link>
    <!--        </el-header>-->
            </div>
            <el-main>
              <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                       class="login-form">
                <el-form-item label="用户名" prop="username">
                  <el-input type="text" maxlength="12" v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                  <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item>
<!--                    <el-button type="success"><router-link to="/Register" style="color:white; text-decoration: none">注册</router-link></el-button>&ndash;&gt;-->
<!--                    <el-button type="primary" @click="openRegister')" >注册</el-button>-->
<!--                    <el-button type="primary" @click="dialogFormVisible = true">注册</el-button>-->
                    <el-button type="primary" @click="submitForm('ruleForm')" >登录</el-button>
                  <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
              </el-form>
          </el-main>
          </div>
        </div>
      </div>

</template>

<script>
export default {
  name: 'Login',
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        password: '',
        username: ''
      },
      rules: {
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
        // console.log(ruleForm.username)
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //提交逻辑
          this.$axios.post('/login', this.ruleForm).then((res)=>{
            const token = res.headers['authorization']
            _this.$store.commit('SET_TOKEN', token)
            _this.$store.commit('SET_USERINFO', res.data.data)
            _this.$router.push("/blogs")
          })
          // const _this = this
          // this.$axios.post('http://localhost:8081/login', this.ruleForm).then(res => {
          // console.log(res.data)
          // const jwt = res.headers['authorization']
          // const userInfo = res.data.data
          //
          // // 把数据共享出去
          // _this.$store.commit("SET_TOKEN", jwt)
          // _this.$store.commit("SET_USERINFO", userInfo)
          //
          //   console.log(_this.$store.getters.getUser)

          // })
        }
          else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  mounted() {
    this.$notify({
      title: '看这里：',
      message: '欢迎来到我的世界，亲，请先登录哦',
      duration: 1500
    });
  }
}
</script>

<style scoped>
  .login-container {
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    background: url(https://s1.ax1x.com/2022/05/31/XGVQFP.jpg) center center /
    contain ;
    border-radius: 10px;
  }
  .login-card {
    position: absolute;
    top: 130px;
    right: 355px;
    /*background: #fff;*/
    background-color: rgba(250,250,250,0.65);
    /*opacity: 65%;*/
    padding: 10px 10px;
    width: 520px;
    height: 320px;
  }
  .login-scard {
    position: relative;
    top: 20px;
    text-align: center;
    height: 270px;
    opacity: 100%;

  }
  .login-title {
    /*color: #303133;*/
    /*font-weight: bold;*/
    /*font-size: 1.2rem;*/
    text-align: center;
  }
  .login-form {
    /*background-color: rgba(250,250,250,0.65);*/
    margin-top: 10px;
    text-align: center;
    height: 200px;
  }
  .login-card button {
    position: relative;
    left: 2px;
    margin-top: 10px;
    width: 30%;
  }
  .register-tip{
    position: relative;
    left: 30px;
    top: 260px;
    z-index: 999;
  }
</style>
