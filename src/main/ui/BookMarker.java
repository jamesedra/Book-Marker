package ui;

import model.Book;
import model.BookList;
import model.Rating;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Book Marker application
public class BookMarker {
    private Book book;
    private BookList bookList;
    private Rating rating;
    private Scanner input;
    private Writer writer;
    private Reader reader;
    private static final String JSON_STORE = "./data/bookList.json";

    // EFFECTS: runs the Book Marker application
    public BookMarker() {
        input = new Scanner(System.in);
        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
        runBookMarker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBookMarker() {
        boolean keepGoing = true;
        String command;
        titleDisplay();
        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for using Book Marker!");
    }

    // EFFECTS: prints out the title of the app
    private void titleDisplay() {
        System.out.println("Welcome to Book Marker!");
        System.out.println("mark the books that interests you");
        System.out.println("------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: initializes a book list
    private void init() {
        bookList = new BookList("My book list");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Select from:");
        System.out.println("\tc -> Check Your Library");
        System.out.println("\ta -> Add a Book");
        System.out.println("\tr -> Remove a Book");
        System.out.println("\tm -> Mark / Look for a Book");
        System.out.println("\ts -> Save your Library");
        System.out.println("\tl -> Load your Library");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            checkLibrary();
        } else if (command.equals("a")) {
            addBook();
        } else if (command.equals("r")) {
            removeBook();
        } else if (command.equals("m")) {
            searchBooks();
        } else if (command.equals("s")) {
            saveBookList();
        } else if (command.equals("l")) {
            loadBookList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void loadBookList() {
        try {
            bookList = reader.read();
            System.out.println("Loaded " + bookList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void saveBookList() {
        try {
            writer.open();
            writer.write(bookList);
            writer.close();
            System.out.println("Saved " + bookList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: Checks the current book list
    //          if there are no books in the book list, print out a notice of it.
    private void checkLibrary() {
        if (bookList.isEmpty()) {
            System.out.println("There are currently no books in your library.");
        } else {
            System.out.println("Here are the list of books in your library:");
            iterateBookList(bookList);
            viewingAndSelecting(bookList, false);
        }
    }

    // MODIFIES: this
    // EFFECTS: asks the user to input the book they want to add by entering its title, author, date, and rating
    private void addBook() {
        System.out.println("To add a book, please enter the title, author, and date published.");
        System.out.println("Title: ");
        String title = addTitle();
        System.out.println("Author: ");
        String author = input.nextLine();
        System.out.println("Date: ");
        String testDate = input.nextLine();
        while (!isInteger(testDate)) {
            System.out.println("Please enter the year of the published date.");
            testDate = input.nextLine();
        }
        int date = Integer.parseInt(testDate);
        System.out.println("Would you also like to add a rating? Enter 'y' if yes, press any key if no.");
        addRatingAndReview();
        System.out.println("Adding book...");
        if (!bookList.addBook(new Book(title, author, date, rating))) {
            System.out.println("Library is Full! Please remove a book from the library first.");
        }
        System.out.println(title + " by " + author + " has been added to your library!");
    }

    // EFFECTS: returns true if the String is an integer
    private static boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: returns the title inputted by the user. It will keep asking to re-enter the title if the title
    //          is already taken by another book
    private String addTitle() {
        String title = input.nextLine();
        while (bookList.checkDuplicate(title)) {
            System.out.println(title + " is already in the library.");
            System.out.println("Please enter a book that is not in the library");
            title = input.nextLine();
        }
        return title;
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to input the rating of a book. They can either input a rate score,
    //          a review, both, or none.
    private void addRatingAndReview() {
        rating = new Rating();
        String select = input.nextLine();
        if (select.equalsIgnoreCase("y")) {
            System.out.println("Please enter your rating on a scale of 1 to 10.");
            String testRate = input.nextLine();
            while (!isProperRating(testRate)) {
                System.out.println("Please only enter an integer on a scale of 1 to 10.");
                testRate = input.nextLine();
            }
            double rate = Double.parseDouble(testRate);
            rating.setRate(rate);
            System.out.println("Rating published!");
            System.out.println("Would you like to add your review? Enter 'y' if yes, press any key if no.");
            select = input.nextLine();
            if (select.equalsIgnoreCase("y")) {
                System.out.println("Please enter a short review for the book");
                String review = input.nextLine();
                rating.setReview(review);
            }
            System.out.println("Review published!");
        }
    }

    // EFFECTS: returns true if the String can be used for rating books
    private static boolean isProperRating(String num) {
        try {
            Double.parseDouble(num.trim());
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return Double.parseDouble(num.trim()) >= 1 && Double.parseDouble(num.trim()) <= 10;
    }

    // MODIFIES: this
    // EFFECTS: prompts the user what book/s they want to remove from the book list. It will keep asking the user
    //          until they removed enough books or there are no more books in the book list
    private void removeBook() {
        boolean keepGoing = true;
        while (keepGoing) {
            if (bookList.isEmpty()) {
                System.out.println("There are no books in the library.");
                keepGoing = false;
            } else {
                bookRemovalProcess();
                String select;
                if (!bookList.isEmpty()) {
                    System.out.println("Would you like to remove another book? Enter 'y' if so. "
                            + "Otherwise, enter any key.");
                    select = input.nextLine();
                } else {
                    select = "n";
                }
                if (!select.equalsIgnoreCase("y")) {
                    keepGoing = false;
                }
                input.nextLine();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes the book removal function and prompts. Asks the user for the index of the book in order to
    //          remove it successfully.
    private void bookRemovalProcess() {
        System.out.println("Here are the list of books in the Library:");
        iterateBookList(bookList);
        System.out.println("Please enter the number of the book you want to remove.");
        int inputIndex = input.nextInt();
        while (inputIndex > bookList.length() || inputIndex < 0) {
            System.out.println("There is not a book with that placement! Please enter the number correctly.");
            inputIndex = input.nextInt();
        }
        Book tempBook = bookList.getBookFromIndex(inputIndex);
        if (bookList.removeBook(tempBook)) {
            System.out.println(tempBook.getTitle() + "is removed from the Library.");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a book title or author in order to find it within the book list.
    private void searchBooks() {
        System.out.println("This feature is to provide you the details of the book you are planning to search.");
        System.out.println("Please enter the title or the author of the book.");
        String userInput = input.nextLine();
        // Create a new Book List to store the matching books
        BookList matchingBooks = new BookList("for match");
        matchBooksFromInput(userInput, matchingBooks);
        searchResults(matchingBooks);
        viewingAndSelecting(matchingBooks, true);
    }

    // MODIFIES: matchingBooks
    // EFFECTS: checks the book list iteratively if the keyword from the user input can be found in
    //          any position of the String of the book title, or author. If the keyword match on any book, store
    //          the results in a new list for the user to view.
    private void matchBooksFromInput(String userInput, BookList matchingBooks) {
        // Loop through each book in the bookList
        for (Book book : bookList.viewBookList()) {
            // Check if the book's title or author contains the user input
            if (book.checkMatch(userInput)) {
                // Add the book to the matchingBooks list
                matchingBooks.addBook(book);
            }
        }
    }

    // EFFECTS: prints out the results of the books that matched from the keyword the user inputted.
    private void searchResults(BookList matchingBooks) {
        // Print out the matching books
        if (matchingBooks.isEmpty()) {
            System.out.println("\nNo books found that match your search.");
        } else {
            System.out.println("\nThe following books match your search:");
            iterateBookList(matchingBooks);
        }
    }

    // MODIFIES: this
    // EFFECTS: this method either comes from the checkLibrary or the searchBooks method
    //          it prompts the user if it would like to view a book from the list provided
    //          if the method is called from a search method, it searchBooks to repeat or
    //          redo a search
    private void viewingAndSelecting(BookList bookList, boolean search) {
        selectViewingInstructions(bookList, search);
        String select = input.nextLine();
        if (select.equalsIgnoreCase("v") && !bookList.isEmpty()) {
            interactWithResults(bookList);
        } else if (select.equalsIgnoreCase("s") && search) {
            searchBooks();
        }
    }

    // EFFECTS: Displays viewing instructions for the user. If the method was called from a search method,
    //          it also displays another option to search for another book.
    private void selectViewingInstructions(BookList bookListing, boolean search) {
        if (!bookListing.isEmpty()) {
            System.out.println("\nWould you like to view a book?");
            System.out.println("\tv -> View a book from the results");
        }
        if (search) {
            System.out.println("\ts -> Search for another book");
        }
        System.out.println("\tPress any key to go back to the main menu.");
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a book index based on the results to view the details of the book.
    private void interactWithResults(BookList results) {
        System.out.println("\nEnter the number of the book you want to view");
        int userInput = input.nextInt();
        while (userInput < 0 || userInput > results.length()) {
            System.out.println("Enter only a number based on the results");
            userInput = input.nextInt();
        }
        input.nextLine();
        book = results.getBookFromIndex(userInput);
        viewDetails();
    }

    // EFFECTS: Displays the book's details
    private void viewDetails() {
        System.out.println("\n------------------");
        System.out.println(book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Date: " + book.getDate());
        System.out.println("----------------");
        if (book.getRating().getRate() < 1) {
            System.out.println("Rating: none");
        } else {
            System.out.printf("Rating: " + "%.1f" + book.rateToStar(), book.getRating().getRate());
        }
        System.out.println();
        System.out.println("Review: ");
        System.out.println(book.printReview());
        editDetails();
    }

    // MODIFIES: this
    // EFFECTS: prompts the user if they want to edit a book's details. It will keep prompting the user as long
    //          as they want to change any field of the book
    private void editDetails() {
        System.out.println("\nWould you like to edit this book? Enter 'y' to do so. Otherwise, enter any key.");
        String userInput = input.nextLine();
        while (userInput.equalsIgnoreCase("y")) {
            displayEditor();
            char select = input.nextLine().charAt(0);
            select = Character.toLowerCase(select);
            editBook(select);
            System.out.println("Would you like to edit something else? Enter y to do so. Otherwise enter any key.");
            userInput = input.nextLine();
        }
    }

    // EFFECTS: Displays a selection for the user on what field they want to edit the current book selected
    private void displayEditor() {
        System.out.println("What would you like to edit in this book?");
        System.out.println("\tt -> Title");
        System.out.println("\ta -> Author");
        System.out.println("\td -> Date");
        System.out.println("\tr -> Rating");
    }

    // MODIFIES: this
    // EFFECTS: edits the book based on the user input and the current selection choice
    private void editBook(char select) {
        String newInput;
        switch (select) {
            case 't':
                System.out.println("Please enter the new title:");
                newInput = input.nextLine();
                book.setTitle(newInput);
                break;
            case 'a':
                System.out.println("Please enter the new author:");
                newInput = input.nextLine();
                book.setAuthor(newInput);
                break;
            case 'd':
                System.out.println("Please enter the new date:");
                newInput = input.nextLine();
                book.setDate(Integer.parseInt(newInput));
                break;
            case 'r':
                editRating();
                break;
            default:
                System.out.println("\nThe character entered is not part of the selection.");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the current rate score and review of the book. Provides the user a choice if they want
    //          to edit the current rating.
    private void editRating() {
        System.out.println("The current rating of the book is: " + book.rateToStar());
        System.out.println("And the current review for this book is as below: ");
        System.out.println(book.getRating().formatReview());
        System.out.println("Enter 'y' to edit the rating: ");
        addRatingAndReview();
        book.setRating(rating);
    }

    // EFFECTS: prints out the list of books with an index
    private void iterateBookList(BookList bookList) {
        int n = 1;
        for (Book book : bookList.viewBookList()) {
            System.out.println(book.toString(n));
            n++;
        }
    }
}
