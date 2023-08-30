package com.jdl.jdlhome.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends Common  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "user_id")
    private String userId;
    private String password;
    private String name;
    private String email;
    private String role;




}
