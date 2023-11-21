package com.example.securityapp.security;

import com.example.securityapp.model.State;
import com.example.securityapp.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor

public class CustomUserDetails implements UserDetails {
    private int userId;
    private String fullName;
    private String userName;
    @JsonIgnore
    private String passWord;
    private String email;
    private String code;
    private State state;
    private Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    //Tu thong tin user chuyen sang thong tin CustomUserDetails
    public static CustomUserDetails mapUserToUserDetail(User user) {
        //Lay cac quyen tu doi tuong user
        List<GrantedAuthority> listAuthorities = user.getListRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                .collect(Collectors.toList());
        //Tra ve doi tuong CustomUserDetails
        return new CustomUserDetails(
                user.getUserId(),
                user.getFullName(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getCode(),
                user.getState(),
                listAuthorities
        );
    }
    @Override
    public String getPassword() {
        return this.passWord;
    }
    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getUserId() {
        return this.userId;
    }
}