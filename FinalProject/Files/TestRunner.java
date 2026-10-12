import java.io.File;
import java.io.IOException;

/**
 * Program 2 - console test driver. Run it and screenshot the output for your submission.
 * Needs an internet connection for the Scryfall tests.
 */
public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        ScryfallClient client = new ScryfallClient();

        System.out.println("=== Scryfall lookup tests ===");
        Card bolt = null;
        try {
            bolt = client.findByName("Lightning Bolt");
            check("Exact name finds a card", bolt.getName().equals("Lightning Bolt"));
            check("Card has a 36-character id", bolt.getId().length() == 36);
            check("Card has an image URL", bolt.getImageUrl() != null);
        } catch (CardLookupException e) {
            check("Exact name finds a card (" + e.getMessage() + ")", false);
        }

        try {
            Card misspelled = client.findByName("Lightening Bolt");
            check("Misspelled name still matches", misspelled.getName().equals("Lightning Bolt"));
        } catch (CardLookupException e) {
            check("Misspelled name still matches (" + e.getMessage() + ")", false);
        }

        try {
            client.findByName("Zzzzqq Not A Real Card");
            check("Nonsense name is rejected", false);
        } catch (CardLookupException e) {
            check("Nonsense name is rejected", true);
        }

        try {
            Card delver = client.findByName("Delver of Secrets");
            check("Two-sided card has an image URL", delver.getImageUrl() != null);
        } catch (CardLookupException e) {
            check("Two-sided card has an image URL (" + e.getMessage() + ")", false);
        }

        if (bolt != null) {
            try {
                Card again = client.findById(bolt.getId());
                check("Lookup by id returns the same card", again.getName().equals(bolt.getName()));
                check("Image downloads", client.fetchImage(again).getWidth() > 100);
            } catch (CardLookupException e) {
                check("Lookup by id (" + e.getMessage() + ")", false);
            }
        }

        System.out.println("\n=== JSON parsing tests (no internet needed) ===");
        try {
            String json = "{\"object\":\"card\",\"id\":\"11111111-2222-3333-4444-555555555555\","
                    + "\"name\":\"Lim-D\\u00fbl's Vault\",\"set_name\":\"Alliances\","
                    + "\"image_uris\":{\"small\":\"https://x/s.jpg\",\"normal\":\"https://x/n.jpg?1\"}}";
            Card c = ScryfallClient.parseCard(json);
            check("Parses id", c.getId().equals("11111111-2222-3333-4444-555555555555"));
            check("Decodes unicode escape in name", c.getName().equals("Lim-D\u00fbl's Vault"));
            check("Picks the 'normal' image", c.getImageUrl().equals("https://x/n.jpg?1"));
        } catch (CardLookupException e) {
            check("JSON parsing (" + e.getMessage() + ")", false);
        }
        try {
            ScryfallClient.parseCard("{\"object\":\"error\"}");
            check("Rejects a non-card response", false);
        } catch (CardLookupException e) {
            check("Rejects a non-card response", true);
        }

        System.out.println("\n=== OCR text cleanup tests ===");
        check("Strips OCR junk", OcrCardIdentifier.cleanText("  Lightning| Bolt \n").equals("Lightning Bolt"));
        check("Keeps apostrophes and commas",
                OcrCardIdentifier.cleanText("Urza's Saga, 4").equals("Urza's Saga,"));

        System.out.println("\n=== Collection file tests ===");
        File temp = new File("test_collection.txt");
        temp.delete();
        try {
            CollectionStore store = new CollectionStore(temp.getPath());
            store.load();
            check("New collection is empty", store.size() == 0);
            check("Adding a card works", store.add("aaa"));
            check("Duplicate is rejected", !store.add("aaa"));
            store.add("bbb");
            check("Size is 2", store.size() == 2);

            CollectionStore reloaded = new CollectionStore(temp.getPath());
            reloaded.load();
            check("Collection persists to disk", reloaded.size() == 2 && reloaded.toArray()[1].equals("bbb"));
            check("Removing a card works", reloaded.remove("aaa") && reloaded.size() == 1);
            check("Removing a missing card returns false", !reloaded.remove("zzz"));
        } catch (IOException e) {
            check("Collection file tests (" + e.getMessage() + ")", false);
        } finally {
            temp.delete();
        }

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed");
    }

    private static void check(String label, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + label);
        if (ok) {
            passed++;
        } else {
            failed++;
        }
    }
}
