package com.codegym.springbootjwtangular.service.user;

import com.codegym.springbootjwtangular.model.Users;
import com.codegym.springbootjwtangular.service.IService;

import java.util.Optional;

public interface IUsersService extends IService<Users> {
    Boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
}
