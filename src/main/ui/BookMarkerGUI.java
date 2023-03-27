package ui;

import javax.swing.*;
import java.awt.*;

public class BookMarkerGUI extends JFrame {

    // Buttons
    JButton openLibraryButton = new JButton("Open Library");
    JButton addBooksButton = new JButton("Add Books");
    JButton removeBooksButton = new JButton("Remove Books");
    JButton searchButton = new JButton("Search");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");
    JButton quitButton = new JButton("Quit");

    // Colors
    Color white = new Color(255, 255, 255);
    Color grey = new Color(55, 55, 55);
    Color buttonColor = new Color(234, 186, 65);

    // Frame
    JFrame frame = new JFrame();

    // Label
    //JLabel logo = new JLabel();
    //JLabel background = new JLabel(new ImageIcon("backgroundStub.png"));

    // Panels
    //JPanel backGroundPanel = new JPanel();
    JPanel toolPanel = new JPanel();


    public BookMarkerGUI() {
        initializeButtons();
        initializePanels();
        initializeFrame();
    }

    public void initializeButton(JButton button, String text, int x, int y,
                                  Font font, Color foreground, Color background) {
        button.setText(text);
        if (text == "Save" || text == "Load") {
            button.setBounds(x, y, 70, 50);
        } else {
            button.setBounds(x, y, 150, 50);
        }
        button.setFont(font);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setForeground(foreground);
        button.setBackground(background);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setActionCommand(text);
        //button.addActionListener(new ButtonListener());
    }

    public void initializeButtons() {
        initializeButton(openLibraryButton, "Open Library", 20, 50,
                new Font("Times New Roman", Font.PLAIN, 17), white, buttonColor);
        initializeButton(addBooksButton, "Add Books", 20, 110,
                new Font("Times New Roman", Font.PLAIN, 17), white, buttonColor);
        initializeButton(removeBooksButton, "Remove Books", 20, 170,
                new Font("Times New Roman", Font.PLAIN, 17), white, buttonColor);
        initializeButton(searchButton, "Search", 20, 230,
                new Font("Times New Roman", Font.PLAIN, 17), white, buttonColor);
        initializeButton(saveButton, "Save", 20, 290,
                new Font("Times New Roman", Font.PLAIN, 17), white, buttonColor);
        initializeButton(loadButton, "Load", 100, 290,
                new Font("Times New Roman", Font.PLAIN, 17), white, buttonColor);
        initializeButton(quitButton, "Quit", 20, 350,
                new Font("Times New Roman", Font.PLAIN, 17), white, buttonColor);
    }

    public void initializeFrame() {
        frame.setTitle("Book Marker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setSize(640, 480);
        frame.setResizable(false);

        JLabel background = new JLabel(new ImageIcon("backgroundStub.png"));
        background.setSize(frame.getContentPane().getSize());
        background.setLocation(0, 0);
        frame.getContentPane().add(background);
        frame.setVisible(true);

        toolPanel.setLayout(null);
        toolPanel.add(openLibraryButton);
        toolPanel.add(addBooksButton);
        toolPanel.add(removeBooksButton);
        toolPanel.add(searchButton);
        toolPanel.add(saveButton);
        toolPanel.add(loadButton);
        toolPanel.add(quitButton);
        frame.add(toolPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes JPanels used in the main menu
    public void initializePanels() {
        toolPanel.setBackground(new Color(0, 0, 0, 0));
        toolPanel.setBounds(0, 0, 250, 480);
        toolPanel.setOpaque(false);
    }

}
