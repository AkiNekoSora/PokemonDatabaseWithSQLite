package org.pokemondatabase.GUI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: File Or Manual Add Page
 * Purpose: Allows the user to choose how they want to add a Pokémon
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class FileOrManualAddPage extends JFrame {
    private final JLayeredPane pane;
    private GuiHelper helper;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: MainMenuPage, List of Pokémon
     */
    public FileOrManualAddPage(MainMenuPage mainApp) {
        helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("ADD POKÉMON",  "/background.jpg");

        // BUILDS MANUAL AND TEXT FILE BUTTONS
        JButton addPokemonManualButton = helper.addLargeButton("ADD POKÉMON - MANUALLY",
                200, 250);
        JButton addPokemonTextFileButton = helper.addLargeButton("ADD POKÉMON - FILE", 200, 400);

        // GOES TO MANUAL PAGE IF MANUAL BUTTON CLICKED
        addPokemonManualButton.addActionListener((ActionEvent e) -> {
            mainApp.goToPage(new AddManualPage(mainApp).getMainPanel());
        });

        // GOES TO TEXT FILE ADD PAGE IF TEXT FILE BUTTON CLICKED
        addPokemonTextFileButton.addActionListener((ActionEvent e) -> {
            mainApp.goToPage(new AddFilePage(mainApp).getMainPanel());
        });

        // BUILDS BACK BUTTON AND HANDLES WHEN IT IS SELECTED
        JButton backButton = helper.addSmallButton("BACK", 15, 680);
        backButton.addActionListener(e -> {
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
