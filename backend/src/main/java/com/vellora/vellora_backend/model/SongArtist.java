package com.vellora.vellora_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "song_artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistProfile artist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SongArtistRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongArtist)) return false;
        SongArtist that = (SongArtist) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}