package com.example.DonationPlateforme.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DonationPlateforme.model.Annonce;
import com.example.DonationPlateforme.model.Favorite;
import com.example.DonationPlateforme.repository.FavoriteRepository;
import com.example.DonationPlateforme.model.User;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AnnonceService annonceService; 

    public List<Favorite> getFavoritesByUserId(UUID userId) {
        return favoriteRepository.findByUserId(userId);
    }    

    public void addToFavorites(UUID userId, UUID annonceId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        Annonce annonce = annonceService.getAnnonceById(annonceId);
        if (annonce == null) {
            throw new IllegalArgumentException("Annonce non trouvée avec l'ID : " + annonceId);
        }

        if (favoriteRepository.findByUserIdAndAnnonceId(userId, annonceId) == null) {
            Favorite favorite = new Favorite();
            favorite.setUser(user); 
            favorite.setAnnonce(annonce);
            favoriteRepository.save(favorite);
        }
    }

    public void removeFromFavorites(UUID userId, UUID annonceId) {
        Favorite favorite = favoriteRepository.findByUserIdAndAnnonceId(userId, annonceId);
        if (favorite != null) {
            favoriteRepository.delete(favorite);
        }
    }

    public boolean isFavorite(UUID userId, UUID annonceId) {
        return favoriteRepository.findByUserIdAndAnnonceId(userId, annonceId) != null;
    }
    
}
