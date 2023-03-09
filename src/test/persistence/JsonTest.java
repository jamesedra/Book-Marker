package persistence;

import model.Book;
import model.Rating;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    // This test case was adapted from CPSC 210 WorkRoomApp
    protected void checkBook(String title, String author, int date, Rating rating, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(date, book.getDate());
        assertEquals(rating.getRate(), book.getRating().getRate());
        assertEquals(rating.getReview(), book.getRating().getReview());
    }
}
