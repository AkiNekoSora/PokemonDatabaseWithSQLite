package org.pokemondatabase.GUI.CustomGUIDesigns;

import javax.swing.*;
import java.awt.*;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Custom Scroll Bar Arrow Button extends JButton
 * Purpose: Used to create a custom arrow for the drop-down and Pokémon List. Changes the button
 * direction based on the direction sent to the constructor.
 *          Contains:
 *              - Constructor
 *              - paintComponent
 *              - getPreferredSize
 */
class CustomScrollBarArrowButton extends JButton {
    private final int direction;
    private final Color arrowColor;

    // CONSTRUCTOR
    public CustomScrollBarArrowButton(int direction, Color arrowColor) {
        this.direction = direction;
        this.arrowColor = arrowColor;
        setOpaque(true);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    /* Method Name: paintComponent @OVERRIDE
     * Purpose: used to build the arrow direction.
     * Parameters: Graphics (To build the arrow on)
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        // ARROW BASE
        Graphics2D arrowBase = (Graphics2D) g.create();
        arrowBase.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        arrowBase.setColor(Color.WHITE);
        arrowBase.fillRect(0, 0, w, h);
        arrowBase.setColor(arrowColor);
        Polygon arrow = new Polygon();

        // ARROW LOCATION
        int centerX = w / 2;
        int centerY = h / 2;

        // Switch used to accept the direction and change the direction of the arrow based on it.
        // I only use 2 directions, but I thought having them all would be safer!
        switch (direction) {
            case SwingConstants.NORTH: // UP
                arrow.addPoint(centerX, centerY - 6);
                arrow.addPoint(centerX - 10, centerY + 4);
                arrow.addPoint(centerX + 10, centerY + 4);
                break;

            case SwingConstants.SOUTH: // DOWN
                arrow.addPoint(centerX, centerY + 6);
                arrow.addPoint(centerX - 10, centerY - 4);
                arrow.addPoint(centerX + 10, centerY - 4);
                break;

            case SwingConstants.WEST: // LEFT
                arrow.addPoint(centerX - 6, centerY);
                arrow.addPoint(centerX + 4, centerY - 10);
                arrow.addPoint(centerX + 4, centerY + 10);
                break;

            case SwingConstants.EAST: // RIGHT
                arrow.addPoint(centerX + 6, centerY);
                arrow.addPoint(centerX - 4, centerY - 10);
                arrow.addPoint(centerX - 4, centerY + 10);
                break;
        }

        arrowBase.fill(arrow);
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
        return new Dimension(40, 30);
    }

}
