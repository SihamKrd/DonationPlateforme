package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

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

    @GetMapping("/{id}")
    public Annonce getAnnonceById(@PathVariable("id") UUID id) {
        return annonceService.findAnnonceById(id);
    }

    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceService.findAllAnnonces();
    }

    @GetMapping("/user/{userId}")
    public List<Annonce> getAnnoncesByUserId(@PathVariable("userId") UUID userId) {
        return annonceService.findAnnoncesByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnonceById(@PathVariable("id") UUID id) {
        annonceService.deleteAnnonceById(id);
    }
}
