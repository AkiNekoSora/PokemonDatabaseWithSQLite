package org.pokemondatabase;

import java.util.List;

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
    private final boolean success;
    private String resultString;
    private Pokemon pokemon;

    // CONSTRUCTOR
    public ValidationResults(boolean success, String resultString) {
        this.success = success;
        this.resultString = resultString;
    }

    // OVERLOADED CONSTRUCTOR
    public ValidationResults(boolean success) {
        this.success = success;
    }

    // OVERLOADED CONSTRUCTOR
    public ValidationResults(boolean success, String resultString, Pokemon pokemon) {
        this.success = success;
        this.resultString = resultString;
        this.pokemon = pokemon;
    }

    // GETTERS
    public boolean getIsSuccess() {return success;}
    public String getResultString() {return resultString;}
    public Pokemon getPokemon() {return pokemon;}
}

