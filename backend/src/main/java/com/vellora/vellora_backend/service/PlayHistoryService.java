package com.vellora.vellora_backend.service;

import com.vellora.vellora_backend.model.PlayHistory;
import com.vellora.vellora_backend.model.Song;
import com.vellora.vellora_backend.model.User;
import com.vellora.vellora_backend.repository.PlayHistoryRepository;
import com.vellora.vellora_backend.repository.SongRepository;
import com.vellora.vellora_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayHistoryService {

    private final PlayHistoryRepository playHistoryRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public PlayHistoryService(PlayHistoryRepository playHistoryRepository,
                              UserRepository userRepository,
                              SongRepository songRepository) {
        this.playHistoryRepository = playHistoryRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Transactional
    public PlayHistory recordPlay(Long userId, Long songId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        song.setPlayCount(song.getPlayCount() + 1);
        songRepository.save(song);

        PlayHistory playHistory = new PlayHistory();
        playHistory.setUser(user);
        playHistory.setSong(song);

        return playHistoryRepository.save(playHistory);
    }

    public List<PlayHistory> getHistoryForUser(Long userId) {
        return playHistoryRepository.findByUserId(userId);
    }
}