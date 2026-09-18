package com.example.media_sequencer.service;

import com.example.media_sequencer.entity.Playlist;
import com.example.media_sequencer.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist createPlaylistItem(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getPlaylistByWindow(Long windowId) {
        return playlistRepository.findByWindowIdOrderByPositionAsc(windowId);
    }

    public List<Playlist> getAllPlaylistItems() {
        return playlistRepository.findAll();
    }

    public void deletePlaylistItem(Long id) {
        playlistRepository.deleteById(id);
    }

    public Playlist updatePlaylistItem(Long id, Playlist updatedPlaylist) {

        Playlist existingPlaylist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist item not found"));

        existingPlaylist.setWindow(updatedPlaylist.getWindow());
        existingPlaylist.setMedia(updatedPlaylist.getMedia());
        existingPlaylist.setPosition(updatedPlaylist.getPosition());

        return playlistRepository.save(existingPlaylist);
    }
}