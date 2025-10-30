package org.pokemondatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

import org.pokemondatabase.DBHelper.Pokemon_DBHelper;
import org.pokemondatabase.DBHelper.Pokemon_Types_DBHelper;
import org.pokemondatabase.DBHelper.Types_DBHelper;
import org.pokemondatabase.exceptions.InvalidPokedexNumberException;
import org.pokemondatabase.exceptions.InvalidPokemonTypeException;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Class Name: PokémonManager
 * Purpose: Used for all the methods that interact with the Pokémon Class.
 *          Uses the userInputHelper class to loop until the user gives a valid input.
 *          Uses Text class to color the output to look nicer in the CLI
 *          Includes methods that allow the user to:
 *              - Add a Pokémon using the CMI
 *                  - Uses methods in this class that add each item before using them to create a
 *                    new Pokémon and add it to the Pokémon Storage.
 *              - Add a Pokémon using a txt file.
 *              - Remove a Pokémon using a Pokédex Number.
 *              - Find a Pokémon using a Pokédex Number.
 *              - Get all Pokémon and prints them to the CMI.
 *              - Update a Pokémon using a Pokédex Number.
 *              - Compare the weight and height of 2 Pokémon
 *              - Check when a specified Pokémon will evolve.
 */

public class PokemonManager {
    public UserInputHelper userInputHelper;
    private final Text text;
    private final PokemonTypes pokemonTypes;
    Pokemon_DBHelper pokemon_DBHelper = new Pokemon_DBHelper();
    Pokemon_Types_DBHelper pokemonTypes_DBHelper = new Pokemon_Types_DBHelper();
    Types_DBHelper types_DBHelper = new Types_DBHelper();

    // Constructor used to bring the userInputHelper and Text classes into scope.
    public PokemonManager() {
        this.text = new Text();
        this.userInputHelper = new UserInputHelper();
        this.pokemonTypes = PokemonTypes.FIRE;
    }

    // Overloaded constructor used by Tests
    public PokemonManager(UserInputHelper userInputHelper) {
        this.userInputHelper = userInputHelper;
        this.pokemonTypes = PokemonTypes.FIRE;
        this.text = new Text();
    }

    /* Method Name: Add Pokémon Method
     * Purpose: Lets the user add a Pokémon using the CMI. Calls other methods in this class to
     *          obtain all Pokémon information using the CLI.
     * Parameters: The List that holds all Pokémon
     * Return Value: String
     */
    public Pokemon addPokemonForGUI(String userPokemonName,
                                    int pokedexNumber, PokemonTypesManager pokemonTypes,
                                    Integer nextEvolutionLevel, BigDecimal pokemonWeight,
                                    BigDecimal pokemonHeight, boolean hasBeenCaught,
                                    String pokedexEntry) {

        return new Pokemon(userPokemonName, pokedexNumber, pokemonTypes,
                nextEvolutionLevel, pokemonWeight, pokemonHeight, hasBeenCaught,
                pokedexEntry);
    }

    /* Method Name: Add Pokémon Method
     * Purpose: Lets the user add a Pokémon using the CMI. Calls other methods in this class to
     *          obtain all Pokémon information using the CLI.
     * Parameters: The List that holds all Pokémon
     * Return Value: String
     */
    public String addPokemon(List<Pokemon> pokemonStorage) {
        System.out.println(text.BLUE + "\nADDING A NEW POKÉMON!");
        System.out.println("---------------------\n" + text.RESET);

        // Obtaining all Pokémon information using methods in this class.
        String pokemonName = addPokemonName();
        if (pokemonName == null) {
            return userInputHelper.errorMessageColors("Pokémon creation cancelled.");
        }

        int pokedexNumber = addPokedexNumber(pokemonStorage);
        PokemonTypesManager pokemonTypes = addPokemonTypes();
        int nextEvolutionLevel = addEvolutionLevel();
        BigDecimal pokemonWeightPounds = addPokemonWeightOrHeight("weight");
        BigDecimal pokemonHeightMeters = addPokemonWeightOrHeight("height");
        boolean hasPokemonBeenCaught = hasPokemonBeenCaught();
        String pokedexEntry = addPokedexEntry();

        pokemon_DBHelper.insert(pokedexNumber, pokemonName, nextEvolutionLevel,
                String.valueOf(pokemonWeightPounds), String.valueOf(pokemonHeightMeters),
                (hasPokemonBeenCaught ? 1 : 0), pokedexEntry,
                types_DBHelper.getTypeIdByName(String.valueOf(pokemonTypes.getPokemonPrimaryType())),
                types_DBHelper.getTypeIdByName(String.valueOf(pokemonTypes.getPokemonSecondaryType())));


        Pokemon newPokemon = new Pokemon(pokemonName, pokedexNumber, pokemonTypes,
                nextEvolutionLevel, pokemonWeightPounds, pokemonHeightMeters, hasPokemonBeenCaught,
                pokedexEntry);

        System.out.println("Please verify all information of the Pokémon: " + newPokemon);
        boolean verificationResults = userInputHelper.getBooleanInput("Would you like to add " +
                "this Pokémon to your database?");

        if (verificationResults) {
            int beforeSize = pokemonStorage.size();
            pokemonStorage.add(newPokemon);
            int afterSize = pokemonStorage.size();

            if (afterSize == beforeSize + 1) {
                return successMessageColors("Pokémon Added!");
            } else {
                return userInputHelper.errorMessageColors("Pokémon was not added to the database.");
            }
        } else {
            return userInputHelper.errorMessageColors("Pokémon was not added to the database.");
        }
    }

