package com.vellora.vellora_backend.dto;

import com.vellora.vellora_backend.model.ArtistProfile;

import java.time.LocalDateTime;

public record ArtistProfileSummary(
        Long id,
        UserSummary user,
        String displayName,
        String bio,
        boolean verified,
        LocalDateTime createdAt
) {
    public static ArtistProfileSummary from(ArtistProfile profile) {
        return new ArtistProfileSummary(
                profile.getId(),
                UserSummary.from(profile.getUser()),
                profile.getDisplayName(),
                profile.getBio(),
                profile.isVerified(),
                profile.getCreatedAt()
        );
    }
}