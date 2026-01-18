package service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DHLNIO extends DHL
{
    private String path;

    public DHLNIO()
    {
    }

    public DHLNIO(String path)
    {
        super();

        try
        {
            in = Files.newBufferedReader(Paths.get(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.path = path;
    }

    @Override
    public BufferedWriter schreiben(String speicherort, String dateiname)
    {
        try
        {
            out = Files.newBufferedWriter(Paths.get(speicherort + dateiname));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return out;
    }
}
