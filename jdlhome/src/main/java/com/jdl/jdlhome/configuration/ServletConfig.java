package com.jdl.jdlhome.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class ServletConfig implements WebMvcConfigurer {

//    @Bean
//    public ViewResolver viewResolver(){
//        // VIEW 지정
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        // 접두사(ex./WEB-INF/views/main.jsp)
//        resolver.setPrefix("/WEB-INF/View/");
//        // 접미사
//
//        resolver.setSuffix(".html");
//
//        return resolver;
//    }

    // 주소창 입력값
    private String connectPath = "/upload_image/**";

    // 업로드 파일  위치
    private String resourcePath = "file:///C:/springbootPJ/jdlhome_img/upload_image/";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {



        // 업로드 이미지 정적 리소스 설정
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);

        // 기본 정적 파일 설정
//        registry.addResourceHandler("resources/**").addResourceLocations("classpath:/static/");
    }


}
