package org.pokemondatabase.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.DBHelper.Pokemon_Types_DBHelper;
import org.pokemondatabase.DBHelper.Types_DBHelper;
import org.pokemondatabase.GUI.CustomGUIDesigns.CustomComboBoxRenderer;
import org.pokemondatabase.GUI.CustomGUIDesigns.CustomComboBoxUI;
import org.pokemondatabase.GUI.CustomGUIDesigns.CustomScrollBarUI;
import org.pokemondatabase.Pokemon;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: GUI Helper
 * Purpose: Used to be called by all GUI pages to perform necessary GUI designs and processes.
 *          Contains:
 *              - Constructor - Builds the base design using GUI helper
 *              - getFont - Used to get font
 *              - createBasePanel - Called by all pages to create the base of the panel.
 *              - addButton - Called by other methods in ths class to create a button
 *              - addSmallButton - Calls add button but makes it small
 *              - addMediumButton - Calls add button but makes it medium
 *              - addLargeButton - Calls add button but makes it Large
 *              - updatePokémonList - Updates the Pokémon List when search is used
 *              - addPokémonList - Creates a Pokémon list using a ScrollPane and calls to get List items
 *              - createPokémonListItem - Creates a single list item
 *              - addLabel - Adds specified text to the panel
 *              - addLabelWithSpecifics - add specified text to the panel (Specify font and size)
 *              - addLabelWithImage - Add a text label with an image using addImageScaled
 *              - addErrorLabel - Adds an error label (Smaller and red)
 *              - addTextField - Adds a place for the user to enter text
 *              - addSearchField - Adds a place for the user to enter text (Larger for Search)
 *              - addTextArea - Adds a bigger place for the user to enter text
 *              - addDropdown - Add a dropdown the user can use.
 *              - addImageScaled - Scaled an image to the text
 *              - addImageIcon - Add an image to the panel
 *              - addTextBackgroundImage - Add image to the background
 */
public class GuiHelper {
    private MainMenuPage mainApp;
    public JPanel foregroundPanel;

    Pokemon_DBHelper pokemon_DBHelper = new Pokemon_DBHelper();
    Pokemon_Types_DBHelper pokemonTypes_DBHelper = new Pokemon_Types_DBHelper();
    Types_DBHelper types_DBHelper = new Types_DBHelper();

    private final Color titleColor = new Color(36, 37, 40);
    public final Color pokemonRed = new Color(239, 49, 49);

    public final Font titleFont = getFont(52);
    public final Font labelFont = getFont(18);
    public final Font smallButtonFont = getFont(27);
    public final Font inputFont = new Font("Courier New", Font.PLAIN, 16);
    public final Font errorFont = new Font("Courier New", Font.BOLD, 12);
    public final Font successFont = getFont(20);
    public final Font pokemonListFont = getFont(40);
    public final Font bigFont = getFont(50);

    private JScrollPane pokemonScrollPane;

    public final String[] pokemonTypes = {"", "Normal", "Fire", "Fighting", "Water", "Flying", "Grass",
                "Poison", "Electric", "Ground", "Psychic", "Rock", "Ice", "Bug", "Dragon",
                "Ghost", "Dark", "Steel", "Fairy"};

    // CONSTRUCTOR
    public GuiHelper(MainMenuPage mainApp) {
        this.mainApp = mainApp;
    }

    /* Method Name: getFont
     * Purpose: Returns the font SHPinscher
     * Parameters: Font Size
     * Return Value: Font
     */
    public Font getFont(int fontSize) {
        Font customFont;

        // Try/Catch in case the font is not obtained
        try {
            // Gets the Font
            InputStream is = getClass().getResourceAsStream("/SHPinscher-Regular.otf");
            if (is == null) {
                throw new IOException("Font resource not found.");
            }
            // Creates the font and adds t to the system
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont((float) fontSize);
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Courier New", Font.BOLD, fontSize);
        }
        return customFont;
    }

