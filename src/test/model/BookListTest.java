package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookListTest {
    private BookList testBookList;
    private Rating nullRating = new Rating();
    private Book book0 = new Book("Book0", "Author0", 2000, nullRating);
    private Book book1 = new Book("Book1", "Author1", 2001, nullRating);

    @BeforeEach
    void runBefore() {
        testBookList = new BookList();
    }

    @Test
    void testConstructor() {
        assertTrue(testBookList.isEmpty());
    }

    @Test
    void testAddBookOneBook() {
        assertTrue(testBookList.addBook(book0));
        assertEquals(testBookList.length(), 1);
    }

    @Test
    void testAddBookTwoBooks() {
        assertTrue(testBookList.addBook(book0));
        assertEquals(testBookList.length(), 1);
        assertTrue(testBookList.addBook(book1));
        assertEquals(testBookList.length(), 2);
        assertEquals(testBookList.getBookFromIndex(1), book0);
        assertEquals(testBookList.getBookFromIndex(2), book1);
    }

    @Test
    void testAddBookWhenFull() {
        Book book2 = new Book("Book2", "Author2", 2002, nullRating);
        Book book3 = new Book("Book3", "Author3", 2003, nullRating);
        Book book4 = new Book("Book4", "Author4", 2004, nullRating);
        Book book5 = new Book("Book5", "Author5", 2005, nullRating);
        Book book6 = new Book("Book6", "Author6", 2006, nullRating);
        Book book7 = new Book("Book7", "Author7", 2007, nullRating);
        Book book8 = new Book("Book8", "Author8", 2008, nullRating);
        Book book9 = new Book("Book9", "Author9", 2009, nullRating);
        assertTrue(testBookList.addBook(book0));
        assertTrue(testBookList.addBook(book1));
        assertTrue(testBookList.addBook(book2));
        assertTrue(testBookList.addBook(book3));
        assertTrue(testBookList.addBook(book4));
        assertTrue(testBookList.addBook(book5));
        assertTrue(testBookList.addBook(book6));
        assertTrue(testBookList.addBook(book7));
        assertTrue(testBookList.addBook(book8));
        assertTrue(testBookList.addBook(book9));
        assertEquals(testBookList.length(), 10);
        assertFalse(testBookList.addBook(new Book ("dummy", "dummy", 0, nullRating)));
    }

    @Test
    void testRemoveBookOneBook() {
        assertTrue(testBookList.addBook(book0));
        assertEquals(testBookList.length(), 1);
        assertTrue(testBookList.removeBook(book0));
        assertEquals(testBookList.length(), 0);
    }

    @Test
    void testRemoveBookTwoBooks() {
        assertTrue(testBookList.addBook(book0));
        assertEquals(testBookList.length(), 1);
        assertTrue(testBookList.addBook(book1));
        assertEquals(testBookList.length(), 2);
        assertTrue(testBookList.removeBook(book0));
        assertEquals(testBookList.length(), 1);
        assertEquals(testBookList.getBookFromIndex(1), book1);
    }

    @Test
    void testCheckDuplicateWhenSameTitle() {
        assertTrue(testBookList.addBook(book0));
        String testTitle = book0.getTitle();
        assertTrue(testBookList.checkDuplicate(testTitle));
    }

    @Test
    void testCheckDuplicateWhenNoDuplicate() {
        assertTrue(testBookList.addBook(book0));
        String testTitle = book1.getTitle();
        assertFalse(testBookList.checkDuplicate(testTitle));
    }

}
