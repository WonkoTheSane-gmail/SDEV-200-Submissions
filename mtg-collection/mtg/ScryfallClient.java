import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * Talks to the Scryfall API (https://scryfall.com/docs/api).
 * Scryfall asks clients to send a User-Agent and Accept header and to wait
 * 50-100 ms between requests, which throttle() does.
 */
public class ScryfallClient {
    private static final String BASE = "https://api.scryfall.com";
    private static final String USER_AGENT = "MTGCollectionApp/1.0 (student project)";
    private static final long MIN_GAP_MS = 100;

    private final HttpClient http = HttpClient.newHttpClient();
    private long lastRequestTime = 0;

    /** Looks up a card by (possibly slightly misspelled) name. */
    public Card findByName(String name) throws CardLookupException {
        String url = BASE + "/cards/named?fuzzy=" + URLEncoder.encode(name, StandardCharsets.UTF_8);
        return parseCard(getText(url));
    }

    /** Looks up a card by its Scryfall id. */
    public Card findById(String id) throws CardLookupException {
        return parseCard(getText(BASE + "/cards/" + id));
    }

    /** Downloads the card's picture. */
    public BufferedImage fetchImage(Card card) throws CardLookupException {
        if (card.getImageUrl() == null) {
            throw new CardLookupException("This card has no image.");
        }
        try {
            throttle();
            HttpRequest request = HttpRequest.newBuilder(URI.create(card.getImageUrl()))
                    .header("User-Agent", USER_AGENT)
                    .build();
            HttpResponse<byte[]> response = http.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() != 200) {
                throw new CardLookupException("Image download failed (HTTP " + response.statusCode() + ").");
            }
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(response.body()));
            if (image == null) {
                throw new CardLookupException("Image download was not a valid picture.");
            }
            return image;
        } catch (IOException e) {
            throw new CardLookupException("Network error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CardLookupException("Download was interrupted.");
        }
    }

    // ---------- internals ----------

    private String getText(String url) throws CardLookupException {
        try {
            throttle();
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .header("User-Agent", USER_AGENT)
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status == 404) {
                throw new CardLookupException("No single matching card was found.");
            }
            if (status != 200) {
                throw new CardLookupException("Scryfall returned HTTP " + status + ".");
            }
            return response.body();
        } catch (IOException e) {
            throw new CardLookupException("Network error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CardLookupException("Request was interrupted.");
        }
    }

    private synchronized void throttle() {
        long wait = MIN_GAP_MS - (System.currentTimeMillis() - lastRequestTime);
        if (wait > 0) {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }

    /**
     * Pulls the fields we need out of Scryfall's JSON with regular expressions, so the
     * project needs no extra libraries. The first "id"/"name" in a card object belong to
     * the card itself. The first "normal" image is used (front face of two-sided cards).
     */
    static Card parseCard(String json) throws CardLookupException {
        String id = field(json, "id", "[0-9a-fA-F\\-]{36}");
        String name = field(json, "name", "(?:[^\"\\\\]|\\\\.)*");
        String setName = field(json, "set_name", "(?:[^\"\\\\]|\\\\.)*");
        String image = field(json, "normal", "https:[^\"]+");

        if (id == null || name == null) {
            throw new CardLookupException("Scryfall's response did not look like a card.");
        }
        return new Card(id, unescape(name), setName == null ? "Unknown set" : unescape(setName),
                image == null ? null : image.replace("\\/", "/"));
    }

    private static String field(String json, String key, String valueRegex) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*\"(" + valueRegex + ")\"");
        Matcher m = p.matcher(json);
        return m.find() ? m.group(1) : null;
    }

    /** Handles the JSON escapes that appear in card names (\" \\ \/ and \\uXXXX). */
    static String unescape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char next = s.charAt(++i);
                if (next == 'u' && i + 4 < s.length()) {
                    sb.append((char) Integer.parseInt(s.substring(i + 1, i + 5), 16));
                    i += 4;
                } else {
                    sb.append(next); // covers \" \\ \/
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
