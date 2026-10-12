import java.io.File;

/**
 * Anything that can work out a card's name from a photo.
 * OcrCardIdentifier is one implementation; others (e.g. image hashing) could be added
 * without changing the GUI.
 */
public interface CardIdentifier {
    String identifyName(File imageFile) throws IdentificationException;
}
