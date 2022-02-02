package com.ivanzap.marvel.repository;

import com.ivanzap.marvel.model.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Integer> {
    Page<Comic> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}
