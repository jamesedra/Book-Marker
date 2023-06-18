# Book Marker

## Mark the books that interest you.

# Project Overview

Book Marker is an application designed to help users track the books they have read, want to read, or are currently reading. It allows users to maintain a personal library and provides features such as book details, reading progress, ratings, and reviews.

The application aims to provide users with a simple and personalized way to keep track of their reading history, without relying on online databases. By using Book Marker, users can create a small viewpoint of their own library and easily manage their reading preferences.

# Project Structure

The project follows a structured organization to maintain code readability and maintainability. Here is an overview of the project structure:

    ui folder: Contains the user interface classes responsible for displaying and managing the application's windows and components.
        BookMarkerGUI.java: The main user interface class that initializes the application window and handles user interactions.
        BookAdder.java: A class responsible for adding books to the catalog.
        BookRemover.java: A class responsible for removing books from the catalog.
        OpenLibrary.java: A class for viewing the book catalog and selecting books for detailed viewing.
        (Note: Refactoring of these classes is planned to improve design, readability, and code organization.)

    model folder: Contains the book-related classes and functionality.
        Book.java: Represents a book object with properties such as title, author, publication date, cover page, etc.
        BookList.java: Manages the list of books in the catalog, allowing users to add and remove books.
        Rating: Represents the rating given by a user for a particular book, on a scale from one to ten stars.

    Other relevant project files and resources.

# UI Examples
Home Page:

<img width="467" alt="Home Page" src="https://github.com/jamesedra/Book-Marker/assets/107374254/76363752-452b-4e51-8609-24bd9a5818e5">

Library Window (left) and Book Adder Window (right) :

<img width="289" alt="Library Page" src="https://github.com/jamesedra/Book-Marker/assets/107374254/1377bfe0-9a93-4f09-95a2-e2a16408520e"> <img width="400" alt="Add book" src="https://github.com/jamesedra/Book-Marker/assets/107374254/1133acd3-8cc6-46cd-923a-fd2fd83bb999">

# Instructions for Reviewers

To review the application, please follow these instructions:

    Launch the application by running Main.java in src/main/ui/Main.java
    Use the "Add Book" button on the main window to add a book to the catalog, providing relevant details.
    Click the "Open Library" button on the main window to view the list of books in your catalog.
    From the "Your Library" window, click "View Book" to see the details and your personal views of a specific book.
    Utilize the provided options to save and load your library state using the corresponding buttons on the main window.

