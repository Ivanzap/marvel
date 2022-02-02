package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.repository.CharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public Character get(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Character> getAllPage(String name, Optional<Integer> page, String direction, Optional<String> sortBy) {
        direction = direction.equalsIgnoreCase("desc") ? direction : "ASC";
        Pageable pageable = PageRequest.of(page.orElse(0),
                5,
                Sort.Direction.fromString(direction),
                sortBy.orElse("id"));
        if (name == null) {
            return repository.findAll(pageable);
        } else {
            return repository.findByNameContainingIgnoreCase(name, pageable);
        }
    }

    public Character create(Character character) {
        return repository.save(character);
    }

    public void update(Character character) {
        repository.save(character);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
