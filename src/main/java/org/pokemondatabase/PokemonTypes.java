package org.pokemondatabase;
import org.pokemondatabase.exceptions.InvalidPokemonTypeException;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Class Name: Pokémon Types
 * Purpose: A class used to hold the group of Type constants.
 * Methods:
 *      - toString method used to print the Type.
 *      - fromString method used to obtain the type enum.
 */
public enum PokemonTypes {
    NORMAL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DARK,
    DRAGON,
    STEEL,
    FAIRY;

    /* Method Name: From String
     * Purpose: Attempts to return the enum value of the input. If it fails it throws an
     *          InvalidPokémonTypeException.
     * Parameters: String
     * Return Value: PokémonTypes
     */
    public PokemonTypes fromString(String input) {
        try {
            return PokemonTypes.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPokemonTypeException("Invalid Pokémon Type: " + input);
        }
    }

    /* Method Name: To String Method Override
     * Purpose: Overrides the original toString method to change how it prints when someone
     *          prints a Type.
     * Parameters: None
     * Return Value: String with Type
     */
    @Override
    public String toString() {
        String type = name().toLowerCase();
        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }
}
