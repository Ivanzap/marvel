package com.ivanzap.marvel.web;

import com.ivanzap.marvel.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.ivanzap.marvel.model.Character;

import java.util.List;

@Controller
public class CharacterRestController {
    private static final Logger log = LoggerFactory.getLogger(CharacterRestController.class);

    private final CharacterService service;

    public CharacterRestController(CharacterService service) {
        this.service = service;
    }

    public Character save(Character character) {
        log.info("save {}", character);
        return service.save(character);
    }

    public Character get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<Character> getAll() {
        log.info("getAll");
        return service.getAll();
    }
}
