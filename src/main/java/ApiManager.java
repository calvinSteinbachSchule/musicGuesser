import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiManager
{

    public String sucheSongs(String suchBegriff)  //wird später aufgerufen um API nach suchbegriff zu durchstöbern
    {
        //Basis-URL von Deezer für die Suche
        String basisUrl = "https://api.deezer.com/search?q=";

        //fertige URL: Basis + Suchbegriff
        String url = basisUrl + suchBegriff.replace(" ", "%20"); //tauscht leer stellen mit %20 damit URL das leerzeichen erkennt

        //gibt fertige url an Funktion getUrl
        return anfrageSenden(url);
    }

    public String anfrageSenden(String url) //sendet anfrage an API
    {
        //eingebauten Client erstellen
        HttpClient client = HttpClient.newHttpClient();

        //Anfrage (Request) bauen
        HttpRequest anfrage = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try //versucht anfrage zu senden und antwort zu erhalten
        {
            // Anfrage abschicken und Antwort als String erhalten
            HttpResponse<String> antwort = client.send(anfrage, HttpResponse.BodyHandlers.ofString());

            // Text (JSON) zurückgeben
            return antwort.body();  //.body() zieht nur die nutzlast aus der antwort ohne header und Protokol zeugs
        }
        catch (Exception e) //fängt probleme ab
        {
            return "ERROR"; //sagt dem Controller das es ein Problem gibt
        }
    }
}