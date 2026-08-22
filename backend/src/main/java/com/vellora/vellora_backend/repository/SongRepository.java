package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query(value = """
        SELECT song_id, title, duration, play_count, primary_artist, genres
        FROM song_catalog
        WHERE LOWER(title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(primary_artist) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(genres) LIKE LOWER(CONCAT('%', :query, '%'))
        """, nativeQuery = true)
    List<SongCatalogView> search(@Param("query") String query);
}