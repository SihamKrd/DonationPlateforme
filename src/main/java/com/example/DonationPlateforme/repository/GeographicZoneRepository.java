package com.example.DonationPlateforme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DonationPlateforme.model.GeographicZone;

@Repository
public interface GeographicZoneRepository extends JpaRepository<GeographicZone, Long> {
    Optional<GeographicZone> findByName(String name);
}
