package com.example.DonationPlateforme.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.DonationPlateforme.model.GeographicZone;
import com.example.DonationPlateforme.repository.GeographicZoneRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private GeographicZoneRepository geographicZoneRepository;

    @Override
    public void run(String... args) throws Exception {
        if (geographicZoneRepository.count() == 0) {
            // Création des zones géographiques principales
            GeographicZone france = new GeographicZone("France");
            GeographicZone ileDeFrance = new GeographicZone("Île-de-France");
            GeographicZone paris = new GeographicZone("Paris");
            GeographicZone marseille = new GeographicZone("Marseille");

            // Définir les relations parentales
            ileDeFrance.setParentZone(france);
            paris.setParentZone(ileDeFrance);
            // Marseille appartient à France, mais pas à Île-de-France
            marseille.setParentZone(france);

            // Sauvegarder les zones géographiques dans la base de données
            geographicZoneRepository.save(france);
            geographicZoneRepository.save(ileDeFrance);
            geographicZoneRepository.save(paris);
            geographicZoneRepository.save(marseille);
        } else {
            // Ajoutez un message ou des actions supplémentaires si nécessaire
            System.out.println("Les zones géographiques par défaut existent déjà.");
        }
    }
}
