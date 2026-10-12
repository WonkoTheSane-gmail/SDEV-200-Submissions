import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * "Add Card" tab: choose a photo, read the name with OCR, look it up on Scryfall,
 * and add the match to the collection. The name box can be edited by hand if OCR is wrong.
 */
public class ScanPanel extends JPanel {
    private final ScryfallClient client;
    private final CollectionStore store;
    private final CardIdentifier identifier;
    private final Runnable onCardAdded;

    private final JLabel photoLabel = new JLabel("Your photo", SwingConstants.CENTER);
    private final JLabel matchLabel = new JLabel("Scryfall match", SwingConstants.CENTER);
    private final JTextField nameField = new JTextField(24);
    private final JButton chooseButton = new JButton("Choose photo...");
    private final JButton lookupButton = new JButton("Look up");
    private final JButton addButton = new JButton("Add to collection");
    private final JLabel statusLabel = new JLabel("Choose a photo of a card, or type a name.");

    private CardView current; // the match currently on screen

    public ScanPanel(ScryfallClient client, CollectionStore store,
                     CardIdentifier identifier, Runnable onCardAdded) {
        this.client = client;
        this.store = store;
        this.identifier = identifier;
        this.onCardAdded = onCardAdded;
        buildLayout();
        wireEvents();
    }

    private void buildLayout() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(chooseButton);
        top.add(new JLabel("Card name:"));
        top.add(nameField);
        top.add(lookupButton);
        add(top, BorderLayout.NORTH);

        JPanel images = new JPanel(new GridLayout(1, 2, 8, 8));
        images.add(photoLabel);
        images.add(matchLabel);
        add(images, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(8, 0));
        addButton.setEnabled(false);
        bottom.add(statusLabel, BorderLayout.CENTER);
        bottom.add(addButton, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }

    private void wireEvents() {
        chooseButton.addActionListener(e -> chooseImage());
        lookupButton.addActionListener(e -> lookUp());
        nameField.addActionListener(e -> lookUp());
        addButton.addActionListener(e -> addCurrent());
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "png", "jpg", "jpeg", "gif", "bmp"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();

        try {
            BufferedImage photo = ImageIO.read(file);
            if (photo == null) {
                throw new IOException("That file is not a readable image.");
            }
            photoLabel.setText(null);
            photoLabel.setIcon(CardView.scaledIcon(photo, 480));
        } catch (IOException ex) {
            statusLabel.setText("Could not open image: " + ex.getMessage());
            return;
        }

        statusLabel.setText("Reading the card name...");
        setBusy(true);
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return identifier.identifyName(file);
            }

            @Override
            protected void done() {
                try {
                    nameField.setText(get());
                    lookUp(); // go straight on to the Scryfall search
                } catch (Exception ex) {
                    setBusy(false);
                    statusLabel.setText("Could not read the name (" + CardApp.rootMessage(ex)
                            + "). Type it in and press Look up.");
                }
            }
        }.execute();
    }

    private void lookUp() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            statusLabel.setText("Enter a card name first.");
            return;
        }
        statusLabel.setText("Searching Scryfall for \"" + name + "\"...");
        setBusy(true);
        addButton.setEnabled(false);
        current = null;

        new SwingWorker<CardView, Void>() {
            @Override
            protected CardView doInBackground() throws Exception {
                Card card = client.findByName(name);
                return new CardView(card, client.fetchImage(card));
            }

            @Override
            protected void done() {
                setBusy(false);
                try {
                    current = get();
                    matchLabel.setText(null);
                    matchLabel.setIcon(CardView.scaledIcon(current.getImage(), 480));
                    statusLabel.setText("Found: " + current.getCard());
                    addButton.setEnabled(true);
                } catch (Exception ex) {
                    matchLabel.setIcon(null);
                    matchLabel.setText("No match");
                    statusLabel.setText("Lookup failed: " + CardApp.rootMessage(ex));
                }
            }
        }.execute();
    }

    private void addCurrent() {
        if (current == null) {
            return;
        }
        try {
            if (store.add(current.getCard().getId())) {
                statusLabel.setText("Added " + current.getCard() + " to your collection.");
                onCardAdded.run();
            } else {
                statusLabel.setText(current.getCard() + " is already in your collection.");
            }
        } catch (IOException ex) {
            statusLabel.setText("Could not save the collection: " + ex.getMessage());
        }
    }

    private void setBusy(boolean busy) {
        chooseButton.setEnabled(!busy);
        lookupButton.setEnabled(!busy);
    }
}
