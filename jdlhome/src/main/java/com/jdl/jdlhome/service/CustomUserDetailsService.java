package com.jdl.jdlhome.service;

import com.jdl.jdlhome.entity.PrincipalDetails;
import com.jdl.jdlhome.entity.User;
import com.jdl.jdlhome.exception.AppException;
import com.jdl.jdlhome.exception.ErrorCode;
import com.jdl.jdlhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
//formLogin.loginProcessingUrl 수행시 . UserDetailsService 를 구현체로 만든 클래스가 자동으로 수행
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //loadUserByUsername 는 form 에서 전송시(post) 폼에 있는 ID를 받음 userId는 폼에 있는 ID임.
        //User userEntity
        //Optional<User> userRepository.findByUserId(userId);

        Optional<User> findUser = userRepository.findByUserId(userId);
        if (!findUser.isPresent()) {
//            System.out.println("존재하지 않는 userid 입니다.");
            throw new UsernameNotFoundException("존재하지 않는 userid 입니다.");
            //익셉션처리해도 아무일도 일어나지 않음. 시큐리티 컨피그에서 로그인 실패시 처리되는 핸들러 적용 필요
            //throw new AppException(ErrorCode.USERNAME_DUPLICATED, "없는 ID입니다.");
        }

        PrincipalDetails principalDetails = new PrincipalDetails(findUser.get());



        return principalDetails;
    }
}
