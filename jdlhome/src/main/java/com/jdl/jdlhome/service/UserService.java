package com.jdl.jdlhome.service;

import com.jdl.jdlhome.dto.UserDto;
import com.jdl.jdlhome.entity.User;
import com.jdl.jdlhome.exception.AppException;
import com.jdl.jdlhome.exception.ErrorCode;
import com.jdl.jdlhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;



    public String signUp(UserDto userDto){

        //String userId = userDto.getUserId();

        userRepository.findByUserId(userDto.getUserId())
                .ifPresent(user ->{
                    //System.out.println("userArr" + userArr.toArray().length);

                    //System.out.println("userid"+user.getUserId());
                    /*
                    if(userArr.toArray().length > 0){
                        //System.out.println(userDto.getUserId() + "  duplication");
                        //System.out.println(ErrorCode.USERNAME_DUPLICATED.getHttpStatus() + "  ErrorCode");

                    }*/
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userDto.getUserId() + "  duplication");
                });




        User user = User.builder()
                .userId(userDto.getUserId())
                .password(userDto.getPassword())
                .password(encoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();



        userRepository.save(user);

        //


        return "success";
    }


}
