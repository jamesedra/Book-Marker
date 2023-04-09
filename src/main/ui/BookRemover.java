package ui;

import model.Book;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static ui.BookAdder.saffron;
import static ui.BookMarkerGUI.bookList;

// Represents the window for showing the book list
public class BookRemover extends JFrame implements ActionListener {
    JList<String> bookJList;
    JScrollPane bookScrollPane;
    JPanel buttonPanel;
    JButton removeBook;

    // MODIFIES: this
    // EFFECTS: runs the Open Library window
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public BookRemover() {
        DefaultListModel<String> bookListModel = new DefaultListModel<>();
        for (Book book : bookList.viewBookList()) {
            bookListModel.addElement(book.getTitle());
        }
        bookJList = new JList<>(bookListModel);
        bookJList.setOpaque(false);
        bookJList.setBackground(new Color(0, 0, 0, 0));
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("src/bookshelf.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImagePanel imagePanel = new ImagePanel(backgroundImage);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(bookJList);

        bookJList.setForeground(Color.WHITE);
        bookJList.setFont(new Font("Times New Roman", Font.BOLD, 15));

        bookScrollPane = new JScrollPane(imagePanel);
        bookScrollPane.getViewport().setOpaque(false);
        bookScrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));

        setButtons();

        this.setLayout(new BorderLayout());
        this.add(bookScrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setTitle("Remove a Book");
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the JButtons
    public void setButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        removeBook = new JButton("Remove Book");
        removeBook.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        removeBook.setOpaque(true);
        removeBook.setBorderPainted(true);

        removeBook.setForeground(BookMarkerGUI.textColor);
        removeBook.setBackground(BookMarkerGUI.buttonColor);
        removeBook.setBorder(BorderFactory.createEtchedBorder());

        removeBook.setActionCommand("remove");
        removeBook.addActionListener(this);

        buttonPanel.add(removeBook);
        buttonPanel.setBackground(BookMarkerGUI.bgColor);

    }

    // REQUIRES: text != null, gridx > 0, gridy > 0, weightx > 0, left != null
    // MODIFIES: this
    // EFFECTS: creates a proper JLabel depending on their column placement(left or right)
    public JLabel createJLabelForViewingBooks(GridBagConstraints gbc, String text, int gridx, int gridy,
                                              double weightx, boolean left) {
        JLabel temp = new JLabel(text);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx; // make this column fill available space
        if (left) {
            gbc.fill = GridBagConstraints.NONE; // reset fill to default value
        } else {
            gbc.fill = GridBagConstraints.HORIZONTAL; // fill available horizontal space
        }
        temp.setFont(new Font("Times New Roman", Font.BOLD, 14));
        return temp;
    }

    // EFFECTS: shows a new window for the details of the book
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("remove")) {
            String selectedTitle = bookJList.getSelectedValue();
            Book selectedBook = null;
            if (selectedTitle != null) {
                for (Book book : bookList.viewBookList()) {
                    if (book.getTitle().equals(selectedTitle)) {
                        selectedBook = book;
                        bookList.removeBook(selectedBook);
                        successMessage();
                        break;
                    }
                }
                if (selectedBook != null) {
                    // Convert the list of Book objects to a list of strings
                    ArrayList<String> bookTitles = new ArrayList<String>();
                    for (Book book : bookList.viewBookList()) {
                        bookTitles.add(book.getTitle());
                    }
                    // Set the list data with the updated list of book titles
                    bookJList.setListData(bookTitles.toArray(new String[0]));
                    // Refresh the JFrame
                    bookJList.repaint();
                    bookJList.revalidate();
                }
            }
        }
    }

    // EFFECTS: creates a new JFrame for the success message of removing a book
    public void successMessage() {
        JFrame successFrame = new JFrame("Success");
        successFrame.setPreferredSize(new Dimension(200, 100));
        JLabel successLabel = new JLabel("Book removed successfully");
        successLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        successLabel.setHorizontalAlignment(JLabel.CENTER);
        successFrame.getContentPane().setBackground(saffron);
        successFrame.add(successLabel);
        successFrame.pack();
        successFrame.setLocationRelativeTo(null);
        successFrame.setVisible(true);
    }
}
