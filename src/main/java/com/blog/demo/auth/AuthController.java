package com.blog.demo.auth;

import com.blog.demo.user.User;
import com.blog.demo.user.UserJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    UserJDBCTemplate userJDBCTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/signup";
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        userJDBCTemplate.create(user.getUsername(), encryptedPassword, user.isEnabled());
        return "redirect:/blogs";
    }
}
