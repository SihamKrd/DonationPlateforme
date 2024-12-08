package com.example.DonationPlateforme.service;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    public Annonce saveAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    public Annonce findAnnonceById(UUID id) {
        return annonceRepository.findById(id).orElse(null);
    }

    public List<Annonce> findAllAnnonces() {
        return annonceRepository.findAll();
    }

    public List<Annonce> findAnnoncesByUserId(UUID userId) {
        return annonceRepository.findByUserId(userId);
    }

    public void deleteAnnonceById(UUID id) {
        annonceRepository.deleteById(id);
    }
}
