package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Comic;
import com.ivanzap.marvel.repository.ComicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComicService {

    private final ComicRepository repository;

    public ComicService(ComicRepository repository) {
        this.repository = repository;
    }

    public Comic get(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Comic> getAllPage(String title, Optional<Integer> page, String direction, Optional<String> sortBy) {
        direction = direction.equalsIgnoreCase("desc") ? direction : "ASC";
        Pageable pageable = PageRequest.of(page.orElse(0), 5, Sort.Direction.fromString(direction), sortBy.orElse("id"));
        if (title == null) {
            return repository.findAll(pageable);
        } else {
            return repository.findByTitleContainingIgnoreCase(title, pageable);
        }
    }

    public Comic create(Comic comic) {
        return repository.save(comic);
    }

    public void update(Comic comic) {
        repository.save(comic);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
