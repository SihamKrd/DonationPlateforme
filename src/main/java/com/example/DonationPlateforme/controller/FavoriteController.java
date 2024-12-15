package com.example.DonationPlateforme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.example.DonationPlateforme.model.Favorite;
import com.example.DonationPlateforme.model.User;
import com.example.DonationPlateforme.service.FavoriteService;
import com.example.DonationPlateforme.service.UserService;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired 
    private UserService userService;

    @GetMapping
    public String getUserFavorites(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new SecurityException("Utilisateur non authentifié.");
        }
    
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);
    
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur introuvable.");
        }
    
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(user.getId());
        model.addAttribute("favorites", favorites);
    
        return "favorites"; 
    }
    
    

    @PostMapping("/add/{annonceId}")
    public RedirectView addToFavorites(@PathVariable UUID annonceId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new SecurityException("Utilisateur non authentifié.");
        }
    
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);
    
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur introuvable.");
        }
    
        favoriteService.addToFavorites(user.getId(), annonceId); 
        return new RedirectView("/annonces/" + annonceId);
    }
    

    @DeleteMapping("/remove/{annonceId}")
    public RedirectView removeFromFavorites(@PathVariable UUID annonceId, 
                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new SecurityException("Utilisateur non authentifié.");
        }
    
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);
    
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur introuvable.");
        }
    
        favoriteService.removeFromFavorites(user.getId(), annonceId);
    
        return new RedirectView("/annonces/" + annonceId);
    }
    
}
