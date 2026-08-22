package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.model.PlayHistory;
import com.vellora.vellora_backend.service.PlayHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class PlayHistoryController {

    private final PlayHistoryService playHistoryService;

    public PlayHistoryController(PlayHistoryService playHistoryService) {
        this.playHistoryService = playHistoryService;
    }

    @PostMapping("/{songId}/play")
    public ResponseEntity<PlayHistory> play(@PathVariable Long songId, @RequestBody PlayRequest request) {
        PlayHistory playHistory = playHistoryService.recordPlay(request.userId(), songId);
        return ResponseEntity.status(HttpStatus.CREATED).body(playHistory);
    }

    @GetMapping("/history/{userId}")
    public List<PlayHistory> getHistory(@PathVariable Long userId) {
        return playHistoryService.getHistoryForUser(userId);
    }

    public record PlayRequest(Long userId) {}
}