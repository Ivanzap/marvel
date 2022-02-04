package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.AbstractBaseEntity;
import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.repository.CharacterRepository;
import com.ivanzap.marvel.repository.ComicRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final ComicRepository comicRepository;

    public CharacterService(CharacterRepository characterRepository, ComicRepository comicRepository) {
        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
    }

    public Character get(int id) {
        return characterRepository.findById(id).orElse(null);
    }

    public Page<Character> getAllPage(String name, Optional<Integer> page, String direction, Optional<String> sort) {
        direction = direction.equalsIgnoreCase("desc") ? direction : "ASC";
        Pageable pageable = PageRequest.of(page.orElse(0),
                5,
                Sort.Direction.fromString(direction),
                sort.orElse("id"));
        if (name == null) {
            return characterRepository.findAll(pageable);
        } else {
            return characterRepository.findByNameContainingIgnoreCase(name, pageable);
        }
    }

    public Character create(Character character) {
        return characterRepository.save(character);
    }

    public void update(Character character) {
        characterRepository.save(character);
    }

    public void delete(int id) {
        characterRepository.deleteById(id);
    }

    @Transactional
    public Page<Comic> getAllComicsPage(int characterId, String title, Optional<Integer> page, String direction, Optional<String> sort) {
        title = title != null ? title : "";
        direction = direction.equalsIgnoreCase("desc") ? direction : "ASC";
        List<Integer> comicsId = characterRepository.getAllComics(characterId, title);
        Pageable pageable = PageRequest.of(page.orElse(0), 5, Sort.Direction.fromString(direction), sort.orElse("id"));
        return comicRepository.findByIdIn(comicsId, pageable);
    }
}
