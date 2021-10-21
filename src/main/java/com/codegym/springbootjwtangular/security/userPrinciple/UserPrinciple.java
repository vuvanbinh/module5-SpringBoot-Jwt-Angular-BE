package com.codegym.springbootjwtangular.security.userPrinciple;

import com.codegym.springbootjwtangular.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID =1L;
    private Long id;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    private String address;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public UserPrinciple(Long id, String fullName, String email, String password, String address, String avatar
            , Collection<? extends GrantedAuthority> roles) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.avatar = avatar;
        this.roles = roles;
    }

    //Hàm build mục đích là build user ở trong request,lưu vào một vùng nhớ static
    public static UserPrinciple build(Users users){
        //Convert từ set<> sang list<>(set<AppRole> sang List<GrantedAuthority> )
        List<GrantedAuthority> authorities = users.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new UserPrinciple(
               users.getId(),
                users.getFullName(),
                users.getEmail(),
                users.getPassword(),
                users.getAddress(),
                users.getAvatar(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
