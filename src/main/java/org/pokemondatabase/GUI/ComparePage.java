package org.pokemondatabase.GUI;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Compare Page
 * Purpose: Used to let the user add two Pokédex numbers. Calculates who is taller and heavier.
 * Then sends the results and changes to the success page.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - hideErrors - hides all error messages
 *              - handleSubmission - handles the processes for the user input file
 *              - IsDigit
 *              - getMainPanel - returns the main panel for this page
 */
public class ComparePage extends JFrame {
    private final JLayeredPane pane;
    public List<Pokemon> pokemonDB;
    Pokemon_DBHelper pokemon_DBHelper = new Pokemon_DBHelper();
    GuiHelper helper;

    private final JTextField firstPokedexNumberField;
    private final JTextField secondPokedexNumberField;
    private final JLabel errorLabelFirstPokeNumer;
    private final JLabel errorLabelSecondPokeNumber;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: MainMenuPage, List of Pokémon
     */
    public ComparePage(MainMenuPage mainApp, List<Pokemon> pokemonStorage) {
        this.pokemonDB = pokemonStorage;
        helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("COMPARE POKÉMON STATS",  "/background.jpg");

        // FIRST POKÉDEX NUMBER  LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("First Pokédex Number", 300, 300, 400);
        firstPokedexNumberField = helper.addTextField(300, 300, 400);
        errorLabelFirstPokeNumer = helper.addErrorLabel(300, 300, 400);

        // SECOND POKÉDEX NUMBER  LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Second Pokédex Number", 300, 390, 400);
        secondPokedexNumberField = helper.addTextField(300, 390, 400);
        errorLabelSecondPokeNumber = helper.addErrorLabel(300, 390, 400);

        // TEXT BOX IMAGE
        helper.addTextBackgroundImage("/SuccessBox.png", 292, 270, 570, 220);

        // BUILDS BACK AND NEXT BUTTONS AND HANDLES WHEN THEY ARE SELECTED
        JButton backButton = helper.addSmallButton("BACK", 15, 680);
        JButton nextButton = helper.addSmallButton("NEXT", 805, 680);

        backButton.addActionListener(e -> {
            mainApp.goToPage(mainApp.getMainMenuLayeredPane());
        });
        nextButton.addActionListener(e -> handleSubmission(mainApp));
    }

