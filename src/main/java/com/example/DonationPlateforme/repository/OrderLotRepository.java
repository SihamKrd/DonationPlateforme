package com.example.DonationPlateforme.repository;

import com.example.DonationPlateforme.model.OrderLot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OrderLotRepository extends JpaRepository<OrderLot, UUID> {
    List<OrderLot> findByAnnoncesId(UUID annonceId);
    List<OrderLot> findByReceiverId(UUID receiverId); 
    List<OrderLot> findByDonorId(UUID donorId); 
}