    /* Method Name: Add Pokémon Name
     * Purpose: Calls the userInputHelper method to ask the user to enter a Pokémon Name.
     * Parameters: None
     * Return Value: String of Pokémon Name
     */
    public String addPokemonName() {
        return userInputHelper.getValidPokemonName("Enter Pokémon Name: ");
    }

    /* Method Name: Add Pokédex Number
     * Purpose: Calls the userInputHelper method getIntInRange with the parameters 1 and 1164 to
     *          get the new Pokédex numbr with specified parameters.
     * Parameters: None
     * Return Value: int of Pokédex Number
     */
    public int addPokedexNumber(List<Pokemon> pokemonStorage) {
        int pokedexNumber = 0;
        boolean uniquePokedexNum = false;

        // Loops until Pokédex Number have been verified as Unique.
        while (!uniquePokedexNum) {
            pokedexNumber = userInputHelper.getIntInRange(
                    "Enter Pokédex Number: ", 1, 1164,
                    "Pokédex cannot be less than 1 or exceed current " +
                            "distinct Pokémon species, 1164. Please try again.");

            // Checks to make sure the Pokédex Number is unique.
            uniquePokedexNum = validateUniquePokedexNumber(pokedexNumber, pokemonStorage).getIsSuccess();
        }
        return pokedexNumber;
    }

    /* Method Name: Validates a unique Pokédex Number
     * Purpose: Checks through the list of Pokémon to verify the provided Pokédex Number does not
     *          match a Pokédex number already in the system.
     * Parameters: Pokédex Number and Pokémon List
     * Return Value: ValidationResults (boolean and String)
     */
    public ValidationResults validateUniquePokedexNumber(int pokedexNumber, List<Pokemon> pokemonStorage) {
        ArrayList<ArrayList<Object>> result = pokemon_DBHelper.select(
                "*", "pokedex_number", String.valueOf(pokedexNumber), null, null);

        if (result != null && !result.isEmpty()) {
            Object existingNameObj = result.get(0).get(1);
            String existingName = existingNameObj != null ? existingNameObj.toString() : "Unknown";

            return new ValidationResults(false,
                    "Pokédex number " + pokedexNumber + " already exists as " + existingName + ".");
        }

        return new ValidationResults(true);
    }

    /* Method Name: Add Pokémon Types
     * Purpose: Calls the userInputHelper method getValidType for the primary type and a
     *          secondary type if the user chooses to add one.
     * Parameters: None
     * Return Value: PokémonTypesManager containing the Primary and possibly Secondary Types.
     */
    public PokemonTypesManager addPokemonTypes() {
        PokemonTypes secondaryType = null;

        System.out.print("Enter Pokémon Type(s)\n");
        PokemonTypes primaryType = userInputHelper.getValidPokemonType(
                "    Please enter the Primary Pokémon Type: ");

        boolean userAnswer = userInputHelper.getBooleanInput("Would you like to add a Secondary " +
                        "Pokémon Type?");

        // If else used to check if the user wants to choose a secondary Pokémon type or not.
        if (userAnswer) {
            while (true) {
                secondaryType = userInputHelper.getValidPokemonType(
                        "    Enter secondary Pokémon type: ");

                if (Objects.equals(secondaryType, primaryType)) {
                    System.out.println(userInputHelper.errorMessageColors("Secondary type can't be the same as " +
                            "primary. Try again."));
                } else {
                    return new PokemonTypesManager(primaryType, secondaryType);
                }
            }
        } else {
            return new PokemonTypesManager(primaryType, secondaryType);
        }
    }

    /* Method Name: Add Pokémon Next Evolution Level
     * Purpose: Calls the userInputHelper method getBooleanInput to check if the Pokémon has a
     * next evolution and then uses the getIntInRange method to get a valid Pokémon Level.
     * Parameters: None
     * Return Value: Int of the Next Evolution Level
     */
    public int addEvolutionLevel() {
        Integer nextEvolutionLevel = null;

        boolean userAnswer = userInputHelper.getBooleanInput("Does this Pokémon have an evolution?");

        // Used to check if the Pokémon has a next Evolution.
        if (userAnswer) {
            while (nextEvolutionLevel == null) {
                nextEvolutionLevel = userInputHelper.getIntInRange(
                        "Enter Next Evolution Level: ", 1, 100,
                        "Please enter a valid Next Evolution Level. Between 1 and 100.");
            }
        } else {
            nextEvolutionLevel = 0;
        }
        return nextEvolutionLevel;
    }

    /* Method Name: Add Pokémon Weight or Height
     * Purpose: Uses the userInputHelper method getBigDecimal to get either the height or weight
     * Parameters: A String containing "Weight" or "Height"
     * Return Value: BigDecimal of either the weight or height of a Pokémon
     */
    public BigDecimal addPokemonWeightOrHeight(String weightOrHeight) {
        String measurementType = "";
        if (weightOrHeight.equals("weight")) {
            measurementType = "kilograms";
        } else if (weightOrHeight.equals("height")) {
            measurementType = "meters";
        }

        // Used to get a big decimal from the user with specific text.
        return userInputHelper.getBigDecimal(
                "Enter Pokémon's " + weightOrHeight + " using " + measurementType + ": ",
                "Pokémon " + weightOrHeight + " cannot be anything other than a number. " +
                        "Please try again.");
    }

