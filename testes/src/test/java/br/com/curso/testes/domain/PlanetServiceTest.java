package br.com.curso.testes.domain;
import static br.com.curso.testes.common.PlanetConstants.PLANET;
import static br.com.curso.testes.common.PlanetConstants.INVALID_PLANET;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

    @InjectMocks
    private PlanetService service;

    @Mock
    private PlanetRepository repository;
    @Test
    public void createPlanet_WithValidPlanet_ShouldReturnPlanet() {
        //arrange
        when(repository.save(PLANET)).thenReturn(PLANET);
        //act
        Planet sut = service.create(PLANET);
        //assert
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidPlanet_ShouldThrowException() {
        //arrange
        when(repository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        //act + assert
        assertThatThrownBy(() -> service.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ShouldReturnPlanet() {
        when(repository.findById(1L)).thenReturn(Optional.of(PLANET));
        Planet sut = service.findById(1L).get();
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByNonExistingId_ShouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Optional<Planet> sut = service.findById(1L);
        assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanet_ByName_ShouldReturnPlanet() {
        when(repository.findByName("Tatooine")).thenReturn(Optional.of(PLANET));
        Planet sut = service.findByName("Tatooine").get();
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByName_ShouldThrowException() {
        when(repository.findByName("Tatooine")).thenReturn(Optional.empty());
        Optional<Planet> sut = service.findByName("Tatooine");
        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets(){
        List<Planet> planets = new ArrayList<>(){{
            add(PLANET);
        }};
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(repository.findAll(query)).thenReturn(planets);
        List<Planet> sut = service.list(PLANET.getTerrain(), PLANET.getClimate());
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets(){
        when(repository.findAll(any(Example.class))).thenReturn(Collections.emptyList());
        List<Planet> sut = service.list(PLANET.getTerrain(), PLANET.getClimate());
        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WhithExistingId_DoesNotThrowException(){
        assertThatCode(() -> service.remove(1L)).doesNotThrowAnyException();
    }
    @Test
    public void removePlanet_WhithUnexistingId_ThrowsException(){
        doThrow(new RuntimeException()).when(repository).deleteById(1L);
        assertThatThrownBy(() -> service.remove(1L)).isInstanceOf(RuntimeException.class);
    }
}