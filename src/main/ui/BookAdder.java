package ui;

import model.Book;
import model.Rating;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.BookMarkerGUI.book;
import static ui.BookMarkerGUI.bookList;
import static ui.BookMarkerGUI.rating;


public class BookAdder extends JFrame implements ActionListener {

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
    JButton addBookButton;

    static Color saffron = new Color(232,195,108);

    public BookAdder() {
        super("Add a Book");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new GridLayout(6, 2, 10, 10));
        // set the background color to yellow
        //getContentPane().setBackground(BookMarkerGUI.bgColor);

        setFields();
        addLabelsAndFields();
        add(new JLabel()); // Empty label for spacing

        addBookButton = new JButton("Add Book");
        addBookButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addBookButton.setOpaque(true);
        addBookButton.setBorderPainted(true);
        addBookButton.setForeground(BookMarkerGUI.textColor);
        addBookButton.setBackground(saffron);
        addBookButton.setBorder(BorderFactory.createEtchedBorder());
        addBookButton.setActionCommand("add");
        addBookButton.addActionListener(this);

        this.add(addBookButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JTextField createTextField(int width) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(width, 30));
        textField.setFont(new Font("Times New Roman", Font.BOLD, 15));
        textField.setForeground(BookMarkerGUI.textColor);
        textField.setBackground(saffron);
        return textField;
    }

    private JComboBox<Integer> createYearComboBox(int width) {
        JComboBox<Integer> comboBox = new JComboBox<>();
        Integer[] years = new Integer[104];
        for (int i = 0; i < years.length; i++) {
            years[i] = 1920 + i;
        }
        comboBox.setModel(new DefaultComboBoxModel<>(years));
        comboBox.setPreferredSize(new Dimension(width, 30));
        comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        comboBox.setForeground(Color.BLUE);
        return comboBox;
    }

    private void setFields() {
        titleLabel = createLabel("Title: ");
        bookTitle = createTextField(200);
        authorLabel = createLabel("Author:");
        bookAuthor = createTextField(200);
        yearLabel = createLabel("Year Published:");
        bookYear = createYearComboBox(200);
        ratingLabel = createLabel("Rating:");
        ratingSlider = new JSlider(0, 10);
        reviewLabel = createLabel("Review:");
        bookReview = createTextField(200);
    }

    private JLabel createLabel(String text) {
        JLabel temp = new JLabel(text);
        temp.setForeground(BookMarkerGUI.buttonColor);
        temp.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        return temp;
    }

    private void addLabelsAndFields() {
        add(titleLabel);
        add(bookTitle);
        add(authorLabel);
        add(bookAuthor);
        add(yearLabel);
        add(bookYear);
        add(ratingLabel);
        add(createRatingPanel());
        add(reviewLabel);
        add(bookReview);
    }

    private JPanel createRatingPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(ratingSlider);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPreferredSize(new Dimension(170, 50));
        return panel;
    }

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

    public void successMessage() {
        JFrame successFrame = new JFrame("Success");
        successFrame.setPreferredSize(new Dimension(200, 100));
        JLabel successLabel = new JLabel("Book added successfully");
        successLabel.setHorizontalAlignment(JLabel.CENTER);
        successFrame.add(successLabel);
        successFrame.pack();
        successFrame.setLocationRelativeTo(null);
        successFrame.setVisible(true);
    }
}
