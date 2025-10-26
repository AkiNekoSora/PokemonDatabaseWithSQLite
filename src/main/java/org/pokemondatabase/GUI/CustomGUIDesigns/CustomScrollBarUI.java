package org.pokemondatabase.GUI.CustomGUIDesigns;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Custom Scroll Bar UI
 * Purpose: Used to create a custom scroll bar and the track for the lists.
 *          Contains:
 *              - configureScrollBarColors
 *              - paintThumb
 *              - getPreferredSize
 *              - paintTrack
 *              - createDecreaseButton
 *              - createIncreaseButton
 */
public class CustomScrollBarUI extends BasicScrollBarUI {
    public final Color pokemonBlue = new Color(88, 112, 248);
    public final Color pokemonDarkBlue = new Color(42, 61, 172);

    private final int scrollBarCorners = 12;

    /* Method Name: configureScrollBarColors @OVERRIDE
     * Purpose: used to detail the colors of the scroll bar and track
     * Parameters: None
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    @Override
    protected void configureScrollBarColors() {
        thumbColor = pokemonBlue;
        trackColor = Color.WHITE;
        thumbDarkShadowColor = pokemonDarkBlue;
        thumbHighlightColor = pokemonBlue;
        thumbLightShadowColor = pokemonDarkBlue;
    }

    /* Method Name: paintThumb @OVERRIDE
     * Purpose: used to detail the colors of the scroll bar and track
     * Parameters: Graphics, JComponent, scrollBarBounds
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle scrollBarBounds) {
        // Creates scroll bar base
        Graphics2D scrollBarBase = (Graphics2D) g.create();
        scrollBarBase.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        scrollBarBase.setColor(thumbColor != null ? thumbColor : Color.GRAY);

        // SCROLL BAR RECTANGLE
        scrollBarBase.fillRoundRect(
                scrollBarBounds.x,
                scrollBarBounds.y,
                scrollBarBounds.width,
                scrollBarBounds.height,
                scrollBarCorners,
                scrollBarCorners
        );

        scrollBarBase.dispose();
    }

    /* Method Name: getPreferredSize @OVERRIDE
     * Purpose: used to override the getPreferredSize method to specify the size I would prefer
     * the arrow to be.
     * Parameters: None
     * Return: Dimensions
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(24, super.getPreferredSize(c).height);
        } else {
            return new Dimension(super.getPreferredSize(c).width, 24);
        }
    }

    /* Method Name: paintTrack @OVERRIDE
     * Purpose: used to override the getPreferredSize method to specify the size and design of
     * the scroll bar track.
     * Parameters: Graphics, JComponent, trackBounds
     * Return: void (I am hoping it is okay to return void for the design side!)
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // Creates scroll bar track base
        Graphics2D trackBoundsBase = (Graphics2D) g.create();
        trackBoundsBase.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        trackBoundsBase.setColor(Color.WHITE);

        // Used to keep the entire track one color
        trackBoundsBase.fillRoundRect(
                trackBounds.x,
                trackBounds.y,
                trackBounds.width,
                trackBounds.height,
                0,
                0
        );

        trackBoundsBase.dispose();
    }

    /* Method Name: createIncreaseButton @OVERRIDE
     * Purpose: used to override the createIncreaseButton and calls the
     * CustomScrollBarArrowButton with the up direction
     * Parameters: orientation
     * Return: JButton
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new CustomScrollBarArrowButton(orientation, pokemonBlue);
    }

    /* Method Name: createDecreaseButton @OVERRIDE
     * Purpose: used to override the createDecreaseButton and calls the
     * CustomScrollBarArrowButton with the down direction
     * Parameters: orientation
     * Return: JButton
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new CustomScrollBarArrowButton(orientation, pokemonBlue);
    }
}
