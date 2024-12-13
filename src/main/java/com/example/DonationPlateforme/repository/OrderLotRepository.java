package com.example.DonationPlateforme.repository;

import com.example.DonationPlateforme.model.OrderLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderLotRepository extends JpaRepository<OrderLot, UUID> {
    List<OrderLot> findByReceiverId(UUID receiverId); // Trouver les lots d'un receveur
    List<OrderLot> findByDonorId(UUID donorId); // Trouver les lots d'un donneur
    List<OrderLot> findByProductsId(UUID productId); // Trouver les lots d'un produit
    Optional<OrderLot> findByReceiverIdAndDonorId(UUID receiverId, UUID donorId); // Vérifier si un lot existe pour un receveur et un donneur
    void deleteByReceiverIdAndDonorId(UUID receiverId, UUID donorId); // Supprimer un lot pour un receveur et un donneur
}
