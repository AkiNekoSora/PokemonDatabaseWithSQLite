package org.pokemondatabase.GUI;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.swing.*;

import org.pokemondatabase.*;
import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.DBHelper.Types_DBHelper;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Update Page
 * Purpose: Has all of the information of the Pokémon in editable text fields or dropdowns.
 * Allows the user to change information and when they submit it changes the Pokémon information
 * in the list.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - hideErrors - hides all error messages
 *              - handleSubmission - handles the processes for the user input file
 *              - Is Digit or Period
 *              - IsDigit
 *              - getMainPanel - returns the main panel for this page
 */
public class UpdatePage extends JFrame {
    private final PokemonManager pokemonManager = new PokemonManager();
    public List<Pokemon> pokemonDB;
    Pokemon_DBHelper pokemon_DBHelper = new Pokemon_DBHelper();
    Types_DBHelper types_DBHelper = new Types_DBHelper();

    private final JLayeredPane pane;

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
     * Parameters: MainMenuPage, Pokémon to update, and List of Pokémon
     */
    public UpdatePage(MainMenuPage mainApp, Pokemon updatedPokemon, List<Pokemon> pokemonStorage) {
        this.pokemonDB = pokemonStorage;
        GuiHelper helper = new GuiHelper(mainApp);
        pane = helper.createBasePanel("EDIT POKÉMON", "/addPokemonPage.jpg");


        //POKÉMON NAME LABEL, TEXT FIELD, SETS TO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Pokémon Name", 300, 100, 400);
        pokemonNameField = helper.addTextField(300, 100, 400);
        pokemonNameField.setText(updatedPokemon.getPokemonName());
        errorLabelPokemonName = helper.addErrorLabel(300, 100, 400);


        //POKÉDEX NUMBER LABEL, TEXT FIELD, SETS TO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Pokédex Number", 300, 175, 400);
        pokedexNumberField = helper.addTextField(300, 175, 400);
        pokedexNumberField.setText(String.valueOf(updatedPokemon.getPokedexNumber()));
        errorLabelPokedexNumber = helper.addErrorLabel(300, 175, 400);


        //POKÉMON TYPES - PRIMARY LABEL, DROPDOWN, SETS TO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Primary Type", 300, 250, 195);
        primaryTypeField = helper.addDropdown(helper.pokemonTypes, 300, 250, 195);
        primaryTypeField.setSelectedItem(updatedPokemon.getPrimaryType());

        //POKÉMON TYPES - SECONDARY LABEL, DROPDOWN, SETSTO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Secondary Type", 505, 250, 195);
        secondaryTypeField = helper.addDropdown(helper.pokemonTypes, 505, 250, 195);
        errorLabelPokemonTypes = helper.addErrorLabel(300, 250, 400);
        if (updatedPokemon.getSecondaryType() != null) {
            secondaryTypeField.setSelectedItem(updatedPokemon.getSecondaryType());
        }


        //NEXT EVOLUTION LEVEL LABEL, TEXT FIELD, SETS TO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Next Evolution Level", 300, 325, 400);
        pokemonNextEvolutionLevelField = helper.addTextField(300, 325, 400);
        pokemonNextEvolutionLevelField.setText(String.valueOf(updatedPokemon.getNextEvolutionLevel()));
        errorLabelNextEvo = helper.addErrorLabel(300, 325, 400);


        //WEIGHT LABEL, TEXT FIELD, SETS TO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Weight(kg)", 300, 400, 190);
        weightField = helper.addTextField(300, 400, 190);
        weightField.setText(String.valueOf(updatedPokemon.getPokemonWeightKilograms()));
        errorLabelWeight = helper.addErrorLabel(300, 400, 190);


        //HEIGHT LABEL, TEXT FIELD, SETS TO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Height(m)", 505, 400, 195);
        heightField = helper.addTextField(505, 400, 195);
        heightField.setText(String.valueOf(updatedPokemon.getPokemonHeightMeters()));
        errorLabelHeight = helper.addErrorLabel(505, 400, 195);


        //HAS POKÉMON BEEN CAUGHT LABEL, DROPDOWN, SETS TO CURRENT INFO, AND ERROR LABEL
        helper.addLabel("Has Pokémon Been Caught", 300, 475, 400);
        String[] caughtOptions = {"Not Caught", "Caught"};
        pokemonIsCaughtField = helper.addDropdown(caughtOptions, 300, 475, 400);
        if (updatedPokemon.getPokemonIsCaught()){
            pokemonIsCaughtField.setSelectedIndex(1);
        } else {
            pokemonIsCaughtField.setSelectedIndex(0);
        }


        //POKÉDEX ENTRY LABEL, TEXT AREA, SETS TO CURRENT INFO, AND ERROR LABEL
        StringBuilder pokedexEntryBuilder = new StringBuilder();
        String fixedPokedexEntry;
        helper.addLabel("Pokédex Entry", 300, 550, 400);
        pokedexEntryField = helper.addTextArea(300, 550, 400);
        // FORMATS POKÉDEX ENTRY
        if (updatedPokemon.getPokedexEntry() != null) {
            fixedPokedexEntry = updatedPokemon.getPokedexEntry().replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");
            pokedexEntryBuilder.append("<br><div style = 'width: 100%; border: solid 1px " + "#ff0000'>").append(fixedPokedexEntry).append("</div><br></body></html>");
        } else {
            fixedPokedexEntry = "";
        }

        pokedexEntryField.setText(fixedPokedexEntry);
        errorLabelPokedexEntry = helper.addErrorLabel(300, 550, 400);

        // BUILDS BACK AND NEXT BUTTONS AND HANDLES WHEN THEY ARE SELECTED
        JButton backButton = helper.addSmallButton("BACK", 15, 680);
        JButton nextButton = helper.addSmallButton("NEXT", 805, 680);

        backButton.addActionListener(e -> {
            mainApp.goToPage(new PokemonInfoPage(mainApp, updatedPokemon, pokemonDB).getMainPanel());
        });

        nextButton.addActionListener(e -> handleSubmission(mainApp, updatedPokemon));
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
    }

