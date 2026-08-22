package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.SongArtist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongArtistRepository extends JpaRepository<SongArtist, Long> {
}