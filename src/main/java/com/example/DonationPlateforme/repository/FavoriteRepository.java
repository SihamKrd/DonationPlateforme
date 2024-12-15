package com.example.DonationPlateforme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DonationPlateforme.model.Favorite;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    List<Favorite> findByUserId(UUID userId);
    Favorite findByUserIdAndAnnonceId(UUID userId, UUID annonceId);
    void deleteByAnnonceId(UUID annonceId);
}
