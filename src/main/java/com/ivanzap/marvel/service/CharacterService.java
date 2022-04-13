package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.repository.CharacterRepository;
import com.ivanzap.marvel.repository.ComicRepository;
import com.ivanzap.marvel.to.CharacterTo;
import com.ivanzap.marvel.util.exception.CharacterNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final ComicRepository comicRepository;

    public CharacterService(CharacterRepository characterRepository, ComicRepository comicRepository) {
        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
    }

    public Character get(int id) {
        try {
            return characterRepository.findById(id);
        } catch (Exception e) {
            throw new CharacterNotFoundException("" + id);
        }
    }

    public List<Character> getAllPage(String name, Optional<Integer> page, String direction, Optional<String> sort) {
        int limit = 5;

        direction = direction.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        List<String> fields = List.of("id", "name", "description");
        String sortField = sort.orElse("id");
        if (!fields.contains(sortField)) {
            sortField = "id";
        }

        if (name == null) {
            return characterRepository.findAll(page.orElse(0), limit, direction, sortField);
        } else {
            return characterRepository.findByNameContainingIgnoreCase(name, page.orElse(0), limit, direction, sortField);
        }
    }

    public Character createTo(CharacterTo characterTo) {
        Character character = new Character();
        save(characterTo, character);
        return characterRepository.insert(character);
    }

    public void save(CharacterTo characterTo, Character character) {
        character.setName(characterTo.getName());
        character.setDescription(characterTo.getDescription());
    }

    public void updateTo(CharacterTo characterTo, Integer characterId) {
        Character character = new Character();
        character.setId(characterId);
        save(characterTo, character);
        characterRepository.update(character);
    }

    @Transactional
    public void delete(int id) {
        get(id);
        characterRepository.deleteById(id);
    }

    @Transactional
    public List<Comic> getAllComicsPage(int characterId, String title, Optional<Integer> page, String direction, Optional<String> sort) {
        int limit = 5;

        direction = direction.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        List<String> fields = List.of("id", "title", "description");
        String sortField = sort.orElse("id");
        if (!fields.contains(sortField)) {
            sortField = "id";
        }

        return comicRepository.findByCharacterId(characterId, title, page.orElse(0), limit, direction, sortField);
    }
}
