import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Highscore;
import service.HighscoreService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MenuController
{
    @FXML
    private TextField textBoxKuenstler;
    @FXML
    private ListView<String> listViewHighscores;

    private Stage stage;
    private MediaPlayer mediaPlayer;
    private AudioClip clickSound;
    private HighscoreService highscoreService;

    public MenuController()
    {
        // Singleton-Instanz verwenden
        highscoreService = HighscoreService.getInstance();
    }

    @FXML
    public void startButtonClicked()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
        }

        // Künstlername aus TextField holen
        String artistName = "";
        if(textBoxKuenstler != null && textBoxKuenstler.getText() != null)
        {
            artistName = textBoxKuenstler.getText().trim();
        }

        try
        {
            // Wechsel zum loadingScreen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoadingScreen.fxml"));
            Parent rootLoadingScreen = loader.load();
            
            LoadingController loadingController = loader.getController();
            loadingController.setStage(stage);
            loadingController.setSearchArtist(artistName);

            stage.setScene(new Scene(rootLoadingScreen));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonMouseEntered()
    {
        if(clickSound != null)
        {
            clickSound.play();
        }
    }

    @FXML
    public void initialize()
    {
        clickSound = new AudioClip(getClass().getResource("/buttonClick.mp3").toString());
        clickSound.setVolume(1.0);

        if(textBoxKuenstler != null)
        {
            textBoxKuenstler.setOnAction(event -> startButtonClicked());
        }

        ladeHighscores();
    }

    private void ladeHighscores()
    {
        if(listViewHighscores != null)
        {
            listViewHighscores.getItems().clear();

            var highscores = highscoreService.getHighscores();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

            for(int i = 0; i < highscores.size(); i++)
            {
                Highscore h = highscores.get(i);
                String eintrag = String.format("%d. %-20s %5d Pkt. (%s)",
                        i + 1,
                        h.getSpielerName(),
                        h.getPunktzahl(),
                        h.getDatum().format(formatter));
                listViewHighscores.getItems().add(eintrag);
            }
        }
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
