package org.pokemondatabase.GUI;

import java.awt.Container;
import java.util.List;
import javax.swing.*;

import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Delete Page
 * Purpose: Used to ask the user if they are sure they want to delete. If accepted, it deletes
 * the Pokémon from the system and moves to the Delete Success Page
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class DeletePage extends JPanel {
    public List<Pokemon> pokemonDB;
    private final JLayeredPane pane;
    Pokemon_DBHelper pokemon_DBHelper = new Pokemon_DBHelper();

    public DeletePage(MainMenuPage mainApp, Pokemon pokemon, List<Pokemon> pokemonStorage) {
        this.pokemonDB = pokemonStorage;
        GuiHelper helper = new GuiHelper(mainApp);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("VERIFY DELETION",  "/background.jpg");

        String successText = "<html><body style='width:345px; text-align:center; font-size:35pt;'>"
                + "ARE YOU SURE YOU WANT TO DELETE " + pokemon.getPokemonName().toUpperCase()
                + "?</body></html>";
        helper.addLabelWithImage(successText, 280, 280, 600, 200);

        // BUILDS DELETE BUTTON
        JButton deleteButton = helper.addSmallButton("DELETE", 805, 680);

        // DELETES THE POKÉMON AND MOVES TO SUCCESS PAGE
        deleteButton.addActionListener(e -> {
            pokemon_DBHelper.delete("pokedex_number", String.valueOf(pokemon.getPokedexNumber()));
            mainApp.goToPage(new DeleteSuccessPage(mainApp).getMainPanel());
        });

        // BUILDS BACK BUTTON AND HANDLES WHEN IT IS SELECTED
        JButton backButton = helper.addSmallButton("BACK", 20, 685);
        backButton.addActionListener(e -> {
            mainApp.goToPage(new PokemonInfoPage(mainApp, pokemon, pokemonDB).getMainPanel());
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
