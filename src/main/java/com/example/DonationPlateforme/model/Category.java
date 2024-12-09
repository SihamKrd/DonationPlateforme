package com.example.DonationPlateforme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "CATEGORIES")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Column(nullable = false, unique = true)
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
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
}
