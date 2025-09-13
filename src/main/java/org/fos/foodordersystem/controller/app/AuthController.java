package org.fos.foodordersystem.controller.app;

import lombok.RequiredArgsConstructor;
import org.fos.foodordersystem.config.UserLoggedInConfig;
import org.fos.foodordersystem.model.response.BaseResponse;
import org.fos.foodordersystem.model.response.LoginRequestRecord;
import org.fos.foodordersystem.service.app.AuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody LoginRequestRecord request) {
        return BaseResponse.ok(null, authService.login(request));
    }

    @GetMapping("logout")
    public BaseResponse<?> logout(@AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        var userLoggedIn = userLoggedInConfig.getUser();
        authService.logout(userLoggedIn);
        return BaseResponse.ok("Berhasil logout", null);
    }

}
