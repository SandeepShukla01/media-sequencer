package com.example.media_sequencer.repository;

import com.example.media_sequencer.entity.SyncEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncEventRepository extends JpaRepository<SyncEvent, Long> {
}