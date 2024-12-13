package com.example.DonationPlateforme.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.DonationPlateforme.model.OrderLot;
import com.example.DonationPlateforme.model.Product;
import com.example.DonationPlateforme.model.User;
import com.example.DonationPlateforme.repository.OrderLotRepository;
import com.example.DonationPlateforme.repository.ProductRepository;
import com.example.DonationPlateforme.repository.UserRepository;

@Service
public class OrderLotService {

    private final OrderLotRepository orderLotRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderLotService(OrderLotRepository orderLotRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderLotRepository = orderLotRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Crée un lot de produits à recevoir (don)
     */
    public OrderLot createOrderLot(UUID receiverId, UUID donorId, List<UUID> productIds) {
        // Récupérer l'utilisateur qui reçoit les dons (l'utilisateur demandeur)
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Récupérer le donneur
        User donor = userRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donneur non trouvé"));

        // Récupérer les produits demandés en fonction de leur ID
        List<Product> products = productRepository.findAllById(productIds);

        if (products.isEmpty()) {
            throw new RuntimeException("Aucun produit sélectionné");
        }

        // Vérifier que tous les produits proviennent du même donneur
        for (Product product : products) {
            if (!product.getAnnonce().getUser().equals(donor)) {
                throw new RuntimeException("Tous les produits doivent provenir du même donneur.");
            }
        }

        // Créer le lot de produits (don)
        OrderLot orderLot = new OrderLot();
        orderLot.setReceiver(receiver);  // Celui qui reçoit les produits
        orderLot.setDonor(donor);  // Le donneur
        orderLot.setProducts(products);  // Liste des produits donnés

        // Sauvegarder le lot dans la base de données
        return orderLotRepository.save(orderLot);
    }
      // Méthode pour récupérer un lot par son id
      public OrderLot getOrderLot(UUID orderLotId) {
        return orderLotRepository.findById(orderLotId)
                .orElseThrow(() -> new RuntimeException("Lot de commande non trouvé"));
    }
}
