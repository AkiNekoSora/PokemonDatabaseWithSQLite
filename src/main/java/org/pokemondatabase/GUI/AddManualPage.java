package org.pokemondatabase.GUI;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import org.pokemondatabase.*;
import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.DBHelper.Pokemon_Types_DBHelper;
import org.pokemondatabase.DBHelper.Types_DBHelper;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Add Manual Page
 * Purpose: Used to create the add manual page for the GUI. Allows the user to enter data for the
 * Pokémon and validates all information given. Returns errors to the screen if there are any issues.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - hideErrors - hides all error messages
 *              - handleSubmission - handles the processes for the user input file
 *              - Is Digit or Period
 *              - IsDigit
 *              - getMainPanel - returns the main panel for this page
 */
public class AddManualPage extends JFrame {
    private final JLayeredPane pane;
    private final PokemonManager pokemonManager = new PokemonManager();
    Pokemon_DBHelper pokemon_DBHelper = new Pokemon_DBHelper();
    Types_DBHelper types_DBHelper = new Types_DBHelper();
    public List<Pokemon> pokemonDB;

    private final JTextField pokemonNameField;
    private final JTextField pokedexNumberField;
    private final JComboBox<String> primaryTypeField;
    private final JComboBox<String> secondaryTypeField;
    private final JTextField pokemonNextEvolutionLevelField;
    private final JTextField weightField;
    private final JTextField heightField;
    private final JComboBox<String> pokemonIsCaughtField;
    private final JTextArea pokedexEntryField;

    private final JLabel errorLabelPokemonName;
    private final JLabel errorLabelPokedexNumber;
    private final JLabel errorLabelPokemonTypes;
    private final JLabel errorLabelNextEvo;
    private final JLabel errorLabelWeight;
    private final JLabel errorLabelHeight;
    private final JLabel errorLabelPokedexEntry;

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: MainMenuPage, List of Pokémon
     */
    public AddManualPage(MainMenuPage mainApp, List<Pokemon> pokemonStorage) {
        this.pokemonDB = pokemonStorage;
        GuiHelper helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("ADD POKÉMON", "/addPokemonPage.jpg");


        // POKÉMON NAME LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Pokémon Name", 300, 100, 400);
        pokemonNameField = helper.addTextField(300, 100, 400);
        errorLabelPokemonName = helper.addErrorLabel(300, 100, 400);


        // POKÉDEX NUMBER LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Pokédex Number", 300, 175, 400);
        pokedexNumberField = helper.addTextField(300, 175, 400);
        errorLabelPokedexNumber = helper.addErrorLabel(300, 175, 400);


        // POKÉMON TYPES - PRIMARY LABEL, DROPDOWN AND ERROR LABEL
        helper.addLabel("Primary Type", 300, 250, 195);
        primaryTypeField = helper.addDropdown(helper.pokemonTypes, 300, 250, 195);

        // POKÉMON TYPES - SECONDARY LABEL, DROPDOWN AND ERROR LABEL
        helper.addLabel("Secondary Type", 505, 250, 195);
        secondaryTypeField = helper.addDropdown(helper.pokemonTypes, 505, 250, 195);
        errorLabelPokemonTypes = helper.addErrorLabel(300, 250, 400);


        // NEXT EVOLUTION LEVEL LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Next Evolution Level", 300, 325, 400);
        pokemonNextEvolutionLevelField = helper.addTextField(300, 325, 400);
        errorLabelNextEvo = helper.addErrorLabel(300, 325, 400);


        // WEIGHT LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Weight(kg)", 300, 400, 190);
        weightField = helper.addTextField(300, 400, 190);
        errorLabelWeight = helper.addErrorLabel(300, 400, 190);


        // HEIGHT LABEL, TEXT FIELD AND ERROR LABEL
        helper.addLabel("Height(m)", 505, 400, 195);
        heightField = helper.addTextField(505, 400, 195);
        errorLabelHeight = helper.addErrorLabel(505, 400, 195);


        // HAS POKÉMON BEEN CAUGHT LABEL, DROPDOWN AND ERROR LABEL
        helper.addLabel("Has Pokémon Been Caught", 300, 475, 400);
        String[] caughtOptions = {"Not Caught", "Caught"};
        pokemonIsCaughtField = helper.addDropdown(caughtOptions, 300, 475, 400);


        // POKÉDEX ENTRY LABEL, TEXT AREA AND ERROR LABEL
        helper.addLabel("Pokédex Entry", 300, 550, 400);
        pokedexEntryField = helper.addTextArea(300, 550, 400);
        errorLabelPokedexEntry = helper.addErrorLabel(300, 602, 400);

        // BUILDS BACK AND NEXT BUTTONS AND HANDLES WHEN THEY ARE SELECTED
        JButton backButton = helper.addSmallButton("BACK", 15, 680);
        JButton nextButton = helper.addSmallButton("NEXT", 805, 680);

        backButton.addActionListener(e -> {
            mainApp.goToPage(new FileOrManualAddPage(mainApp, pokemonDB).getMainPanel());
        });

        nextButton.addActionListener(e -> handleSubmission(mainApp));
    }

