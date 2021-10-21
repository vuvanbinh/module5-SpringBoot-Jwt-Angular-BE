package com.codegym.springbootjwtangular.repository;

import com.codegym.springbootjwtangular.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
    Boolean existsByEmail(String email);
}
