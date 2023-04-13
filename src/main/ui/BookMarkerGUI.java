package ui;

import model.*;
import model.Event;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Book Marker Application with java swing GUI
public class BookMarkerGUI extends JFrame {

    // Frame
    JFrame frame = new JFrame();

    // Panel
    JPanel toolPanel = new JPanel();

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

    // Data
    protected static Book book;
    protected static BookList bookList;
    protected static Rating rating;
    private static final String JSON_STORE = "./data/bookList.json";
    private Writer writer;
    private Reader reader;

    // Splash screen
    private JWindow splashScreen;

    // EFFECTS: runs the Book Marker application
    public BookMarkerGUI() {
        initializeSplashScreen();
        initializeButtons();
        initializePanels();
        initializeFrame();
        initializeData();
        // close splash screen
        splashScreen.dispose();
        EventLog.getInstance().clear();
    }

    // MODIFIES: this
    // EFFECTS: initializes a JWindow for a splash screen before the main menu
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
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the data
    public void initializeData() {
        bookList = new BookList("My book list");
        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JButtons
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

    // MODIFIES: this
    // EFFECTS: initializes the JButtons required for the main window
    public void initializeButtons() {
        initializeButton(openLibraryButton, "Open Library", 20, 50,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(addBooksButton, "Add Book", 20, 110,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(removeBooksButton, "Remove Book", 20, 170,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(searchButton, "Search for Books", 20, 230,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, Color.GRAY);
        initializeButton(loadButton, "Load", 20, 290,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(saveButton, "Save", 100, 290,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
        initializeButton(quitButton, "Quit", 20, 350,
                new Font("Times New Roman", Font.PLAIN, 17), textColor, buttonColor);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JFrames for the main window
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void initializeFrame() {
        frame.setTitle("Book Marker");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                quitAction();
            }
        });
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
        // DISCLAIMER: this method is based on TellerApp:
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

    // MODIFIES: this
    // EFFECTS: opens a new window to view the book list
    public void openLibraryAction() {
        new OpenLibrary();
    }

    // MODIFIES: this
    // EFFECTS: opens a new window to prompt the user to add a book/s
    public void addBookAction() {
        new BookAdder();
    }

    // MODIFIES: this
    // EFFECTS: opens a new window to prompt the user to remove a book/s
    public void removeBookAction() {
        new BookRemover();
    }

    public void searchBooksAction() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: loads a save data of a book list
    //          a success message will be shown if the data was loaded correctly
    public void loadAction() {
        try {
            bookList = reader.read();
            EventLog.getInstance().clear();
            new SuccessfulLoad();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current book list
    //          a success message will be shown if the data was saved correctly
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

    // EFFECTS: terminates the program
    public void quitAction() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString() + "\n");
        }
        System.exit(0);
    }
}
