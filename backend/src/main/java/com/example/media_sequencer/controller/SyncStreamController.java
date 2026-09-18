package com.example.media_sequencer.controller;

import com.example.media_sequencer.entity.SyncEvent;
import com.example.media_sequencer.service.SyncService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
@CrossOrigin
public class SyncStreamController {

    private final SyncService syncService;

    public SyncStreamController(SyncService syncService) {
        this.syncService = syncService;
    }

    @GetMapping("/status")
    public SyncEvent getSyncStatus() {
        return syncService.getLatestSync();
    }
}