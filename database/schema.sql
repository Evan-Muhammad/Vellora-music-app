-- USERS ---------------------------------------------------------
CREATE TABLE users (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50)  UNIQUE NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(20)  NOT NULL DEFAULT 'LISTENER'
                  CHECK (role IN ('LISTENER', 'ARTIST', 'ADMIN')),
    created_at    TIMESTAMP    NOT NULL DEFAULT now()
);

-- ARTIST PROFILES -------------------------------------------------
CREATE TABLE artist_profiles (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    display_name  VARCHAR(255) NOT NULL,
    bio           TEXT,
    verified      BOOLEAN NOT NULL DEFAULT false,
    created_at    TIMESTAMP NOT NULL DEFAULT now()
);

-- ALBUMS -----------------------------------------------------------
CREATE TABLE albums (
    id                 BIGSERIAL PRIMARY KEY,
    title              VARCHAR(255) NOT NULL,
    primary_artist_id  BIGINT NOT NULL REFERENCES artist_profiles(id) ON DELETE CASCADE,
    release_date       DATE,
    cover_url          VARCHAR(500),
    created_at         TIMESTAMP NOT NULL DEFAULT now()
);

-- SONGS --------------------------------------------------------------
CREATE TABLE songs (
    id            BIGSERIAL PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    album_id      BIGINT REFERENCES albums(id) ON DELETE SET NULL,
    duration      INTEGER,              -- seconds
    audio_url     VARCHAR(500),
    release_date  DATE,
    play_count    INTEGER NOT NULL DEFAULT 0,
    created_at    TIMESTAMP NOT NULL DEFAULT now()
);

-- SONG <-> ARTIST (many-to-many, supports collaborations) -----------
CREATE TABLE song_artists (
    id         BIGSERIAL PRIMARY KEY,
    song_id    BIGINT NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    artist_id  BIGINT NOT NULL REFERENCES artist_profiles(id) ON DELETE CASCADE,
    role       VARCHAR(20) NOT NULL DEFAULT 'PRIMARY'
               CHECK (role IN ('PRIMARY', 'FEATURED')),
    UNIQUE (song_id, artist_id)
);

-- GENRES ----------------------------------------------------------------
CREATE TABLE genres (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(100) UNIQUE NOT NULL
);

-- SONG <-> GENRE (many-to-many) -----------------------------------------
CREATE TABLE song_genres (
    id        BIGSERIAL PRIMARY KEY,
    song_id   BIGINT NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    genre_id  BIGINT NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
    UNIQUE (song_id, genre_id)
);

-- PLAYLISTS ------------------------------------------------------------
CREATE TABLE playlists (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(500),
    mood         VARCHAR(50),
    user_id      BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at   TIMESTAMP NOT NULL DEFAULT now()
);

-- PLAYLIST <-> SONG (many-to-many) -----------------------------------
CREATE TABLE playlist_songs (
    id           BIGSERIAL PRIMARY KEY,
    playlist_id  BIGINT NOT NULL REFERENCES playlists(id) ON DELETE CASCADE,
    song_id      BIGINT NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    added_at     TIMESTAMP NOT NULL DEFAULT now(),
    position     INTEGER,
    UNIQUE (playlist_id, song_id)
);

-- PLAY HISTORY -----------------------------------------------------------
CREATE TABLE play_history (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    song_id    BIGINT NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    played_at  TIMESTAMP NOT NULL DEFAULT now()
);

-- ARTIST FOLLOWS (many-to-many) -------------------------------------------
CREATE TABLE artist_follows (
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    artist_id    BIGINT NOT NULL REFERENCES artist_profiles(id) ON DELETE CASCADE,
    followed_at  TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE (user_id, artist_id)
);

CREATE INDEX idx_songs_album_id           ON songs(album_id);
CREATE INDEX idx_song_artists_song_id     ON song_artists(song_id);
CREATE INDEX idx_song_artists_artist_id   ON song_artists(artist_id);
CREATE INDEX idx_song_genres_song_id      ON song_genres(song_id);
CREATE INDEX idx_song_genres_genre_id     ON song_genres(genre_id);
CREATE INDEX idx_playlist_songs_playlist  ON playlist_songs(playlist_id);
CREATE INDEX idx_playlist_songs_song      ON playlist_songs(song_id);
CREATE INDEX idx_play_history_user_time   ON play_history(user_id, played_at);
CREATE INDEX idx_play_history_song        ON play_history(song_id);
CREATE INDEX idx_artist_follows_user      ON artist_follows(user_id);
CREATE INDEX idx_artist_follows_artist    ON artist_follows(artist_id);
CREATE INDEX idx_albums_primary_artist    ON albums(primary_artist_id);




CREATE VIEW song_catalog AS
SELECT
    s.id AS song_id,
    s.title,
    s.duration,
    s.play_count,
    ap.display_name AS primary_artist,
    STRING_AGG(DISTINCT g.name, ', ') AS genres
FROM songs s
         JOIN song_artists sa ON sa.song_id = s.id AND sa.role = 'PRIMARY'
         JOIN artist_profiles ap ON ap.id = sa.artist_id
         LEFT JOIN song_genres sg ON sg.song_id = s.id
         LEFT JOIN genres g ON g.id = sg.genre_id
GROUP BY s.id, s.title, s.duration, s.play_count, ap.display_name;



CREATE MATERIALIZED VIEW top_played_songs_mv AS
SELECT s.id AS song_id, s.title, s.play_count
FROM songs s
ORDER BY s.play_count DESC;

-- Required for REFRESH ... CONCURRENTLY (per your lecture, section 10)
CREATE UNIQUE INDEX idx_top_played_songs_mv_id ON top_played_songs_mv (song_id);


CREATE VIEW top_artists_view AS
SELECT
    ap.id AS artist_id,
    ap.display_name,
    SUM(s.play_count) AS total_plays
FROM artist_profiles ap
         JOIN song_artists sa ON sa.artist_id = ap.id AND sa.role = 'PRIMARY'
         JOIN songs s ON s.id = sa.song_id
GROUP BY ap.id, ap.display_name
ORDER BY total_plays DESC;


CREATE TABLE song_likes (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    song_id    BIGINT NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    liked_at   TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE (user_id, song_id)
);

CREATE INDEX idx_song_likes_user ON song_likes(user_id);
CREATE INDEX idx_song_likes_song ON song_likes(song_id);

