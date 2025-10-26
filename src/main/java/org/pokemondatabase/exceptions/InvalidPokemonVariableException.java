/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Exception class used when a user enters an invalid Pokemon type.
 */

package org.pokemondatabase.exceptions;

public class InvalidPokemonVariableException extends IllegalArgumentException {
    /* Method Name: Invalid Pokemon Type Exception
     * Purpose: Used to return to CMI when a user attempts to enter an invalid Pokemon type.
     * Parameters: A string message from the PokemonManager Class
     */
    public InvalidPokemonVariableException(String message) {
        super(message);
    }

    public InvalidPokemonVariableException(String message, Throwable cause) {
        super(message, cause);
    }
}