    private void handleSubmission(MainMenuPage mainApp) {
        errorLabelFirstPokeNumer.setText("");
        errorLabelSecondPokeNumber.setText("");
        boolean hasErrors = false;

        Pokemon firstPokemon = null;
        Pokemon secondPokemon = null;
        int firstPokedexNumberInt = 0;
        int secondPokedexNumberInt = 0;

        // GETS ALL ENTERED INFORMATION
        String firstPokedexNumber = firstPokedexNumberField.getText().trim();
        String secondPokedexNumber = secondPokedexNumberField.getText().trim();

        //FIRST POKÉDEX NUMBER ERROR CHECKER
        if (firstPokedexNumber.isEmpty()) {
            errorLabelFirstPokeNumer.setText("First Pokédex Number Required.");
            hasErrors = true;
        } else if (!helper.isDigit(firstPokedexNumber)) {
            errorLabelFirstPokeNumer.setText("Letter and Spaces Not Allowed.");
            hasErrors = true;
        } else if(helper.isDigit(firstPokedexNumber)) {
            firstPokedexNumberInt = Integer.parseInt(firstPokedexNumber);
        }

        //SECOND POKÉDEX NUMBER ERROR CHECKER
        if (secondPokedexNumber.isEmpty()) {
            errorLabelSecondPokeNumber.setText("Second Pokédex Number Required.");
            hasErrors = true;
        } else if (!helper.isDigit(secondPokedexNumber)) {
            errorLabelSecondPokeNumber.setText("Letter and Spaces Not Allowed.");
            hasErrors = true;
        } else if(helper.isDigit(secondPokedexNumber)) {
            secondPokedexNumberInt = Integer.parseInt(secondPokedexNumber);
        } else if (firstPokedexNumberInt == secondPokedexNumberInt) {
            errorLabelSecondPokeNumber.setText("Pokédex numbers cannot be the same.");
            hasErrors = true;
        }

        // CONTINUE IF NO ERRORS FOUND
        if (!hasErrors) {
            ArrayList<ArrayList<Object>> selectPokemonList = pokemon_DBHelper.select("*",
                    "pokedex_number", String.valueOf(firstPokedexNumberInt), null, null);

            // VERIFIES BOTH POKÉMON EXIST
            if (selectPokemonList != null && !selectPokemonList.isEmpty()) {
                firstPokemon = (helper.convertToPokemonList(selectPokemonList)).get(0);
            } else {
                errorLabelFirstPokeNumer.setText("No Pokémon Exists with this Pokédex Number");
                hasErrors = true;
            }

            selectPokemonList = pokemon_DBHelper.select("*",
                    "pokedex_number", String.valueOf(secondPokedexNumberInt), null, null);
            if (selectPokemonList != null && !selectPokemonList.isEmpty()) {
                secondPokemon = (helper.convertToPokemonList(selectPokemonList)).get(0);
            } else {
                errorLabelSecondPokeNumber.setText("No Pokémon Exists with this Pokédex Number");
                hasErrors = true;
            }

            // CONTINUE IF NO ERRORS FOUND
            if (!hasErrors) {
                // Get the weight and height of both Pokémon and subtract the first one from the second
                BigDecimal weightDiff =
                        firstPokemon.getPokemonWeightKilograms().subtract(secondPokemon.getPokemonWeightKilograms());
                BigDecimal heightDiff =
                        firstPokemon.getPokemonHeightMeters().subtract(secondPokemon.getPokemonHeightMeters());

                // Starts HTML the results Compare
                StringBuilder pokemonComparisonResults = new StringBuilder();
                pokemonComparisonResults.append("<html><body style='text-align:center; " +
                        "width:340px; font-size:20pt;'>");
                pokemonComparisonResults.append("<h2 style='font-size:35pt;'>FINAL POKÉMON <br>" +
                        "COMPARISON RESULTS:</h2>");

                // WEIGHT COMPARISON
                if (weightDiff.signum() < 0) {
                    pokemonComparisonResults.append(secondPokemon.getPokemonName()).append(" is heavier " +
                                    "than ").append(firstPokemon.getPokemonName())
                            .append(" by ").append(weightDiff.abs()).append(" kilograms.<br>");
                } else if (weightDiff.signum() > 0) {
                    pokemonComparisonResults.append(firstPokemon.getPokemonName()).append(" is " +
                                    "heavier than ").append(secondPokemon.getPokemonName())
                            .append(" by ").append(weightDiff.abs()).append(" kilograms.<br>");
                } else {
                    pokemonComparisonResults.append("Both Pokémon are the same weight!<br>");
                }

                // HEIGHT COMPARISON
                if (heightDiff.signum() < 0) {
                    pokemonComparisonResults.append(secondPokemon.getPokemonName()).append(" is taller " +
                                    "than ").append(firstPokemon.getPokemonName())
                            .append(" by ").append(heightDiff.abs()).append(" meters.<br>");
                } else if (heightDiff.signum() > 0) {
                    pokemonComparisonResults.append(firstPokemon.getPokemonName()).append(" is taller" +
                                    " than ").append(secondPokemon.getPokemonName())
                            .append(" by ").append(heightDiff.abs()).append(" meters.</body></html>");
                } else {
                    pokemonComparisonResults.append("Both Pokémon are the same height!</body></html>");
                }

                // Sends success text and goes to success page.
                CompareSuccessPage compareSuccessPage = new CompareSuccessPage(mainApp,
                        pokemonComparisonResults.toString());
                mainApp.goToPage(compareSuccessPage.getMainPanel());
            }
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
