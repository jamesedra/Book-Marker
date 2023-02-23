package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RatingTest {
    private Rating testRating;
    private static final String shortReview = "One of the better books.";
    private static final String longReview = "This book is undoubtedly darker than the previous ones, " +
            "as Harry learns more and more about the sinister forces that threaten the wizarding world.";

    @BeforeEach
    void runBefore() {
        testRating = new Rating();
        testRating.setRate(8);
        testRating.setReview(shortReview);
    }

    @Test
    void testFormatReviewShort() {
        assertEquals(testRating.formatReview(), shortReview);
    }

    @Test
    void testFormatReviewLong() {
        testRating.setReview(longReview);
        String formattedExpected = "This book is undoubtedly darker than the previous "
        + System.lineSeparator() + "ones, as Harry learns more and more about the sini-"
                + System.lineSeparator() + "ster forces that threaten the wizarding world.";
        assertEquals(testRating.formatReview(), formattedExpected);
    }

    @Test
    void testFormatRateScore() {
        assertEquals(testRating.formatRateScore(), "8 * * * * * * * *");
    }
}
