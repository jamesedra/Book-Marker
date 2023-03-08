package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Book;
import model.BookList;
import model.Rating;
import org.json.*;

// Represents a reader that reads book list from JSON data stored in file
public class Reader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads book list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BookList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses book list from JSON object and returns it
    private BookList parseBookList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BookList bl = new BookList(name);
        addBookList(bl, jsonObject);
        return bl;
    }

    // MODIFIES: bl
    // EFFECTS: parses book list from JSON object and adds them to workroom
    private void addBookList(BookList bl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("book list");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBooks(bl, nextBook);
        }
    }

    // MODIFIES: bl
    // EFFECTS: parses books from JSON object and adds it to workroom
    private void addBooks(BookList bl, JSONObject jsonObject) {
        String name = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        int date = jsonObject.getInt("date");
        Rating rating = new Rating();
        rating.setRate(jsonObject.getDouble("rating score"));
        rating.setReview(jsonObject.getString("review"));
        //BookCategory category = BookCategory.valueOf(jsonObject.getString("category"));
        Book book = new Book(name, author, date, rating);
        bl.addBook(book);
    }
}
