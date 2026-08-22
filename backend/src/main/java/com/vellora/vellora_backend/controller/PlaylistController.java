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
    public ResponseEntity<Playlist> createPlaylist(@RequestBody CreatePlaylistRequest request) {
        Playlist playlist = playlistService.createPlaylist(
                request.userId(), request.name(), request.description(), request.mood());
        return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
    }

    @PostMapping("/{id}/songs")
    public ResponseEntity<PlaylistSong> addSong(@PathVariable Long id, @RequestBody AddSongRequest request) {
        PlaylistSong playlistSong = playlistService.addSongToPlaylist(id, request.songId(), request.position());
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistSong);
    }

    @GetMapping("/{id}/songs")
    public List<PlaylistSong> getSongs(@PathVariable Long id) {
        return playlistService.getSongsInPlaylist(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/songs/{songId}")
    public ResponseEntity<Void> removeSong(@PathVariable Long id, @PathVariable Long songId) {
        playlistService.removeSongFromPlaylist(id, songId);
        return ResponseEntity.noContent().build();
    }

    public record CreatePlaylistRequest(Long userId, String name, String description, String mood) {}
    public record AddSongRequest(Long songId, Integer position) {}
}