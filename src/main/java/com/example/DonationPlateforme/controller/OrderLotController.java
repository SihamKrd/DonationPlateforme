package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.model.OrderLot;
import com.example.DonationPlateforme.service.OrderLotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller  // Utilisez @Controller pour gérer les vues HTML
@RequestMapping("/order-lots")
public class OrderLotController {

    private final OrderLotService orderLotService;

    public OrderLotController(OrderLotService orderLotService) {
        this.orderLotService = orderLotService;
    }

    /**
     * Endpoint pour créer un lot de produits à recevoir
     */
    @PostMapping
    public ResponseEntity<OrderLot> createOrderLot(@RequestBody Map<String, Object> request, Model model) {
        try {
            // Extraction des valeurs du corps de la requête
            UUID receiverId = UUID.fromString((String) request.get("receiverId"));
            UUID donorId = UUID.fromString((String) request.get("donorId"));
            List<Map<String, String>> products = (List<Map<String, String>>) request.get("products");

            // Extraire les IDs de produits
            List<UUID> productIds = products.stream()
                .map(product -> UUID.fromString(product.get("productId")))
                .collect(Collectors.toList());

            // Appel au service pour créer l'ordre de lot
            OrderLot orderLot = orderLotService.createOrderLot(receiverId, donorId, productIds);

            // Ajouter le lot créé au modèle
            model.addAttribute("orderLot", orderLot);

            // Retourne une réponse avec le lot créé au format JSON
            return ResponseEntity.ok(orderLot);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);  // Gère les erreurs
        }
    }

    /**
     * Endpoint pour afficher un lot de commande spécifique
     */
    @GetMapping("/{orderLotId}")
    public String getOrderLot(@PathVariable UUID orderLotId, Model model) {
        try {
            // Récupérer l'ordre de lot à partir du service
            OrderLot orderLot = orderLotService.getOrderLot(orderLotId);

            // Ajouter l'objet 'orderLot' au modèle pour l'afficher avec Thymeleaf
            model.addAttribute("orderLot", orderLot);

            // Retourner la vue HTML 'order-lot.html'
            return "order-lot";  // Thymeleaf l'utilisera pour rendre la page avec les données du modèle
        } catch (RuntimeException e) {
            // En cas d'erreur, redirige vers une page d'erreur
            return "error";
        }
    }
}
