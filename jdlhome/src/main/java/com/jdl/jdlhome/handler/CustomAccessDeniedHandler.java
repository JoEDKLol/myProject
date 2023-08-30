package com.jdl.jdlhome.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exc) throws IOException {
        System.out.println("권한 없는 페이지 접속 여기까지 오나");
        response.addHeader("access_denied_reason", "not_authorized");
        response.sendError(403, "Access Denied");
    }

}



