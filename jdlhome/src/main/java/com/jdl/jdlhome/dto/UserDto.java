package com.jdl.jdlhome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;


@AllArgsConstructor
@Getter
public class UserDto {
    private String userId;
    private String password;
    private String name;
    private String email;


}
