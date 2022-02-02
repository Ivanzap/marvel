package com.ivanzap.marvel.web;

import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.service.ComicService;
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
@RequestMapping(value = ComicRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ComicRestController {
    static final String REST_URL = "/comics";
    private static final Logger log = LoggerFactory.getLogger(ComicRestController.class);

    private final ComicService service;

    public ComicRestController(ComicService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comic> createWithLocation(@RequestBody Comic comic) {
        log.info("createWithLocation {}", comic);
        Comic created = service.create(comic);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Comic comic, @PathVariable int id) {
        log.info("update {} with id {}", comic, id);
        //assureIdConsistent(comic, id);
        service.update(comic);
    }

    @GetMapping("/{id}")
    public Comic get(@PathVariable int id) {
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
    public Page<Comic> getAllPage(
            @RequestParam(required = false) String title,
            @RequestParam Optional<Integer> page,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam Optional<String> sortBy
    ) {
        log.info("getAll");
        return service.getAllPage(title, page, direction, sortBy);
    }
}
