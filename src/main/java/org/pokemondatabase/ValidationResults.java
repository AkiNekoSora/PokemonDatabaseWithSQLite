package org.pokemondatabase;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 22nd, 2025
 *
 * Class Name: Validation Results
 * Purpose: Used to hold a boolean and String for results
 *          Contains:
 *              - Constructor
 *              - Overloaded Constructor
 *              - Getters
 */
public class ValidationResults {
    public final boolean success;
    public String resultString;

    // CONSTRUCTOR
    public ValidationResults(boolean success, String resultString) {
        this.success = success;
        this.resultString = resultString;
    }

    // OVERLOADED CONSTRUCTOR
    public ValidationResults(boolean success) {
        this.success = success;
    }

    // GETTERS
    public boolean getIsSuccess() {return success;}
    public String getResultString() {return resultString;}
}

