package com.example.DonationPlateforme.repository;

import com.example.DonationPlateforme.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnnonceRepository extends JpaRepository<Annonce, UUID> {
    List<Annonce> findByUserId(UUID userId);
    List<Annonce> findByTitleContainingIgnoreCase(String keyword);
}
