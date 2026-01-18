package service;

import model.Song;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameService
{
    private Random random;

    public GameService()
    {
        this.random = new Random();
    }

    public Song waehleZufaelligenSong(List<Song> verfuegbareSongs)
    {
        if(verfuegbareSongs == null || verfuegbareSongs.isEmpty())
        {
            return null;
        }
        return verfuegbareSongs.get(random.nextInt(verfuegbareSongs.size()));
    }

    public List<Song> generiereAntworten(Song richtigerSong, List<Song> alleSongs)
    {
        List<Song> antworten = new ArrayList<>();
        antworten.add(richtigerSong);

        List<Song> falscheSongs = new ArrayList<>(alleSongs);
        falscheSongs.remove(richtigerSong);
        Collections.shuffle(falscheSongs);

        for(int i = 0; i < 3 && i < falscheSongs.size(); i++)
        {
            antworten.add(falscheSongs.get(i));
        }

        Collections.shuffle(antworten);

        return antworten;
    }

    public int berechneScore(boolean istRichtig, int aktuellerScore)
    {
        if(istRichtig)
        {
            return aktuellerScore + 100;
        }
        return aktuellerScore;
    }

    public boolean pruefeAntwort(String gewaehlterTitel, Song richtigerSong)
    {
        if(gewaehlterTitel == null || richtigerSong == null)
        {
            return false;
        }
        return gewaehlterTitel.equals(richtigerSong.getTitle());
    }
}
