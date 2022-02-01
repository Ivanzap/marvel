package com.ivanzap.marvel.repository;

import com.ivanzap.marvel.model.Character;
import com.ivanzap.marvel.util.CharacterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryCharacterRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryCharacterRepository.class);

    private final Map<Integer, Character> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        CharacterUtil.characters.forEach(this::save);
    }

    public Character save(Character character) {
        log.debug("save {}", character);
        if (character.isNew()) {
            character.setId(counter.incrementAndGet());
            repository.put(character.getId(), character);
            return character;
        }
        return repository.computeIfPresent(character.getId(), (id, oldCharacter) -> character);
    }

    public Character get(int id) {
        log.debug("get {}", id);
        return repository.get(id);
    }

    public List<Character> getAll() {
        log.debug("getAll");
        return repository.values().stream().toList();
    }
}
