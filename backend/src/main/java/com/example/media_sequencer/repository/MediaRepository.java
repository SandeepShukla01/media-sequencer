package com.example.media_sequencer.repository;

import com.example.media_sequencer.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}