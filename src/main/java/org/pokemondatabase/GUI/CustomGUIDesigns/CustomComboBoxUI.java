package org.pokemondatabase.GUI.CustomGUIDesigns;

import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Custom Combo Box UI
 * Purpose: Used to as the base to call all other custom design choices. (Scroll Lists)
 *          Contains:
 *              - createArrowButton (calls class)
 *              - createPopup
 */
public class CustomComboBoxUI extends BasicComboBoxUI {
    public final Color pokemonBlue = new Color(88, 112, 248);

    /* Method Name: createArrowButton @OVERRIDE
     * Purpose: used to call the customArrowButton class to create the arrow for the GUI. Sending
     *  the specified color.
     * Parameters: None
     * Return Value: JButton
     */
    @Override
    protected JButton createArrowButton() {
        JButton button = new CustomArrowButton(pokemonBlue);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);

        return button;
    }

    /* Method Name: createPopup @OVERRIDE
     * Purpose: Used to create the popup for the drop-down. Calls the CustomScrollBarUI and set the
     * scroller to the design specified and the background White. Contains createScroller method.
     * Parameters: None
     * Return Value: ComboPopup
     */
    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {
            /* Method Name: createScroller @OVERRIDE
             * Purpose: Calls the CustomScrollBarUI and set the scroller to the design specified
             * and the background White. Contains createScroller method.
             * Parameters: None
             * Return Value: JScrollPane
             */
            @Override
            protected JScrollPane createScroller() {
                JScrollPane scrollPane = super.createScroller();
                scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
                scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
                return scrollPane;
            }
        };
        return popup;
    }
}
