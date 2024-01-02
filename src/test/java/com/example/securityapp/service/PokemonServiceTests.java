package com.example.securityapp.service;

import com.example.securityapp.dto.PokemonDTO;
import com.example.securityapp.dto.response.PokemonResponse;
import com.example.securityapp.model.Pokemon;
import com.example.securityapp.repository.PokemonRepository;
import com.example.securityapp.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTests {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void PokemonService_CreatePokemon_ReturnsPokemonDto() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();
        PokemonDTO pokemonDto = PokemonDTO.builder().name("pickachu").type("electric").build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDTO savedPokemon = pokemonService.createPokemon(pokemonDto);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_GetAllPokemon_ReturnsResponseDto() {
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse savePokemon = pokemonService.getAllPokemon(1,10);

        Assertions.assertThat(savePokemon).isNotNull();
    }
    @Test
    public void PokemonService_FindById_ReturnPokemonDto() {
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDTO pokemonReturn = pokemonService.getPokemonById(pokemonId);

        Assertions.assertThat(pokemonReturn).isNotNull();
    }

    @Test
    public void PokemonService_UpdatePokemon_ReturnPokemonDto() {
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
        PokemonDTO pokemonDto = PokemonDTO.builder().id(1).name("pikachu").type("electric").type("this is a type").build();

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(pokemon)).thenReturn(pokemon);

        PokemonDTO updateReturn = pokemonService.updatePokemon(pokemonDto, pokemonId);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void PokemonService_DeletePokemonById_ReturnVoid() {
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        doNothing().when(pokemonRepository).delete(pokemon);

        assertAll(() -> pokemonService.deletePokemonId(pokemonId));

    }
}
