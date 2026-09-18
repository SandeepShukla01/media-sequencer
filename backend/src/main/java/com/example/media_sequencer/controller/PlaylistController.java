package com.example.media_sequencer.controller;

import com.example.media_sequencer.entity.Playlist;
import com.example.media_sequencer.service.PlaylistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylistItems();
    }

    @GetMapping("/window/{windowId}")
    public List<Playlist> getPlaylistByWindow(@PathVariable Long windowId) {
        return playlistService.getPlaylistByWindow(windowId);
    }

    @PostMapping
    public Playlist createPlaylistItem(@RequestBody Playlist playlist) {
        return playlistService.createPlaylistItem(playlist);
    }

    @PutMapping("/{id}")
    public Playlist updatePlaylistItem(
            @PathVariable Long id,
            @RequestBody Playlist playlist) {

        return playlistService.updatePlaylistItem(id, playlist);
    }

    @DeleteMapping("/{id}")
    public void deletePlaylistItem(@PathVariable Long id) {
        playlistService.deletePlaylistItem(id);
    }
}