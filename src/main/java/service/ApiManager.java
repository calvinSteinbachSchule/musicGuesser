package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Album;
import model.Artist;
import model.Song;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiManager
{
    private ObjectMapper objectMapper;  //Jackson variabel (übersetzer) hilft später json text in objekte zu serialisiren

    public ApiManager()  //erzeugt den übersetzer wenn ApiManager erstellt wird
    {
        this.objectMapper = new ObjectMapper();
    }

    public List<Song> sucheSongs(String suchBegriff) throws Exception
    {
        String basisUrl = "https://api.deezer.com/search?q="; //basis Adresse (API) von Deezer
        String url = basisUrl + suchBegriff.replace(" ", "%20"); //tauscht leerstellen mit %20 damit URL die leerstellen erkennt

        String jsonAntwort = anfrageSenden(url);

        if(jsonAntwort.equals("ERROR")) //prüft ob beim Internet Abruf etwas schiefging
        {
            throw new Exception("API Anfrage fehlgeschlagen");  //gibt Fehlermeldung aus
        }

        return jsonUbersetzen(jsonAntwort);
    }


    private List<Song> jsonUbersetzen(String jsonString) throws Exception
    {
        List<Song> songList = new ArrayList<>();

        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode dataNode = rootNode.get("data");

        if(dataNode != null && dataNode.isArray())
        {
            for(JsonNode songNode : dataNode)
            {
                String previewUrl = songNode.get("preview").asText();
                if(previewUrl == null || previewUrl.isEmpty() || previewUrl.equals("null"))
                {
                    continue; //überspringe songs ohne preview
                }

                Song song = new Song();

                song.setTitle(songNode.get("title").asText());
                song.setPreview(previewUrl);

                JsonNode artistNode = songNode.get("artist");
                if(artistNode != null)
                {
                    Artist artist = new Artist();
                    artist.setName(artistNode.get("name").asText());
                    song.setArtist(artist);
                }

                JsonNode albumNode = songNode.get("album");
                if(albumNode != null)
                {
                    Album album = new Album();
                    album.setTitle(albumNode.get("title").asText());

                    if(albumNode.has("cover_medium"))
                    {
                        album.setCover_medium(albumNode.get("cover_medium").asText());
                    }
                    song.setAlbum(album);
                }

                songList.add(song);
            }
        }

        return songList;
    }


    private String anfrageSenden(String url)
    {
        HttpClient client = HttpClient.newHttpClient();  //erstellt eingebauten http Client
        HttpRequest anfrage = HttpRequest.newBuilder().uri(URI.create(url)).build(); //baut anfrage mit fertiger URL zusammen

        try
        {
            //schickt Anfrage und wartet auf Antwort
            HttpResponse<String> antwort = client.send(anfrage, HttpResponse.BodyHandlers.ofString());
            return antwort.body(); //gibt JSON zurück
        }
        catch (Exception e)
        {
            return "ERROR"; //sagt dem Controller das es ein Problem gibt
        }
    }
}
