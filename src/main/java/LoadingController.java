import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import model.Song;
import service.ApiManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadingController
{
    @FXML
    private Label labelLoadingStatus;

    @FXML
    private ProgressBar progressBar;

    private Stage stage;
    private String searchArtist;
    private ApiManager apiManager;
    private List<Song> loadedSongs;

    public LoadingController()
    {
        apiManager = new ApiManager();
        loadedSongs = new ArrayList<>();
    }

    @FXML
    public void initialize()
    {
        // Starte Ladevorgang
        loadSongsAndStartGame();
    }

    private void loadSongsAndStartGame()
    {
        new Thread(() -> {
            try
            {
                // Schritt 1: API Verbindung
                Platform.runLater(() -> {
                    labelLoadingStatus.setText("Verbinde mit Deezer API...");
                    progressBar.setProgress(0.1);
                });
                Thread.sleep(300);

                // Schritt 2: Songs laden
                Platform.runLater(() -> {
                    String artistText = (searchArtist == null || searchArtist.isEmpty())
                            ? "verschiedenen Künstlern"
                            : searchArtist;
                    labelLoadingStatus.setText("Lade Songs von " + artistText + "...");
                    progressBar.setProgress(0.3);
                });

                // Wenn ein spezifischer Künstler eingegeben wurde
                if(searchArtist != null && !searchArtist.isEmpty())
                {
                    List<Song> songs = apiManager.sucheSongs(searchArtist);
                    loadedSongs.addAll(songs);

                    Platform.runLater(() -> {
                        progressBar.setProgress(0.5);
                    });

                    // Falls zu wenige Songs, andere Künstler hinzufügen
                    if(loadedSongs.size() < 10)
                    {
                        Platform.runLater(() -> {
                            labelLoadingStatus.setText("Ergänze mit weiteren Songs...");
                        });

                        String[] additionalArtists = {"Eminem", "Drake", "Rihanna"};
                        for(String artist : additionalArtists)
                        {
                            if(!artist.equalsIgnoreCase(searchArtist))
                            {
                                List<Song> moreSongs = apiManager.sucheSongs(artist);
                                loadedSongs.addAll(moreSongs);
                                if(loadedSongs.size() >= 20) break;
                            }
                        }
                    }
                }
                else
                {
                    // Standard: Mehrere Künstler
                    String[] artists = {"Eminem", "Drake", "Rihanna", "Ed Sheeran",
                            "Taylor Swift", "The Weeknd", "Post Malone", "Billie Eilish"};

                    int artistCount = 0;
                    for(String artist : artists)
                    {
                        artistCount++;
                        final int count = artistCount;

                        Platform.runLater(() -> {
                            labelLoadingStatus.setText("Lade Songs von " + artist + "...");
                            progressBar.setProgress(0.3 + (count * 0.06));
                        });

                        List<Song> songs = apiManager.sucheSongs(artist);
                        loadedSongs.addAll(songs);

                        Thread.sleep(200); // Kleine Pause für visuelle Effekt
                    }
                }

                // Schritt 3: Verarbeitung
                Platform.runLater(() -> {
                    labelLoadingStatus.setText("Bereite Spiel vor...");
                    progressBar.setProgress(0.9);
                });
                Thread.sleep(500);

                // Schritt 4: Fertig
                Platform.runLater(() -> {
                    labelLoadingStatus.setText("Fertig! Starte Spiel...");
                    progressBar.setProgress(1.0);
                });
                Thread.sleep(300);

                // Prüfe ob Songs geladen wurden
                if(loadedSongs.isEmpty())
                {
                    Platform.runLater(() -> {
                        labelLoadingStatus.setText("FEHLER: Keine Songs gefunden!");
                        progressBar.setProgress(0.0);
                        progressBar.setStyle("-fx-accent: red;");

                        // Nach 3 Sekunden zurück zum Menü
                        new Thread(() -> {
                            try {
                                Thread.sleep(3000);
                                Platform.runLater(this::backToMenu);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    });
                    return;
                }

                // Wechsel zum GameScreen mit vorgeladenen Songs
                Platform.runLater(this::startGame);
            }
            catch(Exception e)
            {
                Platform.runLater(() -> {
                    labelLoadingStatus.setText("Fehler beim Laden! Prüfe Internetverbindung.");
                    progressBar.setProgress(0.0);
                    progressBar.setStyle("-fx-accent: red;");
                });
                e.printStackTrace();

                // Nach 3 Sekunden zurück zum Menü
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        Platform.runLater(this::backToMenu);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        }).start();
    }

    private void startGame()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScreen.fxml"));
            Parent rootGameScreen = loader.load();

            Scene gameScene = new Scene(rootGameScreen);
            stage.setScene(gameScene);

            GameController gameController = loader.getController();
            gameController.setStage(stage);
            gameController.setSearchArtist(searchArtist); // Künstler weitergeben
            gameController.setPreloadedSongs(loadedSongs); // Songs übergeben!
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void backToMenu()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuScreen.fxml"));
            Parent rootMenuScreen = loader.load();

            MenuController menuController = loader.getController();
            menuController.setStage(stage);

            // Menu Musik neu starten
            javafx.scene.media.Media menuMusic = new javafx.scene.media.Media(
                    getClass().getResource("/menuMusic.mp3").toString());
            javafx.scene.media.MediaPlayer menuPlayer = new javafx.scene.media.MediaPlayer(menuMusic);
            menuPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
            menuPlayer.setVolume(1.0);
            menuPlayer.play();

            menuController.setMediaPlayer(menuPlayer);

            stage.setScene(new Scene(rootMenuScreen));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setSearchArtist(String artistName)
    {
        this.searchArtist = artistName != null ? artistName : "";
    }
}
