package com.ivanzap.marvel.repository;

import com.ivanzap.marvel.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepository extends JpaRepository<Comic, Integer> {
}
