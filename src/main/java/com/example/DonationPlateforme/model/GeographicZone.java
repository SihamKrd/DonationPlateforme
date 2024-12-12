package com.example.DonationPlateforme.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "GEOGRAPHIC_ZONES")
public class GeographicZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    @Column(nullable = false, unique = true)
    private String name;  // Nom de la zone géographique

    @ManyToOne
    @JoinColumn(name = "parent_id")  // Référence à la zone parente
    @JsonBackReference
    private GeographicZone parentZone;

    @OneToMany(mappedBy = "parentZone", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference

    private Set<GeographicZone> subZones = new HashSet<>();

    // Constructeur par défaut
    public GeographicZone() {}

    // Constructeur avec le nom
    public GeographicZone(String name) {
        this.name = name;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicZone getParentZone() {
        return parentZone;
    }

    public void setParentZone(GeographicZone parentZone) {
        this.parentZone = parentZone;
    }
    @JsonManagedReference
    public Set<GeographicZone> getSubZones() {
        return subZones;
    }

    public void setSubZones(Set<GeographicZone> subZones) {
        this.subZones = subZones;
    }
}
