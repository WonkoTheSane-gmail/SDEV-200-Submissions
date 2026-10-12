import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

/**
 * Program 1 - the main application. Run this one.
 * Tab 1 adds cards from a photo (or typed name); tab 2 browses the saved collection.
 * The collection is saved in collection.txt in the folder you run the program from.
 */
public class CardApp extends JFrame {

    public CardApp() {
        super("MTG Collection");
        ScryfallClient client = new ScryfallClient();
        CollectionStore store = new CollectionStore("collection.txt");
        try {
            store.load();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not read collection.txt: " + e.getMessage());
        }

        CollectionPanel collectionPanel = new CollectionPanel(client, store);
        ScanPanel scanPanel = new ScanPanel(client, store, new OcrCardIdentifier(),
                collectionPanel::refresh);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Add Card", scanPanel);
        tabs.addTab("My Collection", collectionPanel);
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() == collectionPanel) {
                collectionPanel.refresh();
            }
        });

        add(tabs);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 680);
        setLocationRelativeTo(null);
    }

    /** Unwraps SwingWorker's ExecutionException to get the real error message. */
    static String rootMessage(Exception e) {
        Throwable t = (e instanceof ExecutionException && e.getCause() != null) ? e.getCause() : e;
        return t.getMessage() == null ? t.toString() : t.getMessage();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CardApp().setVisible(true));
    }
}
