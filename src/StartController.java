import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController
{
    //Der Frame in dem die Szenen angezeigt werden
    private Stage stage;
    private MediaPlayer mediaPlayer;

    //Wechsel von StartScreen in GameScreen
    @FXML
    public void startButtonClicked()
    {
        mediaPlayer.stop();
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            Parent rootGameScreen = loader.load();
            stage.setScene(new Scene(rootGameScreen));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize()
    {
        Media menuMusic = new Media(getClass().getResource("menuMusic.mp3").toString());
        mediaPlayer = new MediaPlayer(menuMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
}