    /* Method Name: Create Base Panel
     * Purpose: Creates the layered pane, adds a background and the title.
     * Parameters: Title, Background Link Path
     * Return Value: JLayeredPane
     */
    public JLayeredPane createBasePanel(String title, String backgroundLink) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1000, 750));

        // ADD BACKGROUND
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource(backgroundLink));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1000, 750);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        foregroundPanel = new JPanel(null);
        foregroundPanel.setOpaque(false);
        foregroundPanel.setBounds(0, 0, 1000, 750);

        // ADD TITLE
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setBounds(0, 0, 1000, 55);
        titleLabel.setForeground(titleColor);
        foregroundPanel.add(titleLabel);
        layeredPane.add(foregroundPanel, JLayeredPane.PALETTE_LAYER);

        return layeredPane;
    }

    /* Method Name: addButton
     * Purpose: Creates a button, adds image icon and adds to layered pane
     * Parameters: Image Path, Text, Location (x/y), size(width/height), font
     * Return Value: JButton
     */
    public JButton addButton(String imagePath, String buttonText, int x, int y, int width, int height, Font font) {
        JButton button = new JButton(buttonText);
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));

        // CREATE IMAGE SCALED
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        icon = new ImageIcon(scaledImg);

        // DESIGN BUTTON WTH IMAGE
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(font);
        button.setBounds(x, y, width, height);

        foregroundPanel.add(button);

        return button;
    }

    /* Method Name: addSmallButton
     * Purpose: Calls addButton to build small button
     * Parameters: Text, Location (x/y)
     * Return Value: JButton
     */
    public JButton addSmallButton(String buttonText, int x, int y) {
        return this.addButton("/smallButton.png", buttonText, x, y, 180, 50, smallButtonFont);
    }

    /* Method Name: addMediumButton
     * Purpose: Calls addButton to build medium button
     * Parameters: Text, Location (x/y)
     * Return Value: JButton
     */
    public JButton addMediumButton(String buttonText, int x, int y) {
        return this.addButton("/MediumButton.png", buttonText, x, y, 363, 113, bigFont);
    }

    /* Method Name: addLargeButton
     * Purpose: Calls addButton to build large button
     * Parameters: Text, Location (x/y)
     * Return Value: JButton
     */
    public JButton addLargeButton(String buttonText, int x, int y) {
        return this.addButton("/longButton_Down.png", buttonText, x, y, 600, 135,
                bigFont);
    }

    /* Method Name: addPokémonList
     * Purpose: Creates a listPanel, adds each Pokémon to the list panel with
     * createPokémonListItem, creates a scroll pane, adds list panel to it and adds it to the
     * foreground panel
     * Parameters: List of Pokémon and List of Filtered Pokémon
     * Return Value: JScrollPane
     */
    public JScrollPane addPokemonList(List<Pokemon> pokemonDB, List<Pokemon> filteredPokemonList) {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        if (filteredPokemonList == null) {
            // Adds each Pokémon to the list panel using createPokémonListItem
            for (Pokemon pokemon : pokemonDB) {
                listPanel.add(createPokemonListItem(pokemon, pokemonDB));
            }
        } else {
            for (Pokemon pokemon : filteredPokemonList) {
                listPanel.add(createPokemonListItem(pokemon, pokemonDB));
            }
        }

        listPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Create JScrollPane and designs it using the CustomGUIDesigns
        pokemonScrollPane = new JScrollPane(listPanel);
        pokemonScrollPane.setBounds(100, 110, 800, 550);
        pokemonScrollPane.setOpaque(false);
        pokemonScrollPane.getViewport().setOpaque(false);
        pokemonScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        pokemonScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        pokemonScrollPane.setBorder(null);

        foregroundPanel.add(pokemonScrollPane);

        return pokemonScrollPane;
    }

    /* Method Name: updatePokémonList
     * Purpose: Takes the scrollPane that was created above and replaces the list with the new
     * list give in the parameter
     * Parameters: List of Pokémon and List of Filtered Pokémon
     * Return Value: JScrollPane
     */
    public JScrollPane updatePokemonList(List<Pokemon> pokemonDB,
                                         List<Pokemon> filteredPokemonList) {
        // If there is no scroll pane, call addPokémonList instead
        if (pokemonScrollPane == null) {
            addPokemonList(pokemonDB, filteredPokemonList);
            return pokemonScrollPane;
        }

        // Creates a new listPanel to add to the scrollPane
        JPanel newListPanel = new JPanel();
        newListPanel.setLayout(new BoxLayout(newListPanel, BoxLayout.Y_AXIS));
        newListPanel.setOpaque(false);

        // Adds each Pokémon to the list panel using createPokémonListItem
        for (Pokemon pokemon : filteredPokemonList) {
            newListPanel.add(createPokemonListItem(pokemon, pokemonDB));
        }

        pokemonScrollPane.setViewportView(newListPanel);

        return pokemonScrollPane;
    }

    /* Method Name: createPokémonListItem
     * Purpose: Creates a panel, adds an image depending on if the Pokémon has been caught or
     * not, adds the Pokédex Number and Pokémon Name, adds a button so that it will go to the
     * specified Pokémon page if clicked.
     * Parameters: Pokémon, List of Pokémon
     * Return Value: JPanel
     */
    public JPanel createPokemonListItem(Pokemon pokemon, List<Pokemon> pokemonDB) {
        // CREATE PANEL FOR POKÉMON IN LIST
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 65));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        panel.setOpaque(false);

        // ADD IMAGE BASED ON CAUGHT STATUS
        String imagePath = pokemon.getPokemonIsCaught() ? "/Caught.png" : "/NotCaught.png";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setPreferredSize(new Dimension(57, 58));
        imageLabel.setOpaque(false);

        // ADD POKÉDEX NUMBER AND POKÉMON NAME
        JLabel textLabel =
                new JLabel(String.format( "   %04d", pokemon.getPokedexNumber()) + " " + pokemon.getPokemonName());
        textLabel.setFont(pokemonListFont);
        textLabel.setOpaque(false);

        // ADD THE IMAGE AND LABEL TO THE PANEL
        panel.add(imageLabel, BorderLayout.WEST);
        panel.add(textLabel, BorderLayout.CENTER);

        // CHANGE HAND CURSOR IF HOVERING OVER OPTION
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // IF MOUSE CLICKS ONTO THE PANEL IT GOES TO THE POKÉMON INFO PAGE
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainApp.goToPage(new PokemonInfoPage(mainApp, pokemon, pokemonDB).getMainPanel());
            }
        });
        return panel;
    }

    /* Method Name: addLabel
     * Purpose: used to build a label and add to panel with text specified
     * Parameters: Label Text, Location(x/y), and width
     * Return Value: JLabel
     */
    public JLabel addLabel(String labelText, int x, int y, int width) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(Color.DARK_GRAY);
        label.setBounds(x, y, width, 30);

        foregroundPanel.add(label);
        return label;
    }

    /* Method Name: addLabelWithSpecifics
     * Purpose: used to build a label and add to panel with text specified and height, font size
     * and color specified.
     * Parameters: Label Text, Location(x/y), size(width/height), font choices (size/color)
     * Return Value: JLabel
     */
    public JLabel addLabelWithSpecifics(String labelText, int x, int y, int width, int height,
                                    int fontSize, Color fontColor) {
        JLabel label = new JLabel(labelText);
        label.setFont(getFont(fontSize));
        label.setForeground(fontColor);
        label.setBounds(x, y, width, height);

        foregroundPanel.add(label);
        return label;
    }

    /* Method Name: addLabelWithImage
     * Purpose: used to build a label and add to panel with an Image scaled (using addImageScaled)
     * Parameters: Label Text, Location(x/y), and size(width/height)
     * Return Value: JLabel
     */
    public JLabel addLabelWithImage(String labelText, int x, int y, int width, int height) {
        // CREATE LABEL WITH TEXT SPECIFIED
        JLabel successLabel = new JLabel(labelText);
        successLabel.setFont(successFont);
        successLabel.setForeground(Color.DARK_GRAY);
        successLabel.setBounds(x, y, width, height);
        successLabel.setPreferredSize(new Dimension(width, height));
        successLabel.setOpaque(false);

        // ADD LABEL AND SCALED IMAGES TO THE FOREGROUND PANEL
        foregroundPanel.add(successLabel);
        foregroundPanel.add(addImageScaled("/SuccessBox.png", x, y, width, height));

        return successLabel;
    }

    /* Method Name: addErrorLabel
     * Purpose: used to build a label and add to panel (red and smaller)
     * Parameters: Label Text, Location(x/y), and width
     * Return Value: JLabel
     */
    public JLabel addErrorLabel(int x, int y, int width) {
        JLabel errLabel = new JLabel();
        errLabel.setFont(errorFont);
        errLabel.setForeground(pokemonRed);
        errLabel.setBounds(x, y + 48, width, 35);

        foregroundPanel.add(errLabel);
        return errLabel;
    }

    /* Method Name: addTextField
     * Purpose: used to build a text field that the user can enter information into
     * Parameters: Location(x/y) and width
     * Return Value: JTextField
     */
    public JTextField addTextField(int x, int y, int width) {
        JTextField field = new JTextField();
        field.setBounds(x, y + 27, width, 28);
        field.setFont(inputFont);
        field.setBorder(BorderFactory.createLineBorder(pokemonRed));

        foregroundPanel.add(field);
        return field;
    }

    /* Method Name: addSearchField
     * Purpose: used to build a text field that the user can enter information into. Larger to
     * look nicer with the Pokémon List
     * Parameters: Location(x/y) and size(width/height)
     * Return Value: JTextField
     */
    public JTextField addSearchField(int x, int y, int width, int height) {
        JTextField field = new JTextField();
        field.setBounds(x, y, width, height);
        field.setFont(smallButtonFont);
        field.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        foregroundPanel.add(field);
        return field;
    }

    /* Method Name: addTextArea
     * Purpose: used to build a text area that the user can enter information into. It is taller
     * so that the user can enter more text into the field.
     * Parameters: Location(x/y) and width
     * Return Value: JTextArea
     */
    public JTextArea addTextArea(int x, int y, int width) {
        // CREATE TEXT AREA
        JTextArea textArea = new JTextArea(10, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(inputFont);

        // ADD A SCROLL PANE IF THE USER ENTERS MORE TEXT
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(x, y + 27, width, 80);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.setBorder(BorderFactory.createLineBorder(pokemonRed));

        foregroundPanel.add(scrollPane);
        return textArea;
    }

    /* Method Name: addDropdown
     * Purpose: used to build a dropdown that looks nicer to match the UI
     * Parameters: Array List of Options, Location(x/y) and size(width/height)
     * Return Value: JComboBox<String>
     */
    public JComboBox<String> addDropdown(String[] options, int x, int y, int width) {
        // CREATE THE DROPDOWN WITH SPECIFIED OPTIONS
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setBounds(x, y + 27, width, 30);
        dropdown.setFont(inputFont);
        dropdown.setEditable(true);

        // CREATES AN EDITOR TO ALLOW FOR DESIGN EDITING OF THE DROPDOWN
        JTextField editor = (JTextField) dropdown.getEditor().getEditorComponent();
        editor.setBackground(Color.WHITE);
        editor.setForeground(Color.BLACK);
        editor.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        // CHANGES THE DROPDOWN DESIGN TO USE THE CUSTOM GUI DESIGNS
        dropdown.setRenderer(new CustomComboBoxRenderer());
        dropdown.setUI(new CustomComboBoxUI());
        dropdown.setBorder(BorderFactory.createLineBorder(pokemonRed));

        foregroundPanel.add(dropdown);
        return dropdown;
    }

    /* Method Name: addImageScaled
     * Purpose: creates an image icon with location and size padding to scale it to the location
     * and size given
     * Parameters: Image Path, Location(x/y) and size(width/height)
     * Return Value: JLabel
     */
    public JLabel addImageScaled(String imagePath, int x, int y, int width, int height) {
        // PADDING FOR IMAGE
        int imagePadding = 20;
        int imageX = x - imagePadding / 2 - 68;
        int imageY = y - imagePadding / 2;
        int imageWidth = width + imagePadding;
        int imageHeight = height + imagePadding;

        // CREATE IMAGE
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(imageX, imageY, imageWidth, imageHeight);

        // ATTEMPTS TO GET AND SET IMAGE
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setOpaque(false);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath);
        }

        return imageLabel;
    }

    /* Method Name: addImageIcon
     * Purpose: creates an image icon with location and size
     * Parameters: Image Path, Location(x/y) and size(width/height)
     * Return Value: JLabel
     */
    public JLabel addImageIcon(String imagePath, int x, int y, int width, int height) {
        // CREATES IMAGE ICON
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        icon = new ImageIcon(scaledImg);

        // CREATES AN IMAGE LABEL
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(x, y, width, height);

        foregroundPanel.add(imageLabel);
        return imageLabel;
    }

    /* Method Name: addImageScaled
     * Purpose: Adds a image behind text
     * Parameters: Image Path, Location(x/y) and size(width/height)
     * Return Value: void (I am hoping it is okay to return void for the design side!)
     */
    public void addTextBackgroundImage(String imagePath, int x, int y, int width, int height) {
        foregroundPanel.add(addImageScaled(imagePath, x, y, width, height));
    }

}
