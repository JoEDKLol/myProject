package com.jdl.jdlhome.configuration;


import com.jdl.jdlhome.handler.CustomAccessDeniedHandler;
import com.jdl.jdlhome.handler.CustomAuthFailureHandler;
import com.jdl.jdlhome.handler.CustomAuthenticationEntryPoint;
import com.jdl.jdlhome.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler customFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    //private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //http.csrf(AbstractHttpConfigurer::disable)
        //System.out.println("SecurityFilterChain");


        return http
                //.csrf(AbstractHttpConfigurer::disable)
                //.toString().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

                .authorizeHttpRequests((authorizeRequests) -> {
                    //authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll(); //정적자원허용
                    authorizeRequests.requestMatchers("/user/**").authenticated();
                    //authorizeRequests.requestMatchers("/write").authenticated();
                    //authorizeRequests.requestMatchers("/writeAction").authenticated();
                    //authorizeRequests.requestMatchers("/auth_test").hasRole("ADMIN");

                    authorizeRequests.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER");

                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");

                    authorizeRequests.anyRequest().permitAll();


                })

                .formLogin((formLogin) -> {
                    /* 권한이 필요한 요청은 해당 url로 리다이렉트 */
                    formLogin.loginPage("/signIn"); //get


                    formLogin.loginProcessingUrl("/signInAction"); //post
                    //userId
                    formLogin.usernameParameter("userId");
                    //formLogin.successForwardUrl("/user/user");
                    //formLogin.failureForwardUrl("/fail");
                    //로그인 성공시 핸들러 작성 해야됨
                    formLogin.failureHandler(customFailureHandler); //로그인 실패시 핸들러

                })


                .logout((logout) -> {
                    //logout.logoutUrl("/signIn");
                })
                //.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> customAccessDeniedHandler)
                //.exceptionHandling()
                //인증이 안된 사용자가 접근 할때
                .exceptionHandling(handler -> handler.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                //.exceptionHandling(handler -> handler.accessDeniedHandler(customAccessDeniedHandler))
                //.userDetailsService(userDetailsService)
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/signIn") // 세션 만료시 이동 페이지
                )

                .logout(logout -> logout.logoutUrl("/logOut")
                        .logoutSuccessHandler((request, response, authentication) -> response.sendRedirect("/siginIn")))

                .build();

    }





}
