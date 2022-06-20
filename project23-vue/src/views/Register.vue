<template>
    <div class="backg">
    <el-dialog title="注册" :visible.sync="dialogFormVisible">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" >
            <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
                <el-input maxlength="12" v-model="ruleForm.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item  label="密码" :label-width="formLabelWidth"  prop="password">
                <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" :label-width="formLabelWidth"  prop="email">
                <el-input v-model="ruleForm.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="类型" :label-width="formLabelWidth"  prop="categeory">
                <el-select v-model="ruleForm.categeory" placeholder="请选择类型">
                    <el-option label="健康和观光爱好者" value="0"></el-option>
                    <el-option label="食物与艺术爱好者" value="1"></el-option>
                    <el-option label="自然与人文爱好者" value="2"></el-option>
                    <el-option label="当地服务爱好者" value="3"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="省份" :label-width="formLabelWidth" prop="province">
                <el-select v-model="ruleForm.province" placeholder="请选择省份">
                    <el-option label="北京" value="北京"></el-option>
                    <el-option label="上海" value="上海"></el-option>
                    <el-option label="广州" value="广州"></el-option>
                    <el-option label="成都" value="成都"></el-option>
                    <el-option label="重庆" value="重庆"></el-option>
                    <el-option label=西安 value="西安"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="头像" :label-width="formLabelWidth" prop="avatar">
<!--                <el-input v-model="ruleForm.avatar" autocomplete="off"></el-input>-->
                <el-upload v-model="ruleForm.avatar"
                        action="#"
                        list-type="picture-card"
                        :auto-upload="false">
                    <i slot="default" class="el-icon-plus"></i>
                    <div slot="file" slot-scope="{file}">
                        <img
                                class="el-upload-list__item-thumbnail"
                                :src="file.url" alt=""
                        >
                        <span class="el-upload-list__item-actions">
        <span
                class="el-upload-list__item-preview"
                @click="handlePictureCardPreview(file)"
        >
          <i class="el-icon-zoom-in"></i>
        </span>
        <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleDownload(file)"
        >
          <i class="el-icon-download"></i>
        </span>
        <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleRemove(file)"
        >
          <i class="el-icon-delete"></i>
        </span>
      </span>
                    </div>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible">
                    <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>
            </el-form-item>
        </el-form>
        <div class="register-tip" style="width: 600px; color:#666666; display: inline-block">
            已有账号？<router-link to="/Login" style="color:#008e59; text-decoration: none">登录</router-link>
            <el-button type="primary" @click="submitForm('ruleForm')"
            style="position:relative; left: 240px"
            >注册</el-button>

        </div>
<!--        <div slot="footer" class="dialog-footer">-->
<!--&lt;!&ndash;            <el-button @click="dialogFormVisible = false">取 消</el-button>&ndash;&gt;-->
<!--            <el-button ><router-link to="/Login" style="color:black; text-decoration: none">取消</router-link></el-button>-->

<!--            <el-button type="primary" ><router-link to="/Login" style="color:white; text-decoration: none">确定</router-link></el-button>-->
<!--        </div>-->
    </el-dialog>
    </div>
</template>

<script>
    // import Avater from "../components/Avater";
    export default {
        name: 'Register',
        // name: "Register.vue",
        // components: {Avater},
        data() {
          var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else if(value.length<6){
                    callback(new Error('密码长度不小于6'));
                }else{
                    callback();
                }
                };
            var validatePassEmail = (rule, value, callback) => {
                var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                if (!reg.test(value)) {
                    callback(new Error('邮箱格式不正确'));
                }
                else{
                    callback();
                }
            };
            return {
                dialogFormVisible: true,
                ruleForm: {
                    username: '',
                    password: '',
                    email: '',
                    categeory: '',
                    province: '',
                    avatar: 'https://s1.ax1x.com/2022/06/08/XrC3Mq.jpg',
                    dialogImageUrl: '',
                    dialogVisible: false,
                    disabled: false
                },
                rules: {
                    password: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur'}
                    ],
                    email: [
                        {validator: validatePassEmail, trigger: 'blur'}
                    ]
                },
                formLabelWidth: '120px'
            };
        },
        methods: {
            submitForm(formName) {
                const _this = this
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //提交逻辑
                        this.$axios.post('/register', this.ruleForm).then(res => {
                            console.log(res)
                            _this.$alert('操作成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    _this.$router.push("/Login")
                                }
                            });

                        })
                    }
                    else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            handleRemove(file) {
                console.log(file);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
                console.log(file);
            },
            handleDownload(file) {
                console.log(file);
            }
        },
    }

</script>

<style>
    .backg {
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        background: url(https://s1.ax1x.com/2022/05/31/XGVQFP.jpg) center center /
    contain ;
        border-radius: 10px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
    .register-tip{
        position: relative;
        left: 50px;
    }
</style>
