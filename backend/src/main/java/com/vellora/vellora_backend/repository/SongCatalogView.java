package com.vellora.vellora_backend.repository;

public interface SongCatalogView {
    Long getSongId();
    String getTitle();
    Integer getDuration();
    Integer getPlayCount();
    String getPrimaryArtist();
    String getGenres();
}