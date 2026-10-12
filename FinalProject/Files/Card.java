/**
 * Immutable data object for one Magic: The Gathering card as returned by Scryfall.
 * The id is Scryfall's unique printing id (a UUID) - this is what the collection stores.
 */
public class Card {
    private final String id;
    private final String name;
    private final String setName;
    private final String imageUrl;

    public Card(String id, String name, String setName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.setName = setName;
        this.imageUrl = imageUrl;
    }

    public String getId()       { return id; }
    public String getName()     { return name; }
    public String getSetName()  { return setName; }
    public String getImageUrl() { return imageUrl; }

    @Override
    public String toString() {
        return name + " (" + setName + ")";
    }
}
