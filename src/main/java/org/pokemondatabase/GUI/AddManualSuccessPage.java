package org.pokemondatabase.GUI;

import java.awt.Container;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Add Manual Success Page
 * Purpose: Used to display the success results from adding a Pokémon using the GUI
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class AddManualSuccessPage {
    private final JLayeredPane pane;
    private GuiHelper helper;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper and prints the success text
     * Parameters: MainMenuPage, List of Pokémon, successText
     */
    public AddManualSuccessPage(MainMenuPage mainApp, String successText) {
        helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("POKÉMON SUCCESSFULLY ADDED", "/background.jpg");

        // BUILDS THE SUCCESS LABEL WITH AN IMAGE
        helper.addLabelWithImage(successText, 277, 250, 600, 350);

        // BUILDS NEXT BUTTON AND HANDLES WHEN IT IS SELECTED
        JButton nextButton = helper.addSmallButton("NEXT", 805, 680);
        nextButton.addActionListener(e -> {
            mainApp.goToPage(mainApp.getMainMenuLayeredPane());
        });
    }

    /* Method Name: getMainPanel
     * Purpose: used to return the main panel of this page
     * Parameters: NONE
     * Return Value: Container(panel)
     */
    public Container getMainPanel() {
        return pane;
    }
}
