package com.example.media_sequencer.controller;

import com.example.media_sequencer.entity.Window;
import com.example.media_sequencer.repository.WindowRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/windows")
@CrossOrigin
public class WindowController {

    private final WindowRepository windowRepository;

    public WindowController(WindowRepository windowRepository) {
        this.windowRepository = windowRepository;
    }

    @GetMapping
    public List<Window> getAllWindows() {
        return windowRepository.findAll();
    }

    @PostMapping
    public Window createWindow(@RequestBody Window window) {
        return windowRepository.save(window);
    }
}