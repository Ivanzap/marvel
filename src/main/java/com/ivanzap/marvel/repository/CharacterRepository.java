package com.ivanzap.marvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ivanzap.marvel.model.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
}
