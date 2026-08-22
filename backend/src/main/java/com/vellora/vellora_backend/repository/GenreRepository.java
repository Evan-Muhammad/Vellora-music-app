package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}