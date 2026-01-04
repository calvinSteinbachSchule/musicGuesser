import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
        Parent rootStartScreen = loader.load();
        StartController startController = loader.getController();
        startController.setStage(stage);

        stage.setTitle("MusicGuesser");
        stage.setScene(new Scene(rootStartScreen));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}