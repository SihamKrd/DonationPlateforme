package com.example.DonationPlateforme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DonationPlateforme.model.GeographicZone;

@Repository
public interface GeographicZoneRepository extends JpaRepository<GeographicZone, Long> {
    // Ici vous pouvez ajouter des méthodes personnalisées si nécessaire
    // Par exemple, une méthode pour trouver une zone par son nom
    Optional<GeographicZone> findByName(String name);
}
