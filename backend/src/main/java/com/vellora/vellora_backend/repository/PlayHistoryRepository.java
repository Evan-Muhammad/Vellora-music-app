package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.PlayHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayHistoryRepository extends JpaRepository<PlayHistory, Long> {
    List<PlayHistory> findByUserId(Long userId);
}