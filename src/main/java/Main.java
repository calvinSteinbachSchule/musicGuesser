import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import service.HighscoreService;

public class Main extends Application
{
    private HighscoreService highscoreService;

    @Override
    public void start(Stage stage) throws Exception
    {
        highscoreService = HighscoreService.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartScreen.fxml"));
        Parent rootStartScreen = loader.load();
        StartController startController = loader.getController();
        startController.setStage(stage);

        Image appIcon = new Image(getClass().getResource("/logo.png").toString());

        stage.getIcons().add(appIcon);
        stage.setTitle("MusicGuesser");
        stage.setScene(new Scene(rootStartScreen));
        stage.show();

        stage.setOnCloseRequest(event -> {
            if(highscoreService != null)
            {
                highscoreService.speichereHighscores(); // Speichert beim Schließen
            }
        });
    }

    @Override
    public void stop() throws Exception
    {
        if(highscoreService != null)
        {
            highscoreService.speichereHighscores(); // Speichert beim Beenden
        }
        super.stop();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