    /* Method Name: Hide Errors
     * Purpose: Hides all errors everytime the user attempts to enter information again
     * Parameters: None
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    private void hideErrors() {
        errorLabelPokemonName.setText("");
        errorLabelPokedexNumber.setText("");
        errorLabelPokemonTypes.setText("");
        errorLabelNextEvo.setText("");
        errorLabelWeight.setText("");
        errorLabelHeight.setText("");
        errorLabelPokedexEntry.setText("");
        errorLabelPokedexEntry.setText("");
    }

    /* Method Name: handleSubmission
     * Purpose: Obtains all entered information, checks for any errors, and returns errors or
     * moves to the success page.
     * Parameters: MainMenuPage panel
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    private void handleSubmission(MainMenuPage mainApp) {
        hideErrors();
        boolean hasErrors = false;

        int pokedexNumberInt = 0;
        PokemonTypesManager pokemonTypes = null;
        Integer pokemonNextEvolutionLevelInt = null;
        BigDecimal pokemonWeightBigDecimal = null;
        BigDecimal pokemonHeightBigDecimal = null;
        Boolean hasPokemonBeenCaughtBoolean;

        // GETS ALL ENTERED INFORMATION
        String pokemonName = pokemonNameField.getText().trim();
        String pokedexNumber = pokedexNumberField.getText().trim();
        String primaryType = (String) primaryTypeField.getSelectedItem();
        String secondaryType = (String) secondaryTypeField.getSelectedItem();
        String pokemonNextEvolutionLevel = pokemonNextEvolutionLevelField.getText().trim();
        String pokemonWeight = weightField.getText().trim();
        String pokemonHeight = heightField.getText().trim();
        String hasPokemonBeenCaught = (String) pokemonIsCaughtField.getSelectedItem();
        String pokedexEntry = pokedexEntryField.getText();

        // POKÉMON NAME ERROR CHECKER
        if (pokemonName.isEmpty()) {
            errorLabelPokemonName.setText("Pokémon Name Required.");
            hasErrors = true;
        } else if (pokemonName.length() > 14) {
            errorLabelPokemonName.setText("Pokémon Name length greater than 14.");
            hasErrors = true;
        }

        // POKÉDEX NUMBER ERROR CHECKER
        if (pokedexNumber.isEmpty()) {
            errorLabelPokedexNumber.setText("Pokédex Number Required.");
            hasErrors = true;
        } else if (!isDigit(pokedexNumber)) {
            errorLabelPokedexNumber.setText("Can only contain Digits.");
            hasErrors = true;
        } else if(isDigit(pokedexNumber)) {
            pokedexNumberInt = Integer.parseInt(pokedexNumber);

            // CHECKS FOR UNIQUE POKÉDEX NUMBER
            ValidationResults validationResults =
                    pokemonManager.validateUniquePokedexNumber(pokedexNumberInt, pokemonDB);
            if (!(validationResults.getIsSuccess())) {
                String errorMessage = validationResults.getResultString();
                errorLabelPokedexNumber.setText(errorMessage);
                hasErrors = true;
            } if (pokedexNumberInt < 1 || pokedexNumberInt > 1164) {
                errorLabelPokedexNumber.setText("Pokédex number must be between 1 and 1164.");
                hasErrors = true;
            }
        }

        // POKÉMON TYPES ERROR CHECKER
        if (primaryType.isEmpty()) {
            errorLabelPokemonTypes.setText("Primary Type Required.");
            hasErrors = true;
        } else if (primaryType.equalsIgnoreCase(secondaryType)) {
            errorLabelPokemonTypes.setText("Types cannot be the same.");
            hasErrors = true;
        } else {
            // Fixes the types
            PokemonTypes primaryTypeFixed = PokemonTypes.valueOf(primaryType.toUpperCase());
            PokemonTypes secondaryTypeFixed = null;

            assert secondaryType != null;
            if (!(secondaryType.isEmpty())) {
                secondaryTypeFixed = PokemonTypes.valueOf(secondaryType.toUpperCase());
            }

            pokemonTypes = new PokemonTypesManager(primaryTypeFixed, secondaryTypeFixed);
        }

        // NEXT EVOLUTION LEVEL ERROR CHECKER
        if (pokemonNextEvolutionLevel.isEmpty()) {
            pokemonNextEvolutionLevelInt = null;
        } else if (!isDigit(pokemonNextEvolutionLevel)) {
            errorLabelNextEvo.setText("Can only contain Digits.");
            hasErrors = true;
        }  else if(isDigit(pokemonNextEvolutionLevel)) {
            pokemonNextEvolutionLevelInt = Integer.valueOf(pokemonNextEvolutionLevel);

            if (pokemonNextEvolutionLevelInt < 1 || pokemonNextEvolutionLevelInt > 99) {
                errorLabelNextEvo.setText("Must be between 1 and 99.");
                hasErrors = true;
            }
        }

        // WEIGHT ERROR CHECKER
        if (pokemonWeight.isEmpty()) {
            errorLabelWeight.setText("Weight Required.");
            hasErrors = true;
        } else if (!isDigitOrPeriod(pokemonWeight)) {
            errorLabelWeight.setText("Invalid Weight.");
            hasErrors = true;
        } else if(isDigitOrPeriod(pokemonWeight)) {
            pokemonWeightBigDecimal = BigDecimal.valueOf(Double.parseDouble(weightField.getText().trim()));
        }

        // HEIGHT ERROR CHECKER
        if (pokemonHeight.isEmpty()) {
            errorLabelHeight.setText("Height Required.");
            hasErrors = true;
        } else if (!isDigitOrPeriod(pokemonHeight)) {
            errorLabelHeight.setText("Invalid Height.");
            hasErrors = true;
        } else if(isDigitOrPeriod(pokemonHeight)) {
            pokemonHeightBigDecimal = BigDecimal.valueOf(Double.parseDouble(heightField.getText().trim()));
        }

        // HAS POKÉMON BEEN CAUGHT ERROR CHECKER
        hasPokemonBeenCaughtBoolean = hasPokemonBeenCaught.equals("Caught");

        // POKÉDEX ENTRY ERROR CHECKER
        if (pokedexEntry.isEmpty()) {
            pokedexEntry = "";
        } else if (pokedexEntry.length() > 200) {
            errorLabelPokedexEntry.setText("Pokédex Entry must be less than 200 characters.");
            hasErrors = true;
        } else {
            pokedexEntry = pokedexEntry.trim().replaceAll("\\s+", " ");
        }

        // Continues if it does not have errors
        if (!hasErrors) {

            pokemon_DBHelper.insert(pokedexNumberInt, pokemonName, pokemonNextEvolutionLevelInt,
                    String.valueOf(pokemonWeightBigDecimal), String.valueOf(pokemonHeightBigDecimal),
                    (hasPokemonBeenCaughtBoolean ? 1 : 0), pokedexEntry, types_DBHelper.getTypeIdByName(primaryType),
                    types_DBHelper.getTypeIdByName(secondaryType));


            // Query database for the Pokémon with the given Pokédex number
            ArrayList<ArrayList<Object>> result = pokemon_DBHelper.select(
                    "*",                         // select all columns
                    "pokedex_number",            // WHERE field
                    String.valueOf(pokedexNumberInt), // WHERE value
                    null,                        // no sort needed
                    null
            );

            if (result != null && !result.isEmpty()) {
                ArrayList<Object> row = result.get(0);

                String name = row.get(1) != null ? row.get(1).toString() : "Unknown";
                String nextEvoLevel = row.get(2) != null ? row.get(2).toString() : "N/A";
                String weight = row.get(3) != null ? row.get(3).toString() : "N/A";
                String height = row.get(4) != null ? row.get(4).toString() : "N/A";
                String caught = row.get(5) != null ? row.get(5).toString() : "N/A";
                String enteredPokedexEntry = row.get(6) != null ? row.get(6).toString() : "";
                String primaryTypeString = row.get(7) != null ? row.get(7).toString() : "";
                String secondaryTypeString = row.get(8) != null ? row.get(8).toString() : "";


                String fixedPokedexEntry = enteredPokedexEntry.replace("&", "&amp;")
                        .replace("<", "&lt;")
                        .replace(">", "&gt;");
                if (fixedPokedexEntry.isEmpty()) {
                    fixedPokedexEntry = "NO POKÉDEX ENTRY";
                }

                String typeDisplay = "";
                if (primaryTypeString != "") {
                    if (secondaryTypeString != "") {
                        typeDisplay = primaryTypeString + " & " + secondaryTypeString;
                    } else {
                        typeDisplay = primaryTypeString;
                    }
                }

                // Build HTML text
                String setText = "<html><body style='width:345px; font-family:\"SH Pinscher Regular\";'>"
                        + "Name: " + name + " <br>"
                        + "Pokédex Number: " + pokedexNumberInt + " <br>"
                        + "Type: " + typeDisplay + " <br>"
                        + "Next Evolution Level: " + nextEvoLevel + "<br>"
                        + "Weight: " + weight + "<br>"
                        + "Height: " + height + "<br>"
                        + "Has Pokémon Been Caught: " + caught + "<br>"
                        + "Pokédex Entry: <br><div style='width: 100%; border: solid 1px #ff0000'>"
                        + fixedPokedexEntry + "</div><br></body></html>";

                // Go to the success page
                AddManualSuccessPage addManualSuccessPage =
                        new AddManualSuccessPage(mainApp, null, setText); // you may not need pokemonDB anymore
                mainApp.goToPage(addManualSuccessPage.getMainPanel());
            } else {
                // Pokémon not found in DB
                JOptionPane.showMessageDialog(null, "Pokémon with number " + pokedexNumberInt + " not found.");
            }
        }

    }

    /* Method Name: Is Digit or Period
     * Purpose: Checks through a string. Checking if each char is either a digit or a period.
     *          Returns true if all are either a digit or period.
     * Parameters: String to check
     * Return Value: boolean
     */
    public boolean isDigitOrPeriod(String input) {
        return input.chars().allMatch(c -> Character.isDigit(c) || c == '.');
    }

    /* Method Name: Is Digit
     * Purpose: Checks through a string. Checking if each char is either a digit.
     *          Returns true if all are either a digit.
     * Parameters: String to check
     * Return Value: boolean
     */
    public boolean isDigit(String input) {
        return input.chars().allMatch(Character::isDigit);
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
