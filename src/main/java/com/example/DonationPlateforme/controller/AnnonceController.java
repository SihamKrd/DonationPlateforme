package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.model.DeliveryMode;
import com.example.DonationPlateforme.model.GeographicZone;
import com.example.DonationPlateforme.model.Product;
import com.example.DonationPlateforme.model.ProductState;
import com.example.DonationPlateforme.service.AnnonceService;
import com.example.DonationPlateforme.service.CategoryService;
import com.example.DonationPlateforme.service.GeographicZoneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GeographicZoneService geographicZoneService;

    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(@RequestBody Annonce annonceRequest) {
        try {
            Annonce savedAnnonce = annonceService.saveAnnonce(annonceRequest);
            return ResponseEntity.ok(savedAnnonce);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public String afficherAnnonces(Model model) {
        List<Annonce> annonces = annonceService.findAllAnnonces();
        System.out.println("Number of announcements retrieved: " + annonces.size());
        model.addAttribute("annonces", annonces);
        return "annonces";
    }

    @GetMapping("/{id}")
    public String afficherDetailsAnnonce(@PathVariable("id") UUID id, Model model, Authentication authentication) {
        Annonce annonce = annonceService.findAnnonceById(id);
        if (annonce == null) {
            throw new IllegalArgumentException("Annonce introuvable.");
        }
    
        // Vérifier que l'annonce a un utilisateur associé
        if (annonce.getUser() == null) {
            throw new IllegalArgumentException("L'annonce n'a pas d'utilisateur associé.");
        }
    
        // Vérifier si l'utilisateur authentifié est le propriétaire
        boolean isOwner = authentication != null && authentication.getName().equals(annonce.getUser().getEmail());
    
        model.addAttribute("annonce", annonce);
        model.addAttribute("isOwner", isOwner);
    
        return "details-annonce";
    }
    
    @DeleteMapping("/{id}")
    public String deleteAnnonceById(@PathVariable("id") UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        Annonce annonce = annonceService.findAnnonceById(id);
        if (annonce == null) {
            throw new IllegalArgumentException("Annonce introuvable.");
        }

        if (!annonce.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new SecurityException("Vous n'êtes pas autorisé à supprimer cette annonce.");
        }

        annonceService.deleteAnnonceById(id);
        return "redirect:/home";
    }


    @GetMapping("/edit-form/{id}")
    public String editAnnonceForm(@PathVariable("id") UUID id, Model model, Authentication authentication) {
        Annonce annonce = annonceService.findAnnonceById(id);
        if (annonce == null) {
            throw new IllegalArgumentException("Annonce introuvable.");
        }
        if (!annonce.getUser().getEmail().equals(authentication.getName())) {
            throw new SecurityException("Vous n'êtes pas autorisé à modifier cette annonce.");
        }
    
        model.addAttribute("annonce", annonce);
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("zones", geographicZoneService.getAllZones());
        model.addAttribute("deliveryModes", DeliveryMode.values());
        model.addAttribute("productStates", ProductState.values());
    
        // Convertir les mots-clés en chaîne
        String keywords = String.join(",", annonce.getKeywords());
        model.addAttribute("keywords", keywords);
    
        return "edit-annonce";
    }
    


    @PostMapping("/edit-form/{id}")
    public String updateAnnonce(@PathVariable("id") UUID id,
                                @ModelAttribute Annonce updatedAnnonce,
                                @RequestParam(value = "keywords", required = false) String keywords,
                                @RequestParam(value = "geographicZone.id", required = false) Long geographicZoneId,
                                Authentication authentication) {
        if (authentication == null) {
            throw new SecurityException("Vous devez être connecté pour effectuer cette action.");
        }
    
        Annonce existingAnnonce = annonceService.findAnnonceById(id);
        if (existingAnnonce == null) {
            throw new IllegalArgumentException("Annonce introuvable.");
        }
        if (!existingAnnonce.getUser().getEmail().equals(authentication.getName())) {
            throw new SecurityException("Vous n'êtes pas autorisé à modifier cette annonce.");
        }
    
        existingAnnonce.setTitle(updatedAnnonce.getTitle());
        existingAnnonce.setDescription(updatedAnnonce.getDescription());
        existingAnnonce.setDeliveryMode(updatedAnnonce.getDeliveryMode());
        existingAnnonce.getProduct().setName(updatedAnnonce.getProduct().getName());
        existingAnnonce.getProduct().setProductState(updatedAnnonce.getProduct().getProductState());
        existingAnnonce.getProduct().setCategories(updatedAnnonce.getProduct().getCategories());
    
        if (keywords != null && !keywords.trim().isEmpty()) {
            Set<String> keywordSet = new HashSet<>(List.of(keywords.split(",")));
            existingAnnonce.setKeywords(keywordSet);
        }
    
        if (geographicZoneId != null) {
            GeographicZone zone = geographicZoneService.getZoneById(geographicZoneId);
            existingAnnonce.setGeographicZone(zone);
        }
    
        annonceService.saveAnnonce(existingAnnonce);
        return "redirect:/home";
    }
    

    
    @GetMapping("/create-form")
    public String createAnnonceForm(Model model) {
        model.addAttribute("annonce", new Annonce());
        model.addAttribute("categories", categoryService.findAllCategories());
        List<GeographicZone> zones = geographicZoneService.getAllZones();
        model.addAttribute("zones", zones);
        zones.forEach(zone -> {
            System.out.println("Zone: " + zone.getName());
            zone.getSubZones().forEach(subZone -> System.out.println("  SubZone: " + subZone.getName()));
        });
        
        model.addAttribute("deliveryModes", DeliveryMode.values());
        model.addAttribute("productStates", ProductState.values());
        return "create-annonce";
    }
    
    
    
    @PostMapping("/create-form")
    public String saveAnnonce(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("productName") String productName,
                              @RequestParam("productState") ProductState productState,
                              @RequestParam(value = "categoryIds", required = false) List<UUID> categoryIds,
                              @RequestParam("deliveryMode") DeliveryMode deliveryMode,
                              @RequestParam(value = "keywords", required = false) String keywords, // `String` pour être compatible avec `split()`
                              @RequestParam("geographicZoneId") Long geographicZoneId,
                              Model model) {
        try {
            // Créer le produit
            Product product = new Product();
            product.setName(productName);
            product.setProductState(productState);
    
            // Créer l'annonce
            Annonce annonce = new Annonce();
            annonce.setTitle(title);
            annonce.setDescription(description);
            annonce.setDeliveryMode(deliveryMode);
    
            // Associer les mots-clés
            if (keywords != null && !keywords.trim().isEmpty()) { // Utilisation correcte pour vérifier une chaîne
                Set<String> keywordSet = new HashSet<>(List.of(keywords.split(","))); // Séparer les mots-clés
                annonce.setKeywords(keywordSet);
            }
    
            // Associer la zone géographique
            GeographicZone zone = geographicZoneService.getZoneById(geographicZoneId);
            annonce.setGeographicZone(zone);
    
            // Sauvegarder l'annonce et le produit associés
            annonceService.saveAnnonceWithProduct(annonce, product, categoryIds);
    
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la création de l'annonce : " + e.getMessage());
            return "create-annonce";
        }
    }
    

    @GetMapping("/user/{userId}")
    public List<Annonce> getAnnoncesByUserId(@PathVariable("userId") UUID userId) {
        return annonceService.findAnnoncesByUserId(userId);
    }

}
