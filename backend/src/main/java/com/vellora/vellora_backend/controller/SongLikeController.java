package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.model.SongLike;
import com.vellora.vellora_backend.service.SongLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/songs")
public class SongLikeController {

    private final SongLikeService songLikeService;

    public SongLikeController(SongLikeService songLikeService) {
        this.songLikeService = songLikeService;
    }

    @PostMapping("/{songId}/like")
    public ResponseEntity<SongLike> like(@PathVariable Long songId, @RequestBody LikeRequest request) {
        SongLike like = songLikeService.like(request.userId(), songId);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }

    @DeleteMapping("/{songId}/like")
    public ResponseEntity<Void> unlike(@PathVariable Long songId, @RequestParam Long userId) {
        songLikeService.unlike(userId, songId);
        return ResponseEntity.noContent().build();
    }

    public record LikeRequest(Long userId) {}
}