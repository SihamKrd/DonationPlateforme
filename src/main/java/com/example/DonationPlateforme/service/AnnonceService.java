package com.example.DonationPlateforme.service;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.model.Category;
import com.example.DonationPlateforme.model.Product;
import com.example.DonationPlateforme.model.User;
import com.example.DonationPlateforme.repository.AnnonceRepository;
import com.example.DonationPlateforme.repository.CategoryRepository;
import com.example.DonationPlateforme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    public Annonce saveAnnonce(Annonce annonce) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());

        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé.");
        }

        annonce.setUser(user);

        if (annonce.getProduct() == null || annonce.getProduct().getId() == null) {
            throw new IllegalArgumentException("Un produit valide est requis pour l'annonce.");
        }

        Product product = productService.findProductById(annonce.getProduct().getId());
        if (product == null) {
            throw new IllegalArgumentException("Le produit spécifié n'existe pas.");
        }

        annonce.setProduct(product);

        return annonceRepository.save(annonce);
    }

    public Annonce saveAnnonceWithProduct(Annonce annonce, Product product, List<UUID> categoryIds) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
    
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé.");
        }
    
        annonce.setUser(user);
    
        if (categoryIds != null && !categoryIds.isEmpty()) {
            List<Category> validatedCategories = new ArrayList<>();
            for (UUID categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée avec l'ID : " + categoryId));
                validatedCategories.add(category);
            }
            product.setCategories(validatedCategories);
        }
    
        product = productService.saveProduct(product);
    
        annonce.setProduct(product);
    
        return annonceRepository.save(annonce);
    }
    
    

    public Annonce findAnnonceById(UUID id) {
        return annonceRepository.findById(id).orElse(null);
    }

    public List<Annonce> findAllAnnonces() {
        return annonceRepository.findAll();
    }

    public List<Annonce> findAnnoncesByUserId(UUID userId) {
        return annonceRepository.findByUserId(userId);
    }

    public void deleteAnnonceById(UUID id) {
        annonceRepository.deleteById(id);
    }

    public void updateAnnonce(UUID id, Annonce updatedAnnonce, String authenticatedUserEmail) {
        Annonce existingAnnonce = findAnnonceById(id);
        if (existingAnnonce == null) {
            throw new IllegalArgumentException("Annonce introuvable.");
        }

        if (!existingAnnonce.getUser().getEmail().equals(authenticatedUserEmail)) {
            throw new SecurityException("Vous n'êtes pas autorisé à modifier cette annonce.");
        }

        existingAnnonce.setTitle(updatedAnnonce.getTitle());
        existingAnnonce.setDescription(updatedAnnonce.getDescription());
        existingAnnonce.setProduct(updatedAnnonce.getProduct());

        annonceRepository.save(existingAnnonce);
    }
}
