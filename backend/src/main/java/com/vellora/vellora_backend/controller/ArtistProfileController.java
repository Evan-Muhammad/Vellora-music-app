package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.dto.ArtistProfileSummary;
import com.vellora.vellora_backend.model.ArtistProfile;
import com.vellora.vellora_backend.service.ArtistProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistProfileController {

    private final ArtistProfileService artistProfileService;

    public ArtistProfileController(ArtistProfileService artistProfileService) {
        this.artistProfileService = artistProfileService;
    }

    @PostMapping
    public ArtistProfileSummary becomeArtist(@RequestBody BecomeArtistRequest request) {
        ArtistProfile profile = artistProfileService.becomeArtist(
                request.userId(), request.displayName(), request.bio());
        return ArtistProfileSummary.from(profile);
    }

    public record BecomeArtistRequest(Long userId, String displayName, String bio) {}
}