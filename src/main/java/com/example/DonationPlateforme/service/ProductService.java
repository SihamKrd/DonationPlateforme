package com.example.DonationPlateforme.service;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.model.Category;
import com.example.DonationPlateforme.model.Product;
import com.example.DonationPlateforme.repository.AnnonceRepository;
import com.example.DonationPlateforme.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.DonationPlateforme.repository.CategoryRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product saveProduct(Product product) {
        // Vérifier si une annonce est associée
        if (product.getAnnonce() != null) {
            UUID annonceId = product.getAnnonce().getId();
            if (annonceId == null) {
                throw new IllegalArgumentException("L'ID de l'annonce est requis.");
            }
            Annonce annonce = annonceRepository.findById(annonceId)
                    .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée avec l'ID fourni."));
            product.setAnnonce(annonce);
        } else {
            System.out.println("Aucune annonce associée au produit. Le produit sera enregistré sans annonce.");
        }

        // Valider les catégories
        if (product.getCategories() != null && !product.getCategories().isEmpty()) {
            List<Category> validatedCategories = new ArrayList<>();
            for (Category category : product.getCategories()) {
                UUID categoryId = category.getId();
                if (categoryId == null) {
                    throw new IllegalArgumentException("Chaque catégorie doit avoir un ID valide.");
                }
                Category existingCategory = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée avec l'ID fourni : " + categoryId));
                validatedCategories.add(existingCategory);
            }
            product.setCategories(validatedCategories);
        }

        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID fourni : " + id));
    }
}
