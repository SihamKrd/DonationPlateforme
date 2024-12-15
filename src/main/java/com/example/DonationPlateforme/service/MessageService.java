package com.example.DonationPlateforme.service;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.model.Message;
import com.example.DonationPlateforme.model.User;
import com.example.DonationPlateforme.repository.AnnonceRepository;
import com.example.DonationPlateforme.repository.MessageRepository;
import com.example.DonationPlateforme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    // Envoi d'un message
    public Message envoyerMessage(UUID expediteurId, UUID destinataireId, UUID annonceId, String contenu) {
        // Récupérer les utilisateurs et l'annonce
        User expediteur = userRepository.findById(expediteurId)
                .orElseThrow(() -> new NoSuchElementException("Expéditeur non trouvé"));

        User destinataire = userRepository.findById(destinataireId)
                .orElseThrow(() -> new NoSuchElementException("Destinataire non trouvé"));

        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new NoSuchElementException("Annonce non trouvée"));

        // Création du message
        Message message = new Message(expediteur, destinataire, annonce, contenu);

        // Sauvegarde du message dans la base de données
        return messageRepository.save(message);
    }

    // Récupérer les messages échangés entre deux utilisateurs pour une annonce
    public List<Message> getMessagesEchanges(UUID expediteurId, UUID destinataireId) {
        return messageRepository.findByExpediteurIdAndDestinataireIdOrderByDateEnvoiAsc(expediteurId, destinataireId);
    }

    // Méthode pour obtenir les messages reçus par un destinataire
    public List<Message> getMessagesReçus(UUID destinataireId) {
        return messageRepository.findByDestinataireIdOrderByDateEnvoiAsc(destinataireId);
    }
}
