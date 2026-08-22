package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}