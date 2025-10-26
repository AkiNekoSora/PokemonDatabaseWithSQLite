/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Exception class used when a user tries to enter a duplicate type.
 */
package org.pokemondatabase.exceptions;

public class NoDuplicateTypesException extends RuntimeException {
    /* Method Name: No Duplicate Types Exception
     * Purpose: Used to return to CMI when a user attempts to enter the same type twice
     * Parameters: A string message from the Types Class
     */
    public NoDuplicateTypesException(String message) {
        super(message);
    }
}
