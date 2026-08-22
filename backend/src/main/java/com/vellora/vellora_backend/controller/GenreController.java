package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.model.Genre;
import com.vellora.vellora_backend.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public Genre createGenre(@RequestBody CreateGenreRequest request) {
        return genreService.createGenre(request.name());
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    public record CreateGenreRequest(String name) {}
}