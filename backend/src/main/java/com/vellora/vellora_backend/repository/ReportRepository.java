package com.vellora.vellora_backend.repository;

import com.vellora.vellora_backend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Song, Long> {

    @Query(value = """
        SELECT song_id, title, play_count
        FROM top_played_songs_mv
        ORDER BY play_count DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<TopSongView> topSongs(@Param("limit") int limit);

    @Query(value = "SELECT artist_id, display_name, total_plays FROM top_artists_view LIMIT :limit", nativeQuery = true)
    List<TopArtistView> topArtists(@Param("limit") int limit);

    interface TopSongView {
        Long getSongId();
        String getTitle();
        Integer getPlayCount();
    }

    interface TopArtistView {
        Long getArtistId();
        String getDisplayName();
        Long getTotalPlays();
    }
}