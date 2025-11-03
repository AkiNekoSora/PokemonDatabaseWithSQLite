package org.pokemondatabase.GUI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import org.pokemondatabase.DBHelper.DBHelper;
import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.DBHelper.Types_DBHelper;
import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: MainMenu Page
 * Purpose: Used to let the user choose what they would like to do in the system. Contains
 * buttons to go to FileOrManualAddPage, ListPage, ComparePage, CheckNextEvoPage
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - goToPage -
 *              - getMainMenuLayeredPane -
 *              - getMainPanel - returns the main panel for this page
 */
public class MainMenuPage extends JFrame {

    private final JLayeredPane pane;

    private final JButton addPokemonButton;
    private final JButton pokemonListButton;
    private final JButton comparePokemonStatsButton;
    private final JButton checkNextEvolutionButton;

    public DBHelper db;
    GuiHelper helper = new GuiHelper(this);

    /* Method Name: CONSTRUCTOR
     * Purpose: Builds the base design using GUI helper
     * Parameters: List of Pokémon
     */
    public MainMenuPage() {
        GuiHelper helper = new GuiHelper(MainMenuPage.this);

        // SET SIZE OF WINDOW
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // BUILDS BASE PANEL
        pane = helper.createBasePanel("POKÉMON DATABASE",  "/background.jpg");

        // ADD BUTTONS TO PANEL
        addPokemonButton = helper.addLargeButton("ADD POKÉMON", 200, 100);
        pokemonListButton = helper.addLargeButton("POKÉMON LIST", 200, 250);
        comparePokemonStatsButton = helper.addLargeButton("COMPARE POKÉMON STATS", 200, 400);
        checkNextEvolutionButton = helper.addLargeButton("CHECK NEXT EVOLUTION", 200, 550);

        setContentPane(pane);
        pack();
        setVisible(true);

        if (this.db == null) {
            this.db = new DBHelper();
        }
        if (!this.db.connected) {
            this.goToPage(new ChooseDBPage(this).getMainPanel());
        }

        // ADD BUTTON ACTION
        addPokemonButton.addActionListener((ActionEvent e) -> {
            goToPage(new FileOrManualAddPage(this).getMainPanel());
        });

        // LIST BUTTON ACTION
        pokemonListButton.addActionListener((ActionEvent e) -> {
            goToPage(new ListPage(this).getMainPanel());
        });

        // COMPARE BUTTON ACTION
        comparePokemonStatsButton.addActionListener((ActionEvent e) -> {
            goToPage(new ComparePage(this).getMainPanel());
        });

        // CHECK NEXT EVOLUTION BUTTON ACTION
        checkNextEvolutionButton.addActionListener((ActionEvent e) -> {
            goToPage(new CheckNextEvoPage(this).getMainPanel());
        });
    }

    /* Method Name: GoToPage
     * Purpose: When called it rewrites what page is currently displayed to the screen
     * Parameters: Container of the page
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    public void goToPage(Container page) {
        this.setContentPane(page);
        this.revalidate();
        this.repaint();
    }

    /* Method Name: getMainMenuLayeredPane
     * Purpose: returns the main menu layered Panel
     * Parameters: NONE
     * Return Value: JLayeredPane
     */
    public JLayeredPane getMainMenuLayeredPane() {
        return pane;
    }

}
