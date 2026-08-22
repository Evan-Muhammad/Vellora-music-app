package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.model.Playlist;
import com.vellora.vellora_backend.model.PlaylistSong;
import com.vellora.vellora_backend.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody CreatePlaylistRequest request) {
        try {
            Playlist playlist = playlistService.createPlaylist(
                    request.userId(), request.name(), request.description(), request.mood());
            return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/songs")
    public ResponseEntity<?> addSong(@PathVariable Long id, @RequestBody AddSongRequest request) {
        try {
            PlaylistSong playlistSong = playlistService.addSongToPlaylist(id, request.songId(), request.position());
            return ResponseEntity.status(HttpStatus.CREATED).body(playlistSong);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/songs")
    public List<PlaylistSong> getSongs(@PathVariable Long id) {
        return playlistService.getSongsInPlaylist(id);
    }

    public record CreatePlaylistRequest(Long userId, String name, String description, String mood) {}
    public record AddSongRequest(Long songId, Integer position) {}
}