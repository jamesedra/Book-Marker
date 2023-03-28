package ui;

import model.Book;
import model.BookList;
import model.Rating;
import persistence.Reader;
import persistence.Writer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BookMarkerGUI extends JFrame {

    // Buttons
    JButton openLibraryButton = new JButton("Open Library");
    JButton addBooksButton = new JButton("Add Book");
    JButton removeBooksButton = new JButton("Remove Book");
    JButton searchButton = new JButton("Search for Books");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");
    JButton quitButton = new JButton("Quit");

    // Colors
    static Color textColor = new Color(24, 24, 20);
    static Color bgColor = new Color(80, 41, 28);
    static Color buttonColor = new Color(234, 186, 65);

    // Frame
    JFrame frame = new JFrame();

    // Panels
    JPanel toolPanel = new JPanel();

    // Data
    protected static Book book;
    protected static BookList bookList;
    protected static Rating rating;
    private static final String JSON_STORE = "./data/bookList.json";
    private Writer writer;
    private Reader reader;

    // Splash screen
    private JWindow splashScreen;

    public BookMarkerGUI() {
        initializeSplashScreen();
        initializeButtons();
        initializePanels();
        initializeFrame();
        initializeData();
        // close splash screen
        splashScreen.dispose();
    }

    public void initializeSplashScreen() {
        splashScreen = new JWindow();
        JLabel splashLabel = new JLabel(new ImageIcon("src/splashScreen1.gif"));
        splashScreen.add(splashLabel);
        splashScreen.pack();
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);

        // improve image rendering quality
        Graphics2D g = (Graphics2D)splashScreen.getRootPane().getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // simulate loading time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initializeData() {
        bookList = new BookList("My book list");
        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
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
        button.addActionListener(new ButtonListener());
    }

    public void initializeButtons() {
        initializeButton(openLibraryButton, "Open Library", 20, 50,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(addBooksButton, "Add Book", 20, 110,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(removeBooksButton, "Remove Book", 20, 170,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(searchButton, "Search for Books", 20, 230,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(loadButton, "Load", 20, 290,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(saveButton, "Save", 100, 290,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(quitButton, "Quit", 20, 350,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
    }

    public void initializeFrame() {
        frame.setTitle("Book Marker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setResizable(false);

        // Load the background image
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create an ImagePanel with the background image
        ImagePanel contentPane = new ImagePanel(backgroundImage);
        contentPane.setLayout(null);

        // Add your components to the ImagePanel
        contentPane.add(toolPanel);

        toolPanel.setLayout(null);
        toolPanel.add(openLibraryButton);
        toolPanel.add(addBooksButton);
        toolPanel.add(removeBooksButton);
        toolPanel.add(searchButton);
        toolPanel.add(saveButton);
        toolPanel.add(loadButton);
        toolPanel.add(quitButton);

        // Add the content pane to the frame
        frame.add(contentPane);

        // Make the frame visible
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes JPanels used in the main menu
    public void initializePanels() {
        toolPanel.setBackground(new Color(0, 0, 0, 0));
        toolPanel.setBounds(0, 0, 250, 480);
        toolPanel.setOpaque(false);
    }


    // Represents the listener of the buttons in the main menu of the GUI
    class ButtonListener implements ActionListener {

        // EFFECTS: performs appropriate actions after buttons are clicked
        // DISCLAIMER: method structure based on TellerApp:
        // https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Open Library")) {
                openLibraryAction();
            } else if (e.getActionCommand().equals("Add Book")) {
                addBookAction();
            } else if (e.getActionCommand().equals("Remove Book")) {
                removeBookAction();
            } else if (e.getActionCommand().equals("Search for Books")) {
                searchBooksAction();
            } else if (e.getActionCommand().equals("Save")) {
                saveAction();
            } else if (e.getActionCommand().equals("Load")) {
                loadAction();
            } else if (e.getActionCommand().equals("Quit")) {
                quitAction();
            } else {
                System.out.println("Sorry! There is no such selection!");
            }
        }
    }

    public void openLibraryAction() {
        new OpenLibrary();
    }

    public void addBookAction() {
        new BookAdder();
    }

    public void removeBookAction() {

    }

    public void searchBooksAction() {

    }

    public void loadAction() {
        try {
            bookList = reader.read();
            new SuccessfulLoad();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void saveAction() {
        try {
            writer.open();
            writer.write(bookList);
            writer.close();
            new SuccessfulSave();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public void quitAction() {
        System.exit(0);
    }
}
