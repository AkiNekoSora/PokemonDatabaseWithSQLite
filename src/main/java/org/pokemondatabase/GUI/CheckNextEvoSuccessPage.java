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
 * Class Name: Check Next Evolution Success Page
 * Purpose: Used to display the success results from Checking the next evolution
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class CheckNextEvoSuccessPage {
    private final JLayeredPane pane;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper and prints the success text
     * Parameters: MainMenuPage, successText
     */
    public CheckNextEvoSuccessPage(MainMenuPage mainApp, String successText) {
        GuiHelper helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("CHECK NEXT EVOLUTION RESULTS",  "/background.jpg");

        // BUILDS THE SUCCESS LABEL WITH AN IMAGE
        helper.addLabelWithImage(successText, 278, 260, 600, 250);

        // BUILDS NEXT BUTTON AND HANDLES WHEN IT IS SELECTED
        JButton nextButton = helper.addSmallButton("NEXT", 800, 650);
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
