package org.pokemondatabase.GUI.CustomGUIDesigns;

import javax.swing.*;
import java.awt.*;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Custom Arrow Button
 * Purpose: Used to create a custom arrow for the drop-down and Pokémon List. Makes the button
 * wider and bigger.
 *          Contains:
 *              - Constructor
 *              - paintComponent
 *              - getPreferredSize
 */
class CustomArrowButton extends JButton {
    private final Color arrowColor;

    // CONSTRUCTOR
    public CustomArrowButton(Color arrowColor) {
        this.arrowColor = arrowColor;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    /* Method Name: paintComponent @OVERRIDE
     * Purpose: used to build the arrow. Add color and detail how the arrow is shaped
     * Parameters: Graphics (To build the arrow on)
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        Graphics2D arrowBase = (Graphics2D) g.create();
        arrowBase.setColor(arrowColor);
        arrowBase.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Arrow Size
        int arrowWidth = 23;
        int arrowHeight = 14;

        // Arrow Location
        int xCenter = w / 2;
        int yCenter = h / 2;

        // Builds the Arrow
        // POINTS
        int[] xPoints = {
                xCenter - arrowWidth / 2, // LEFT
                xCenter + arrowWidth / 2, // RIGHT
                xCenter                   // BOTTOM
        };
        // SIDES
        int[] yPoints = {
                yCenter - arrowHeight / 2, // LEFT
                yCenter - arrowHeight / 2, // RIGHT
                yCenter + arrowHeight / 2  // BOTTOM
        };

        arrowBase.fillPolygon(xPoints, yPoints, 3);
        arrowBase.dispose();
    }

    /* Method Name: getPreferredSize @OVERRIDE
     * Purpose: used to override the getPreferredSize method to specify the size I would prefer
     * the arrow to be.
     * Parameters: None
     * Return: Dimensions
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(30, 25);
    }
}
