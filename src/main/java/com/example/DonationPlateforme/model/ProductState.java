package com.example.DonationPlateforme.model;

public enum ProductState {
    NEUF,            // L'objet est neuf, jamais utilisé
    TRES_BON_ETAT,   // L'objet est comme neuf, très peu d'usure
    BON_ETAT,        // L'objet est en bon état général
    ETAT_CORRECT,    // L'objet est correct mais montre des signes d'usure
    MAUVAIS_ETAT,    // L'objet est endommagé ou nécessite des réparations
    NON_FONCTIONNEL 
}
