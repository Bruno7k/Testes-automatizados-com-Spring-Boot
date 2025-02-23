package br.com.curso.testes.web;

import br.com.curso.testes.domain.Planet;
import br.com.curso.testes.domain.PlanetService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService service;

    @PostMapping
    public ResponseEntity<Planet>create(@RequestBody Planet planet) {
        Planet planetCreated = service.create(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Planet> findById(@PathVariable("id") Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> findByName(@PathVariable("name") String name) {
        return service.findByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Planet>> list(@RequestParam(required = false) String terrain,
                                              @RequestParam(required = false) String climate) {
        return ResponseEntity.ok(service.list(terrain, climate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
