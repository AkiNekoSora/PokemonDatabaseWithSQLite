package org.pokemondatabase;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 5th, 2025
 *
 * Class Name: Pokémon
 * Purpose: Used to create new Pokémon.
 *          Contains:
 *              - Constructor
 *              - Getters/Setters
 *              - toString
 *              - 3 private methods that are used by the toString method that changes the output
 *                to be easier to understand and look nicer.
 */
public class Pokemon {

    // Pokémon Variables!
    private String pokemonName;
    private int pokedexNumber;
    private PokemonTypesManager pokemonTypes;
    private Integer nextEvolutionLevel;
    private BigDecimal pokemonWeightKg;
    private BigDecimal pokemonHeightMeters;
    private Boolean pokemonIsCaught;
    private String pokedexEntry;

    // Constructor for the Pokémon Class!
    public Pokemon(String pokemonName, int PokedexNumber, PokemonTypesManager pokemonTypes,
                   Integer nextEvolutionLevel, BigDecimal pokemonWeightPounds,
                   BigDecimal pokemonHeightMeters,
                   Boolean pokemonIsCaught, String pokedexEntry) {
        this.pokemonName = formatPokemonName(pokemonName);
        this.pokedexNumber = PokedexNumber;
        this.pokemonTypes = pokemonTypes;
        this.nextEvolutionLevel = nextEvolutionLevel;
        this.pokemonWeightKg = pokemonWeightPounds;
        this.pokemonHeightMeters = pokemonHeightMeters;
        this.pokemonIsCaught = pokemonIsCaught;
        this.pokedexEntry = pokedexEntry;
    }

    // Getters!
    public String getPokemonName() {return pokemonName;}
    public int getPokedexNumber() {return pokedexNumber;}
    public PokemonTypesManager getPokemonType() {return pokemonTypes;}
    public PokemonTypes getPrimaryType() {return pokemonTypes.getPokemonPrimaryType();}
    public PokemonTypes getSecondaryType() {return pokemonTypes.getPokemonSecondaryType();}
    public Integer getNextEvolutionLevel() {return nextEvolutionLevel;}
    public BigDecimal getPokemonWeightKilograms() {return pokemonWeightKg;}
    public BigDecimal getPokemonHeightMeters() {return pokemonHeightMeters;}
    public Boolean getPokemonIsCaught() {return pokemonIsCaught;}
    public String getPokedexEntry() {return pokedexEntry;}

    // Setters!
    public String setPokemonName(String pokemonName) {
        this.pokemonName = formatPokemonName(pokemonName);
        return "Pokémon Name updated to: " + this.pokemonName;
    }
    public String setPokedexNumber(int pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
        return "Pokédex Number has been updated to: " + String.valueOf(this.pokedexNumber);
    }
    public String setPokemonType(PokemonTypesManager pokemonTypes) {
        this.pokemonTypes = pokemonTypes;
        return "Pokémon Type has been updated to: " + pokemonTypes;
    }
    public String setNextEvolutionLevel(Integer nextEvolutionLevel) {
        this.nextEvolutionLevel = nextEvolutionLevel;
        return "Pokémon Next Evolution as been updated to: " + String.valueOf(this.nextEvolutionLevel);
    }
    public String setPokemonWeightPounds(BigDecimal pokemonWeightPounds) {
        this.pokemonWeightKg = pokemonWeightPounds;
        return "Pokémon Weight has been updated to: " + String.valueOf(this.pokemonWeightKg);
    }
    public String setPokemonHeightMeters(BigDecimal pokemonHeightMeters) {
        this.pokemonHeightMeters = pokemonHeightMeters;
        return "Pokémon Height has been updated to: " + String.valueOf(this.pokemonHeightMeters);
    }
    public String setPokemonIsCaught(boolean pokemonIsCaught) {
        this.pokemonIsCaught = pokemonIsCaught;
        return "'Has Pokémon been caught?' has been updated to: " + hasPokemonBeenCaught();
    }
    public String setPokedexEntry(String pokedexEntry) {
        this.pokedexEntry = pokedexEntry;
        return "Pokémon Name updated to: \"" + pokedexEntry + "\"";
    }

    /* Method Name: Format Pokemon Name
     * Purpose: formats Pokemon name to have capitals after a space, dash, and at the start
     * Parameters: Pokemon Name
     * Return Value: String
     */
    public String formatPokemonName(String pokemonName) {
        if (pokemonName == null || pokemonName.isEmpty()) {
            return pokemonName;
        }

        pokemonName = pokemonName.toLowerCase().trim();
        char[] chars = pokemonName.toCharArray();
        boolean capitalizeNext = true;

        // Cycles through the String and capitalizes after white space, -, /, or first number
        for (int i = 0; i < chars.length; i++) {
            if (Character.isWhitespace(chars[i]) || chars[i] == '-' || chars[i] == '\'') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                chars[i] = Character.toUpperCase(chars[i]);
                capitalizeNext = false;
            }
        }
        return new String(chars);
    }

    /* Method Name: has Pokémon Been Caught (Called by toString method)
     * Purpose: Checks to see if the PokémonIsCaught variable is true or false. If true it returns
     *          "Caught" if false it returns "Not Caught"
     * Parameters: None
     * Return Value: String with either "Caught" or "Not Caught"
     */
    public String hasPokemonBeenCaught (){
        String pokemonCaughtAnswer;

        if (pokemonIsCaught) {
            pokemonCaughtAnswer = "Caught";
        } else {
            pokemonCaughtAnswer = "Not Caught";
        }
        return pokemonCaughtAnswer;
    }

    /* Method Name: has next Evolution (Called by toString method)
     * Purpose: Checks to see if the nextEvolution variable is null or 0. If null, it prints
     *          "FULLY EVOLVED"
     * Parameters: None
     * Return Value: String with either the Pokédex Next Evolution level or "NO NEXT EVOLUTION."
     */
    public String hasNextEvolution(){
        if (this.nextEvolutionLevel == null || this.nextEvolutionLevel == 0) {
            return "FULLY EVOLVED";
        } else {
            return String.valueOf(this.nextEvolutionLevel);
        }
    }

    /* Method Name: has Pokédex Entry (Called by toString method)
     * Purpose: Checks to see if the Pokédex entry is null. If null, it prints "NO Pokédex ENTRY."
     * Parameters: None
     * Return Value: String with either the Pokédex Entry or "NO Pokédex ENTRY."
     */
    public String hasPokedexEntry() {
        return Objects.requireNonNullElse(this.pokedexEntry, "NO POKÉDEX ENTRY.");
    }

    /* Method Name: To String Method Override
     * Purpose: Overrides the original toString method to change how it prints when someone
     *          prints a Pokémon.
     * Parameters: None
     * Return Value: String with all Pokémon information.
     */
    @Override
    public String toString() {
        return  "\n" +
                "    Pokémon Name: " + this.pokemonName + "\n" +
                "    Pokédex Number: " + this.pokedexNumber + "\n" +
                "    Pokédex Type: " + this.pokemonTypes.toString() + "\n" +
                "    Next Evolution Level: " + hasNextEvolution() + "\n" +
                "    Pokémon Weight in Pounds: " + this.pokemonWeightKg + "\n" +
                "    Pokémon Height in Meters: " + this.pokemonHeightMeters + "\n" +
                "    Have you Caught this Pokémon?: " + hasPokemonBeenCaught() + "\n" +
                "    Pokédex Entry: " + hasPokedexEntry() + "\n";
    }

}
