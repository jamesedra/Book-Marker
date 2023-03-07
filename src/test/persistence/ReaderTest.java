package persistence;

import model.Book;
import model.BookList;
import model.Rating;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        Reader reader = new Reader("./data/noSuchFile.json");
        try {
            BookList bl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBookList() {
        Reader reader = new Reader("./data/testReaderEmptyBookList.json");
        try {
            BookList bl = reader.read();
            assertEquals("My book list", bl.getName());
            assertEquals(0, bl.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBookList() {
        Reader reader = new Reader("./data/testReaderGeneralBookList.json");
        try {
            BookList bl = reader.read();
            assertEquals("My book list", bl.getName());
            List<Book> bookList = bl.viewBookList();
            assertEquals(2, bookList.size());
            Rating testRate1 = new Rating();
            testRate1.setRate(8);
            testRate1.setReview("Best book");
            Rating testRate2 = new Rating();
            checkBook("Book 1", "Author 1", 1999, testRate1, bookList.get(0));
            checkBook("Book 2", "Author 2", 2000, testRate2, bookList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}