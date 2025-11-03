package org.pokemondatabase.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Choose DB Page
 * Purpose: Used to let the user choose what database they would like to connect to.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class ChooseDBPage extends JFrame {
    private final JLayeredPane pane;
    private GuiHelper helper;

    private final JButton fileChooserButton;
    private final JLabel errorFilePath;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: Main App
     */
    public ChooseDBPage(MainMenuPage mainApp) {
        helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("CHOOSE DATABASE",  "/background.jpg");

        // BUILDS FILE CHOOSE BUTTON AND ERROR LABELS
        fileChooserButton = helper.addLargeButton("CHOOSE DATABASE", 200, 340);
        errorFilePath = helper.addErrorLabel(200, 430, 600);

        // ACTION WHEN FILE CHOOSE BUTTON IS SELECTED
        fileChooserButton.addActionListener((ActionEvent event) -> {
            errorFilePath.setText("");

            // Opens the file chooser
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            int returnVal = fileChooser.showOpenDialog(null);

            // If the user attempts to add a file: get the file location, call addPokemonFrom File
            // GUI using the path.
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Boolean successfulDatabaseConnection = true;
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();

                if (path.endsWith(".db") || path.endsWith(".sqlite")) {
                    try {
                        mainApp.db.connect(path);
                    } catch (Exception e) {
                        errorFilePath.setText("Database connection failed");
                        successfulDatabaseConnection = false;
                    }
                } else {
                    errorFilePath.setText("Not a valid database. Please choose a database file.");
                    successfulDatabaseConnection = false;
                }

                if (successfulDatabaseConnection) {
                    mainApp.goToPage(mainApp.getMainMenuLayeredPane());
                }
            }
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
