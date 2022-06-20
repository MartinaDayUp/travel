//package com.zsj.controller;
//
//
//import com.zsj.common.lang.Result;
//import com.zsj.entity.User;
//import com.zsj.service.UserService;
//import org.apache.shiro.authz.annotation.RequiresAuthentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author zsj
// * @since 2022-03-15
// */
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    UserService userService;
//
//    @RequiresAuthentication
//    @GetMapping("/index")
//    public Result index() {
//        User user = userService.getById(2L);
//        return Result.succ(user);
//    }
//
//    @PostMapping("/save")
//    public Result save(@Validated @RequestBody User user) {
//        return Result.succ(user);
//    }
//}
