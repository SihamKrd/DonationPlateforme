package com.example.DonationPlateforme.service;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.model.Product;
import com.example.DonationPlateforme.model.User;
import com.example.DonationPlateforme.repository.AnnonceRepository;
import com.example.DonationPlateforme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Annonce saveAnnonce(Annonce annonce) {
    UUID userId = annonce.getUser() != null ? annonce.getUser().getId() : null;
    if (userId == null) {
        throw new IllegalArgumentException("L'ID de l'utilisateur est requis.");
    }

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID fourni : " + userId));
    annonce.setUser(user);

    System.out.println("user ok");
    // Vérifier les produits associés
    if (annonce.getProducts() != null && !annonce.getProducts().isEmpty()) {
        List<Product> validProducts = new ArrayList<>();
        for (Product product : annonce.getProducts()) {
            System.out.println("boucle liste produits");
            if (product.getId() != null) {
                // verifier qu'il existe dans la base
                Product existingProduct = productService.findProductById(product.getId());
                existingProduct.setAnnonce(annonce);
                validProducts.add(existingProduct);
            } else {
                throw new IllegalArgumentException("Un produit avec un ID valide est requis.");
            }
        }

        annonce.setProducts(validProducts);
    } else {
        // Si aucun produit n'est fourni, initialiser une liste vide
        annonce.setProducts(new ArrayList<>());
    }
    if (annonce.getDeliveryMode() == null) {
        throw new RuntimeException("Le mode de remise doit être spécifié");
    } 
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
}
