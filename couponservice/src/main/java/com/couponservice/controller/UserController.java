package com.couponservice.controller;

import com.couponservice.model.Role;
import com.couponservice.model.User;
import com.couponservice.repo.UserRepo;
import com.couponservice.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;

@RestController
public class UserController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ModelAndView showLoginPage(){
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @GetMapping("/showReg")
    public ModelAndView showRegistrationPage(){
        ModelAndView mav = new ModelAndView("registerUser");
        return mav;
    }

    @PostMapping("/registerUser")
    public ModelAndView register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<Role> roles=new HashSet<>();
        Role adminRole = new Role();
        adminRole.setId(1L);
        roles.add(adminRole);
        user.setRoles(roles);
        userRepo.save(user);
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(String email, String password
    , HttpServletRequest request, HttpServletResponse response){
        boolean isLogin = securityService.login(email,password,request,response);
        if(isLogin){
            ModelAndView mav = new ModelAndView("index");
            return mav;
        }
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

}
