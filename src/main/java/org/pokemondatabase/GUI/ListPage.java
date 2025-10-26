package org.pokemondatabase.GUI;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: List Page
 * Purpose: Used to show the user the list of Pokémon, and lets them search for a specific
 * Pokémon or click on a Pokémon and go to their Information page.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - handleSubmission - handles the processes for the user input file
 *              - IsDigit
 *              - getMainPanel - returns the main panel for this page
 */
public class ListPage {
    private final JLayeredPane pane;
    public List<Pokemon> pokemonDB;
    private JTextField searchField = new JTextField();

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper and states what happens when you click on
     * each button.
     * Parameters: MainMenuPage, List of Pokémon
     */
    public ListPage(MainMenuPage mainApp, List<Pokemon> pokemonStorage) {
        this.pokemonDB = pokemonStorage;
        GuiHelper helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("POKÉMON LIST", "/background.jpg");

        // BUILDS LIST OF POKÉMON
        helper.addPokemonList(pokemonDB, null);

        // ADDS IMAGE BEHIND LIST
        helper.addImageIcon("/PokemonList.png", 80, 100, 840, 570);

        // CREATES SEARCH TITLE, TEXT FIELD, AND IMAGE BEHIND
        searchField = helper.addSearchField(345, 688, 615, 35);
        helper.addLabelWithSpecifics("SEARCH: ", 230, 674, 200, 60, 40, Color.DARK_GRAY);
        helper.addImageIcon("/SearchField.png", 205, 680, 780, 50);

        // BUILDS NEXT BUTTON AND HANDLES WHEN IT IS SELECTED
        JButton backButton = helper.addSmallButton("BACK", 15, 680);
        backButton.addActionListener(e -> {
            mainApp.goToPage(mainApp.getMainMenuLayeredPane());
        });

        // Handles what happens when the user enters or deletes text into the search bar
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            // IF A USER INSERTS TEXT INTO THE FIELD
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateStatus(e);
            }

            // IF A USER REMOVES TEXT INTO THE FIELD
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateStatus(e);
            }

            // IF A USER CHANGES TEXT IN THE FIELD
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateStatus(e);
            }

            /* Method Name: Update Status
             * Purpose: used to create a new list of Pokémon when called and then updates the
             * displayed list on the GUI
             * Parameters: Document Event
             * Return Value: void (It is only called by actions, so I thought this was okay!)
             */
            private void updateStatus(DocumentEvent e) {
                List<Pokemon> searchResults = new ArrayList<>();

                // Show full list if the searchField is empty
                if (searchField.getText().isEmpty()) {
                    helper.updatePokemonList(pokemonDB, pokemonDB);
                    return;
                }

                // Creates the new list to display based on either numbers or letters
                for (Pokemon pokemon : pokemonDB) {
                    if (isDigit(searchField.getText())) {
                        if (pokemon.getPokedexNumber() == Integer.parseInt(searchField.getText())) {
                            searchResults.add(pokemon);
                            continue;
                        }
                    }
                    if (pokemon.getPokemonName().toLowerCase().contains(searchField.getText().toLowerCase())) {
                        searchResults.add(pokemon);
                        continue;
                    }
                }

                // Updates the list shown on the GUI
                helper.updatePokemonList(pokemonDB, searchResults);
            }
        });
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
