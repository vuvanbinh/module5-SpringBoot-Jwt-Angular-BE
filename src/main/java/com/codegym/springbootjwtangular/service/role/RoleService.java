package com.codegym.springbootjwtangular.service.role;

import com.codegym.springbootjwtangular.model.Role;
import com.codegym.springbootjwtangular.model.RoleName;
import com.codegym.springbootjwtangular.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService{

    @Autowired
    IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
