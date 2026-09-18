package com.example.media_sequencer.service;

import com.example.media_sequencer.entity.Media;
import com.example.media_sequencer.entity.SyncEvent;
import com.example.media_sequencer.repository.SyncEventRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SyncService {

    private final SyncEventRepository syncEventRepository;

    public SyncService(SyncEventRepository syncEventRepository) {
        this.syncEventRepository = syncEventRepository;
    }

    public SyncEvent startSync(Media media, int durationSeconds) {

        SyncEvent syncEvent = new SyncEvent(
                media,
                durationSeconds,
                true,
                Instant.now()
        );

        return syncEventRepository.save(syncEvent);
    }

    public SyncEvent getLatestSync() {

        SyncEvent syncEvent = syncEventRepository.findAll()
                .stream()
                .reduce((first, second) -> second)
                .orElse(null);

        if (syncEvent == null) {
            return null;
        }

        Instant endTime = syncEvent.getStartedAt()
                .plusSeconds(syncEvent.getDurationSeconds());

        if (Instant.now().isAfter(endTime)) {
            syncEvent.setActive(false);
            syncEventRepository.save(syncEvent);
        }

        return syncEvent;
    }
}