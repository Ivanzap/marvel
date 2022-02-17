package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.repository.CharacterRepository;
import com.ivanzap.marvel.repository.ComicRepository;
import com.ivanzap.marvel.to.CharacterTo;
import com.ivanzap.marvel.util.exception.CharacterNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final ComicRepository comicRepository;
    private final ImageService imageService;

    public CharacterService(CharacterRepository characterRepository, ComicRepository comicRepository, ImageService imageService) {
        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
        this.imageService = imageService;
    }

    public Character get(int id) {
        return characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException("" + id));
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

    public Character createTo(CharacterTo characterTo) {
        return save(characterTo, new Character());
    }

    private Character save(CharacterTo characterTo, Character character) {
        character.setName(characterTo.getName());
        character.setDescription(characterTo.getDescription());
        if (characterTo.getImage() != null) {
            character.setImage(imageService.uploadImage(characterTo.getImage()));
        }
        return characterRepository.save(character);
    }

    public void update(Character character) {
        characterRepository.save(character);
    }

    public void updateTo(CharacterTo characterTo, Integer characterId) {
        Character character = new Character();
        character.setId(characterId);
        save(characterTo, character);
    }

    @Transactional
    public void delete(int id) {
        characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException("" + id));
        characterRepository.deleteById(id);
    }

    @Transactional
    public Page<Comic> getAllComicsPage(int characterId, String title, Optional<Integer> page, String direction, Optional<String> sort) {
        direction = direction.equalsIgnoreCase("desc") ? direction : "ASC";
        List<Integer> comicsId = characterRepository.getAllComics(characterId, title);
        Pageable pageable = PageRequest.of(page.orElse(0), 5, Sort.Direction.fromString(direction), sort.orElse("id"));
        return comicRepository.findByIdIn(comicsId, pageable);
    }
}
