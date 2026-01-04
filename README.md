# musicGuesser
JavaFX Musik-Ratespiel mit der Deezer API

🗺️ Roadmap: Deezer Musik Quiz

Dies ist der Entwicklungsplan für unser Java-basiertes Musik-Ratespiel. Das Projekt wird in mehreren Phasen entwickelt, wobei der Fokus auf einer sauberen Trennung von Backend (API-Logik) und Frontend (JavaFX) liegt.

🚀 Projektstatus

Aktuelle Phase: Initialisierung & Setup

📅 Meilenstein 1: Projekt-Initialisierung & Setup

Ziel: Eine stabile Entwicklungsumgebung ohne Build-Tools (Maven/Gradle) schaffen.

[X] Repository Setup

[X] .gitignore für IntelliJ und Java erstellen.

[X] Initialer Commit und Push auf GitHub.

[X] Abhängigkeiten (Dependencies) einrichten

[X] JavaFX SDK herunterladen und lokal einbinden.

[X] Jackson Core/Annotations/Databind JARs herunterladen und einbinden.

[X] Run-Configuration (VM Options) für JavaFX Module in IntelliJ konfigurieren.

[X] Einfaches "Hello World" JavaFX-Fenster starten, um Setup zu bestätigen.

⚙️ Meilenstein 2: Backend & Datenlogik (Model)

Ziel: Erfolgreicher Abruf und Verarbeitung von Daten der Deezer API.

[ ] Datenmodellierung

[ ] Analyse der JSON-Response von api.deezer.com.

[ ] Erstellung der Java POJOs (Track, Artist, DeezerResponse) mit passenden Jackson-Annotationen.

[ ] API Service

[ ] Implementierung DeezerService Klasse.

[ ] HTTP Request Logik (java.net.http.HttpClient) implementieren.

[ ] JSON-Parsing Logik (ObjectMapper) integrieren.

[ ] Testing

[ ] Konsolen-Test: Erfolgreiche Ausgabe von Songtiteln und Preview-URLs nach Künstlersuche.

🖥️ Meilenstein 3: Benutzeroberfläche (View)

Ziel: Eine funktionierende grafische Oberfläche mit Audio-Support.

[ ] Layout Erstellung

[ ] Grundgerüst mit VBox oder BorderPane.

[ ] Suchleiste für Künstlernamen.

[ ] Grid-Layout für die 4 Antwort-Buttons.

[ ] Audio-Integration

[ ] Implementierung der MediaPlayer Klasse (JavaFX Media).

[ ] Testen von Play/Stop Funktionalität mit einer statischen URL.

🧩 Meilenstein 4: Integration & Spiellogik (StartController)

Ziel: Verbindung von Backend und Frontend zu einem spielbaren Spiel.

[ ] Game Loop

[ ] Logik zum Starten einer Runde (Laden der Songs -> Auswahl von 4 Zufallstracks).

[ ] Zuweisung eines "richtigen" Songs und Abspielen der Preview.

[ ] Interaktion

[ ] Event-Handling für Button-Klicks.

[ ] Gewinnprüfung (User-Auswahl vs. richtiger Song).

[ ] Visuelles Feedback (Grün/Rot Färbung der Buttons).

[ ] Score System

[ ] Punktezähler implementieren.

✨ Meilenstein 5: Polish & Release (Optional)

Ziel: Verbesserung der UX und Fehlerbehandlung.

[ ] Fehlerbehandlung

[ ] User-Feedback bei fehlender Internetverbindung oder leeren Suchergebnissen.

[ ] Styling

[ ] CSS-Styling für Buttons und Hintergründe (Modern UI).

[ ] Anzeigen von Album-Covern.

[ ] Finalisierung

[ ] Code Cleanup & Kommentare.

[ ] Abschlusspräsentation vorbereiten.
