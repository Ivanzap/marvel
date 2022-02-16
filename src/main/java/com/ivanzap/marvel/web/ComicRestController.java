package com.ivanzap.marvel.web;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.service.ComicService;
import com.ivanzap.marvel.to.ComicTo;
import com.ivanzap.marvel.util.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Tag(name = "Comics", description = "The comics API")
@RestController
@RequestMapping(value = ComicRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ComicRestController {
    static final String REST_URL = "/comics";
    private static final Logger log = LoggerFactory.getLogger(ComicRestController.class);

    private final ComicService service;

    public ComicRestController(ComicService service) {
        this.service = service;
    }

    @Operation(summary = "Create comic", description = "Create new comic", tags = "Comics")
    @ApiResponse(responseCode = "200", description = "Comic created")
    @ApiResponse(responseCode = "400", description = "Invalid comic parameters")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comic> createWithLocation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Created comic object", required = true, content = @Content(schema = @Schema(implementation = ComicTo.class)))
            @Valid @RequestBody ComicTo comicTo) {
        log.info("createWithLocation {}", comicTo);
        ValidationUtil.checkNew(comicTo);
        Comic created = service.createTo(comicTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Update comic", description = "Update comic", tags = "Comics")
    @ApiResponse(responseCode = "204", description = "Comic updated")
    @ApiResponse(responseCode = "400", description = "Invalid comic parameters")
    @ApiResponse(responseCode = "404", description = "Comic not found by id")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Created comic", required = true, content = @Content(schema = @Schema(implementation = ComicTo.class)))
            @Valid @RequestBody ComicTo comicTo,
            @Parameter(description = "Comic id from url", example = "100015")
            @PathVariable int id) {
        log.info("update {} with id {}", comicTo, id);
        ValidationUtil.assureIdConsistent(comicTo, id);
        service.updateTo(comicTo, id);
    }

    @Operation(summary = "Get comic", description = "Get comic by id", tags = "Comics")
    @ApiResponse(responseCode = "200", description = "Comic find and show")
    @ApiResponse(responseCode = "404", description = "Comic not found")
    @GetMapping("/{id}")
    public Comic get(
            @Parameter(description = "Comic id from url", example = "100015")
            @PathVariable int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @Operation(summary = "Delete comic", description = "Delete comic by id", tags = "Comics")
    @ApiResponse(responseCode = "204", description = "Comic deleted")
    @ApiResponse(responseCode = "404", description = "Comic not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Comic id from url", example = "100014")
            @PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @Operation(summary = "Get all comics", description = "Get all comics with pageable", tags = "Comics")
    @ApiResponse(responseCode = "200", description = "Comics show")
    @GetMapping
    public Page<Comic> getAllPage(
            @Parameter(description = "Part title for filter")
            @RequestParam(required = false) String title,
            @Parameter(description = "Page number for filter")
            @RequestParam(required = false) Optional<Integer> page,
            @Parameter(description = "Direction for filter")
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @Parameter(description = "Sort fields from Comic for filter (id, title, description)")
            @RequestParam(required = false) Optional<String> sort
    ) {
        log.info("getAllPage title {}, page{}, direction {}, sort {}", title, page, direction, sort);
        return service.getAllPage(title, page, direction, sort);
    }

    @Operation(summary = "Get all characters by comic", description = "Get all characters by comic with pageable", tags = "Comics")
    @ApiResponse(responseCode = "200", description = "Character by comic show")
    @GetMapping("/{comicId}/characters")
    public Page<Character> getAllCharactersPage(
            @Parameter(description = "Comic id from url", example = "100015")
            @PathVariable int comicId,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @Parameter(description = "Page number for filter")
            @RequestParam(required = false) Optional<Integer> page,
            @Parameter(description = "Direction for filter")
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @Parameter(description = "Sort fields from Character for filter (id, name, description)")
            @RequestParam(required = false) Optional<String> sort
    ) {
        log.info("getAllComicsPage comicId {}, name {}, page{}, direction {}, sort {}", comicId, name, page, direction, sort);
        return service.getAllCharactersPage(comicId, name, page, direction, sort);
    }
}
