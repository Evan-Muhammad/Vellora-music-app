package com.vellora.vellora_backend.service;

import com.vellora.vellora_backend.exception.PlaylistNotFoundException;
import com.vellora.vellora_backend.exception.SongNotFoundException;
import com.vellora.vellora_backend.exception.UserNotFoundException;
import com.vellora.vellora_backend.model.*;
import com.vellora.vellora_backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           PlaylistSongRepository playlistSongRepository,
                           UserRepository userRepository,
                           SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public Playlist createPlaylist(Long userId, String name, String description, String mood) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Playlist playlist = new Playlist();
        playlist.setUser(user);
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setMood(mood);

        return playlistRepository.save(playlist);
    }

    public PlaylistSong addSongToPlaylist(Long playlistId, Long songId, Integer position) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found"));

        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);
        playlistSong.setPosition(position);

        return playlistSongRepository.save(playlistSong);
    }

    public List<PlaylistSong> getSongsInPlaylist(Long playlistId) {
        return playlistSongRepository.findByPlaylistId(playlistId);
    }
}