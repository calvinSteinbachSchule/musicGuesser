package service;

import model.Highscore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HighscoreController
{
    private DHL object;
    private String dateiname;
    private String speicherort;

    public HighscoreController(String dateiname)
    {
        String userHome = System.getProperty("user.home");
        this.speicherort = userHome + "/.musicguesser/";
        this.dateiname = dateiname;

        try
        {
            Path dir = Paths.get(speicherort);
            if (!Files.exists(dir))
            {
                Files.createDirectories(dir);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void starten()
    {
        System.out.println("Datei wird ausgelesen mit NIO");
        object = new DHLNIO(speicherort + dateiname);
        object.auslesen(object.getIn());

        System.out.println("Die Datei wird geschrieben");
        object.schreibenDatei(object.schreiben(speicherort, dateiname));
        System.out.println("Datei wurde erstellt!");
    }

    public void speichern(List<Highscore> highscores) throws IOException
    {
        Path dir = Paths.get(speicherort);
        if (!Files.exists(dir))
        {
            Files.createDirectories(dir);
        }

        object = new DHLNIO();
        object.setHighscores(new ArrayList<>(highscores));
        object.schreibenDatei(object.schreiben(speicherort, dateiname));
    }

    public List<Highscore> laden() throws IOException
    {
        Path pfad = Paths.get(speicherort + dateiname);

        if (!Files.exists(pfad))
        {
            return new ArrayList<>();
        }

        object = new DHLNIO(speicherort + dateiname);
        return object.auslesen(object.getIn());
    }

    public boolean existiert()
    {
        return Files.exists(Paths.get(speicherort + dateiname));
    }

    public String getVollstaendigerPfad()
    {
        return speicherort + dateiname;
    }
}
