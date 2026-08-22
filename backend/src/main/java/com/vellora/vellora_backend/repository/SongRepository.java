package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}