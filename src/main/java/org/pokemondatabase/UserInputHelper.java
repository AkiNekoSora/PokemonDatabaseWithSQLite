package org.pokemondatabase;

import org.pokemondatabase.exceptions.InvalidPokemonTypeException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Class Name: UserInputHelper
 * Purpose: Used for all the methods that accept user Input
 *          Checks in the input includes any digits or spaces
 *          Checks if the input is a digit or period.
 *          Includes methods that get these things from the user:
 *              - An Int
 *              - An Int in a specified range (With or without a specific error message)
 *              - A BigDecimal
 *              - A Boolean
 *              - A String
 *              - A valid Pokémon Name
 *              - Valid Pokémon Type(s)
 */

public class UserInputHelper {
    private final Scanner scnr = new Scanner(System.in);
    private final PokemonTypes pokemonTypes = PokemonTypes.FIRE;
    Text text = new Text();

    /* Method Name: Get Int
     * Purpose: Attempts to get an Int from the user until it is Valid
     * Parameters: The prompt(question) for the user and the error message.
     * Return Value: Int
     */
    public int getInt(String prompt, String errorMessage) {
        // Loops until valid int is given
        while (true) {
            try {
                System.out.print(prompt);
                int value = scnr.nextInt();
                scnr.nextLine(); // Used to consume the newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println(errorMessageColors(errorMessage));
                scnr.nextLine(); // Used to consume the newline
            }
        }
    }

    /* Method Name: Get Int In Range (method Overloader)
     * Purpose: Attempts to get an Int with specified range from the user until it is Valid.
     *          Calls getInt method.
     * Parameters: The prompt(question) for the user and the min and max options.
     * Return Value: Int
     */
    public int getIntInRange(String prompt, int min, int max) {
        String errorMessage = text.BRIGHT_RED +
                "Input must be between " + min + " and " + max + "." + text.RESET;

        // Loops until valid int in range is given
        while (true) {
            int value = getInt(prompt, errorMessage);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println(errorMessageColors(errorMessage));
        }
    }

    /* Method Name: Get Int In Range (method Overloader)
     * Purpose: Attempts to get an Int with specified range from the user until it is Valid.
     *          Calls getInt method.
     * Parameters: The prompt(question) for the user, min and max options, and the error message.
     * Return Value: Int
     */
    public int getIntInRange(String prompt, int min, int max, String errorMessage) {
        // Loops until valid int in range is given
        while (true) {
            int value = getInt(prompt, "Input must be a number. Please try again.");
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println(errorMessageColors(errorMessage));

        }
    }

    /* Method Name: Get BigDecimal
     * Purpose: Attempts to get a BigDecimal From the user until it is valid
     * Parameters: The prompt(question) for the user and the error message.
     * Return Value: BigDecimal
     */
    public BigDecimal getBigDecimal(String prompt, String errorMessage) {
        // Loops until valid BigDecimal is given
        while (true) {
            try {
                System.out.print(prompt);
                BigDecimal value = scnr.nextBigDecimal();
                scnr.nextLine(); // clear newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println(errorMessageColors(errorMessage));
                scnr.nextLine(); // Used to consume the newline
            }
        }
    }

    /* Method Name: Get Boolean Input
     * Purpose: Attempts to get a Boolean From the user until it is valid
     * Parameters: The prompt(question) for the user
     * Return Value: Boolean
     */
    public boolean getBooleanInput(String question) {
        // Loops until valid "y/yes" or "n/no" is given
        while (true) {
            System.out.print(question + " (y or n): ");
            String input = scnr.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println(errorMessageColors("Please enter \"y\" or \"n\"."));
            }
        }
    }

    /* Method Name: Get String
     * Purpose: Attempts to get a String From the user until it is valid
     * Parameters: The prompt(question) for the user
     * Return Value: String
     */
    public String getString(String prompt) {
        System.out.print(prompt);
        return scnr.nextLine().trim();
    }

    /* Method Name: Get Valid Pokémon Name
     * Purpose: Attempts to get a String From the user until it is valid.
     *          Calls getString method.
     * Parameters: The prompt(question) for the user
     * Return Value: String
     */
    public String getValidPokemonName(String prompt) {
        // Loops until valid String is given
        while (true) {
            String input = getString(prompt);

            if (hasNoDigitsOrSpaces(input)) {
                return input;
            } else {
                System.out.println(errorMessageColors("Invalid Pokémon name. Numbers or spaces are not allowed. Please try again."));

                boolean cancelAddPokemon = getBooleanInput("Would you like to cancel current " +
                        "process?");

                if (cancelAddPokemon) {
                    return null;
                }
            }
        }
    }

    /* Method Name: Get Valid Pokémon Type
     * Purpose: Attempts to get a Pokémon Type from the user until it is valid.
     *          Calls getString method.
     * Parameters: The prompt(question) for the user.
     * Return Value: PokémonTypes
     */
    public PokemonTypes getValidPokemonType(String prompt) {
        // Loops until valid Pokémon Type is given
        while (true) {
            String input = getString(prompt);

            try {
                return pokemonTypes.fromString(input);
            } catch (InvalidPokemonTypeException e) {
                System.out.println(errorMessageColors("\"" + input + "\" is not a valid Pokémon type. " +
                        "Please try again."));
            }
        }
    }

    /* Method Name: Has No Digits or Spaces
     * Purpose: Checks through a string. Checking if each char is either a digit or a space.
     *          Returns true if none of them are.
     * Parameters: String to check
     * Return Value: boolean
     */
    public boolean hasNoDigitsOrSpaces(String input) {
        return input.chars().noneMatch(c -> Character.isDigit(c) || Character.isWhitespace(c));
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

    /* Method Name: Error Message Colors
     * Purpose: Used to change the color of any error messages or results.
     * Parameters: String containing an error message
     * Return Value: String of the original message colored Bright Red
     */
    public String errorMessageColors(String errorMessage) {
        return text.BRIGHT_RED + errorMessage + text.RESET + "\n";
    }
}
