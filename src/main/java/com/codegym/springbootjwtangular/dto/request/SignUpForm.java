package com.codegym.springbootjwtangular.dto.request;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpForm {
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String avatar;
    private Set<String> roles;
}
