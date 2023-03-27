package persistence;

import model.BookList;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of book list to file
public class Writer {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // This constructor was copied from CPSC 210 WorkRoomApp
    // EFFECTS: constructs writer to write to destination file
    public Writer(String destination) {
        this.destination = destination;
    }

    // This method was copied from CPSC 210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // This method was adapted from CPSC 210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: writes JSON representation of book list to file
    public void write(BookList bl) {
        JSONObject json = bl.toJson();
        saveToFile(json.toString(TAB));
    }

    // This method was copied from CPSC 210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // This method was copied from CPSC 210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
