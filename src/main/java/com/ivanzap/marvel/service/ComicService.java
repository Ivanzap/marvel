package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.repository.CharacterRepository;
import com.ivanzap.marvel.repository.ComicRepository;
import com.ivanzap.marvel.util.exception.ComicNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComicService {

    private final ComicRepository comicRepository;
    private final CharacterRepository characterRepository;

    public ComicService(ComicRepository comicRepository, CharacterRepository characterRepository) {
        this.comicRepository = comicRepository;
        this.characterRepository = characterRepository;
    }

    public Comic get(int id) {
        return comicRepository.findById(id).orElseThrow(() -> new ComicNotFoundException("" + id));
    }

    public Page<Comic> getAllPage(String title, Optional<Integer> page, String direction, Optional<String> sort) {
        direction = direction.equalsIgnoreCase("desc") ? direction : "ASC";
        Pageable pageable = PageRequest.of(page.orElse(0), 5, Sort.Direction.fromString(direction), sort.orElse("id"));
        if (title == null) {
            return comicRepository.findAll(pageable);
        } else {
            return comicRepository.findByTitleContainingIgnoreCase(title, pageable);
        }
    }

    public Comic create(Comic comic) {
        return comicRepository.save(comic);
    }

    public void update(Comic comic) {
        comicRepository.save(comic);
    }

    public void delete(int id) {
        comicRepository.findById(id).orElseThrow(() -> new ComicNotFoundException("" + id));
        comicRepository.deleteById(id);
    }

    @Transactional
    public Page<Character> getAllCharactersPage(int comicId, String name, Optional<Integer> page, String direction, Optional<String> sort) {
        name = name != null ? name : "";
        direction = direction.equalsIgnoreCase("desc") ? direction : "ASC";
        List<Integer> charactersId = comicRepository.getAllCharacters(comicId, name);
        Pageable pageable = PageRequest.of(page.orElse(0), 5, Sort.Direction.fromString(direction), sort.orElse("id"));
        return characterRepository.findByIdIn(charactersId, pageable);
    }
}
