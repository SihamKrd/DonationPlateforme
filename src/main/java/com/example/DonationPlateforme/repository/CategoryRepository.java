package com.example.DonationPlateforme.repository;

import com.example.DonationPlateforme.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
