package com.example.media_sequencer;

import com.example.media_sequencer.entity.Media;
import com.example.media_sequencer.entity.Playlist;
import com.example.media_sequencer.entity.Window;
import com.example.media_sequencer.repository.MediaRepository;
import com.example.media_sequencer.repository.PlaylistRepository;
import com.example.media_sequencer.repository.WindowRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final WindowRepository windowRepository;
    private final MediaRepository mediaRepository;
    private final PlaylistRepository playlistRepository;

    public DataInitializer(
            WindowRepository windowRepository,
            MediaRepository mediaRepository,
            PlaylistRepository playlistRepository
    ) {
        this.windowRepository = windowRepository;
        this.mediaRepository = mediaRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void run(String... args) {

        // Create sample windows if they don't exist
        if (windowRepository.count() == 0) {
            windowRepository.save(new Window("Window 1"));
            windowRepository.save(new Window("Window 2"));
            windowRepository.save(new Window("Window 3"));
        }

        // Create sample media if they don't exist
        if (mediaRepository.count() == 0) {

            mediaRepository.save(
                    new Media(
                            "M1",
                            "image",
                            "https://picsum.photos/id/10/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M2",
                            "image",
                            "https://picsum.photos/id/20/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M3",
                            "image",
                            "https://picsum.photos/id/30/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M4",
                            "image",
                            "https://picsum.photos/id/40/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M5",
                            "image",
                            "https://picsum.photos/id/50/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M6",
                            "image",
                            "https://picsum.photos/id/60/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M7",
                            "image",
                            "https://picsum.photos/id/70/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M8",
                            "image",
                            "https://picsum.photos/id/80/800/600",
                            10
                    )
            );

            mediaRepository.save(
                    new Media(
                            "M9",
                            "image",
                            "https://picsum.photos/id/90/800/600",
                            10
                    )
            );
        }

        // Create sample playlists if none exist
        if (playlistRepository.count() == 0) {

            Window window1 = windowRepository.findById(1L).orElse(null);
            Window window2 = windowRepository.findById(2L).orElse(null);
            Window window3 = windowRepository.findById(3L).orElse(null);

            Media m1 = mediaRepository.findById(1L).orElse(null);
            Media m2 = mediaRepository.findById(2L).orElse(null);
            Media m3 = mediaRepository.findById(3L).orElse(null);
            Media m4 = mediaRepository.findById(4L).orElse(null);
            Media m5 = mediaRepository.findById(5L).orElse(null);

            if (window1 != null && m1 != null && m2 != null && m3 != null) {
                playlistRepository.save(
                        new Playlist(window1, m1, 1)
                );

                playlistRepository.save(
                        new Playlist(window1, m2, 2)
                );

                playlistRepository.save(
                        new Playlist(window1, m3, 3)
                );
            }

            if (window2 != null && m4 != null && m2 != null) {
                playlistRepository.save(
                        new Playlist(window2, m4, 1)
                );

                playlistRepository.save(
                        new Playlist(window2, m2, 2)
                );
            }

            if (window3 != null && m5 != null) {
                playlistRepository.save(
                        new Playlist(window3, m5, 1)
                );
            }
        }
    }
}