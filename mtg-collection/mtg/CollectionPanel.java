import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * "My Collection" tab: page through saved cards one at a time. Each card's details and
 * image are fetched from Scryfall by id the first time it is shown, then cached.
 */
public class CollectionPanel extends JPanel {
    private final ScryfallClient client;
    private final CollectionStore store;
    private final Map<String, CardView> cache = new HashMap<>();
    private int index = 0;

    private final JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel infoLabel = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel countLabel = new JLabel("0 / 0", SwingConstants.CENTER);
    private final JButton prevButton = new JButton("< Previous");
    private final JButton nextButton = new JButton("Next >");
    private final JButton removeButton = new JButton("Remove");

    public CollectionPanel(ScryfallClient client, CollectionStore store) {
        this.client = client;
        this.store = store;

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(imageLabel, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.add(infoLabel, BorderLayout.NORTH);
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(prevButton);
        buttons.add(countLabel);
        buttons.add(nextButton);
        buttons.add(removeButton);
        south.add(buttons, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        prevButton.addActionListener(e -> { index--; showCurrent(); });
        nextButton.addActionListener(e -> { index++; showCurrent(); });
        removeButton.addActionListener(e -> removeCurrent());
        showCurrent();
    }

    /** Called when the tab is opened or a card has been added. */
    public void refresh() {
        index = Math.max(0, Math.min(index, store.size() - 1));
        showCurrent();
    }

    private void showCurrent() {
        String[] ids = store.toArray();
        boolean hasCards = ids.length > 0;
        prevButton.setEnabled(hasCards && index > 0);
        nextButton.setEnabled(hasCards && index < ids.length - 1);
        removeButton.setEnabled(hasCards);

        if (!hasCards) {
            imageLabel.setIcon(null);
            imageLabel.setText("Your collection is empty. Add cards on the first tab.");
            infoLabel.setText(" ");
            countLabel.setText("0 / 0");
            return;
        }

        final String id = ids[index];
        countLabel.setText((index + 1) + " / " + ids.length);

        CardView cached = cache.get(id);
        if (cached != null) {
            display(cached);
            return;
        }

        imageLabel.setIcon(null);
        imageLabel.setText("Loading...");
        infoLabel.setText(" ");
        new SwingWorker<CardView, Void>() {
            @Override
            protected CardView doInBackground() throws Exception {
                Card card = client.findById(id);
                return new CardView(card, client.fetchImage(card));
            }

            @Override
            protected void done() {
                try {
                    CardView view = get();
                    cache.put(id, view);
                    // Only draw it if the user hasn't paged away in the meantime.
                    String[] now = store.toArray();
                    if (index < now.length && now[index].equals(id)) {
                        display(view);
                    }
                } catch (Exception ex) {
                    imageLabel.setText("Could not load card: " + CardApp.rootMessage(ex));
                }
            }
        }.execute();
    }

    private void display(CardView view) {
        imageLabel.setText(null);
        imageLabel.setIcon(CardView.scaledIcon(view.getImage(), 500));
        infoLabel.setText(view.getCard().toString());
    }

    private void removeCurrent() {
        String[] ids = store.toArray();
        if (ids.length == 0) {
            return;
        }
        try {
            store.remove(ids[index]);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save: " + ex.getMessage());
        }
        refresh();
    }
}
