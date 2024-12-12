package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.dto.RegistrationDto;
import com.example.DonationPlateforme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute RegistrationDto registrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register"; // Retourner à la page du formulaire avec les erreurs.
        }
        userService.saveUser(registrationDto);
        return "redirect:/auth/login"; // Rediriger après succès.
    }


    @GetMapping("/success")
    @ResponseBody
    public String loginSuccess() {
        return "Login successful!";
    }

}
