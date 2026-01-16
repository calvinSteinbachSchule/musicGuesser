import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController
{
    //Der Frame in dem die Szenen angezeigt werden
    private Stage stage;
    private MediaPlayer mediaPlayer;
    private AudioClip clickSound;

    //Wechsel von StartScreen in GameScreen
    @FXML
    public void startButtonClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuScreen.fxml"));
            Parent rootMenuScreen = loader.load();

            MenuController menuController = loader.getController();
            menuController.setStage(this.stage);
            menuController.setMediaPlayer(this.mediaPlayer);

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

    @FXML
    public void initialize()
    {
        Media menuMusic = new Media(getClass().getResource("/menuMusic.mp3").toString());
        mediaPlayer = new MediaPlayer(menuMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(1.0);
        mediaPlayer.play();

        clickSound = new AudioClip(getClass().getResource("/buttonClick.mp3").toString());
        clickSound.setVolume(1.0);

    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
}