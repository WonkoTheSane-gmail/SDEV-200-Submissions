import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

/**
 * Reads the card name by cropping the title bar of the photo and running the
 * Tesseract OCR command-line tool on it. Tesseract must be installed and on the PATH.
 * (Scryfall has no "search by image" API, so we read the name and look that up instead.)
 */
public class OcrCardIdentifier implements CardIdentifier {
    // Where the name bar sits on a standard card, as fractions of the whole image.
    private static final double X = 0.05, Y = 0.035, W = 0.78, H = 0.075;
    private static final int SCALE = 3; // enlarge the crop so OCR is more accurate

    private final String tesseractCommand;

    public OcrCardIdentifier() {
        this("tesseract");
    }

    public OcrCardIdentifier(String tesseractCommand) {
        this.tesseractCommand = tesseractCommand;
    }

    @Override
    public String identifyName(File imageFile) throws IdentificationException {
        BufferedImage source;
        try {
            source = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new IdentificationException("Could not read image: " + e.getMessage());
        }
        if (source == null) {
            throw new IdentificationException("That file is not a readable image.");
        }

        File temp = null;
        try {
            temp = File.createTempFile("mtg-name", ".png");
            ImageIO.write(prepareNameBanner(source), "png", temp);

            ProcessBuilder pb = new ProcessBuilder(
                    tesseractCommand, temp.getAbsolutePath(), "stdout", "--psm", "7");
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);
            Process process = pb.start();
            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            process.waitFor();

            String cleaned = cleanText(output);
            if (cleaned.isEmpty()) {
                throw new IdentificationException("OCR found no text in the card's name area.");
            }
            return cleaned;
        } catch (IOException e) {
            throw new IdentificationException(
                    "Could not run Tesseract (is it installed and on your PATH?): " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IdentificationException("OCR was interrupted.");
        } finally {
            if (temp != null) {
                temp.delete();
            }
        }
    }

    /** Crops the name bar and enlarges it as grayscale. */
    private BufferedImage prepareNameBanner(BufferedImage src) {
        int x = (int) (src.getWidth() * X);
        int y = (int) (src.getHeight() * Y);
        int w = Math.min((int) (src.getWidth() * W), src.getWidth() - x);
        int h = Math.min((int) (src.getHeight() * H), src.getHeight() - y);
        BufferedImage banner = src.getSubimage(x, y, w, h);

        BufferedImage out = new BufferedImage(w * SCALE, h * SCALE, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(banner, 0, 0, w * SCALE, h * SCALE, null);
        g.dispose();
        return out;
    }

    /** Keeps letters, apostrophes, commas, hyphens and spaces; drops OCR junk. */
    static String cleanText(String raw) {
        return raw.replaceAll("[^\\p{L}',\\- ]", " ").replaceAll("\\s+", " ").trim();
    }
}
