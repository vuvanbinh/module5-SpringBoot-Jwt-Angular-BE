package com.codegym.springbootjwtangular.security.userPrinciple;

import com.codegym.springbootjwtangular.model.Users;
import com.codegym.springbootjwtangular.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    IUsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with -> username or mail:"+email)
        );
        return UserPrinciple.build(users);
    }


}
