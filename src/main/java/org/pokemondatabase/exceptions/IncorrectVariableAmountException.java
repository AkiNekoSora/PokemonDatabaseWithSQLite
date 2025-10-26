/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Exception class used when a txt file line does not match the required amount of variables.
 */

package org.pokemondatabase.exceptions;

public class IncorrectVariableAmountException extends RuntimeException {
    /* Method Name: Incorrect Variable Amount Type Exception
     * Purpose: Used to return to CMI when a line in a txt file line does not match the required
     * variable length.
     * Parameters: A string message from the PokemonManager Class
     */
    public IncorrectVariableAmountException(String message) {
        super(message);
    }
}
