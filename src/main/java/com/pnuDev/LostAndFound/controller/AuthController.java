package com.pnuDev.LostAndFound.controller;

import com.pnuDev.LostAndFound.model.User;
import com.pnuDev.LostAndFound.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            return "redirect:/login?error=password";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/login?error=exists";
        }

        User user = new User(
                email,
                passwordEncoder.encode(password)
        );

        userRepository.save(user);

        return "redirect:/login?registered";
    }
}