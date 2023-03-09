package persistence;

import model.Book;
import model.BookList;
import model.Rating;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class WriterTest extends JsonTest {

    // This test case was adapted from CPSC 210 WorkRoomApp
    @Test
    void testWriterInvalidFile() {
        try {
            BookList bl = new BookList("My book list");
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // This test case was adapted from CPSC 210 WorkRoomApp
    @Test
    void testWriterEmptyBookList() {
        try {
            BookList bl = new BookList("My book list");
            Writer writer = new Writer("./data/testWriterEmptyBookList.json");
            writer.open();
            writer.write(bl);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmptyBookList.json");
            bl = reader.read();
            assertEquals("My book list", bl.getName());
            assertEquals(0, bl.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // This test case was adapted from CPSC 210 WorkRoomApp
    @Test
    void testWriterGeneralBookList() {
        try {
            BookList bl = new BookList("My book list");
            Rating testRate1 = new Rating();
            testRate1.setRate(8);
            testRate1.setReview("Best book");
            Rating testRate2 = new Rating();
            bl.addBook(new Book("Book 1", "Author 1", 1999, testRate1));
            bl.addBook(new Book("Book 2", "Author 2", 2000, testRate2));
            Writer writer = new Writer("./data/testWriterGeneralBookList.json");
            writer.open();
            writer.write(bl);
            writer.close();

            Reader reader = new Reader("./data/testWriterGeneralBookList.json");
            bl = reader.read();
            assertEquals("My book list", bl.getName());
            List<Book> bookList = bl.viewBookList();
            assertEquals(2, bookList.size());
            checkBook("Book 1", "Author 1", 1999, testRate1, bookList.get(0));
            checkBook("Book 2", "Author 2", 2000, testRate2, bookList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}