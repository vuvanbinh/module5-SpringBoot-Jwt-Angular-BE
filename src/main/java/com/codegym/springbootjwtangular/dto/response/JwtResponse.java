package com.codegym.springbootjwtangular.dto.response;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    String token;
    private Long id;
    private String type = "Bearer";
    private String fullName;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;


    public JwtResponse() {
    }


    public JwtResponse(String token, Long id
            , String fullName, String avatar
            , Collection<? extends GrantedAuthority> roles) {

        this.token = token;
        this.id = id;
        this.type = type;
        this.fullName = fullName;
        this.avatar = avatar;
        this.roles = roles;

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}