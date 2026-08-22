package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.dto.ArtistProfileSummary;
import com.vellora.vellora_backend.model.ArtistProfile;
import com.vellora.vellora_backend.service.ArtistProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistProfileController {

    private final ArtistProfileService artistProfileService;

    public ArtistProfileController(ArtistProfileService artistProfileService) {
        this.artistProfileService = artistProfileService;
    }

    @PostMapping
    public ResponseEntity<?> becomeArtist(@RequestBody BecomeArtistRequest request) {
        try {
            ArtistProfile profile = artistProfileService.becomeArtist(
                    request.userId(), request.displayName(), request.bio());
            return ResponseEntity.status(HttpStatus.CREATED).body(ArtistProfileSummary.from(profile));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public record BecomeArtistRequest(Long userId, String displayName, String bio) {}
}