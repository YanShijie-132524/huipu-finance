package com.qst.finance.controller.user;

import com.qst.finance.common.R;
import com.qst.finance.controller.user.dto.request.LoginParam;
import com.qst.finance.controller.user.dto.response.LoginDto;
import com.qst.finance.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2025/08/09 18:08
 * @Created by YanShijie
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public R<LoginDto> login(@RequestBody LoginParam loginParam) {
        R<LoginDto> success = R.success(userService.login(loginParam));
        System.out.println("Hello,World");
        return success;
    }

}
