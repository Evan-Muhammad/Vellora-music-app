# Vellora

A personal music library and playlist manager — organize songs into custom playlists, tag them
by mood, track your listening habits, and browse a real music catalog where artists upload their
own songs (including collaborations and multi-genre tracks).

This project started as a backend planning assignment for summer training and is being expanded
throughout August into a full application with a real database, backend API, and user interface.

## Project Status
🚧 In progress — database structure complete, backend and frontend in development.

## Tech Stack
- **Database:** PostgreSQL
- **Backend:** _TBD_
- **Frontend:** _TBD_

## Repository Structure
```
Vellora/
├── README.md
├── docs/
│   └── project-plan.pdf        # Original backend planning document
├── database/
│   ├── schema.sql               # Full PostgreSQL schema (DDL)
│   └── erd.png                  # Entity-Relationship Diagram
├── backend/                     # API source code (in progress)
└── frontend/                    # UI source code (in progress)
```

## Database Overview
The schema has 11 tables covering two layers:

**Catalog layer** (shared, artist-owned)
- `users` — all accounts, with a `role` of LISTENER / ARTIST / ADMIN
- `artist_profiles` — artist identity, 1:1 with a user
- `albums` — songs can optionally belong to an album
- `songs` — core song record
- `song_artists` — join table for song ↔ artist (supports collaborations)
- `genres` — genre catalog
- `song_genres` — join table for song ↔ genre (a song can have multiple genres)

**Personal layer** (per-user)
- `playlists` — user-owned playlists, taggable by mood
- `playlist_songs` — join table for playlist ↔ song
- `play_history` — per-user play log
- `artist_follows` — join table for user ↔ followed artist

See [`database/erd.png`](database/erd.png) for the full diagram and
[`database/schema.sql`](database/schema.sql) for the complete DDL.

## Getting Started (Database)
```bash
# Create the database
createdb vellora

# Apply the schema
psql -d vellora -f database/schema.sql
```

## Roadmap
- [x] Problem statement, entities, and REST API design
- [x] Full database schema (PostgreSQL)
- [ ] Backend API implementation
- [ ] Authentication & role-based authorization
- [ ] Frontend UI
- [ ] Deployment
