import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The user's collection: a list of Scryfall card ids saved one per line in a text file.
 * Only ids are stored - names and images are fetched from Scryfall when needed.
 */
public class CollectionStore {
    private final File file;
    private final List<String> ids = new ArrayList<>();

    public CollectionStore(String path) {
        this.file = new File(path);
    }

    /** Reads the file (if it exists), skipping blank lines and duplicates. */
    public void load() throws IOException {
        ids.clear();
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !ids.contains(line)) {
                    ids.add(line);
                }
            }
        }
    }

    /** Adds an id and saves. Returns false if it was already in the collection. */
    public boolean add(String id) throws IOException {
        if (ids.contains(id)) {
            return false;
        }
        ids.add(id);
        save();
        return true;
    }

    /** Removes an id and saves. Returns false if it was not present. */
    public boolean remove(String id) throws IOException {
        boolean removed = ids.remove(id);
        if (removed) {
            save();
        }
        return removed;
    }

    public int size() {
        return ids.size();
    }

    public String[] toArray() {
        return ids.toArray(new String[0]);
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    private void save() throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (String id : ids) {
                writer.println(id);
            }
        }
    }
}
