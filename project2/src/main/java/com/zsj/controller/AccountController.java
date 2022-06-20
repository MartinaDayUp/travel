package com.zsj.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsj.common.dto.LoginDto;
import com.zsj.common.lang.Result;
import com.zsj.entity.Blog;
import com.zsj.entity.User;
import com.zsj.service.BlogService;
import com.zsj.service.UserService;
import com.zsj.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    JwtUtils jwtUtils;

    public Long userCat;

    public String province;

    @PostMapping("/register")
    public Result register(@Validated @RequestBody User user, HttpServletResponse response) {
        User temp =null;
        temp = new User();

        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setCreated(LocalDateTime.now());
        temp.setLastLogin(LocalDateTime.now());
        temp.setStatus(0);

        BeanUtil.copyProperties(user, temp, "id", "last_login", "created", "status","password");
        userService.save(temp);
        return Result.succ(null);
    }

    @PostMapping("/saveUserInfo")
    public Result saveUserInfo(@Validated @RequestBody User user, HttpServletResponse response) {
        System.out.println(user);
//        temp = userService.getById(user.getId());
        User u = userService.getOne(new QueryWrapper<User>().eq("username",user.getUsername()));
        Long id = u.getId();
        user.setId(id);
        User temp =null;
        temp = new User();

        temp.setPassword(SecureUtil.md5(user.getPassword()));
//        temp.setCreated(LocalDateTime.now());
        temp.setLastLogin(LocalDateTime.now());
        temp.setStatus(0);

        BeanUtil.copyProperties(user, temp,  "last_login", "status","password");
        userService.saveOrUpdate(temp);
        return Result.succ(null);
    }

    @GetMapping("/user/{id}")
    public Result showUserInfo(@PathVariable(name = "id") Long id) {
//        User user = userService.getOne(new QueryWrapper<>(  User).eq("id",id));
        User user = userService.getById(id);

        return Result.succ(user);
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }
        userCat = user.getCategeory();
        System.out.println(userCat);

        province = user.getProvince();
        System.out.println(province);

        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    @GetMapping("/likeblogs")
    public Result likeblogslist(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        System.out.println("userCat"+userCat);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().eq("categeory",userCat));
        return Result.succ(pageData);
    }

    @GetMapping("/localblogs")
    public Result localblogslist(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        System.out.println("pro"+province);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().eq("province",province));

        return Result.succ(pageData);
    }




}
