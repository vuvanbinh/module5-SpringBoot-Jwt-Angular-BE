package com.codegym.springbootjwtangular.repository;

import com.codegym.springbootjwtangular.model.Role;
import com.codegym.springbootjwtangular.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
