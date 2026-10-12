import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/** A Card paired with its downloaded image, plus a helper for scaling images for display. */
public class CardView {
    private final Card card;
    private final BufferedImage image;

    public CardView(Card card, BufferedImage image) {
        this.card = card;
        this.image = image;
    }

    public Card getCard()           { return card; }
    public BufferedImage getImage() { return image; }

    /** Scales an image to the given height, keeping the aspect ratio. */
    public static ImageIcon scaledIcon(BufferedImage img, int maxHeight) {
        int h = Math.min(maxHeight, img.getHeight());
        int w = (int) Math.round(img.getWidth() * (h / (double) img.getHeight()));
        return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }
}
