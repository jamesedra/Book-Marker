# Book Marker

## Mark the books that interest you.

*What is Book Marker?*
- Book Marker is an application for tracking the books the user has read, wants to read, or is currently reading.<br> 
Possible features include the details of the books such as: the title, the author/s, the date it was published,
the cover page, and if it belongs to a set of books (such as being included on a 7-book series). User features 
will involve the current status if it's being read or not, the chapter or page of the book they are currently on, 
the number of books in their log, and the user's rating and reviews when possible. <br>

*Who is this for?*
- Book Marker is for people who would like to keep track of the things they have been reading without using an
online database in order to create a small viewpoint of their own library.

*Why does this interest you?*
- I would like to keep track of the books I have read, currently reading, or I am interested in. There are also
some books that I have read before that were not published, so it was hard to keep track of them, even locally.

## User Stories
- As a user, I want to be able to add and remove a book to the catalog
- As a user, I want to be able to view the list of books I have read on my catalog
- As a user, I want to be able to rate the book from one to 10 stars and add a review to it
- As a user, I want to be able to search and select a book in my catalog to view the details and my views of the book
- As a user, I want to be given the option to load a book list/library from file.
- As a user, I want to be given the option to save my library to file.

## Instructions for the Grader
- You can add a book to a book list by clicking "Add Book" button on the main window, which opens a window where you 
can input the details of the book and click the "Add Book" button.
- You can view the book list by clicking the "Open Library" button on the main window.
- You can view the details of the book by clicking "View Book" from the "Your Library" window, after clicking the 
"Open Library" button from the main window.
- You can locate the visual components from the splash screen when opening the application.
- You can save the state of the application by clicking the Save button from the main window.
- You can reload the state of the application by clicking the Load button from the main window.

## Phase 4: Task 3
- There are some classes that I would like to do some refactoring in once I have the time. First is the BookMarkerGUI 
class, which can be improved by its design and readability. While at first, I managed to split the methods into smaller 
parts to improve the readability of the class, the initializeFrame() and initializeButton() methods can still be 
improved (an example of it would be creating a method for the toolPanel.add() lines instead of just placing them all in 
the initializeFrame() method).
  
- I have tried splitting up the BookMarkerGUI class into separate classes to shorten the code. This made my project 
have additional classes in the ui folder such as the BookAdder, BookRemover, OpenLibrary, etc. What I first would like 
to refactor from those classes is that, since I was focusing more on finishing the functionality of the program itself, 
I placed all the methods that I needed to run in the ActionPerformed() method (or a helper method that has 30 more 
lines of code). I would like to cut those down into smaller pieces to also improve the readability of the code. I also 
noticed late that I might have done unnecessary imports to those classes such as ‘ui.BookMarkerGUI.book’ in the 
BookAdder class. It does not have to be called from the BookMarkerGUI class at all, while also adding unnecessary 
complexity that even shows in the UML diagram.