package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a book having a title, author, date, and rating
public class Book implements Writable {
    private String title;
    private String author;
    private int date;
    private Rating rating;

    /*
     * REQUIRES: title and author has a non-zero length
     *           date should be a positive integer
     *           rating should be constructed
     * EFFECTS: title on book is set to title; author on book is set to author;
     *          date on book is set to date; rating on book is set to rating.
     */
    public Book(String title, String author, int date, Rating rating) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.rating = rating;
    }

    /*
     * REQUIRES: n > 0 and n <= the length of the current book list
     * EFFECTS: returns a string with the current count, the books' title, author, and date.
     */
    public String toString(int n) {
        return n + " - " + "Title: " + title + " | Author: " + author + " | Date: " + date;
    }

    /*
     * REQUIRES: userInput != null
     * EFFECTS: returns true if the userInput's string can be matched to the books' title
     *          or author at any starting position.
     */
    public boolean checkMatch(String userInput) {
        return getTitle().toLowerCase().contains(userInput.toLowerCase())
                || getAuthor().toLowerCase().contains(userInput.toLowerCase());
    }

    /*
     * EFFECTS: Calls the book's rating to return a string in a format for display purposes.
     */
    public String rateToStar() {
        return getRating().formatRateScore();
    }

    /*
     * EFFECTS: returns a string from the book's review called from the Rating class.
     */
    public String printReview() {
        return getRating().formatReview();
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getDate() {
        return this.date;
    }

    public Rating getRating() {
        return this.rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    /*
     * This method was adapted from CPSC 210 WorkRoomApp
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("date", date);
        json.put("rating score", getRating().getRate());
        json.put("review", getRating().getReview());
        return json;
    }
}
