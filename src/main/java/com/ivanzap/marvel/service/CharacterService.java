package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.repository.CharacterRepository;
import com.ivanzap.marvel.repository.InMemoryCharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public Character save(Character character) {
        return repository.save(character);
    }

    public Character get(int id) {
        return repository.getById(id);
    }

    public List<Character> getAll() {
        return repository.findAll();
    }
}
