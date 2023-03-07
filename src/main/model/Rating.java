package model;

public class Rating {
    private double rate;
    private String review;

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getRate() {
        return this.rate;
    }

    public String getReview() {
        if (this.review == null) {
            return "none";
        }
        return this.review;
    }

    /*
     * EFFECTS: returns a string of a review formatted with line breaks
     *          to fit the IDE print statement.
     *          If the review is null, returns a string "none".
     */
    public String formatReview() {
        if (this.review == null) {
            return "none";
        }

        int lineLength = 50;
        int charCount = 0;
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < review.length(); i++) {
            char c = review.charAt(i);
            formatted.append(c);
            charCount++;

            if (charCount >= lineLength && c != ' ') {
                formatted.append('-');
                formatted.append(System.lineSeparator());
                charCount = 0;
            } else if (charCount >= lineLength) {
                formatted.append(System.lineSeparator());
                charCount = 0;
            }
        }

        return formatted.toString();
    }

    /*
     * EFFECTS: returns a string that displays the rate score of the book, followed by a
     *          number of stars or the character '*' which amount is also dependent by its score.
     *          If the rating is 0, the method will return the string 'none'.
     */
    public String formatRateScore() {
        if (getRate() == 0) {
            return "none";
        }

        double num = getRate();
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < num; i++) {
            stars.append("* ");
        }
        return " " + stars.toString().trim();
    }
}
