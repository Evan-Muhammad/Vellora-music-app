package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.ArtistProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistProfileRepository extends JpaRepository<ArtistProfile, Long> {
    Optional<ArtistProfile> findByUserId(Long userId);
}