package com.ivanzap.marvel.repository;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.model.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    Page<Character> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("select comic.id from Character character join character.comics comic where character.id=?1 and upper(comic.title) like concat('%', upper(?2), '%')")
    List<Integer> getAllComics(@Param("id") int characterId, @Param("title") String title);

    Page<Character> findByIdIn(@Param("id") List<Integer> id, Pageable pageable);
}
