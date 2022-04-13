package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.repository.CharacterRepository;
import com.ivanzap.marvel.repository.ComicRepository;
import com.ivanzap.marvel.to.ComicTo;
import com.ivanzap.marvel.util.exception.ComicNotFoundException;
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
        try {
            return comicRepository.findById(id);
        } catch (Exception e) {
            throw new ComicNotFoundException("" + id);
        }
    }

    public List<Comic> getAllPage(String title, Optional<Integer> page, String direction, Optional<String> sort) {
        int limit = 5;

        direction = direction.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        List<String> fields = List.of("id", "title", "description");
        String sortField = sort.orElse("id");
        if (!fields.contains(sortField)) {
            sortField = "id";
        }

        if (title == null) {
            return comicRepository.findAll(page.orElse(0), limit, direction, sortField);
        } else {
            return comicRepository.findByTitleContainingIgnoreCase(title, page.orElse(0), limit, direction, sortField);
        }
    }

    public Comic createTo(ComicTo comicTo) {
        Comic comic = new Comic();
        save(comicTo, comic);
        return comicRepository.insert(comic);
    }

    private void save(ComicTo comicTo, Comic comic) {
        comic.setTitle(comicTo.getTitle());
        comic.setDescription(comicTo.getDescription());
    }

    public void updateTo(ComicTo comicTo, Integer comicId) {
        Comic comic = new Comic();
        comic.setId(comicId);
        save(comicTo, comic);
        comicRepository.update(comic);
    }

    @Transactional
    public void delete(int id) {
        get(id);
        comicRepository.deleteById(id);
    }

    public List<Character> getAllCharactersPage(int comicId, String name, Optional<Integer> page, String direction, Optional<String> sort) {
        int limit = 5;

        direction = direction.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        List<String> fields = List.of("id", "name", "description");
        String sortField = sort.orElse("id");
        if (!fields.contains(sortField)) {
            sortField = "id";
        }

        return characterRepository.findByComicId(comicId, name, page.orElse(0), limit, direction, sortField);
    }
}
