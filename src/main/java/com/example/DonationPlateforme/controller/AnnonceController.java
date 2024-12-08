package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @PostMapping
    public Annonce createAnnonce(@RequestBody Annonce annonce) {
        return annonceService.saveAnnonce(annonce);
    }

    @GetMapping("/{id}")
    public Annonce getAnnonceById(@PathVariable UUID id) {
        return annonceService.findAnnonceById(id);
    }

    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceService.findAllAnnonces();
    }

    @GetMapping("/user/{userId}")
    public List<Annonce> getAnnoncesByUserId(@PathVariable UUID userId) {
        return annonceService.findAnnoncesByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnonceById(@PathVariable UUID id) {
        annonceService.deleteAnnonceById(id);
    }
}
