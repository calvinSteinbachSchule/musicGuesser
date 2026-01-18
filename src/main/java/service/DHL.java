package service;

import model.Highscore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class DHL
{
    protected ArrayList<Highscore> highscores = new ArrayList<>();
    protected BufferedReader in;
    protected BufferedWriter out;

    public abstract BufferedWriter schreiben(String speicherort, String dateiname);

    public ArrayList<Highscore> auslesen(BufferedReader in)
    {
        try
        {
            String zeile = null;
            try
            {
                while ((zeile = in.readLine()) != null)
                {
                    String[] split = zeile.split(";");
                    System.out.println(zeile);
                    highscores.add(new Highscore(split[0], Integer.parseInt(split[1])));
                    System.out.println(highscores.toString());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                in.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return highscores;
    }

    public BufferedReader getIn()
    {
        return in;
    }

    public BufferedWriter getOut()
    {
        return out;
    }

    public void schreibenDatei(BufferedWriter out)
    {
        try
        {
            // Schreibe alle Highscores im CSV-Format
            StringBuilder sb = new StringBuilder();
            for (Highscore h : highscores)
            {
                sb.append(h.getSpielerName())
                        .append(";")
                        .append(h.getPunktzahl())
                        .append("\n");
            }
            out.write(sb.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setHighscores(ArrayList<Highscore> highscores)
    {
        this.highscores = highscores;
    }

    public ArrayList<Highscore> getHighscores()
    {
        return highscores;
    }
}
