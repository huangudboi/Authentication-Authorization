package com.example.securityapp.service;

import com.example.securityapp.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(int pokemonId, ReviewDTO reviewDto);
    List<ReviewDTO> getReviewsByPokemonId(int id);
    ReviewDTO getReviewById(int reviewId, int pokemonId);
    ReviewDTO updateReview(int pokemonId, int reviewId, ReviewDTO reviewDto);
    void deleteReview(int pokemonId, int reviewId);
}
