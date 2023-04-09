package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Represents an Image Panel that sets the background image properly for the window
public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
