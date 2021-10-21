package com.codegym.springbootjwtangular.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String avatar;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
