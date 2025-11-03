package org.pokemondatabase.GUI;

import java.awt.Container;

import javax.swing.*;

import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Delete Success Page
 * Purpose: Used to display the success results from deleting a Pokémon from the system
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class DeleteSuccessPage extends JPanel {
    private final JLayeredPane pane;
    private GuiHelper helper;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper and prints the success text
     * Parameters: MainMenuPage
     */
    public DeleteSuccessPage(MainMenuPage mainApp) {
        helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("DELETION COMPLETED",  "/background.jpg");

        // BUILDS THE SUCCESS LABEL WITH AN IMAGE
        String successText = "<html><body style='width:345px; text-align:center; font-size:35pt;'>"
                + "POKEMON SUCCESSFULLY DELETED!</body></html>";
        helper.addLabelWithImage(successText, 280, 280, 600, 200);

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
