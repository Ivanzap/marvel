package com.ivanzap.marvel.repository;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Integer> {
    Page<Comic> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    @Query("select character.id from Comic comic join comic.characters character where comic.id=?1 and upper(character.name) like concat('%', upper(?2), '%')")
    List<Integer> getAllCharacters(@Param("id") int comicId, @Param("name") String name);

    Page<Comic> findByIdIn(@Param("id") List<Integer> id, Pageable pageable);
}
