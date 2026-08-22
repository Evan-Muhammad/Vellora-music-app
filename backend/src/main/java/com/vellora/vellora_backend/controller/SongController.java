package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.model.Song;
import com.vellora.vellora_backend.repository.SongCatalogView;
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
    public ResponseEntity<Song> createSong(@RequestBody CreateSongRequest request) {
        Song song = songService.createSong(
                request.primaryArtistId(),
                request.title(),
                request.duration(),
                request.featuredArtistIds(),
                request.genreIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(song);
    }

    @GetMapping("/search")
    public List<SongCatalogView> search(@RequestParam String query) {
        return songService.searchSongs(query);
    }

    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody UpdateSongRequest request) {
        return songService.updateSong(id, request.title(), request.duration());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    public record CreateSongRequest(
            Long primaryArtistId,
            String title,
            Integer duration,
            List<Long> featuredArtistIds,
            List<Long> genreIds
    ) {}

    public record UpdateSongRequest(String title, Integer duration) {}
}