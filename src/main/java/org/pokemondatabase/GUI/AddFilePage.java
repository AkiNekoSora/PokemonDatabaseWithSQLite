package org.pokemondatabase.GUI;

import java.awt.Container;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import org.pokemondatabase.PokemonManager;
import org.pokemondatabase.ValidationResults;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Add File Page
 * Purpose: Used to create the file page for the GUI. Allows the user to add a file to the system
 * and sorts through the lines to enter Pokémon into the database. Returns errors to the screen
 * if there are any issues.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - handleSubmission - handles the processes for the user input file
 *              - getMainPanel - returns the main panel for this page
 */
public class AddFilePage extends JFrame {
    private final PokemonManager pokemonManager = new PokemonManager();
    private final JLayeredPane pane;
    private final JButton fileChooserButton;
    private final JLabel errorFilePath;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: MainMenuPage, List of Pokémon
     */
    public AddFilePage(MainMenuPage mainApp) {
        GuiHelper helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("ADD POKÉMON", "/background.jpg");

        // BUILD TEXT BOX WITH TXT FILE INFORMATION
        helper.addLabelWithImage(getCorrectTxtFormat(), 278, 100, 600, 360);

        // BUILDS FILE CHOOSE BUTTON AND ERROR LABELS
        fileChooserButton = helper.addLargeButton("CHOOSE FILE", 200, 470);
        errorFilePath = helper.addErrorLabel(200, 558, 600);

        // ACTION WHEN FILE CHOOSE BUTTON IS SELECTED
        fileChooserButton.addActionListener(e -> handleSubmission(mainApp));

        // BUILDS BACK BUTTON AND HANDLES WHEN IT IS SELECTED
        JButton backButton = helper.addSmallButton("BACK", 15, 680);
        backButton.addActionListener(e -> {
            mainApp.goToPage(new FileOrManualAddPage(mainApp).getMainPanel());
        });
    }

    /* Method Name: handleSubmission
     * Purpose: Opens the dialogue box for the user to select, calls the addPokemonFromFileGUI
     * method in the pokemonManager (goes through the lines of the file and adds the Pokémon to
     * the list, returns an error message if failed, and the line number), if any errors occur it
     *  shows the error on the screen, if successful it goes to the AddFileSuccessPage.
     * Parameters: MainMenuPage panel
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    private void handleSubmission(MainMenuPage mainApp) {
        errorFilePath.setText("");

        // Opens the file chooser
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        int returnVal = fileChooser.showOpenDialog(null);

        // If the user attempts to add a file: get the file location, call addPokemonFrom File
        // GUI using the path.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();

            ValidationResults fileAddResponse = pokemonManager.addPokemonFromFileGUI(path, mainApp);

            if (fileAddResponse != null) {
                // Checks to make sure the addPokemonFromFileGUI was successful and then send it
                // to the success
                if (fileAddResponse.getIsSuccess()) {
                    AddFileSuccessPage fileOrManualScreen = new AddFileSuccessPage(mainApp,
                            fileAddResponse.getResultString());
                    mainApp.goToPage(fileOrManualScreen.getMainPanel());
                } else {
                    errorFilePath.setText(fileAddResponse.getResultString());
                }
            } else {
                errorFilePath.setText("File not added.");
            }
        }
    }

    /* Method Name: getCorrectTxtFormat
     * Purpose: Returns the text for how to format the .txt file.
     * Parameters: None
     * Return Value: String
     */
    public String getCorrectTxtFormat() {
        return ("<html>"
                + "<body style='width:360px; font-family:\"SH Pinscher Regular\"; font-size:13pt;'>"
                + "<span style='width:400px; font-family:\"SH Pinscher Regular\"; font-size:16pt;'>"
                + "<b>How the .txt file needs to be formatted:</b></span><br>"
                + "<ul style=\"margin-left:10px; padding-left:10px;\">"
                + "<li>Each line must include <b>7 to 8 variables</b> for each Pokémon.</li>"
                + "<li>The order must be:"
                + "<ol type='1' style=\"margin-left:10px; padding-left:10px;\">"
                + "<li><b>Pokémon Name:</b> String</li>"
                + "<li><b>Pokédex Number:</b> Integer between 1 and 1164</li>"
                + "<li><b>Pokémon Type:</b> String — if there are 2 types, use a \"/\"</li>"
                + "<li><b>Next Evolution Level:</b> Integer between 1 and 100 (use \"0\" if final evolution)</li>"
                + "<li><b>Weight:</b> BigDecimal</li>"
                + "<li><b>Height:</b> BigDecimal</li>"
                + "<li><b>Caught:</b> 'y' or 'n'</li>"
                + "<li><b>Pokédex Entry:</b> Optional String — do not add a \"|\" if omitted</li>"
                + "</ol>"
                + "</li>"
                + "<li>Only <b>one Pokémon</b> per line.</li>"
                + "<li>Each variable must be separated by a \"|\"</li"
                + "</ul>"
                + "<b>Examples:</b>"
                + "<ul style='margin-left:10px; padding-left:10px;'>"
                + "<li><code style='font-family: \"SH Pinscher Regular\"; font-size:13pt;'>"
                + "Charmander|4|Fire|16|18.7|0.6|y|The flame on its tail shows the strength of its life-force.</code></li>"
                + "<li><code style='font-family: \"SH Pinscher Regular\"; font-size:13pt;'>"
                + "Mewtwo|105|Psychic|0|122|2.0|n</code></li>"
                + "</ul>"
                + "</body></html>");
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
