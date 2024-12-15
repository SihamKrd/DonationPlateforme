package com.example.DonationPlateforme.repository;

import com.example.DonationPlateforme.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    // Récupérer tous les messages échangés entre un expéditeur et un destinataire pour une annonce
    List<Message> findByExpediteurIdAndDestinataireIdOrderByDateEnvoiAsc(UUID expediteurId, UUID destinataireId);

    // Récupérer les messages reçus par un destinataire donné
    List<Message> findByDestinataireIdOrderByDateEnvoiAsc(UUID destinataireId);

    // Récupérer les messages liés à une annonce spécifique
    List<Message> findByAnnonceIdOrderByDateEnvoiAsc(UUID annonceId);
}
