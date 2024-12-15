package com.example.DonationPlateforme.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ORDER_LOTS")
public class OrderLot {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver; // Utilisateur receveur

    @ManyToMany
    @JoinTable(
        name = "order_lot_annonces",
        joinColumns = @JoinColumn(name = "order_lot_id"),
        inverseJoinColumns = @JoinColumn(name = "annonce_id")
    )
    private List<Annonce> annonces; // Liste des annonces associées au lot

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor; // Donneur (les annonces proviennent de cet utilisateur)

    @Enumerated(EnumType.STRING)
    private OrderLotStatus status; // Statut de la commande (EN_ATTENTE, CONFIRMÉ, etc.)

    public OrderLot() {}

    public OrderLot(User receiver, User donor, List<Annonce> annonces) {
        this.receiver = receiver;
        this.donor = donor;
        this.annonces = annonces;
        this.status = OrderLotStatus.EN_ATTENTE; // Statut par défaut
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor = donor;
    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public OrderLotStatus getStatus() {
        return status;
    }

    public void setStatus(OrderLotStatus status) {
        this.status = status;
    }
}