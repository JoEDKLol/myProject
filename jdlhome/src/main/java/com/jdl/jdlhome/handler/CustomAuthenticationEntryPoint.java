package com.jdl.jdlhome.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        System.out.println("인증 안했는데 접근하는 경우 로그인 제외 여기까지 오나");;
        response.addHeader("access_denied_reason", "authentication_required");
        response.sendError(403, "Access Denied");
    }

}
