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

import static ui.BookMarkerGUI.bookList;

// Represents the window for showing the book list
public class OpenLibrary extends JFrame implements ActionListener {
    JList<String> bookJList;
    JScrollPane bookScrollPane;
    JPanel buttonPanel;
    JButton viewBook;
    JButton editBook;

    // MODIFIES: this
    // EFFECTS: runs the Open Library window
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public OpenLibrary() {
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
        this.setTitle("Your Library");
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the JButtons
    public void setButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        viewBook = new JButton("View Book");
        editBook = new JButton("Edit Book");
        viewBook.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        editBook.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        viewBook.setOpaque(true);
        viewBook.setBorderPainted(true);
        editBook.setOpaque(true);
        editBook.setBorderPainted(true);

        viewBook.setForeground(BookMarkerGUI.textColor);
        viewBook.setBackground(BookMarkerGUI.buttonColor);
        viewBook.setBorder(BorderFactory.createEtchedBorder());

        viewBook.setActionCommand("view");
        viewBook.addActionListener(this);

        editBook.setForeground(BookMarkerGUI.textColor);
        editBook.setBackground(Color.GRAY);
        editBook.setBorder(BorderFactory.createEtchedBorder());

        buttonPanel.add(viewBook);
        buttonPanel.add(editBook);
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
        if (e.getActionCommand().equals("view")) {
            viewBookAction();
        }
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void viewBookAction() {
        String selectedTitle = bookJList.getSelectedValue();
        if (selectedTitle != null) {
            for (Book book : bookList.viewBookList()) {
                if (book.getTitle().equals(selectedTitle)) {
                    // create new JFrame to display book details
                    JFrame bookDetailsFrame = new JFrame(selectedTitle);
                    bookDetailsFrame.setSize(400, 150);
                    bookDetailsFrame.setLocationRelativeTo(null);

                    // ------------------------------------------------------------------
                    // Creates a panel that adds the details of the book
                    JPanel detailsPanel = new JPanel(new GridBagLayout());
                    detailsPanel.setOpaque(true);
                    detailsPanel.setBackground(BookAdder.saffron);

                    // create GridBagConstraints with default values
                    GridBagConstraints gbc = new GridBagConstraints();

                    // add title label to the left column
                    JLabel titleLabel = new JLabel(" Title:");
                    titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.WEST; // align label to left side
                    detailsPanel.add(titleLabel, gbc);

                    // add title value label to the right column
                    JLabel titleValueLabel = createJLabelForViewingBooks(gbc, book.getTitle(),
                            1, 0, 1.0, false);
                    detailsPanel.add(titleValueLabel, gbc);

                    // add author label to the left column
                    JLabel authorLabel = createJLabelForViewingBooks(gbc, " Author: ",
                            0, 1, 0.0, true);
                    detailsPanel.add(authorLabel, gbc);

                    // add author value label to the right column
                    JLabel authorValueLabel = createJLabelForViewingBooks(gbc, book.getAuthor(),
                            1, 1, 1.0, false);
                    detailsPanel.add(authorValueLabel, gbc);

                    // add year published label to the left column
                    JLabel dateLabel = createJLabelForViewingBooks(gbc, " Year: ",
                            0, 2, 0.0, true);
                    detailsPanel.add(dateLabel, gbc);

                    // add year published value label to the right column
                    JLabel dateValueLabel = createJLabelForViewingBooks(gbc, (String.valueOf(book.getDate())),
                            1, 2, 1.0, false);
                    detailsPanel.add(dateValueLabel, gbc);

                    // add rating label to the left column
                    JLabel ratingLabel = createJLabelForViewingBooks(gbc, " Rating: ",
                            0, 3, 0.0, true);
                    detailsPanel.add(ratingLabel, gbc);

                    // add rating value label to the right column
                    JLabel ratingValueLabel = createJLabelForViewingBooks(gbc, book.rateToStar(),
                            1, 3, 1.0, false);
                    detailsPanel.add(ratingValueLabel, gbc);

                    // add review label to the left column
                    JLabel reviewLabel = createJLabelForViewingBooks(gbc, " Review: ",
                            0, 4, 0.0, true);
                    detailsPanel.add(reviewLabel, gbc);

                    // add review value label to the right column
                    JLabel reviewValueLabel = createJLabelForViewingBooks(gbc, book.printReview(),
                            1, 4, 1.0, false);
                    detailsPanel.add(reviewValueLabel, gbc);
                    // ------------------------------------------------------------------

                    // add the details panel to the background panel
                    //backgroundPanel.add(detailsPanel, BorderLayout.CENTER);
                    bookDetailsFrame.add(detailsPanel);
                    bookDetailsFrame.setVisible(true);
                    break;
                }
            }
        }
    }
}
