package com.vellora.vellora_backend.service;

import com.vellora.vellora_backend.model.*;
import com.vellora.vellora_backend.repository.ArtistProfileRepository;
import com.vellora.vellora_backend.repository.GenreRepository;
import com.vellora.vellora_backend.repository.SongArtistRepository;
import com.vellora.vellora_backend.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final SongArtistRepository songArtistRepository;
    private final ArtistProfileRepository artistProfileRepository;
    private final GenreRepository genreRepository;

    public SongService(SongRepository songRepository,
                       SongArtistRepository songArtistRepository,
                       ArtistProfileRepository artistProfileRepository,
                       GenreRepository genreRepository) {
        this.songRepository = songRepository;
        this.songArtistRepository = songArtistRepository;
        this.artistProfileRepository = artistProfileRepository;
        this.genreRepository = genreRepository;
    }

    public Song createSong(Long primaryArtistId, String title, Integer duration,
                           List<Long> featuredArtistIds, List<Long> genreIds) {

        ArtistProfile primaryArtist = artistProfileRepository.findById(primaryArtistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        Song song = new Song();
        song.setTitle(title);
        song.setDuration(duration);

        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(genreIds));
        song.setGenres(genres);

        song = songRepository.save(song);

        SongArtist primaryLink = new SongArtist();
        primaryLink.setSong(song);
        primaryLink.setArtist(primaryArtist);
        primaryLink.setRole(SongArtistRole.PRIMARY);
        songArtistRepository.save(primaryLink);

        if (featuredArtistIds != null) {
            for (Long featuredId : featuredArtistIds) {
                ArtistProfile featuredArtist = artistProfileRepository.findById(featuredId)
                        .orElseThrow(() -> new RuntimeException("Featured artist not found: " + featuredId));
                SongArtist featuredLink = new SongArtist();
                featuredLink.setSong(song);
                featuredLink.setArtist(featuredArtist);
                featuredLink.setRole(SongArtistRole.FEATURED);
                songArtistRepository.save(featuredLink);
            }
        }

        return song;
    }
}