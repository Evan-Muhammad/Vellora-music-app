package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongLikeRepository extends JpaRepository<SongLike, Long> {
    Optional<SongLike> findByUserIdAndSongId(Long userId, Long songId);
}