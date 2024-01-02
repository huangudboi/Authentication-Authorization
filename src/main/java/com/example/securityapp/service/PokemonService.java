package com.example.securityapp.service;

import com.example.securityapp.dto.PokemonDTO;
import com.example.securityapp.dto.response.PokemonResponse;

public interface PokemonService {
    PokemonDTO createPokemon(PokemonDTO pokemonDto);
    PokemonResponse getAllPokemon(int pageNo, int pageSize);
    PokemonDTO getPokemonById(int id);
    PokemonDTO updatePokemon(PokemonDTO pokemonDto, int id);
    void deletePokemonId(int id);
}
