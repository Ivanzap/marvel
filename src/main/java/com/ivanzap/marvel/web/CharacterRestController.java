package com.ivanzap.marvel.web;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = CharacterRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacterRestController {
    static final String REST_URL = "/characters";
    private static final Logger log = LoggerFactory.getLogger(CharacterRestController.class);

    private final CharacterService service;

    public CharacterRestController(CharacterService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Character> createWithLocation(@RequestBody Character character) {
        log.info("createWithLocation {}", character);
        Character created = service.create(character);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Character character, @PathVariable int id) {
        log.info("update {} with id {}", character, id);
        //assureIdConsistent(character, id);
        service.update(character);
    }

    @GetMapping("/{id}")
    public Character get(@PathVariable int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @GetMapping
    public Page<Character> getAllPage(
            @RequestParam(required = false) String name,
            @RequestParam Optional<Integer> page,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam Optional<String> sortBy
    ) {
        log.info("getAll");
        return service.getAllPage(name, page, direction, sortBy);
    }
}
