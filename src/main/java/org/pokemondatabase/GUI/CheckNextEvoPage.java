package org.pokemondatabase.GUI;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.DBHelper.Types_DBHelper;
import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Check Next Evolution Page
 * Purpose: Used to let the user add a Pokédex number and the current level. Calculates the
 * results. Then sends the results and changes to the success page.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - handleSubmission - handles the processes for the user input file
 *              - IsDigit
 *              - getMainPanel - returns the main panel for this page
 */
public class CheckNextEvoPage extends JFrame {
    private final JLayeredPane pane;
    public List<Pokemon> pokemonDB;
    Pokemon_DBHelper pokemon_DBHelper = new Pokemon_DBHelper();
    GuiHelper helper;

    private JTextField PokedexNumberField;
    private JTextField currentLevelField;
    private JLabel errorLabelPokeNumber;
    private JLabel errorLabelCurrentLevel;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: MainMenuPage, List of Pokémon
     */
    public CheckNextEvoPage(MainMenuPage mainApp, List<Pokemon> pokemonStorage) {
        helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("CHECK NEXT POKÉMON EVOLUTION",  "/background.jpg");

        // POKÉDEX NUMBER LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Pokédex Number", 300, 300, 400);
        PokedexNumberField = helper.addTextField(300, 300, 400);
        errorLabelPokeNumber = helper.addErrorLabel(300, 300, 400);

        // CURRENT POKÉMON LEVEL LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Current Pokémon Level", 300, 390, 400);
        currentLevelField = helper.addTextField(300, 390, 400);
        errorLabelCurrentLevel = helper.addErrorLabel(300, 390, 400);

        // ADDS BACKGROUND
        helper.addTextBackgroundImage("/SuccessBox.png", 292, 270, 570, 220);

        // BUILDS BACK AND NEXT BUTTONS AND HANDLES WHEN THEY ARE SELECTED
        JButton backButton = helper.addSmallButton("BACK", 15, 680);
        JButton nextButton = helper.addSmallButton("NEXT", 805, 680);

        backButton.addActionListener(e -> {
            mainApp.goToPage(mainApp.getMainMenuLayeredPane());
        });
        nextButton.addActionListener(e -> handleSubmission(mainApp));
    }

    /* Method Name: handleSubmission
     * Purpose: Obtains all entered information, checks for any errors, and returns errors or
     * moves to the success page.
     * Parameters: MainMenuPage panel
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    private void handleSubmission(MainMenuPage mainApp) {
        errorLabelPokeNumber.setText("");
        errorLabelCurrentLevel.setText("");
        boolean hasErrors = false;

        // Starts Results HTML
        StringBuilder pokemonEvolutionResults = new StringBuilder();
        pokemonEvolutionResults.append("<html><body style='text-align:center; " +
                "width:350px; font-size:20pt;'>");

        ArrayList<ArrayList<Object>> selectPokemonList = null;
        Pokemon selectPokemon = null;
        int pokedexNumberInt = 0;
        int currentLevelInt = 0;

        // GETS ALL ENTERED INFORMATION
        String pokedexNumber = PokedexNumberField.getText().trim();
        String currentLevel = currentLevelField.getText().trim();

        //POKÉDEX NUMBER ERROR CHECKER
        if (pokedexNumber.isEmpty()) {
            errorLabelPokeNumber.setText("Pokédex Number Required.");
            hasErrors = true;
        } else if (!helper.isDigit(pokedexNumber)) {
            errorLabelPokeNumber.setText("Letter and Spaces Not Allowed.");
            hasErrors = true;
        } else if(helper.isDigit(pokedexNumber)) {
            pokedexNumberInt = Integer.parseInt(pokedexNumber);

            selectPokemonList = pokemon_DBHelper.select("*",
            "pokedex_number", String.valueOf(pokedexNumberInt), null, null);
            if (selectPokemonList != null && !selectPokemonList.isEmpty()) {
                selectPokemon = (helper.convertToPokemonList(selectPokemonList)).get(0);
            } else {
                errorLabelPokeNumber.setText("No Pokémon Exists with this Pokédex Number");
                hasErrors = true;
            }
        }

        //CURRENT EVOLUTION LEVEL ERROR CHECKER
        if (currentLevel.isEmpty()) {
            errorLabelCurrentLevel.setText("Current Level Required.");
            hasErrors = true;
        }
        else if (!helper.isDigit(currentLevel)) {
            errorLabelCurrentLevel.setText("Letter and Spaces Not Allowed.");
            hasErrors = true;
        } else {
            currentLevelInt = Integer.parseInt(currentLevel);

            if (currentLevelInt <= 0 || currentLevelInt > 100) {
                errorLabelCurrentLevel.setText("Current Level must be between 1 and 100.");
                hasErrors = true;
            }
        }

        // CONTINUE IF NO ERRORS FOUND
        if (!hasErrors) {
            Integer nextEvolutionLevel = selectPokemon.getNextEvolutionLevel();

            // Checks if the Pokémon has a next Evolution
            if (nextEvolutionLevel == null || nextEvolutionLevel == 0) {
                pokemonEvolutionResults.append("<h2 style='font-size:35pt;'>").append
                        ("Pokémon does not have a next evolution level!</h2></body></html>");
            } else {
                pokemonEvolutionResults.append("<h2 style='font-size:35pt;'>NEXT EVOLUTION " +
                        "RESULTS:</h2>");

                // Compares current level versus next evolution level and adds to result string
                if (currentLevelInt >= nextEvolutionLevel) {
                    pokemonEvolutionResults.append("Current level " +
                            "exceeds or equals the next evolution level!</body></html>");
                } else {
                    int evolutionLevelDiff = nextEvolutionLevel - currentLevelInt;
                    String isOrAre = evolutionLevelDiff == 1 ? " is " : " are ";

                    pokemonEvolutionResults.append("There ").append(isOrAre)
                            .append(evolutionLevelDiff).append(" evolution levels until ")
                            .append(selectPokemon.getPokemonName()).append(" evolves.<br>")
                            .append(selectPokemon.getPokemonName()).append(" evolves at level: ")
                            .append(selectPokemon.getNextEvolutionLevel()).append("</body></html>");
                }
            }

            // Sends success text and goes to success page
            CheckNextEvoSuccessPage checkNextEvoSuccessPage =
                    new CheckNextEvoSuccessPage(mainApp, pokemonEvolutionResults.toString());
            mainApp.goToPage(checkNextEvoSuccessPage.getMainPanel());

        }
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
