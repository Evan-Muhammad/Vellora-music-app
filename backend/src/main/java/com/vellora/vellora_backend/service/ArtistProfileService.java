package com.vellora.vellora_backend.service;

import com.vellora.vellora_backend.exception.ArtistAlreadyExistsException;
import com.vellora.vellora_backend.exception.UserNotFoundException;
import com.vellora.vellora_backend.model.ArtistProfile;
import com.vellora.vellora_backend.model.Role;
import com.vellora.vellora_backend.model.User;
import com.vellora.vellora_backend.repository.ArtistProfileRepository;
import com.vellora.vellora_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ArtistProfileService {

    private final ArtistProfileRepository artistProfileRepository;
    private final UserRepository userRepository;

    public ArtistProfileService(ArtistProfileRepository artistProfileRepository, UserRepository userRepository) {
        this.artistProfileRepository = artistProfileRepository;
        this.userRepository = userRepository;
    }

    public ArtistProfile becomeArtist(Long userId, String displayName, String bio) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (artistProfileRepository.findByUserId(userId).isPresent()) {
            throw new ArtistAlreadyExistsException("User is already an artist");
        }

        user.setRole(Role.ARTIST);
        userRepository.save(user);

        ArtistProfile profile = new ArtistProfile();
        profile.setUser(user);
        profile.setDisplayName(displayName);
        profile.setBio(bio);

        return artistProfileRepository.save(profile);
    }
}