    /* Method Name: handleSubmission
     * Purpose: Obtains all updated information, checks for any errors, and returns errors or
     * moves to the success page.
     * Parameters: MainMenuPage panel and Pokémon being updated
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    private void handleSubmission(MainMenuPage mainApp, Pokemon updatedPokemon) {
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
        String primaryType = Objects.requireNonNull(primaryTypeField.getSelectedItem()).toString();
        String secondaryType;
        Object selectedSecondary = secondaryTypeField.getSelectedItem();
        if (selectedSecondary == null || selectedSecondary.toString().isEmpty() || selectedSecondary.equals("null")) {
            secondaryType = null;
        } else {
            secondaryType = Objects.equals(selectedSecondary.toString(), "null") ? "0" : selectedSecondary.toString();
        }
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
            errorLabelPokemonName.setText("Max Pokémon Name Length Greater than 14.");
            hasErrors = true;
        }

        // POKÉDEX NUMBER ERROR CHECKER
        if (pokedexNumber.isEmpty()) {
            errorLabelPokedexNumber.setText("Pokédex Number Required.");
            hasErrors = true;
        } else if (!isDigit(pokedexNumber)) {
            errorLabelPokedexNumber.setText("Letter and Spaces Not Allowed.");
            hasErrors = true;
        } else if(isDigit(pokedexNumber)) {
            pokedexNumberInt = Integer.parseInt(pokedexNumber);

            // CHECKS FOR UNIQUE POKÉDEX NUMBER
            ValidationResults validationResults =
                    pokemonManager.validateUniquePokedexNumber(pokedexNumberInt, pokemonDB);
            if (!(validationResults.getIsSuccess())) {
                String errorMessage = "";
                for (Pokemon pokemon : pokemonDB) {
                    if (pokemon.getPokedexNumber() != updatedPokemon.getPokedexNumber()) {
                        if (pokemon.getPokedexNumber() == pokedexNumberInt) {
                            errorMessage = "Pokédex Number already belongs to " + pokemon.getPokemonName();
                            errorLabelPokedexNumber.setText(errorMessage);
                            hasErrors = true;
                            break;
                        }
                    }
                }
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

            if (secondaryType != null) {
                if (!(secondaryType.isEmpty())){
                    secondaryTypeFixed = PokemonTypes.valueOf(secondaryType.toUpperCase());
                }
            }

            pokemonTypes = new PokemonTypesManager(primaryTypeFixed, secondaryTypeFixed);
        }

        // NEXT EVOLUTION LEVEL ERROR CHECKER
        if (pokemonNextEvolutionLevel.isEmpty()) {
            pokemonNextEvolutionLevelInt = null;
        } else if (!isDigit(pokemonNextEvolutionLevel)) {
            errorLabelNextEvo.setText("Letter and Spaces Not Allowed.");
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

        // IF NO ERRORS FOUND, CONTINUE, UPDATE POKÉMON AND GO BACK TO POKÉMON INFO PAGE
        if (!hasErrors) {
            pokemon_DBHelper.updateAll(String.valueOf(pokedexNumberInt), pokemonName,
                    String.valueOf(pokedexNumberInt), pokemonWeight, pokemonHeight,
                    (hasPokemonBeenCaughtBoolean ? "1" : "0"), pokedexEntry,
                    String.valueOf(types_DBHelper.getTypeIdByName(String.valueOf(pokemonTypes.getPokemonPrimaryType()))),
                    String.valueOf(types_DBHelper.getTypeIdByName(String.valueOf(pokemonTypes.getPokemonSecondaryType()))),
                    String.valueOf(updatedPokemon.getPokedexNumber()));

            updatedPokemon.setPokemonName(pokemonName);
            updatedPokemon.setPokedexNumber(pokedexNumberInt);
            updatedPokemon.setPokemonType(pokemonTypes);
            updatedPokemon.setNextEvolutionLevel(pokemonNextEvolutionLevelInt);
            updatedPokemon.setPokemonWeightPounds(pokemonWeightBigDecimal);
            updatedPokemon.setPokemonHeightMeters(pokemonHeightBigDecimal);
            updatedPokemon.setPokemonIsCaught(hasPokemonBeenCaughtBoolean);
            updatedPokemon.setPokedexEntry(pokedexEntry);

            mainApp.goToPage(new PokemonInfoPage(mainApp, updatedPokemon, pokemonDB).getMainPanel());

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
