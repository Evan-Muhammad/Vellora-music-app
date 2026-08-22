package com.vellora.vellora_backend.service;

import com.vellora.vellora_backend.exception.SongNotFoundException;
import com.vellora.vellora_backend.exception.UserNotFoundException;
import com.vellora.vellora_backend.model.Song;
import com.vellora.vellora_backend.model.SongLike;
import com.vellora.vellora_backend.model.User;
import com.vellora.vellora_backend.repository.SongLikeRepository;
import com.vellora.vellora_backend.repository.SongRepository;
import com.vellora.vellora_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SongLikeService {

    private final SongLikeRepository songLikeRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public SongLikeService(SongLikeRepository songLikeRepository,
                           UserRepository userRepository,
                           SongRepository songRepository) {
        this.songLikeRepository = songLikeRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public SongLike like(Long userId, Long songId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found"));

        return songLikeRepository.findByUserIdAndSongId(userId, songId)
                .orElseGet(() -> {
                    SongLike like = new SongLike();
                    like.setUser(user);
                    like.setSong(song);
                    return songLikeRepository.save(like);
                });
    }

    public void unlike(Long userId, Long songId) {
        songLikeRepository.findByUserIdAndSongId(userId, songId)
                .ifPresent(songLikeRepository::delete);
    }
}