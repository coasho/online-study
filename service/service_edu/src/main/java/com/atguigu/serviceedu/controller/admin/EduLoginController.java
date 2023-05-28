package com.atguigu.serviceedu.controller.admin;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceedu/user")
public class EduLoginController {
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("avatar", "http://159.75.234.20:8888/img/2022/12/20/1716523433124302856.jpeg").data("name", "zhangsan").data("roles", "[admin]");
    }

    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }

}
