package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Highscore implements Serializable, Comparable<Highscore>
{

    private String spielerName;
    private int punktzahl;
    private LocalDateTime datum;

    public Highscore(String spielerName, int punktzahl)
    {
        this.spielerName = spielerName;
        this.punktzahl = punktzahl;
        this.datum = LocalDateTime.now();
    }

    public String getSpielerName()
    {
        return spielerName;
    }

    public int getPunktzahl()
    {
        return punktzahl;
    }

    public LocalDateTime getDatum()
    {
        return datum;
    }

    @Override
    public int compareTo(Highscore other)
    {
        return Integer.compare(other.punktzahl, this.punktzahl); //höhere punktzahl zuerst
    }

    @Override
    public String toString()
    {
        return spielerName + " - " + punktzahl + " Punkte";
    }
}
