package com.codegym.springbootjwtangular.service.role;

import com.codegym.springbootjwtangular.model.Role;
import com.codegym.springbootjwtangular.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
