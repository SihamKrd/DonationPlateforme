package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.model.OrderLot;
import com.example.DonationPlateforme.model.User;
import com.example.DonationPlateforme.service.OrderLotService;
import com.example.DonationPlateforme.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderLotController {

    @Autowired
    private OrderLotService orderLotService;

    @Autowired
    private UserService userService;

    /**
     * Créer une commande (lot)
     */
    @PostMapping("/create")
    public ResponseEntity<OrderLot> createOrderLot(@RequestBody Map<String, Object> request) {
        try {
            UUID receiverId = UUID.fromString((String) request.get("receiverId"));
            UUID donorId = UUID.fromString((String) request.get("donorId"));
            List<Map<String, String>> products = (List<Map<String, String>>) request.get("products");

            List<UUID> productIds = products.stream()
                .map(product -> UUID.fromString(product.get("productId")))
                .collect(Collectors.toList());

            OrderLot orderLot = orderLotService.createOrderLot(receiverId, donorId, productIds);

            return ResponseEntity.ok(orderLot);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupérer un lot par son ID
     */
    @GetMapping("/{orderLotId}")
    public String getOrderLot(@PathVariable UUID orderLotId, Model model) {
        try {
            OrderLot orderLot = orderLotService.getOrderLot(orderLotId);
            model.addAttribute("orderLot", orderLot);
            return "order-lot";
        } catch (RuntimeException e) {
            return "error";
        }
    }


    @GetMapping("/user")
    public String viewUserOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new SecurityException("Utilisateur non authentifié.");
        }

        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Utilisateur introuvable.");
        }

        List<OrderLot> orders = orderLotService.getOrderLotsByReceiver(user.getId());
        model.addAttribute("orders", orders);
        return "orders"; 
    }

    @PostMapping("/create-single-order")
    public String createSingleOrder(
            @RequestParam String receiverId,
            @RequestParam UUID donorId,
            @RequestParam UUID annonceId,
            Model model) {
        try {
            User user = userService.findUserByEmail(receiverId);
            // Créer une commande pour un produit unique
            OrderLot orderLot = orderLotService.createOrderLot(user.getId(), donorId, List.of(annonceId));
            
            model.addAttribute("orderLot", orderLot);
            
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la création de la commande : " + e.getMessage());
            return "error";
        }
    }
}