    /* Method Name: Has the Pokémon Been Caught
     * Purpose: Calls userInputHelper method getBooleanInput to get true or false from the user.
     * Parameters: None
     * Return Value: Boolean of if the Pokémon has been caught or not.
     */
    public boolean hasPokemonBeenCaught() {
        return userInputHelper.getBooleanInput("Has the Pokémon been Caught?");
    }

    /* Method Name: add Pokédex Entry
     * Purpose: Calls userInputHelper method getBooleanInput to ask the user if they want to add a
     *          Pokédex Entry and then the getString method to get the Pokédex Entry.
     * Return Value: String of the Pokédex Entry (can be null)
     */
    public String addPokedexEntry() {
        String pokedexEntry = null;
        boolean userAnswer = userInputHelper.getBooleanInput("Would you like to add a Pokédex Entry?");

        if (userAnswer) {
            pokedexEntry = userInputHelper.getString("Enter Pokémon's Pokédex Entry: ");
        }

        return pokedexEntry;
    }

    /* Method Name: Add Pokémon From File Method for GUI
     * Purpose: Allows the user to specify a file location and add all Pokémon in that
     *          file to the system. Then looks through the lines calling the getPokémon Method
     * Parameters: The List that holds all Pokémon, File Location
     * Return Value: Validation Results (boolean, String)
     */
    public ValidationResults  addPokemonFromFileGUI(List<Pokemon> pokemonStorage, String fileLocation) {
        int pokemonListPreviousSize = pokemonStorage.size();
        int lineCounter = 0;
        int successfulPokemonCount = 0;
        String line;
        boolean isFileValid = true;
        String errorMessage = "";

        Pokemon newPokemon = null;

        // Attempts to read the file. Throws an error if it cannot be read.
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {

            while ((line = reader.readLine()) != null) {
                lineCounter++;
                successfulPokemonCount++;

                line = line.trim();
                // Finds and skips blank lines
                if (line.isEmpty()) {
                    successfulPokemonCount--;
                    continue;
                }

                String[] variables = line.split("\\|", -1);

                // Cancels input if a line does not meet the required length of variables.
                if (variables.length < 7 || variables.length > 8) {
                    isFileValid = false;
                    errorMessage = "\nLines must contain at least 7 variables. Error on line " + lineCounter + ".";
                    break;
                }

                ValidationResults returnedList = getPokemonForGUI(pokemonStorage, variables, lineCounter);

                if (returnedList != null) {
                    if (returnedList.getIsSuccess()) {
                        continue;
                    } else {
                        errorMessage = returnedList.getResultString();
                        isFileValid = false;
                    }
                } else {
                    isFileValid = false;
                }

            }
        } catch (IOException e) {
            errorMessage = "Error reading from file: " + e.getMessage();
            isFileValid = false;
        }
        // Checks if all Pokémon were entered. If not, it deletes all entries and goes back to
        // the main menu.
        if (isFileValid) {
            return new ValidationResults(true, String.valueOf(successfulPokemonCount));
        } else {
            // Used to remove the Pokémon that were added in this run if there was an error.
            while (pokemonStorage.size() > pokemonListPreviousSize) {
                pokemonStorage.remove(pokemonStorage.size() - 1);
            }
            return new ValidationResults(false, ("<html><body style='font-size:15pt'>"
                    + errorMessage + "<br> Pokémon have not been added. Please try again."));
        }
    }

