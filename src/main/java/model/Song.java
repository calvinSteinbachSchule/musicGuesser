package model;

public class Song
{
    private String title, preview;
    private Artist artist;
    private Album album;



    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setPreview(String preview)
    {
        this.preview = preview;
    }

    public String getPreview()
    {
        return preview;
    }

    public String getTitle()
    {
        return title;
    }

    public Artist getArtist()
    {
        return artist;
    }

    public Album getAlbum()
    {
        return album;
    }

}
