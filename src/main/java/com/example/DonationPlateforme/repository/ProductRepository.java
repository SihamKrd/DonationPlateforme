package com.example.DonationPlateforme.repository;

import com.example.DonationPlateforme.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {}