    /* Method Name: Get Pokémon for GUI
     * Purpose: Called by addPokémonFromFile method. Used to take a String list and
     *          split it into the specified variables and create a new Pokémon with it.
     * Parameters: List of Pokémon, A List of Strings from each line of the text document, the
     *          counter used to check what current line the system is on.
     * Return Value: ValidationResults(Boolean, String)
     */
    public ValidationResults getPokemonForGUI(List<Pokemon> pokemonStorage, String[] variables,
                                 int lineCounter) {
        // Grab Pokémon Name
        String pokemonName = "";
        if (userInputHelper.hasNoDigitsOrSpaces(variables[0])) {
            pokemonName = variables[0];
        } else {
            return new ValidationResults(false, "Pokémon Name. Error can be " +
                    "found on line " + lineCounter + ".");
        }

        // Grab Pokédex Number
        int pokedexNumber;
        try {
            pokedexNumber = Integer.parseInt(variables[1]);
        } catch (NumberFormatException e) {
            return new ValidationResults(false, "Pokédex ID must be a number. Error can be " +
                    "found on line " + lineCounter + ".");
        }

        if (!(validateUniquePokedexNumber(pokedexNumber, pokemonStorage).getIsSuccess())) {
            return new ValidationResults(false, "Pokédex Number must be unique. Error " +
                    "can be found on line " + lineCounter + ".");
        } if (pokedexNumber < 1 || pokedexNumber > 1164) {
            return new ValidationResults(false, "Pokémon number must be between 1 and 1164. Error" +
                    " can be found on line "
                    + lineCounter + ".");
        }

        // Grab Pokémon Type(s)
        PokemonTypes primaryType;
        PokemonTypes secondaryType;
        PokemonTypesManager pokemonTypesList = null;

        // Checks if the type is valid. Throwing an exception if there is an error.
        try {
            if (variables[2] == null || variables[2].isEmpty()) {
                return new ValidationResults(false, "Pokémon type is empty. Error can be found on line " + lineCounter);
            }

            if (!userInputHelper.hasNoDigitsOrSpaces(variables[2])) {
                return new ValidationResults(false, "Pokémon type contains invalid characters (digits or spaces). " +
                        "Error on line " + lineCounter);
            }

            if (variables[2].contains("/")) {
                String[] types = variables[2].split("/");
                if (types.length != 2) {
                    return new ValidationResults(false, "Invalid type format (must be exactly one '/'). Error on " +
                            "line " + lineCounter);
                }
                if (types[0].equals(types[1])) {
                    return new ValidationResults(false, "Types cannot be the same. Error on " +
                            "line " + lineCounter);
                }

                primaryType = pokemonTypes.fromString(types[0].trim());
                secondaryType = pokemonTypes.fromString(types[1].trim());
            } else {
                primaryType = pokemonTypes.fromString(variables[2].trim());
                secondaryType = null;
            }
        } catch (IllegalArgumentException e) {
            return new ValidationResults(false, "Invalid Pokémon Type. Error can be found on line " + lineCounter);
        }

        pokemonTypesList = new PokemonTypesManager(primaryType, secondaryType);

        // Grab Pokémon Next Evolution
        Integer pokemonNextEvolution = null;
        try {
            pokemonNextEvolution = Integer.parseInt(variables[3]);
        } catch (NumberFormatException e) {
            return new ValidationResults(false, "Next Evolution Level must be an integer! Error found on line " + lineCounter);
        }

        if (pokemonNextEvolution < 0 || pokemonNextEvolution > 99) {
            return new ValidationResults(false, "Next Evolution Level must be between 0 and 99. " +
                    "Error found on line " + lineCounter);
        }

        // Grab Pokémon Weight
        BigDecimal pokemonWeight = new BigDecimal(0);
        if (userInputHelper.isDigitOrPeriod(variables[4])) {
            pokemonWeight = new BigDecimal(variables[4]);
        } else {
            return new ValidationResults(false, "Pokémon Weight must only contain numbers! Error can" +
                    " be found on line " + lineCounter);
        }

        // Grab Pokémon Height
        BigDecimal pokemonHeight = new BigDecimal(0);
        if (userInputHelper.isDigitOrPeriod(variables[5])) {
            pokemonHeight = new BigDecimal(variables[5]);
        } else {
            return new ValidationResults(false, "Pokémon Height must only contain numbers! Error" +
                    " can be found on line " + lineCounter);
        }

        // Grab if Pokémon has been caught or not
        boolean pokemonIsCaught;
        if (variables[6].equals("y") || variables[6].equals("n")) {
            pokemonIsCaught = variables[6].equals("y");
        } else {
            return new ValidationResults(false, "Been Caught must be \"y\" or \"n\"!" +
                    " Error can be found on line " + lineCounter);
        }

        // Grab Pokédex Entry
        String pokedexEntry = (variables.length == 8) ? variables[7] : null;

        if (pokedexEntry != null) {
            if (pokedexEntry.length() > 200) {
                return new ValidationResults(false, "Entry cannot exceed 200 characters. Error can be found on line " + lineCounter);
            }
        }

        try {
//            Pokemon newPokemon = new Pokemon(pokemonName, pokedexNumber, pokemonTypesList, pokemonNextEvolution,
//                pokemonWeight, pokemonHeight, pokemonIsCaught, pokedexEntry);
//            pokemonStorage.add(newPokemon);
            pokemon_DBHelper.insert(pokedexNumber, pokemonName, pokemonNextEvolution,
                    String.valueOf(pokemonWeight), String.valueOf(pokemonHeight),
                    (pokemonIsCaught ? 1 : 0), pokedexEntry,
                    types_DBHelper.getTypeIdByName(String.valueOf(primaryType)),
                    types_DBHelper.getTypeIdByName(String.valueOf(secondaryType)));

            pokemonTypes_DBHelper.insert(pokedexNumber,
                    types_DBHelper.getTypeIdByName(String.valueOf(pokemonTypesList.getPokemonPrimaryType())));
            if (secondaryType != null) {
                pokemonTypes_DBHelper.insert(pokedexNumber,
                        types_DBHelper.getTypeIdByName(String.valueOf(pokemonTypesList.getPokemonSecondaryType())));
            }
        } catch (InvalidPokedexNumberException | InputMismatchException |
                 NumberFormatException | InvalidPokemonTypeException e) {
            return new ValidationResults(false, "Pokémon was not added to list. Error can be found on line " + lineCounter);
        }
        return new ValidationResults(true, String.valueOf(lineCounter));
    }

