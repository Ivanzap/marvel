package com.ivanzap.marvel.service;

import com.ivanzap.marvel.repository.ComicRepository;
import com.ivanzap.marvel.repository.InMemoryComicRepository;
import org.springframework.stereotype.Service;

@Service
public class ComicService {

    private final ComicRepository repository;

    public ComicService(ComicRepository repository) {
        this.repository = repository;
    }
}
