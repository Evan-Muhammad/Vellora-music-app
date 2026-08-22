package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {
    List<PlaylistSong> findByPlaylistId(Long playlistId);
}