    /* Method Name: Add Pokémon From File Method
     * Purpose: Allows the user to specify a file location and add all Pokémon in that
     *          file to the system. Then looks through the lines calling the getPokémon Method
     * Parameters: The List that holds all Pokémon
     * Return Value: String
     */
    public String addPokemonFromFile(List<Pokemon> pokemonStorage) {
        int pokemonListPreviousSize = pokemonStorage.size();
        String correctTxtFormat = """
               How the .txt file needs to be formatted:
                    - Each line must include between 7 to 8 variable for each Pokémon.
                    - The order must be:
                          - Pokémon Name: String.
                          - Pokédex Number: Integer between 1 and 1164.
                          - Pokémon Type: String. If there are 2 types it should have a "/" between them (Type1/Type2).
                          - Pokémon Next Evolution Level: Integer between 1 and 100. (If the Pokémon is the final evolution, enter "0".)
                          - Pokémon Weight: BigDecimal
                          - Pokémon Height: BigDecimal
                          - Has Pokémon Been Caught: y or n
                          - Pokédex Entry: String (OPTIONAL. Do not add a "|" after previous variable and just leave blank.)
                    - Only one Pokémon is allowed per line.
                    - Each variable are separated by a "|"
               Example of line (with and without a Pokédex Entry) (One has an evolution, one does not):
                    - Charmander|4|Fire|16|18.7|0.6|y|The flame on its tail shows the strength of its life-force. If Charmander is weak, the flame also burns weakly.
                    - Mewtwo|105|Psychic|0|122|2.0|n
               """;

        System.out.println(text.BLUE + "\nADDING POKÉMON FROM FILE!");
        System.out.println("-------------------------\n" + text.RESET);

        System.out.println(text.GREEN + correctTxtFormat + text.RESET);

        String fileLocation = userInputHelper.getString("Enter Pokémon File Location: ");
        String line;
        int lineCounter = 0;
        boolean isFileValid = true;

        // Attempts to read the file. Throws an error if it cannot be read.
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
            while ((line = reader.readLine()) != null) {
                lineCounter++;

                line = line.trim();
                // Finds and skips blank lines
                if (line.isEmpty()) {
                    System.out.println(userInputHelper.errorMessageColors("\n\nSkipping blank " +
                            "line at line " + lineCounter));
                    continue;
                }

                String[] variables = line.split("\\|", -1);

                // Cancels input if a line does not meet the required length of variables.
                if (variables.length < 7 || variables.length > 8) {
                    isFileValid = false;
                    System.out.println(userInputHelper.errorMessageColors(
                            "\nAll lines must contain at least 7 variables. Error can be found on line " + lineCounter + "."));
                    break;
                }

                //Attempts to create a Pokémon using a line and then adds it to the Pokémon storage.
                try {
                    Pokemon newPokemon = getPokemon(pokemonStorage, variables, lineCounter);

                    pokemonStorage.add(newPokemon);
                    System.out.println(text.CYAN + "\nLine " + lineCounter + ": " +
                            newPokemon.getPokemonName() + " has been added to the database." + text.RESET);

                } catch (InvalidPokedexNumberException | InputMismatchException | NumberFormatException |
                         InvalidPokemonTypeException e) {
                    System.out.println(userInputHelper.errorMessageColors(e.getMessage()));
                    isFileValid = false;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(userInputHelper.errorMessageColors("Error reading from file: " + e.getMessage()));
            isFileValid = false;
        }

        // Checks if all Pokémon were entered. If not, it deletes all entries and goes back to
        // the main menu.
        if (isFileValid) {
            return successMessageColors("All Pokémon added successfully!");
        } else {
            // Used to remove the Pokémon that were added in this run if there was an error.
            while (pokemonStorage.size() > pokemonListPreviousSize) {
                pokemonStorage.remove(pokemonStorage.size() - 1);
            }
            return userInputHelper.errorMessageColors("Please verify file and try again. " +
                    "Going back to the main Pokémon database menu.");
        }
    }

    /* Method Name: Get Pokémon Method
     * Purpose: Called by addPokémonFromFile method. Used to take a String list and
     *          split it into the specified variables and create a new Pokémon with it.
     * Parameters: List of Pokémon, A List of Strings from each line of the text document, the
     *          counter used to check what current line the system is on.
     * Return Value: Pokémon
     */
    public Pokemon getPokemon(List<Pokemon> pokemonStorage, String[] variables, int lineCounter) {
        // Grab Pokémon Name
        String pokemonName = "";
        if (userInputHelper.hasNoDigitsOrSpaces(variables[0])) {
            pokemonName = variables[0];
        } else {
            System.out.println("Numbers or spaces are not allowed in a Pokémon Name. Please " +
                    "try again.");
        }

        // Grab Pokédex Number
        int pokedexNumber = Integer.parseInt(variables[1]);

        if (validateUniquePokedexNumber(pokedexNumber, pokemonStorage).getIsSuccess()) {
            throw new InvalidPokedexNumberException("Pokédex Number must be unique. Error " +
                    "can be found on line " + lineCounter);
        } if (pokedexNumber < 1 || pokedexNumber > 1164) {
            throw new InvalidPokedexNumberException("Pokémon number " + pokedexNumber +
                    " is out of range. Must be between 1 and 1164.");
        }

        // Grab Pokémon Type(s)
        PokemonTypes primaryType;
        PokemonTypes secondaryType;
        PokemonTypesManager pokemonTypesList = null;

        // Checks if the type is valid. Throwing an exception if there is an error.
        try {
            if (variables[2] == null || variables[2].isEmpty()) {
                throw new InvalidPokemonTypeException("Pokémon type is empty. Error can be found on line " + lineCounter);
            }

            if (!userInputHelper.hasNoDigitsOrSpaces(variables[2])) {
                throw new InvalidPokemonTypeException("Pokémon type contains invalid characters (digits or spaces). " +
                        "Error on line " + lineCounter);
            }

            if (variables[2].contains("/")) {
                String[] types = variables[2].split("/");
                if (types.length != 2) {
                    throw new InvalidPokemonTypeException("Invalid type format (must be exactly one '/'). Error on " +
                            "line " + lineCounter);
                }

                primaryType = pokemonTypes.fromString(types[0].trim());
                secondaryType = pokemonTypes.fromString(types[1].trim());
            } else {
                primaryType = pokemonTypes.fromString(variables[2].trim());
                secondaryType = null;
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidPokemonTypeException("Invalid Pokémon Type. Error can be found on line " + lineCounter);
        }

        pokemonTypesList = new PokemonTypesManager(primaryType, secondaryType);


        // Grab Pokémon Next Evolution
        Integer pokemonNextEvolution = null;

        try {
            pokemonNextEvolution = Integer.parseInt(variables[3]);
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Next Evolution Level must be an integer! Error found on line " + lineCounter);
        }

        if (pokemonNextEvolution < 0 || pokemonNextEvolution > 100) {
            throw new InputMismatchException("\nNext Evolution Level must be between 0 and 100. " +
                    "Error found on line " + lineCounter);
        }

        // Grab Pokémon Weight
        BigDecimal pokemonWeight = new BigDecimal(0);
        if (userInputHelper.isDigitOrPeriod(variables[4])) {
            pokemonWeight = new BigDecimal(variables[4]);
        } else {
            throw new InputMismatchException("Pokémon Weight must only contain numbers! Error can" +
                    " be found on line " + lineCounter);
        }

        // Grab Pokémon Height
        BigDecimal pokemonHeight = new BigDecimal(0);
            if (userInputHelper.isDigitOrPeriod(variables[5])) {
                pokemonHeight = new BigDecimal(variables[5]);
            } else {
                throw new InputMismatchException("Pokémon Height must only contain numbers! Error" +
                        " can be found on line " + lineCounter);
            }

        // Grab if Pokémon has been caught or not
        boolean pokemonIsCaught;
        if (variables[6].equals("y") || variables[6].equals("n")) {
            pokemonIsCaught = variables[6].equals("y");
        } else {
            throw new InputMismatchException("Pokémon Is Caught can only have a \"y\" or a \"n\"!" +
                    " Error can be found on line " + lineCounter);
        }

        // Grab Pokédex Entry
        String pokedexEntry = (variables.length == 8) ? variables[7] : null;

        return new Pokemon(pokemonName, pokedexNumber, pokemonTypesList, pokemonNextEvolution,
                pokemonWeight, pokemonHeight, pokemonIsCaught, pokedexEntry);
    }

    /* Method Name: Update Pokémon Information
     * Purpose: Used to accept a Pokédex Number to specify a Pokémon to change specified piece of
     *          information. Then uses a switch to update a single piece of information.
     * Parameters: List of Pokémon
     * Return Value: String
     */
    public String updatePokemonInformation(List<Pokemon> pokemonStorage) {
        boolean userTryAgain = true;
        String answers = """
                    What piece of information you would like to update?
                        0. Cancel Update
                        1. Pokémon Name
                        2. Pokédex Number
                        3. Pokédex Type
                        4. Next Evolution Level
                        5. Pokémon Weight in Pounds
                        6. Pokémon Height in Meters
                        7. Have you Caught this Pokémon?
                        8. Pokédex Entry
                        
                    """;

        System.out.println(text.BLUE + "\nUPDATING POKÉMON INFORMATION!");
        System.out.println("-----------------------------\n" + text.RESET);

        // Loops asking the user for a valid Pokédex number until a Pokémon is found or the user
        // cancels the search.
        while (userTryAgain) {
            int userEnteredPokedexNum = userInputHelper.getInt(
                    "Enter the Pokédex Number you would like to search for: ",
                    "Pokédex number must be an integer!");

            Pokemon foundPokemon = null;
            for (Pokemon pokemon : pokemonStorage) {
                if (pokemon.getPokedexNumber() == userEnteredPokedexNum) {
                    foundPokemon = pokemon;
                    break;
                }
            }

            boolean userContinueToUpdate = true;
            if (foundPokemon != null) {
                System.out.println(successMessageColors("Pokémon found: " + foundPokemon.getPokemonName()));

                // Loops until the user cancels the update or gives a valid number
                while (userContinueToUpdate) {
                    System.out.print(text.GREEN + answers + text.RESET);

                    int userNumber = userInputHelper.getIntInRange(
                            "Please enter a number between 0 and 8 (0 to cancel update): ", 0, 8);
                    System.out.println(); //Used to separate line!

                    if (userNumber == 0) {
                        return successMessageColors("Pokémon update cancelled!");
                    }

                    // Switch used to specify what piece of information the user wants to update.
                    switch (userNumber) {
                        case 1 -> { // UPDATE Pokémon name
                            String newPokemonName = addPokemonName();
                            foundPokemon.setPokemonName(newPokemonName);
                        }
                        case 2 -> { // UPDATE Pokémon Pokédex number
                            int newPokedexNumber = addPokedexNumber(pokemonStorage);
                            foundPokemon.setPokedexNumber(newPokedexNumber);
                        }
                        case 3 -> { // UPDATE Pokémon Type(s)
                            PokemonTypesManager newPokemonTypesList = addPokemonTypes();
                            foundPokemon.setPokemonType(newPokemonTypesList);
                        }
                        case 4 -> { // UPDATE Pokémon next evolution level
                            Integer newEvolutionLevel = addEvolutionLevel();
                            foundPokemon.setNextEvolutionLevel(newEvolutionLevel);
                        }
                        case 5 -> { // UPDATE Pokémon Weight
                            BigDecimal newPokemonWeight = addPokemonWeightOrHeight("weight");
                            foundPokemon.setPokemonWeightPounds(newPokemonWeight);
                        }
                        case 6 -> { // UPDATE Pokémon Height
                            BigDecimal newPokemonHeight = addPokemonWeightOrHeight("height");
                            foundPokemon.setPokemonHeightMeters(newPokemonHeight);
                        }
                        case 7 -> { // UPDATE Pokémon if the Pokémon has been caught
                            boolean newHasPokemonBeenCaught = hasPokemonBeenCaught();
                            foundPokemon.setPokemonIsCaught(newHasPokemonBeenCaught);
                        }
                        case 8 -> { // UPDATE Pokémon Pokédex entry
                            String newPokedexEntry = addPokedexEntry();
                            foundPokemon.setPokedexEntry(newPokedexEntry);
                        }
                    }
                    userContinueToUpdate = userInputHelper.getBooleanInput("Would you like to update any " +
                            "more information for this Pokémon?");
                }
                return "Pokémon " + foundPokemon.getPokemonName() + " was updated!";
            }
            System.out.println(userInputHelper.errorMessageColors("Pokédex Number was not found."));
            userTryAgain = userInputHelper.getBooleanInput("Would you like to try again?");
        }
        return userInputHelper.errorMessageColors("No Pokémon updated!");
    }

    /* Method Name: Remove Pokémon By Pokédex Number
     * Purpose: Allows a user to remove a Pokémon using a Pokédex Number
     * Parameters: The List that holds all Pokémon
     * Return Value: String
     */
    public String removePokemonByPokedexID(List<Pokemon> pokemonStorage) {
        boolean userTryAgain = true;

        System.out.println(text.BLUE + "\nREMOVING AN EXISTING POKÉMON!");
        System.out.println("-----------------------------\n" + text.RESET);

        // Loops until the user gives a valid Pokédex Number
        while (userTryAgain){
            int userStatedPokedexID = userInputHelper.getInt(
                    "Enter the Pokédex number you would like to remove: ",
                    "Pokémon Pokédex Number cannot be anything other than a number. Please try again.");

            for (Pokemon pokemon : pokemonStorage) {
                if (pokemon.getPokedexNumber() == userStatedPokedexID) {
                    pokemonStorage.remove(pokemon);
                    System.out.println(successMessageColors("Pokédex Number " +
                            pokemon.getPokedexNumber() + " has been removed!"));
                    return successMessageColors("Pokémon Successfully Removed!");
                }
            }
            System.out.println(text.BRIGHT_RED + "Pokédex Number was " +
                    "not found." + text.RESET);

            userTryAgain = userInputHelper.getBooleanInput("Would you like to try again?");
        }

        return userInputHelper.errorMessageColors("No Pokémon was removed!");
    }

    /* Method Name: Find Pokémon By Pokédex ID Method
     * Purpose: Allows the user to search for a Pokémon using the Pokédex ID
     * Parameters: The List that holds all Pokémon
     * Return Value: String
     */
    public String findPokemonByPokedexID(List<Pokemon> pokemonStorage) {
        boolean userTryAgain = true;

        System.out.println(text.BLUE + "\nSEARCHING FOR AN EXISTING POKÉMON!");
        System.out.println("---------------------------------\n" + text.RESET);

        // Loops until the user gives a valid Pokédex Number
        while (userTryAgain) {
            int userStatedPokedexNum = userInputHelper.getInt(
                    "Enter the Pokédex Number you would like to find: ",
                    "Pokémon Pokédex Number cannot be anything other than a number. Please try again.");

            for (Pokemon pokemon : pokemonStorage) {
                if (pokemon.getPokedexNumber() == userStatedPokedexNum) {
                    return successMessageColors("FOUND POKÉMON:" + pokemon);
                }
            }

            System.out.println(userInputHelper.errorMessageColors("No Pokémon found with that Pokédex Number."));

            userTryAgain = userInputHelper.getBooleanInput("Would you like to try again?");
        }

        return userInputHelper.errorMessageColors("No Pokémon was found!");
    }

    /* Method Name: Compare Pokémon By Pokédex ID
     * Purpose: Allows the user to specify 2 Pokémon and compare their heights and weights.
     * Parameters: The List that holds all Pokémon
     * Return Value: String
     */
    public String comparePokemonByPokedexID(List<Pokemon> pokemonStorage) {
        boolean userTryAgain = true;

        System.out.println(text.BLUE + "\nCOMPARING WEIGHT AND HEIGHT OF TWO POKÉMON!");
        System.out.println("-------------------------------------------\n" + text.RESET);

        // Loops until the user gives two valid Pokédex Numbers
        while (userTryAgain) {
            int PokedexNumber1 = userInputHelper.getInt(
                    "Enter the first Pokédex Number you would like to compare: ",
                    "Pokémon Pokédex Number cannot be anything other than a number. Please try again.");
            int pokedexNumber2 = userInputHelper.getInt(
                    "Enter the second Pokédex Number you would like to compare: ",
                    "Pokémon Pokédex Number cannot be anything other than a number. Please try again.");

            Pokemon pokemon1 = null;
            Pokemon pokemon2 = null;

            for (Pokemon pokemon : pokemonStorage) {
                if (pokemon.getPokedexNumber() == PokedexNumber1) pokemon1 = pokemon;
                if (pokemon.getPokedexNumber() == pokedexNumber2) pokemon2 = pokemon;
            }

            if (pokemon1 == null || pokemon2 == null) {
                System.out.println(userInputHelper.errorMessageColors("One or both Pokémon were not found."));
            } else {
                BigDecimal weightDiff = pokemon1.getPokemonWeightKilograms().subtract(pokemon2.getPokemonWeightKilograms());
                BigDecimal heightDiff = pokemon1.getPokemonHeightMeters().subtract(pokemon2.getPokemonHeightMeters());

                StringBuilder pokemonComparisonResults = new StringBuilder();
                pokemonComparisonResults.append(text.MAGENTA + "FINAL POKÉMON COMPARISON RESULTS:\n     ");

                // Weight comparison
                if (weightDiff.signum() < 0) {
                    pokemonComparisonResults.append(pokemon2.getPokemonName()).append(" is heavier than ").append(pokemon1.getPokemonName())
                            .append(" by ").append(weightDiff.abs()).append(" kilograms.\n     ");
                } else if (weightDiff.signum() > 0) {
                    pokemonComparisonResults.append(pokemon1.getPokemonName()).append(" is heavier than ").append(pokemon2.getPokemonName())
                            .append(" by ").append(weightDiff.abs()).append(" kilograms.\n     ");
                } else {
                    pokemonComparisonResults.append("Both Pokémon weigh the same!\n     ");
                }

                // Height comparison
                if (heightDiff.signum() < 0) {
                    pokemonComparisonResults.append(pokemon2.getPokemonName()).append(" is taller than ").append(pokemon1.getPokemonName())
                            .append(" by ").append(heightDiff.abs()).append(" meters.\n");
                } else if (heightDiff.signum() > 0) {
                    pokemonComparisonResults.append(pokemon1.getPokemonName()).append(" is taller than ").append(pokemon2.getPokemonName())
                            .append(" by ").append(heightDiff.abs()).append(" meters.\n");
                } else {
                    pokemonComparisonResults.append("Both Pokémon are the same height!\n");
                }

                System.out.println(text.RESET);
                return pokemonComparisonResults.toString();
            }

            userTryAgain = userInputHelper.getBooleanInput("Would you like to try again?");
        }

        return userInputHelper.errorMessageColors("No comparisons made.");
    }

    /* Method Name: Check Pokémon Next Evolution
     * Purpose: Allows the user to specify a Pokédex Number and a current level and then states if
     *          the Pokémon has reached its max evolution or how many levels until it will evolve.
     * Parameters: The List that holds all Pokémon
     * Return Value: String
     */
    public String checkPokemonNextEvolution(List<Pokemon> pokemonStorage) {
        boolean userTryAgain = true;

        System.out.println(text.BLUE + "\nCHECKING HOW MANY LEVELS UNTIL A POKÉMON EVOLUTION!");
        System.out.println("---------------------------------------------------\n" + text.RESET);

        // Loops until the user gives a valid Pokédex Number
        while (userTryAgain) {
            Pokemon userPokemon = null;
            int userPokedexNumber = userInputHelper.getInt(
                    "Enter the Pokédex Number you would like to check next Evolution: ",
                    "Pokémon Pokédex Number cannot be anything other than a number. Please try again.");

            for (Pokemon pokemon : pokemonStorage) {
                if (pokemon.getPokedexNumber() == userPokedexNumber) {
                    userPokemon = pokemon;
                    break;
                }
            }

            if (userPokemon == null) {
                System.out.println(userInputHelper.errorMessageColors("No Pokémon found with that Pokédex Number."));
                userTryAgain = userInputHelper.getBooleanInput("Would you like to try again?");
                continue;
            }

            Integer nextEvolutionLevel = userPokemon.getNextEvolutionLevel();
            if (nextEvolutionLevel == null || nextEvolutionLevel == 0) {
                return successMessageColors("Pokémon has reached the final evolution!");
            }
            int currentEvoLevel = userInputHelper.getInt(
                    "Enter the current evolution level: ",
                    "Evolution level must be a valid number. Please try again.");

            if (currentEvoLevel >= nextEvolutionLevel) {
                System.out.println(userInputHelper.errorMessageColors("Current level exceeds or equals the next evolution level!"));
                userTryAgain = userInputHelper.getBooleanInput("Would you like to try again?");
                continue;
            }

            int evolutionLevelDiff = nextEvolutionLevel - currentEvoLevel;
            String isOrAre = evolutionLevelDiff == 1 ? " is " : " are ";

            return  successMessageColors ("\nFINAL POKÉMON COMPARISON RESULTS:\n" +
                    "There" + isOrAre + evolutionLevelDiff + " evolution levels until " +
                    userPokemon.getPokemonName() + " evolves.\n" +
                    userPokemon.getPokemonName() + " evolves at level: " + userPokemon.getNextEvolutionLevel());
        }
        return userInputHelper.errorMessageColors("No evolution check made.");
    }

    /* Method Name: print All Pokémon
     * Purpose: Used to print the list of Pokémon provided
     * Parameters: List of Pokémon
     * Return Value: String of the original message colored Magenta
     */
    public String printAllPokemon(List<Pokemon> pokemonStorage) {
        System.out.println(text.BLUE + "\nPRINTING ALL POKÉMON!");
        System.out.println("---------------------\n" + text.RESET);
        return text.CYAN + pokemonStorage + text.RESET;
    }

    /* Method Name: Success Message Colors
     * Purpose: Used to change the color of any success messages or results.
     * Parameters: String containing a success message
     * Return Value: String of the original message colored Magenta
     */
    public String successMessageColors(String successMessage) {
        return "\n" + text.MAGENTA + successMessage + text.RESET + "\n";
    }
}
