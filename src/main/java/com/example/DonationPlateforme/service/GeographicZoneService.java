package com.example.DonationPlateforme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DonationPlateforme.model.GeographicZone;
import com.example.DonationPlateforme.repository.GeographicZoneRepository;  // Importer le repository

@Service
public class GeographicZoneService {

    @Autowired
    private GeographicZoneRepository geographicZoneRepository;  // Injecter le repository, pas l'entité

    // Récupérer toutes les zones géographiques
    public List<GeographicZone> getAllZones() {
        return geographicZoneRepository.findAll();
    }

    // Trouver une zone par son ID
    public GeographicZone getZoneById(Long id) {
        return geographicZoneRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Zone géographique introuvable"));
    }
}
