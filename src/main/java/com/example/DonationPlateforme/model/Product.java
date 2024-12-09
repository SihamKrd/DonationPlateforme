package com.example.DonationPlateforme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "annonce_id", nullable = true)
    @JsonBackReference
    private Annonce annonce;

    public Product() {}

    public Product(String name, List<Category> categories, Annonce annonce) {
        this.name = name;
        this.categories = categories;
        this.annonce = annonce;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }
}
