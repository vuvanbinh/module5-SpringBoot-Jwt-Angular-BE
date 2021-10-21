package com.codegym.springbootjwtangular.controller;

import com.codegym.springbootjwtangular.dto.request.SignInForm;
import com.codegym.springbootjwtangular.dto.request.SignUpForm;
import com.codegym.springbootjwtangular.dto.response.JwtResponse;
import com.codegym.springbootjwtangular.dto.response.ResponseMessage;
import com.codegym.springbootjwtangular.model.Role;
import com.codegym.springbootjwtangular.model.Users;
import com.codegym.springbootjwtangular.model.RoleName;
import com.codegym.springbootjwtangular.security.jwt.JwtAuthTokenFilter;
import com.codegym.springbootjwtangular.security.jwt.JwtProvider;
import com.codegym.springbootjwtangular.security.userPrinciple.UserPrinciple;
import com.codegym.springbootjwtangular.service.role.IRoleService;
import com.codegym.springbootjwtangular.service.user.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UsersController {

    @Autowired
    IUsersService usersService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    IRoleService roleService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;



    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody SignInForm signInForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInForm.getEmail(), signInForm.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.createToken(authentication);
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(token,userPrinciple.getId(),userPrinciple.getFullName()
                    , userPrinciple.getAvatar(), userPrinciple.getAuthorities()));
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage("no"), HttpStatus.OK);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (usersService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email is existed!"), HttpStatus.OK);
        }
        Users users = new Users();
        users.setFullName(signUpForm.getFullName());
        users.setEmail(signUpForm.getEmail());
        users.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        users.setAddress(signUpForm.getAddress());
        users.setAvatar(signUpForm.getAvatar());
        Set<String> strRole = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "user":
                    Role studentRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(studentRole);
                    break;
            }
        });
        users.setRoles(roles);
        usersService.save(users);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }

    @PutMapping("/changeAvatar")
    public ResponseEntity<?>changeAvatar(HttpServletRequest request, @RequestParam("avatar")Optional<String> avatar){
        if (avatar.isPresent()){
            String jwt = jwtAuthTokenFilter.getJwt(request);
            String email = jwtProvider.getUsernameFromJwtToken(jwt);
            Users users;
            users = usersService.findByEmail(email).get();
            users.setAvatar(avatar.get());
            usersService.save(users);
            return new ResponseEntity<>(new ResponseMessage("Change avatar success!"),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseMessage("no"),HttpStatus.OK);
        }
    }




}
