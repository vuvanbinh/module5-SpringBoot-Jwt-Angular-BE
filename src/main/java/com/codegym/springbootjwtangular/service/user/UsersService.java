package com.codegym.springbootjwtangular.service.user;

import com.codegym.springbootjwtangular.model.Users;
import com.codegym.springbootjwtangular.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService implements IUsersService{

    @Autowired
    IUsersRepository usersRepository;

    @Override
    public Iterable<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public void save(Users users) {
        usersRepository.save(users);
    }

    @Override
    public void remove(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
