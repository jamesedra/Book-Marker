package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {
    private Book testBook;
    private Rating rating= new Rating();

    @BeforeEach
    void runBefore() {
        testBook = new Book("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling",
                1999, rating);
    }

    @Test
    void testConstructor() {
        assertEquals(testBook.getTitle(), "Harry Potter and the Prisoner of Azkaban");
        assertEquals(testBook.getAuthor(), "J.K. Rowling");
        assertEquals(testBook.getDate(), 1999);
        assertEquals(testBook.getRating(), rating);
    }

    @Test
    void testToString() {
        int n = 1;
        String expected = n + " - " + "Title: " + testBook.getTitle() +
                " | Author: " + testBook.getAuthor() +
                " | Date: " + testBook.getDate();
        assertEquals(testBook.toString(n), expected);
    }

    @Test
    void testCheckMatchFromTitle() {
        String fullTitle = "Harry Potter and the Prisoner of Azkaban";
        String oneWord = "Harry";
        String wordCaseInsensitive = "harry";
        String wordInMiddle = "prisoner";
        assertTrue(testBook.checkMatch(fullTitle));
        assertTrue(testBook.checkMatch(oneWord));
        assertTrue(testBook.checkMatch(wordCaseInsensitive));
        assertTrue(testBook.checkMatch(wordInMiddle));
    }

    @Test
    void testCheckMatchFromAuthor() {
        String fullTitle = "J.K. Rowling";
        String oneWord = "Rowling";
        String wordCaseInsensitive = "j.k.";
        String wordInMiddle = "row";
        assertTrue(testBook.checkMatch(fullTitle));
        assertTrue(testBook.checkMatch(oneWord));
        assertTrue(testBook.checkMatch(wordCaseInsensitive));
        assertTrue(testBook.checkMatch(wordInMiddle));
    }

    @Test
    void testCheckMatchWhenFalse() {
        String otherTitle = "Lord of the Rings";
        String otherAuthor = "J. R. R. Tolkien";
        assertFalse(testBook.checkMatch(otherTitle));
        assertFalse(testBook.checkMatch(otherAuthor));
    }

}
