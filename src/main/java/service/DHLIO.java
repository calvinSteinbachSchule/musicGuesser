package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DHLIO extends DHL
{
    private String pfad;

    public DHLIO()
    {
    }

    public DHLIO(String pfad)
    {
        super();

        try
        {
            in = new BufferedReader(new FileReader(pfad));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        this.pfad = pfad;
    }

    @Override
    public BufferedWriter schreiben(String speicherort, String dateiname)
    {
        try
        {
            out = new BufferedWriter(new FileWriter(speicherort + dateiname));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return out;
    }
}
