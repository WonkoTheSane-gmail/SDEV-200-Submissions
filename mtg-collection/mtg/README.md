# MTG Collection (Java Swing + Scryfall)

Reads a photo of a Magic: The Gathering card, finds it on Scryfall, saves its Scryfall id,
and lets you browse your collection with card images fetched from the Scryfall API.

## Requirements
- JDK 11 or newer (uses java.net.http). Internet connection.
- OPTIONAL for photo scanning: Tesseract OCR installed and on PATH
  (Windows: https://github.com/UB-Mannheim/tesseract/wiki ; macOS: `brew install tesseract`).
  Without it, type the card name in the box instead - everything else works.

## Run (command line)
    javac *.java
    java CardApp        # the application
    java TestRunner     # console tests (screenshot this output)

## Run (jGRASP)
Open every .java file, Build > Compile All, then open CardApp.java and press Run.
Open TestRunner.java and Run for the tests.

## Where data is stored
collection.txt in the folder you run from: one Scryfall card id per line.
