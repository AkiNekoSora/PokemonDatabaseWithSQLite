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
 * Class Name: Add File Success Page
 * Purpose: Used to display the success results from uploading a file with Pokémon
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class AddFileSuccessPage {
    private final JLayeredPane pane;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: MainMenuPage, lineCounter as a string
     */
    public AddFileSuccessPage(MainMenuPage mainApp, String successfulLines) {
        GuiHelper helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("POKÉMON ADDED",  "/background.jpg");

        String successText = "<html><body style='width:340px; text-align:center;'>"
                + "<span style='font-size:50pt'>"
                + "SUCCESS!<br>YOUR POKÉMON WERE <br>ADDED TO THE DATABASE</span><br><br>"
                + "Number of Pokémon added: " + successfulLines
                + "</body></html>";
        helper.addLabelWithImage(successText, 278, 200, 600, 300);

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
