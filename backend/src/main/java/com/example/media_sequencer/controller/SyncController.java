package com.example.media_sequencer.controller;

import com.example.media_sequencer.entity.Media;
import com.example.media_sequencer.entity.SyncEvent;
import com.example.media_sequencer.repository.MediaRepository;
import com.example.media_sequencer.service.SyncService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
@CrossOrigin
public class SyncController {

    private final SyncService syncService;
    private final MediaRepository mediaRepository;

    public SyncController(SyncService syncService, MediaRepository mediaRepository) {
        this.syncService = syncService;
        this.mediaRepository = mediaRepository;
    }

    @PostMapping("/{mediaId}")
    public SyncEvent startSync(
            @PathVariable Long mediaId,
            @RequestParam(defaultValue = "10") int durationSeconds) {

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        return syncService.startSync(media, durationSeconds);
    }

    @GetMapping
    public SyncEvent getLatestSync() {
        return syncService.getLatestSync();
    }
}