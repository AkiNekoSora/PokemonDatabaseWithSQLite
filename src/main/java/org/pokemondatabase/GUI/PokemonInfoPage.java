package org.pokemondatabase.GUI;

import org.pokemondatabase.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Pokémon Information Page
 * Purpose: Displays Pokémon information to the GUI. Allows the user to Update information,
 * change if the Pokémon has been caught or not and delete the Pokémon
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getMainPanel - returns the main panel for this page
 */
public class PokemonInfoPage {
    private final JLayeredPane pane;

    public List<Pokemon> pokemonDB;

    public PokemonInfoPage(MainMenuPage mainApp, Pokemon pokemon, List<Pokemon> pokemonDB) {
        GuiHelper helper = new GuiHelper(mainApp);
        this.pokemonDB = pokemonDB;
        String pokedexNumberString;
        String nextEvoLevelString;

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("POKÉMON INFORMATION", "/PokemonInfoPageBackground.jpg");

        // POKÉMON NAME
        helper.addLabelWithSpecifics(pokemon.getPokemonName().toUpperCase(), 417, 170, 300, 35, 45,
                Color.DARK_GRAY);

        // POKÉDEX NUMBER
        pokedexNumberString = String.format("%04d", pokemon.getPokedexNumber());
        helper.addLabelWithSpecifics(pokedexNumberString, 480, 103, 300, 40,
                55, Color.WHITE);

        // POKÉMON TYPES
        String[] types = pokemon.getPokemonType().toString().split(" & ");
        // Displays image based on the type of Pokémon for the Primary Type
        helper.addImageIcon("/" + types[0] + ".png", 560, 250, 188, 63);
        // Displays image based on the type of Pokémon for the Secondary Type if exists
        if (types.length > 1) helper.addImageIcon("/" + types[1] + ".png", 755, 250, 188, 63);

        // NEXT EVOLUTION NUMBER WITH FORMATING
        if (pokemon.getNextEvolutionLevel() != null || pokemon.getNextEvolutionLevel() != 0) {
            nextEvoLevelString = String.format("%02d", pokemon.getNextEvolutionLevel());
        } else {
            nextEvoLevelString = "00";
        }
        helper.addLabelWithSpecifics(nextEvoLevelString, 438, 293, 500, 60, 80, Color.DARK_GRAY);

        // WEIGHT WITH FORMATING
        String weightString = String.format("%-5s %5.1fkg", "WEIGHT:",
                pokemon.getPokemonWeightKilograms());
        helper.addLabelWithSpecifics(weightString, 595, 413, 600, 50, 40, Color.DARK_GRAY);

        // HEIGHT WITH FORMATING
        String heightString = String.format("%-5s %5.1fm", "HEIGHT:",
                pokemon.getPokemonHeightMeters());
        helper.addLabelWithSpecifics(heightString, 595, 356, 600, 30, 40, Color.DARK_GRAY);

        // HAS POKÉMON BEEN CAUGHT
        if (pokemon.getPokemonIsCaught()) {
            helper.addImageIcon("/Caught.png", 409, 97, 57, 56);
        }

        // POKÉDEX ENTRY WITH FORMATING
        if (pokemon.getPokedexEntry() != null) {
            String htmlPokedexEntry =
                    "<html><body style='width:650px; font-family:\"SH Pinscher Regular\";'>" + pokemon.getPokedexEntry() + "</body></html>";
            helper.addLabelWithSpecifics(htmlPokedexEntry, 85, 480, 900, 200, 33, Color.DARK_GRAY);
        }

        // ADDS UPDATE BUTTON AND SENDS TO UPDATE PAGE IF SELECTED
        JButton updateButton = helper.addMediumButton("UPDATE", 20, 90);
        updateButton.addActionListener(e -> {
            mainApp.goToPage(new UpdatePage(mainApp, pokemon, pokemonDB).getMainPanel());
        });

        // ADDS CAUGHT BUTTON AND REFRESHES THE PAGE TO SHOW THE IMAGE
        JButton caughtButton = helper.addMediumButton("CAUGHT", 20, 220);
        caughtButton.addActionListener(e -> {
            pokemon.setPokemonIsCaught(!pokemon.getPokemonIsCaught());
            mainApp.goToPage(new PokemonInfoPage(mainApp, pokemon, pokemonDB).getMainPanel());
        });

        // ADDS DELETE BUTTON AND SENDS TO DELETE PAGE IF CLICKED
        JButton deleteButton = helper.addMediumButton("DELETE", 20, 350);
        deleteButton.addActionListener(e -> {
            mainApp.goToPage(new DeletePage(mainApp, pokemon, pokemonDB).getMainPanel());
        });

        // ADDS BACK BUTTON AND SENDS BACK TO LIST PAGE IF CLICKED
        JButton backButton = helper.addSmallButton("BACK", 20, 685);
        backButton.addActionListener(e -> {
            mainApp.goToPage(new ListPage(mainApp, pokemonDB).getMainPanel());
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
