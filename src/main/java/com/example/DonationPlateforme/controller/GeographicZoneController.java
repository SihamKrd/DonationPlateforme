package com.example.DonationPlateforme.controller;

import java.util.List;  // Importation de List
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DonationPlateforme.model.GeographicZone;  // Importation du modèle
import com.example.DonationPlateforme.service.GeographicZoneService;  // Importation du service

@RestController
@RequestMapping("/zones")
public class GeographicZoneController {

    @Autowired
    private GeographicZoneService geographicZoneService;  
    @GetMapping
    public List<GeographicZone> getAllZones() {
        return geographicZoneService.getAllZones();
    }
}
