import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Song;
import service.GameService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController
{
    @FXML
    private Button buttonPlayId, buttonStopId;
    @FXML
    private Button buttonAnswerAid, buttonAnswerBid, buttonAnswerCid, buttonAnswerDid;
    @FXML
    private Label labelScoreId, labelArtistId, labelAlbumId;
    @FXML
    private ImageView albumCoverImageView;

    private Stage stage;
    private MediaPlayer songPlayer;
    private GameService gameService;

    private List<Song> allSongs;
    private List<Song> playedSongs;
    private List<Song> availableSongs;
    private Song correctSong;
    private int score = 0;
    private int roundNumber = 0;
    private boolean hasAnswered = false;
    private String searchArtist;

    public GameController()
    {
        gameService = new GameService();
        allSongs = new ArrayList<>();
        playedSongs = new ArrayList<>();
        availableSongs = new ArrayList<>();
    }

    @FXML
    public void initialize()
    {
        labelArtistId.setText("???");
        labelAlbumId.setText("???");
    }

    public void setPreloadedSongs(List<Song> songs)
    {
        this.allSongs = new ArrayList<>(songs);
        this.availableSongs = new ArrayList<>(songs);
        this.playedSongs = new ArrayList<>();

        Platform.runLater(() -> {
            startNewRound();
        });
    }

    private void startNewRound()
    {
        if(allSongs == null || allSongs.isEmpty())
        {
            labelArtistId.setText("Fehler: Keine Songs verfügbar!");
            return;
        }

        if(availableSongs.isEmpty())
        {
            labelArtistId.setText("Alle Songs gespielt! Starte von vorne...");
            availableSongs = new ArrayList<>(allSongs);
            playedSongs.clear();

            PauseTransition resetPause = new PauseTransition(Duration.seconds(2));
            resetPause.setOnFinished(e -> continueNewRound());
            resetPause.play();
            return;
        }

        continueNewRound();
    }

    private void continueNewRound()
    {
        hasAnswered = false;
        roundNumber++;

        if(songPlayer != null)
        {
            songPlayer.stop();
            songPlayer.dispose();
            songPlayer = null;
        }

        correctSong = gameService.waehleZufaelligenSong(availableSongs);

        availableSongs.remove(correctSong);
        playedSongs.add(correctSong);

        int totalSongs = allSongs.size();
        int songsPlayed = playedSongs.size();
        System.out.println("Runde " + roundNumber + " - Song " + songsPlayed + "/" + totalSongs);

        labelArtistId.setText("???");
        labelAlbumId.setText("???");
        try
        {
            if(correctSong.getAlbum() != null && correctSong.getAlbum().getCover_medium() != null)
            {
                Image coverImage = new Image(correctSong.getAlbum().getCover_medium());
                albumCoverImageView.setImage(coverImage);
            }
        }
        catch(Exception e)
        {
            Image placeholder = new Image(getClass().getResource("/coverPlaceholder.jpg").toString());
            albumCoverImageView.setImage(placeholder);
        }

        List<Song> answerOptions = gameService.generiereAntworten(correctSong, allSongs);

        buttonAnswerAid.setText(answerOptions.get(0).getTitle());
        buttonAnswerBid.setText(answerOptions.get(1).getTitle());
        buttonAnswerCid.setText(answerOptions.get(2).getTitle());
        buttonAnswerDid.setText(answerOptions.get(3).getTitle());

        resetButtonStyles();

        PauseTransition autoPlayDelay = new PauseTransition(Duration.seconds(0.5));
        autoPlayDelay.setOnFinished(e -> autoPlaySong());
        autoPlayDelay.play();
    }

    private void autoPlaySong()
    {
        if(correctSong == null || correctSong.getPreview() == null)
        {
            labelArtistId.setText("Kein Preview verfügbar - nächster Song...");

            PauseTransition skipDelay = new PauseTransition(Duration.seconds(2));
            skipDelay.setOnFinished(e -> startNewRound());
            skipDelay.play();
            return;
        }

        try
        {
            if(songPlayer != null)
            {
                songPlayer.stop();
            }

            Media media = new Media(correctSong.getPreview());
            songPlayer = new MediaPlayer(media);
            songPlayer.setVolume(0.7);
            songPlayer.play();

            labelArtistId.setText("♪ Song wird abgespielt...");
        }
        catch(Exception e)
        {
            labelArtistId.setText("Fehler beim Abspielen!");
            e.printStackTrace();
        }
    }

    @FXML
    public void playButtonClicked()
    {
        // Manuelle Play-Funktion bleibt für Wiederholung
        autoPlaySong();
    }

    @FXML
    public void stopButtonClicked()
    {
        if(songPlayer != null)
        {
            songPlayer.stop();
            labelArtistId.setText("??? (gestoppt)");
        }
    }

    @FXML
    public void answerAClicked()
    {
        checkAnswer(buttonAnswerAid);
    }

    @FXML
    public void answerBClicked()
    {
        checkAnswer(buttonAnswerBid);
    }

    @FXML
    public void answerCClicked()
    {
        checkAnswer(buttonAnswerCid);
    }

    @FXML
    public void answerDClicked()
    {
        checkAnswer(buttonAnswerDid);
    }

    private void checkAnswer(Button clickedButton)
    {
        if(hasAnswered || correctSong == null)
        {
            return;
        }

        hasAnswered = true;

        disableAnswerButtons();

        if(songPlayer != null)
        {
            songPlayer.stop();
        }

        String selectedTitle = clickedButton.getText();
        boolean isCorrect = gameService.pruefeAntwort(selectedTitle, correctSong);

        if(isCorrect)
        {
            clickedButton.setStyle("-fx-background-color: #00ff00;");
            score = gameService.berechneScore(true, score);
            labelScoreId.setText(String.valueOf(score));
        }
        else
        {
            clickedButton.setStyle("-fx-background-color: #ff0000;");
            highlightCorrectAnswer();
        }

        if(correctSong.getArtist() != null)
        {
            labelArtistId.setText(correctSong.getArtist().getName());
        }
        if(correctSong.getAlbum() != null)
        {
            labelAlbumId.setText(correctSong.getAlbum().getTitle());
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            enableAnswerButtons();

            if(isCorrect)
            {
                startNewRound();
            }
            else
            {
                gameOver();
            }
        });
        pause.play();
    }

    private void highlightCorrectAnswer()
    {
        if(buttonAnswerAid.getText().equals(correctSong.getTitle()))
        {
            buttonAnswerAid.setStyle("-fx-background-color: #00ff00;");
        }
        else if(buttonAnswerBid.getText().equals(correctSong.getTitle()))
        {
            buttonAnswerBid.setStyle("-fx-background-color: #00ff00;");
        }
        else if(buttonAnswerCid.getText().equals(correctSong.getTitle()))
        {
            buttonAnswerCid.setStyle("-fx-background-color: #00ff00;");
        }
        else if(buttonAnswerDid.getText().equals(correctSong.getTitle()))
        {
            buttonAnswerDid.setStyle("-fx-background-color: #00ff00;");
        }
    }

    private void resetButtonStyles()
    {
        String defaultStyle = "";
        buttonAnswerAid.setStyle(defaultStyle);
        buttonAnswerBid.setStyle(defaultStyle);
        buttonAnswerCid.setStyle(defaultStyle);
        buttonAnswerDid.setStyle(defaultStyle);
    }

    private void disableAnswerButtons()
    {
        buttonAnswerAid.setDisable(true);
        buttonAnswerBid.setDisable(true);
        buttonAnswerCid.setDisable(true);
        buttonAnswerDid.setDisable(true);
    }

    private void enableAnswerButtons()
    {
        buttonAnswerAid.setDisable(false);
        buttonAnswerBid.setDisable(false);
        buttonAnswerCid.setDisable(false);
        buttonAnswerDid.setDisable(false);
    }

    private void gameOver()
    {
        if(songPlayer != null)
        {
            songPlayer.stop();
            songPlayer.dispose();
            songPlayer = null;
        }

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameoverScreen.fxml"));
            Parent rootGameoverScreen = loader.load();

            GameoverController gameoverController = loader.getController();
            gameoverController.setStage(stage);
            gameoverController.setFinalScore(score);
            gameoverController.setSearchArtist(searchArtist); // Künstler weitergeben

            stage.setScene(new Scene(rootGameoverScreen));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;

        stage.getScene().setOnKeyPressed(this::handleKeyPress);
    }

    @FXML
    private void handleKeyPress(KeyEvent event)
    {
        if(hasAnswered) return;

        KeyCode code = event.getCode();

        switch(code)
        {
            case DIGIT1:
            case NUMPAD1:
                answerAClicked();
                break;
            case DIGIT2:
            case NUMPAD2:
                answerBClicked();
                break;
            case DIGIT3:
            case NUMPAD3:
                answerCClicked();
                break;
            case DIGIT4:
            case NUMPAD4:
                answerDClicked();
                break;
            case SPACE:
                playButtonClicked();
                break;
            case S:
                stopButtonClicked();
                break;
            default:
                break;
        }
    }

    public void setSearchArtist(String searchArtist)
    {
        this.searchArtist = searchArtist;
    }
}
