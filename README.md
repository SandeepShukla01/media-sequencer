# Media Sequencer

A full-stack Multi-Window Media Sequencer application built with React, Java Spring Boot, and PostgreSQL.

## Project Overview

The application manages multiple display windows where each window can play its own media playlist continuously.

It supports:

- Multiple independent display windows
- Custom media playlists for each window
- Dynamic playlist updates
- Continuous playlist looping
- 5-hour playback cycle
- Synchronized media playback across all windows
- Persistent PostgreSQL storage
- REST APIs for windows, media, playlists, and synchronization
- Cloud deployment using Render

## Tech Stack

### Frontend

- React
- Vite
- JavaScript
- CSS

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs

### Database

- PostgreSQL

### Deployment

- Render

## Project Structure

```text
media-sequencer/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/media_sequencer/
│   │
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── vite.config.js
│
└── README.md
```

## Core Features

### 1. Multiple Windows

The application supports multiple display windows. Each window maintains its own playlist and playback sequence.

### 2. Dynamic Playlists

Media can be added to a selected window playlist with a specific position.

Playlist changes are persisted in PostgreSQL and reflected in the frontend.

### 3. Continuous Playback

Each window continuously cycles through its assigned playlist.

After reaching the end of the playlist, playback starts again from the beginning.

### 4. Five-Hour Playback Cycle

Each window operates within a 5-hour playback cycle.

### 5. Synchronized Playback

A media item can be selected for synchronization.

When synchronization starts:

- The selected media is displayed on all windows.
- All windows enter synchronized playback.
- The synchronization remains active for the configured duration.
- After synchronization ends, every window resumes its own playlist.

## Backend API

Base URL:

```text
https://media-sequencer-6w6y.onrender.com/api
```

### Windows

Get all windows:

```http
GET /windows
```

Create a window:

```http
POST /windows
```

### Media

Get all media:

```http
GET /media
```

Create media:

```http
POST /media
```

### Playlists

Get all playlists:

```http
GET /playlists
```

Get playlist for a specific window:

```http
GET /playlists/window/{windowId}
```

Create playlist item:

```http
POST /playlists
```

Update playlist item:

```http
PUT /playlists/{id}
```

Delete playlist item:

```http
DELETE /playlists/{id}
```

### Synchronization

Start synchronization:

```http
POST /sync/{mediaId}?durationSeconds=10
```

Get current synchronization status:

```http
GET /sync
```

Stream synchronization status:

```http
GET /sync/status
```

## Database Entities

The application uses the following main entities:

- Window
- Media
- Playlist
- SyncEvent

Relationships:

```text
Window
   |
   └── Playlist ─── Media

SyncEvent ─── Media
```

## Local Setup

### Backend

Navigate to the backend directory:

```bash
cd backend
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

### Frontend

Navigate to the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5174
```

## Environment Configuration

The frontend uses:

```text
VITE_API_URL
```

Example:

```env
VITE_API_URL=http://localhost:8080/api
```

For production deployment:

```env
VITE_API_URL=https://media-sequencer-6w6y.onrender.com/api
```

The backend database configuration uses environment variables:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
```

## Deployment

### Backend

The Spring Boot backend is deployed on Render.

Production backend:

```text
https://media-sequencer-6w6y.onrender.com
```

### Frontend

The React frontend is deployed as a Render Static Site.

Production frontend:

```text
https://media-sequencer-frontend-2clp.onrender.com
```

## GitHub Repository

```text
https://github.com/SandeepShukla01/media-sequencer
```

## Application Flow

```text
User
  |
  v
React Frontend
  |
  | REST API
  v
Spring Boot Backend
  |
  | JPA / Hibernate
  v
PostgreSQL
```

For synchronized playback:

```text
User selects media
        |
        v
Start Sync API
        |
        v
SyncEvent created
        |
        v
All windows display selected media
        |
        v
Sync duration completed
        |
        v
Each window resumes its own playlist
```

## Future Improvements

- Video media support
- Authentication and authorization
- Drag-and-drop playlist management
- Real-time synchronization using WebSockets
- Media upload functionality
- Improved playback controls
- Playlist scheduling

## Author

**Sandeep Shukla**

B.Tech - Computer Science Engineering
