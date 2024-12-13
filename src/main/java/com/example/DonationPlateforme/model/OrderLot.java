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
    private User receiver; // Utilisateur qui reçoit les dons

    @ManyToMany
    @JoinTable(
        name = "order_lot_products",
        joinColumns = @JoinColumn(name = "order_lot_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products; // Produits du lot (provenant de plusieurs annonces)

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor; // Le donneur (les produits doivent provenir de ses annonces)

    public OrderLot() {}

    public OrderLot(User receiver, User donor, List<Product> products) {
        this.receiver = receiver;
        this.donor = donor;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
 