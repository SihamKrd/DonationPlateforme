package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.model.Message;
import com.example.DonationPlateforme.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Envoi d'un message (vérifie l'authentification avant d'envoyer le message)
    @PostMapping("/envoyer")
    @PreAuthorize("isAuthenticated()")  // Assure que l'utilisateur est authentifié
    public ResponseEntity<Message> envoyerMessage(@RequestBody Message message) {
        // Vérification de la validité du message
        if (message.getExpediteur() == null || message.getDestinataire() == null || message.getAnnonce() == null) {
            return ResponseEntity.badRequest().body(null);  // Mauvaise requête si des champs sont manquants
        }

        if (message.getContenu() == null || message.getContenu().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // Mauvaise requête si le contenu est vide
        }

        try {
            Message createdMessage = messageService.envoyerMessage(
                    message.getExpediteur().getId(),
                    message.getDestinataire().getId(),
                    message.getAnnonce().getId(),
                    message.getContenu()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);  // 201 Créé
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Erreur serveur
        }
    }

    // Récupérer les messages reçus (exige également une authentification)
    @GetMapping("/recus/{destinataireId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Message>> getMessagesRecus(@PathVariable UUID destinataireId) {
        List<Message> messages = messageService.getMessagesReçus(destinataireId);

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();  // Aucun message trouvé
        }

        return ResponseEntity.ok(messages);  // 200 OK
    }

    // Récupérer les messages échangés entre deux utilisateurs
    @GetMapping("/echanges/{expediteurId}/{destinataireId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Message>> getMessagesEchanges(
            @PathVariable UUID expediteurId,
            @PathVariable UUID destinataireId
    ) {
        List<Message> messages = messageService.getMessagesEchanges(expediteurId, destinataireId);

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();  // Aucun échange trouvé
        }

        return ResponseEntity.ok(messages);  // 200 OK
    }
}
