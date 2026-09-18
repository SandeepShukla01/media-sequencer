package com.example.media_sequencer.controller;

import com.example.media_sequencer.entity.Media;
import com.example.media_sequencer.repository.MediaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media")
@CrossOrigin
public class MediaController {

    private final MediaRepository mediaRepository;

    public MediaController(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @GetMapping
    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    @PostMapping
    public Media createMedia(@RequestBody Media media) {
        return mediaRepository.save(media);
    }
}