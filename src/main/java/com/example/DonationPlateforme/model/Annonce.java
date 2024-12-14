package com.example.DonationPlateforme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ANNONCES")
public class Annonce {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100, message = "Le titre doit contenir entre 3 et 100 caractères")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "La description est obligatoire")
    @Size(min = 10, max = 500, message = "La description doit contenir entre 10 et 500 caractères")
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
    
    @Enumerated(EnumType.STRING)  
    @Column(nullable = false)
    private DeliveryMode deliveryMode;  

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonManagedReference
    private Product product;

    
    @ElementCollection
    @CollectionTable(name = "annonce_keywords", joinColumns = @JoinColumn(name = "annonce_id"))
    @Column(name = "keyword")
    private Set<String> keywords = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime dateCreation;
    
    public Annonce() {}

    public Annonce(String title, String description, User user, Product product,  DeliveryMode deliveryMode, Set<String> keywords) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.product = product;
        this.deliveryMode = deliveryMode;
        this.keywords = keywords;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    @ManyToOne
    @JoinColumn(name = "geographic_zone_id")
    private GeographicZone geographicZone;  

    public GeographicZone getGeographicZone() {
        return geographicZone;
    }

    public void setGeographicZone(GeographicZone geographicZone) {
        this.geographicZone = geographicZone;
    }
}
