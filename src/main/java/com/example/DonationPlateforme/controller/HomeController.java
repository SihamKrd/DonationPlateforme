package com.example.DonationPlateforme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.service.AnnonceService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private AnnonceService annonceService;

    @GetMapping
    public String home(Model model) {
        List<Annonce> annonces = annonceService.findAllAnnonces();
        model.addAttribute("annonces", annonces);
        return "home";
    }
}

