package com.example.media_sequencer.repository;

import com.example.media_sequencer.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByWindowIdOrderByPositionAsc(Long windowId);
}