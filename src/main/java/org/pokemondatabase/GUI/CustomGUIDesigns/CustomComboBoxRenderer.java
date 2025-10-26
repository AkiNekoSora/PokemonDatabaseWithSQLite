package org.pokemondatabase.GUI.CustomGUIDesigns;

import javax.swing.*;
import java.awt.*;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Custom Combo Box Renderer - Extends DefaultListCellRenderer
 * Purpose: Used to change how the JList elements are displayed (Drop-Down and Pokémon List)
 *          Contains:
 *              - Override getListCellRendererComponent
 */
public class CustomComboBoxRenderer extends DefaultListCellRenderer {
    /* Method Name: getListCellRendererComponent @OVERRIDE
     * Purpose: used to override the getListCellRendererComponent method to which is called for
     * each list item to display them how it is specified below.
     * Parameters: JList, value, index, isSelected, cellHasFocus
     * Return Value: Component label
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        // Changes the colors based on if the item is selected
        if (isSelected) {
            label.setBackground(Color.RED);
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(Color.WHITE);
            label.setForeground(Color.DARK_GRAY);
        }

        return label;
    }
}