package com.jdl.jdlhome.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdl.jdlhome.dto.ResponseDataCode;
import com.jdl.jdlhome.dto.ResponseDataDTO;
import com.jdl.jdlhome.dto.ResponseDataStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMessage =  exception.getMessage();
        }

        ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
        ResponseDataDTO responseDataDTO = new ResponseDataDTO();
        responseDataDTO.setCode(ResponseDataCode.ERROR);
        responseDataDTO.setStatus(ResponseDataStatus.ERROR);
        responseDataDTO.setMessage(errorMessage);


        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(responseDataDTO));
        response.getWriter().flush();


        //errorMessage= URLEncoder.encode(errorMessage,"UTF-8");//한글 인코딩 깨지는 문제 방지

        //request.setAttribute("errorMessage", errorMessage);
        //setDefaultFailureUrl("/fail?error=true&exception="+errorMessage);

//        response.addHeader("access_denied_reason", "not_authorized");
//        //response.sendError(403, "Access Denied");
//        System.out.println(request.toString());
//        System.out.println(response.toString());
//        System.out.println(exception.toString());


        //super.onAuthenticationFailure(request, response, exception);
    }


}
