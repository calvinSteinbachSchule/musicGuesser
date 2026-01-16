package model;

import java.util.List;

public class Artist
{
    private String name;
    private List<Song> songList;


    public void setName(String name)
    {
        this.name = name;
    }

    public void setSongList(List<Song> songList)
    {
        this.songList = songList;
    }

    public String getName()
    {
        return name;
    }

    public List<Song> getSongList()
    {
        return songList;
    }
}
