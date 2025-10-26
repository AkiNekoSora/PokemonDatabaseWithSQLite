package org.pokemondatabase;

import org.pokemondatabase.exceptions.NoDuplicateTypesException;
import java.util.Objects;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Class Name: PokémonTypesManager
 * Purpose: Used to hold up to two Pokémon Types
 *          Verifies that a secondary type is not the same as the primary type.
 *          Prints a nicer version of types if there is one or two.
 */

public class PokemonTypesManager {
    private PokemonTypes primaryType;
    private PokemonTypes secondaryType;

    // Constructor
    public PokemonTypesManager(PokemonTypes pokemonType1, PokemonTypes pokemonType2) {
        this.primaryType = pokemonType1;

        if (pokemonType2 != null) {
            this.secondaryType = pokemonType2;
        }
    }

    // Overloaded Constructor
    public PokemonTypesManager(PokemonTypes pokemonType1) {
        this.primaryType = pokemonType1;
    }

    // Getters!
    public PokemonTypes getPokemonPrimaryType() {return primaryType;}
    public PokemonTypes getPokemonSecondaryType() {return secondaryType;}

    // Setters!
    public void setPokemonPrimaryType(PokemonTypes pokemonType1) {this.primaryType = pokemonType1;}
    public void setPokemonSecondaryType(PokemonTypes pokemonType2) {
        try {
            if (verifyNoDuplicatePokemonTypes(this.primaryType, pokemonType2)) {
                this.secondaryType = pokemonType2;
            } else {
                throw new NoDuplicateTypesException("Secondary type can't be the same as primary.");
            }
        } catch (NoDuplicateTypesException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Method Name: Verify No Duplicate Pokémon Types
     * Purpose: Called by setPokémonSecondaryType to check if the secondary type matches the first.
     * Parameters: PokémonTypes primary and secondary
     * Return Value: String of a nice version of the types.
     */
    public boolean verifyNoDuplicatePokemonTypes(PokemonTypes primaryType, PokemonTypes secondaryType) {
        return !Objects.equals(secondaryType, primaryType);
    }

    /* Method Name: Get All Pokémon Types
     * Purpose: Called by toString method to print the types nicely if there is one or two
     * Parameters: None
     * Return Value: String of a nice version of the types.
     */
    public String getAllPokemonTypes() {
        if (this.secondaryType == null) {
            return this.primaryType.toString();
        } else {
            return this.primaryType + " & " + this.secondaryType;
        }
    }

    /* Method Name: To String Method Override
     * Purpose: Overrides the original toString method to change how it prints when someone
     *          prints the type(s) of a Pokémon. Calls the getAllPokémonTypes method.
     * Parameters: None
     * Return Value: String with either one or two types.
     */
    @Override
    public String toString() {
        return this.getAllPokemonTypes();
    }



}
