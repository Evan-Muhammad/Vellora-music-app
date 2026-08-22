package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.model.Song;
import com.vellora.vellora_backend.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<?> createSong(@RequestBody CreateSongRequest request) {
        try {
            Song song = songService.createSong(
                    request.primaryArtistId(),
                    request.title(),
                    request.duration(),
                    request.featuredArtistIds(),
                    request.genreIds()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(song);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public record CreateSongRequest(
            Long primaryArtistId,
            String title,
            Integer duration,
            List<Long> featuredArtistIds,
            List<Long> genreIds
    ) {}
}