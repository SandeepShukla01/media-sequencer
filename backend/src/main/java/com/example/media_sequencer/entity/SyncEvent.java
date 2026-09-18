package com.example.media_sequencer.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sync_events")
public class SyncEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @Column(nullable = false)
    private int durationSeconds;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Instant startedAt;

    public SyncEvent() {
    }

    public SyncEvent(Media media, int durationSeconds, boolean active, Instant startedAt) {
        this.media = media;
        this.durationSeconds = durationSeconds;
        this.active = active;
        this.startedAt = startedAt;
    }

    public Long getId() {
        return id;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }
}