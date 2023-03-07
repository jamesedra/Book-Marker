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
    }

    @Test
    void testFormatReviewWhenNull() {
        assertEquals(testRating.getReview(), "none");
        assertEquals(testRating.formatReview(), "none");
    }
    @Test
    void testFormatReviewShort() {
        testRating.setReview(shortReview);
        assertEquals(testRating.formatReview(), shortReview);
    }

    @Test
    void testFormatReviewLong() {
        testRating.setReview(longReview);
        String formattedExpected = "This book is undoubtedly darker than the previous "
        + System.lineSeparator() + "ones, as Harry learns more and more about the sini-"
                + System.lineSeparator() + "ster forces that threaten the wizarding world.";
        assertEquals(testRating.getReview(), longReview);
        assertEquals(testRating.formatReview(), formattedExpected);
    }

    @Test
    void testFormatRateScoreWhenNotSet() {
        assertEquals(testRating.formatRateScore(), "none");
    }
    
    @Test
    void testFormatRateScoreWhenSet() {
        testRating.setRate(8);
        assertEquals(testRating.formatRateScore(), " * * * * * * * *");
    }
}
