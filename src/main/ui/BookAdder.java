package ui;

import model.Book;
import model.Rating;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.BookMarkerGUI.book;
import static ui.BookMarkerGUI.bookList;
import static ui.BookMarkerGUI.rating;

// Represents the window for adding a book to the book list
public class BookAdder extends JFrame implements ActionListener {

    private JLabel backgroundLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel yearLabel;
    private JLabel ratingLabel;
    private JLabel reviewLabel;

    private JTextField bookTitle;
    private JTextField bookAuthor;
    private JComboBox<Integer> bookYear;
    private JSlider ratingSlider;
    private JTextField bookReview;

    private JPanel forSlider;
    JButton addBookButton;

    static Color saffron = new Color(232,195,108);

    // MODIFIES: this
    // EFFECTS: runs the Add Books window
    public BookAdder() {
        super("Add a Book");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        // Create a JLabel to hold the background image
        backgroundLabel = new JLabel(new ImageIcon("src/adderBackground.jpg"));
        backgroundLabel.setLayout(null); // Set the layout to null
        setContentPane(backgroundLabel);

        // set the fields
        setFields();

        // add the labels and fields to the frame
        addLabelsAndFields();
        add(new JLabel()); // Empty label for spacing

        // set the add book button
        setAddBookButton();

        backgroundLabel.add(addBookButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes and sets the format of the add book button
    private void setAddBookButton() {
        addBookButton = new JButton("Add Book");
        addBookButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addBookButton.setOpaque(true);
        addBookButton.setBorderPainted(true);
        addBookButton.setForeground(BookMarkerGUI.textColor);
        addBookButton.setBackground(saffron);
        addBookButton.setBorder(BorderFactory.createEtchedBorder());
        addBookButton.setActionCommand("add");
        addBookButton.addActionListener(this);

        // Set the position and size of the addBookButton
        addBookButton.setBounds(160, 210, 100, 30);
    }

    // MODIFIES: this
    // EFFECTS: adds the labels, text fields, slider, and combo box to the background JLabel
    private void addLabelsAndFields() {
        titleLabel.setBounds(30, 30, 100, 20);
        backgroundLabel.add(titleLabel);

        bookTitle.setBounds(140, 30, 200, 20);
        backgroundLabel.add(bookTitle);

        authorLabel.setBounds(30, 60, 100, 20);
        backgroundLabel.add(authorLabel);

        bookAuthor.setBounds(140, 60, 200, 20);
        backgroundLabel.add(bookAuthor);

        yearLabel.setBounds(30, 90, 100, 20);
        backgroundLabel.add(yearLabel);

        bookYear.setBounds(140, 90, 200, 20);
        backgroundLabel.add(bookYear);

        ratingLabel.setBounds(30, 120, 100, 20);
        backgroundLabel.add(ratingLabel);

        forSlider = createRatingPanel();
        forSlider.setBounds(140, 120, 200, 60);
        backgroundLabel.add(forSlider);

        reviewLabel.setBounds(30, 190, 100, 20);
        backgroundLabel.add(reviewLabel);

        bookReview.setBounds(140, 190, 200, 20);
        backgroundLabel.add(bookReview);
    }

    // REQUIRES: width > 0
    // EFFECTS: creates a proper text field for the add book window
    private JTextField createTextField(int width) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(width, 30));
        textField.setFont(new Font("Times New Roman", Font.BOLD, 15));
        textField.setForeground(BookMarkerGUI.textColor);
        textField.setBackground(saffron);
        return textField;
    }

    // REQUIRES: width > 0
    // EFFECTS: creates a proper combo box for the add book window
    private JComboBox<Integer> createYearComboBox(int width) {
        JComboBox<Integer> comboBox = new JComboBox<>();
        Integer[] years = new Integer[104];
        for (int i = 0; i < years.length; i++) {
            years[i] = 1920 + i;
        }
        comboBox.setModel(new DefaultComboBoxModel<>(years));
        comboBox.setPreferredSize(new Dimension(width, 30));
        comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        comboBox.setForeground(BookMarkerGUI.bgColor);
        return comboBox;
    }

    // MODIFIES: this
    // EFFECTS: sets the fields, labels, combo box, and slider for the add book window
    private void setFields() {
        titleLabel = createLabel("Title: ");
        bookTitle = createTextField(250);
        authorLabel = createLabel("Author:");
        bookAuthor = createTextField(250);
        yearLabel = createLabel("Year Published:");
        bookYear = createYearComboBox(250);
        ratingLabel = createLabel("Rating:");
        ratingSlider = new JSlider(0, 10);
        reviewLabel = createLabel("Review:");
        bookReview = createTextField(250);
    }

    // MODIFIES: this
    // EFFECTS: creates a proper JLabel for the add box window
    private JLabel createLabel(String text) {
        JLabel temp = new JLabel(text);
        temp.setForeground(Color.white);
        temp.setFont(new Font("Times New Roman", Font.BOLD, 14));
        return temp;
    }

    // MODIFIES: this
    // EFFECTS: creates a proper rating slider for the add box window
    private JPanel createRatingPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(ratingSlider);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPreferredSize(new Dimension(190, 50));
        return panel;
    }

    // EFFECTS: tries to place the book to the book list
    //          if it's successful, will print a success message
    @Override
    public void actionPerformed(ActionEvent e) {
        rating = new Rating();
        rating.setRate(ratingSlider.getValue());
        rating.setReview(bookReview.getText());

        if (bookYear.getSelectedItem() == null) {
            book = new Book(
                    bookTitle.getText(),
                    bookAuthor.getText(),
                    0,
                    rating
            );
            bookList.addBook(book);
        } else {
            // cast selected item to Integer and proceed
            book = new Book(
                    bookTitle.getText(),
                    bookAuthor.getText(),
                    (Integer) bookYear.getSelectedItem(),
                    rating
            );
            bookList.addBook(book);
        }
        successMessage();
    }

    // EFFECTS: creates a new JFrame for the success message of adding a book
    public void successMessage() {
        JFrame successFrame = new JFrame("Success");
        successFrame.setPreferredSize(new Dimension(200, 100));
        JLabel successLabel = new JLabel("Book added successfully");
        successLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        successLabel.setHorizontalAlignment(JLabel.CENTER);
        successFrame.getContentPane().setBackground(saffron);
        successFrame.add(successLabel);
        successFrame.pack();
        successFrame.setLocationRelativeTo(null);
        successFrame.setVisible(true);
    }
}
