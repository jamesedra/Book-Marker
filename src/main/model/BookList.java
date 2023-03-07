package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

public class BookList implements Writable {
    private LinkedList<Book> bookList;
    private String name;
    public static final int MAX_SIZE = 10;

    /*
     * MODIFIES: this
     * EFFECTS: constructs a new book list
     */
    public BookList(String name) {
        this.name = name;
        this.bookList = new LinkedList<>();
    }

    /*
     * REQUIRES: a constructed book
     * MODIFIES: this
     * EFFECTS: if the book list is not full, the method adds the book in the book list
     *          and returns true. Otherwise, returns false.
     */
    public boolean addBook(Book book) {
        if (isFull()) {
            return false;
        }
        bookList.add(book);
        return true;
    }

    /*
     * REQUIRES: a constructed book
     * MODIFIES: this
     * EFFECTS: if the book list contains the book, the method removes the book in the
     *          book list and returns true. Otherwise, returns false.
     */
    public boolean removeBook(Book book) {
        if (bookList.contains(book)) {
            bookList.remove(book);
            return true;
        }
        return false;
    }

    /*
     * REQUIRES: title != null
     * EFFECTS: returns true if the title of the book can be found in one of the books
     *          in the book list. Otherwise, return false.
     */
    public boolean checkDuplicate(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    /*
     * REQUIRES: index > 0 and index < the length of the book list
     * EFFECTS: returns a book based on the index
     */
    public Book getBookFromIndex(int index) {
        return bookList.get(index - 1);
    }

    /*
     * EFFECTS: returns the book list
     */
    public LinkedList<Book> viewBookList() {
        return bookList;
    }

    /*
     * EFFECTS: returns the number of books currently in the book list
     */
    public int length() {
        return bookList.size();
    }

    /*
     * EFFECTS: outputs true if the book list is empty
     */
    public boolean isEmpty() {
        return bookList.isEmpty();
    }

    /*
     * EFFECTS: outputs true if the book list is full
     */
    public boolean isFull() {
        return MAX_SIZE <= length();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("book list", bookListToJson());
        return json;
    }

    // EFFECTS: returns books in this book list as a JSON array
    private JSONArray bookListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : bookList) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

}
