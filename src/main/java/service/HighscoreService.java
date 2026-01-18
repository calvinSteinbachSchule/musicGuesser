package service;

import model.Highscore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighscoreService
{
    private static final int MAX_HIGHSCORES = 10;
    private static HighscoreService instance;

    private HighscoreController highscoreController;
    private List<Highscore> highscores;

    private HighscoreService()
    {
        this.highscoreController = new HighscoreController("highscores.txt");
        this.highscores = new ArrayList<>();
        ladeHighscores();
    }

    public static synchronized HighscoreService getInstance()
    {
        if (instance == null)
        {
            instance = new HighscoreService();
        }
        return instance;
    }

    public void ladeHighscores()
    {
        try
        {
            highscores = highscoreController.laden();
            Collections.sort(highscores);
        }
        catch (IOException e)
        {
            System.out.println("Keine vorherigen Highscores gefunden. Starte mit leerer Liste.");
            highscores = new ArrayList<>();
        }
    }

    public void speichereHighscores()
    {
        try
        {
            highscoreController.speichern(highscores);
            System.out.println("Highscores gespeichert!");
        }
        catch (IOException e)
        {
            System.err.println("Fehler beim Speichern der Highscores: " + e.getMessage());
        }
    }

    public void fuegeHinzu(String spielerName, int punktzahl)
    {
        Highscore neuerScore = new Highscore(spielerName, punktzahl);
        highscores.add(neuerScore);
        Collections.sort(highscores);

        if (highscores.size() > MAX_HIGHSCORES)
        {
            highscores = new ArrayList<>(highscores.subList(0, MAX_HIGHSCORES));
        }

        speichereHighscores();
    }

    public List<Highscore> getHighscores()
    {
        return new ArrayList<>(highscores);
    }

    public boolean istHighscore(int punktzahl)
    {
        if (highscores.size() < MAX_HIGHSCORES)
        {
            return true;
        }

        return punktzahl > highscores.get(highscores.size() - 1).getPunktzahl();
    }
}
