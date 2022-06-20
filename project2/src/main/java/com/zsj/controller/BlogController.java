package com.zsj.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsj.common.lang.Result;
import com.zsj.entity.Blog;
import com.zsj.service.BlogService;
import com.zsj.service.UserService;
import com.zsj.util.JwtUtils;
import com.zsj.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
public class BlogController {
    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    JwtUtils jwtUtils;

    public String keywords;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);
    }


    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
//        Assert.isTrue(false, "公开版不能任意编辑！");
        Blog temp = null;
        System.out.println(blog.getId());
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

            if(blog.getCollect()==null){
                temp.setCollect((long) 0);
            }
            Long a = blog.getCategeory();
            temp.setCategeory(a);
            System.out.println("新的类" +a);
            if (a == 0) {
                temp.setColor("#ABDDCD");
            } else if (a == 1) {
                temp.setColor("#f89155");
            } else if (a == 2) {
                temp.setColor("#5e90b8");
            } else if (a == 3) {
                temp.setColor("#FFA2BE");
            }
            BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status","categeory","color");
            blogService.saveOrUpdate(temp);
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);


        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        }

        return Result.succ(null);
    }

    @GetMapping("/blog/delete")
    public Result delete(Long id) {
        System.out.println(id);
//        Assert.isTrue(false, "公开版不能任意编辑！");
        blogService.removeById(id);
        System.out.println("成功删除");
        return Result.succ(null);
    }

    @GetMapping("/hotblogs")
    public Result hotblogslist(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("collect"));

        return Result.succ(pageData);
    }

    @GetMapping("/searchsubmit")
    public Result searchsubmit(String input) {
        System.out.println("input " +input);
        keywords=input;
        return Result.succ(null);
    }

    @GetMapping("/searchblogs")
    public Result searchblogslist(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 5);
        System.out.println("keywords "+keywords);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().like("content",keywords));

        return Result.succ(pageData);
    }

    @GetMapping("/collectsubmit")
    public Result collectsubmit(Long collect,Long id) {
        collect = collect + 1;
        System.out.println("collect " +collect);
        System.out.println("id " +id);
        Blog blog = blogService.getOne(new QueryWrapper<Blog>().eq("id", id));
        blog.setCollect(collect);
        blogService.saveOrUpdate(blog);
        return Result.succ(null);
    }


}
