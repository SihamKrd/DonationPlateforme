package com.example.DonationPlateforme.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.model.OrderLot;
import com.example.DonationPlateforme.model.OrderLotStatus;
import com.example.DonationPlateforme.model.User;
import com.example.DonationPlateforme.repository.AnnonceRepository;
import com.example.DonationPlateforme.repository.OrderLotRepository;
import com.example.DonationPlateforme.repository.UserRepository;

@Service
public class OrderLotService {

    private final OrderLotRepository orderLotRepository;
    private final AnnonceRepository annonceRepository;
    private final UserRepository userRepository;

    public OrderLotService(OrderLotRepository orderLotRepository, AnnonceRepository annonceRepository, UserRepository userRepository) {
        this.orderLotRepository = orderLotRepository;
        this.annonceRepository = annonceRepository;
        this.userRepository = userRepository;
    }


    public OrderLot getOrderLot(UUID orderLotId) {
        return orderLotRepository.findById(orderLotId)
                .orElseThrow(() -> new RuntimeException("Lot de commande non trouvé"));
    }

    public List<OrderLot> getOrderLotsByReceiver(UUID receiverId) {
        return orderLotRepository.findByReceiverId(receiverId);
    }

    public List<OrderLot> getOrderLotsByDonor(UUID donorId) {
        return orderLotRepository.findByDonorId(donorId);
    }

    public OrderLot createOrderLot(UUID receiverId, UUID donorId, List<UUID> annonceIds) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Utilisateur receveur non trouvé"));
    
        User donor = userRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Utilisateur donneur non trouvé"));
    
        List<Annonce> annonces = annonceRepository.findAllById(annonceIds);
    
        if (annonces.isEmpty()) {
            throw new RuntimeException("Aucune annonce sélectionnée");
        }
    
        for (Annonce annonce : annonces) {
            if (!annonce.getUser().equals(donor)) {
                throw new RuntimeException("Toutes les annonces doivent provenir du même donneur");
            }
        }
    
        OrderLot orderLot = new OrderLot();
        orderLot.setReceiver(receiver);
        orderLot.setDonor(donor);
        orderLot.setAnnonces(annonces);
        orderLot.setStatus(OrderLotStatus.EN_ATTENTE);
    
        return orderLotRepository.save(orderLot);
    }
    
}
