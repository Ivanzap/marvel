package com.ivanzap.marvel.web;

import com.ivanzap.marvel.service.ComicService;
import org.springframework.stereotype.Controller;

@Controller
public class ComicRestController {

    private final ComicService service;

    public ComicRestController(ComicService service) {
        this.service = service;
    }
}
