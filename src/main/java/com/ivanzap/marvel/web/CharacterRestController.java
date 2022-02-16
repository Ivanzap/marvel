package com.ivanzap.marvel.web;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.service.CharacterService;
import com.ivanzap.marvel.to.CharacterTo;
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

@Tag(name = "Characters", description = "The characters API")
@RestController
@RequestMapping(value = CharacterRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacterRestController {
    static final String REST_URL = "/characters";
    private static final Logger log = LoggerFactory.getLogger(CharacterRestController.class);

    private final CharacterService service;

    public CharacterRestController(CharacterService service) {
        this.service = service;
    }

    @Operation(summary = "Create character", description = "Create new character", tags = "Characters")
    @ApiResponse(responseCode = "200", description = "Character created")
    @ApiResponse(responseCode = "400", description = "Invalid character parameters")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Character> createWithLocation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Created character object", required = true, content = @Content(schema = @Schema(implementation = CharacterTo.class)))
            @Valid @RequestBody CharacterTo characterTo) {
        log.info("createWithLocation {}", characterTo);
        ValidationUtil.checkNew(characterTo);
        Character created = service.createTo(characterTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Update character", description = "Update character", tags = "Characters")
    @ApiResponse(responseCode = "204", description = "Character updated")
    @ApiResponse(responseCode = "400", description = "Invalid character parameters")
    @ApiResponse(responseCode = "404", description = "Character not found by id")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Created character", required = true, content = @Content(schema = @Schema(implementation = CharacterTo.class)))
            @Valid @RequestBody CharacterTo characterTo,
            @Parameter(description = "Character id from url", example = "100001")
            @PathVariable int id) {
        log.info("update {} with id {}", characterTo, id);
        ValidationUtil.assureIdConsistent(characterTo, id);
        service.updateTo(characterTo, id);
    }

    @Operation(summary = "Get character", description = "Get character by id", tags = "Characters")
    @ApiResponse(responseCode = "200", description = "Character find and show")
    @ApiResponse(responseCode = "404", description = "Character not found")
    @GetMapping("/{id}")
    public Character get(
            @Parameter(description = "Character id from url", example = "100001")
            @PathVariable int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @Operation(summary = "Delete character", description = "Delete character by id", tags = "Characters")
    @ApiResponse(responseCode = "204", description = "Character deleted")
    @ApiResponse(responseCode = "404", description = "Character not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Character id from url", example = "100001")
            @PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @Operation(summary = "Get all characters", description = "Get all characters with pageable", tags = "Characters")
    @ApiResponse(responseCode = "200", description = "Characters show")
    @GetMapping
    public Page<Character> getAllPage(
            @Parameter(description = "Part name for filter", example = "sp")
            @RequestParam(required = false) String name,
            @Parameter(description = "Page number for filter")
            @RequestParam(required = false) Optional<Integer> page,
            @Parameter(description = "Direction for filter")
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @Parameter(description = "Sort fields from Character for filter (id, name, description)")
            @RequestParam(required = false) Optional<String> sort
    ) {
        log.info("getAllPage name {}, page{}, direction {}, sort {}", name, page, direction, sort);
        return service.getAllPage(name, page, direction, sort);
    }

    @Operation(summary = "Get all comics by character", description = "Get all comics be character with pageable", tags = "Characters")
    @ApiResponse(responseCode = "200", description = "Comics by character show")
    @GetMapping("/{characterId}/comics")
    public Page<Comic> getAllComicsPage(
            @Parameter(description = "Character id from url", example = "100001")
            @PathVariable int characterId,
            @Parameter(description = "Part comic.title for filter")
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @Parameter(description = "Page number for filter")
            @RequestParam(required = false) Optional<Integer> page,
            @Parameter(description = "Direction for filter")
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @Parameter(description = "Sort fields from Comic for filter (id, title, description)")
            @RequestParam(required = false) Optional<String> sort
    ) {
        log.info("getAllComicsPage characterId {}, title {}, page{}, direction {}, sort {}", characterId, title, page, direction, sort);
        return service.getAllComicsPage(characterId, title, page, direction, sort);
    }
}

