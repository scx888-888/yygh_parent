package com.atguigu.yygh.hosp.controller.admin;

import com.atguigu.yygh.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author scx
 * @Date 2022/3/2 14:42
 * @Description
 */
@RestController
@RequestMapping("/admin/user")
@CrossOrigin//跨域操作
@Api(description = "管理员登录")
public class UserLoginController {

    /**
     * 用户登录
     *
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin-token");
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    @ApiOperation(value = "查询用户信息")
    @GetMapping("/info")
    public R info() {
        return R.ok()
                .data("roles", "[\"admin\"]")
                .data("introduction", "I am a super administrator")
                .data("name", "超级管理员")
                .data("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi.qqkou.com%2Fi%2F0a3800763850x2858729958b26.jpg&refer=http%3A%2F%2Fi.qqkou.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648801567&t=feb44b4bfda3ed31c4d0297a0a537f6b")
                ;
    }

    /**
     * 退出登录
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }
}
