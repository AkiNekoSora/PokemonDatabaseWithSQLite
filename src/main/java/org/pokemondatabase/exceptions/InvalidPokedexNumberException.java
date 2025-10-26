/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Exception class used when a user enters an invalid Pokédex Number.
 */

package org.pokemondatabase.exceptions;

public class InvalidPokedexNumberException extends RuntimeException {
    /* Method Name: Invalid Pokédex Number Exception
     * Purpose: Used to return to CMI when a user attempts to enter a Pokédex number already
     * in use or one that is not between 1 and 1164.
     * Parameters: A string message from the PokemonManager Class
     */
    public InvalidPokedexNumberException(String message) {
        super(message);
    }
}
