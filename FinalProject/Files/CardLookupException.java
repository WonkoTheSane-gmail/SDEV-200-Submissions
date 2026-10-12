/** Thrown when a Scryfall lookup fails (no match, network error, bad response). */
public class CardLookupException extends Exception {
    public CardLookupException(String message) {
        super(message);
    }
}
