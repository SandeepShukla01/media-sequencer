import { useEffect, useState } from "react";
import "./App.css";

const API = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

// Each window operates within a 5-hour cycle
const CYCLE_DURATION = 5 * 60 * 60 * 1000;

function App() {
  const [windows, setWindows] = useState([]);
  const [mediaList, setMediaList] = useState([]);
  const [playlists, setPlaylists] = useState({});
  const [sync, setSync] = useState(null);
  const [currentIndex, setCurrentIndex] = useState({});
  const [cycleStart, setCycleStart] = useState({});

  const [selectedWindow, setSelectedWindow] = useState("");
  const [selectedMedia, setSelectedMedia] = useState("");
  const [position, setPosition] = useState(1);

  const [syncMedia, setSyncMedia] = useState("");
  const [syncDuration, setSyncDuration] = useState(10);

  const loadData = async () => {
    try {
      const windowResponse = await fetch(`${API}/windows`);
      const windowData = await windowResponse.json();

      setWindows(windowData);

      const mediaResponse = await fetch(`${API}/media`);
      const mediaData = await mediaResponse.json();

      setMediaList(mediaData);

      if (windowData.length > 0 && !selectedWindow) {
        setSelectedWindow(windowData[0].id);
      }

      if (mediaData.length > 0 && !syncMedia) {
        setSyncMedia(mediaData[0].id);
      }

      const playlistData = {};

      for (const window of windowData) {
        const response = await fetch(
          `${API}/playlists/window/${window.id}`
        );

        const data = await response.json();

        playlistData[window.id] = data;
      }

      setPlaylists(playlistData);
    } catch (error) {
      console.error("Error loading data:", error);
    }
  };

  const checkSync = async () => {
    try {
      const response = await fetch(`${API}/sync`);

      if (response.ok) {
        const data = await response.json();
        setSync(data);
      }
    } catch (error) {
      console.error("Sync error:", error);
    }
  };

  const addToPlaylist = async () => {
    if (!selectedWindow || !selectedMedia) {
      alert("Please select window and media");
      return;
    }

    try {
      const response = await fetch(`${API}/playlists`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          window: {
            id: Number(selectedWindow),
          },
          media: {
            id: Number(selectedMedia),
          },
          position: Number(position),
        }),
      });

      if (response.ok) {
        alert("Media added to playlist");

        await loadData();

        setCurrentIndex((previous) => ({
          ...previous,
          [selectedWindow]: 0,
        }));

        setCycleStart((previous) => ({
          ...previous,
          [selectedWindow]: Date.now(),
        }));
      } else {
        alert("Failed to add media");
      }
    } catch (error) {
      console.error("Add playlist error:", error);
    }
  };

  const startSync = async () => {
    if (!syncMedia) {
      alert("Please select media for sync");
      return;
    }

    try {
      const response = await fetch(
        `${API}/sync/${syncMedia}?durationSeconds=${syncDuration}`,
        {
          method: "POST",
        }
      );

      if (response.ok) {
        const data = await response.json();
        setSync(data);
      } else {
        alert("Sync failed");
      }
    } catch (error) {
      console.error("Sync error:", error);
    }
  };

  // Initial data loading
  useEffect(() => {
    loadData();
    checkSync();

    // Only check sync status.
    // Playlist data is not reloaded every 3 seconds,
    // otherwise playback timers would reset.
    const interval = setInterval(() => {
      checkSync();
    }, 3000);

    return () => clearInterval(interval);
  }, []);

  // Initialize 5-hour cycle
  useEffect(() => {
    if (windows.length === 0) {
      return;
    }

    setCycleStart((previous) => {
      const updated = { ...previous };

      windows.forEach((window) => {
        if (!updated[window.id]) {
          updated[window.id] = Date.now();
        }
      });

      return updated;
    });
  }, [windows]);

  // Playlist playback
  useEffect(() => {
    const timers = [];

    windows.forEach((window) => {
      const list = playlists[window.id];

      if (!list || list.length === 0) {
        return;
      }

      const index = currentIndex[window.id] || 0;
      const media = list[index]?.media;

      if (!media) {
        return;
      }

      const duration =
        (media.durationSeconds || 10) * 1000;

      const timer = setTimeout(() => {
        const startTime =
          cycleStart[window.id] || Date.now();

        const elapsed =
          Date.now() - startTime;

        if (elapsed >= CYCLE_DURATION) {
          setCurrentIndex((previous) => ({
            ...previous,
            [window.id]: 0,
          }));

          setCycleStart((previous) => ({
            ...previous,
            [window.id]: Date.now(),
          }));
        } else {
          setCurrentIndex((previous) => ({
            ...previous,
            [window.id]:
              (index + 1) % list.length,
          }));
        }
      }, duration);

      timers.push(timer);
    });

    return () => {
      timers.forEach(clearTimeout);
    };
  }, [
    windows,
    playlists,
    currentIndex,
    cycleStart,
  ]);

  const getCurrentMedia = (windowId) => {
    const list = playlists[windowId];

    if (!list || list.length === 0) {
      return null;
    }

    const index = currentIndex[windowId] || 0;

    return list[index]?.media;
  };

  const isSyncActive = () => {
    if (!sync || !sync.active) {
      return false;
    }

    const endTime =
      new Date(sync.startedAt).getTime() +
      sync.durationSeconds * 1000;

    return Date.now() < endTime;
  };

  const syncActive = isSyncActive();

  return (
    <div className="app">

      {/* Header */}

      <header className="header">

        <div>
          <h1>Media Sequencer</h1>

          <p>
            Multi-window synchronized media playback
          </p>
        </div>

        <div className="status">

          {syncActive ? (
            <span className="sync-active">
              ● Sync Active
            </span>
          ) : (
            <span className="normal-status">
              ● Normal Playback
            </span>
          )}

        </div>

      </header>

      {/* Controls */}

      <section className="controls">

        {/* Playlist Control */}

        <div className="control-card">

          <div className="control-title">

            <div>
              <h2>Playlist Control</h2>

              <p>
                Add media to a display window
              </p>
            </div>

          </div>

          <div className="form-grid">

            <div className="form-group">

              <label>
                Display Window
              </label>

              <select
                value={selectedWindow}
                onChange={(e) =>
                  setSelectedWindow(e.target.value)
                }
              >

                <option value="">
                  Select Window
                </option>

                {windows.map((window) => (
                  <option
                    key={window.id}
                    value={window.id}
                  >
                    {window.name}
                  </option>
                ))}

              </select>

            </div>

            <div className="form-group">

              <label>
                Media
              </label>

              <select
                value={selectedMedia}
                onChange={(e) =>
                  setSelectedMedia(e.target.value)
                }
              >

                <option value="">
                  Select Media
                </option>

                {mediaList.map((media) => (
                  <option
                    key={media.id}
                    value={media.id}
                  >
                    {media.name}
                  </option>
                ))}

              </select>

            </div>

            <div className="form-group">

              <label>
                Position
              </label>

              <input
                type="number"
                min="1"
                value={position}
                onChange={(e) =>
                  setPosition(e.target.value)
                }
              />

            </div>

            <div className="form-group button-group">

              <label>&nbsp;</label>

              <button
                className="primary-button"
                onClick={addToPlaylist}
              >
                + Add Media
              </button>

            </div>

          </div>

        </div>

        {/* Sync Control */}

        <div className="control-card sync-card">

          <div className="control-title">

            <div>
              <h2>Sync Control</h2>

              <p>
                Play selected media across all windows
              </p>
            </div>

            {syncActive && (
              <span className="live-badge">
                LIVE
              </span>
            )}

          </div>

          <div className="form-grid">

            <div className="form-group">

              <label>
                Media
              </label>

              <select
                value={syncMedia}
                onChange={(e) =>
                  setSyncMedia(e.target.value)
                }
              >

                <option value="">
                  Select Media
                </option>

                {mediaList.map((media) => (
                  <option
                    key={media.id}
                    value={media.id}
                  >
                    {media.name}
                  </option>
                ))}

              </select>

            </div>

            <div className="form-group">

              <label>
                Duration
              </label>

              <input
                type="number"
                min="1"
                value={syncDuration}
                onChange={(e) =>
                  setSyncDuration(e.target.value)
                }
              />

            </div>

            <div className="form-group button-group">

              <label>&nbsp;</label>

              <button
                className="sync-button"
                onClick={startSync}
                disabled={syncActive}
              >
                {syncActive
                  ? "Sync Running..."
                  : "Start Sync"}
              </button>

            </div>

          </div>

        </div>

      </section>

      {/* Display Windows */}

      <main className="windows-container">

        {windows.map((window) => {

          const normalMedia =
            getCurrentMedia(window.id);

          const media = syncActive
            ? sync?.media
            : normalMedia;

          return (

            <div
              className="window-card"
              key={window.id}
            >

              <div className="window-header">

                <div>

                  <h2>
                    {window.name}
                  </h2>

                  <span>
                    Display Window {window.id}
                  </span>

                </div>

                <span className="window-status">
                  {syncActive
                    ? "SYNCED"
                    : "PLAYING"}
                </span>

              </div>

              <div className="display-area">

                {media ? (

                  media.type === "video" ? (

                    <video
                      src={media.url}
                      autoPlay
                      muted
                      loop
                      controls={false}
                    />

                  ) : (

                    <img
                      src={media.url}
                      alt={media.name}
                      onError={(e) => {

                        e.currentTarget.style.display =
                          "none";

                        e.currentTarget.nextSibling.style.display =
                          "flex";
                      }}
                    />

                  )

                ) : (

                  <div className="fallback">
                    No media configured
                  </div>

                )}

                <div
                  className="image-fallback"
                  style={{
                    display: "none",
                  }}
                >
                  Media unavailable
                </div>

              </div>

              <div className="media-info">

                <div>

                  <strong>
                    {media
                      ? media.name
                      : "No Media"}
                  </strong>

                  <span>
                    {syncActive
                      ? "Synchronized Media"
                      : "Normal Playlist"}
                  </span>

                </div>

                <span className="cycle-label">
                  5 Hour Cycle
                </span>

              </div>

            </div>

          );
        })}

      </main>

    </div>
  );
}

export default App;