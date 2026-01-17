import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MenuController
{
    private Stage stage;
    private MediaPlayer mediaPlayer;
    private AudioClip clickSound;

    public MenuController()
    {

    }

    @FXML
    public void startButtonClicked()
    {
        mediaPlayer.stop();

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScreen.fxml"));
            Parent rootGameScreen = loader.load();
            stage.setScene(new Scene(rootGameScreen));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonMouseEntered()
    {
        clickSound.play();
    }

    @FXML
    public void initialize()
    {
        clickSound = new AudioClip(getClass().getResource("/buttonClick.mp3").toString());
        clickSound.setVolume(1.0);

    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
    }
}
