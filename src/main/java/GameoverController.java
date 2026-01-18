import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import service.HighscoreService;

import java.io.IOException;

public class GameoverController
{
    @FXML
    private Label labelFinalScoreId;
    @FXML
    private TextField textFieldSpielerName;
    @FXML
    private Button buttonSpeichern;

    private Stage stage;
    private MediaPlayer mediaPlayer;
    private AudioClip clickSound;
    private HighscoreService highscoreService;
    private int finalScore;
    private String searchArtist;
    private boolean scoreGespeichert = false;

    public GameoverController()
    {
        // Singleton-Instanz verwenden - dieselbe wie in Main.java
        highscoreService = HighscoreService.getInstance();
    }

    @FXML
    public void initialize()
    {
        Media gameoverMusic = new Media(getClass().getResource("/gameoverMusic.mp3").toString());
        mediaPlayer = new MediaPlayer(gameoverMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();

        clickSound = new AudioClip(getClass().getResource("/buttonClick.mp3").toString());
        clickSound.setVolume(1.0);

        if(textFieldSpielerName != null)
        {
            textFieldSpielerName.setOnAction(event -> speichernButtonClicked());
        }
    }

    @FXML
    public void speichernButtonClicked()
    {
        String spielerName = textFieldSpielerName.getText().trim();

        if(spielerName.isEmpty())
        {
            spielerName = "Anonym";
        }

        highscoreService.fuegeHinzu(spielerName, finalScore);
        scoreGespeichert = true;

        textFieldSpielerName.setDisable(true);
        buttonSpeichern.setDisable(true);
        buttonSpeichern.setText("✓ Gespeichert");

        System.out.println("Highscore gespeichert: " + spielerName + " - " + finalScore);
    }

    @FXML
    public void neuStartenButtonClicked()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
        }

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoadingScreen.fxml"));
            Parent rootLoadingScreen = loader.load();

            LoadingController loadingController = loader.getController();
            loadingController.setStage(stage);
            loadingController.setSearchArtist(searchArtist);

            stage.setScene(new Scene(rootLoadingScreen));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void menuButtonClicked()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
        }

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuScreen.fxml"));
            Parent rootMenuScreen = loader.load();

            MenuController menuController = loader.getController();
            menuController.setStage(stage);

            Media menuMusic = new Media(getClass().getResource("/menuMusic.mp3").toString());
            MediaPlayer menuPlayer = new MediaPlayer(menuMusic);
            menuPlayer.setCycleCount(MediaPlayer.INDEFINITE);
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

    @FXML
    public void beendenButtonClicked()
    {
        Platform.exit();
    }

    @FXML
    public void buttonMouseEntered()
    {
        clickSound.play();
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setFinalScore(int score)
    {
        this.finalScore = score;
        if(labelFinalScoreId != null)
        {
            labelFinalScoreId.setText(String.valueOf(score));
        }
    }

    public void setSearchArtist(String searchArtist)
    {
        this.searchArtist = searchArtist;
    }
}
