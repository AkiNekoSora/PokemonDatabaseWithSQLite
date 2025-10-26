import org.junit.Test;
import org.pokemondatabase.exceptions.IncorrectVariableAmountException;
import org.pokemondatabase.exceptions.InvalidPokedexNumberException;
import org.pokemondatabase.exceptions.InvalidPokemonTypeException;
import org.pokemondatabase.exceptions.NoDuplicateTypesException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 7th, 2025
 *
 * Class Name: ExceptionClass Tests
 * Purpose: Used to test all exceptions classes work as needed.
 */
public class ExceptionClassTests {
    /* Method Name: Test NoDuplicateTypesException class
     * Purpose: Tests to make sure the NoDuplicateTypesException class throws an exception
     * Parameters: None
     * Return Value: Void
     */
    @Test
    public void testNoDuplicateTypesException() {
        String errorMessage = "Duplicate Pokemon Types are not allowed.";
        NoDuplicateTypesException ex = new NoDuplicateTypesException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }

    /* Method Name: Test InvalidPokemonTypeException class
     * Purpose: Tests to make sure the InvalidPokemonTypeException class throws an exception
     * Parameters: None
     * Return Value: Void
     */
    @Test
    public void testInvalidPokemonTypeException() {
        String errorMessage = "Invalid Pokemon Types are not allowed.";
        InvalidPokemonTypeException ex = new InvalidPokemonTypeException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }

    /* Method Name: Test InvalidPokedexNumberException class
     * Purpose: Tests to make sure the InvalidPokedexNumberException class throws an exception
     * Parameters: None
     * Return Value: Void
     */
    @Test
    public void testInvalidPokedexNumberException() {
        String errorMessage = "Must be a valid Number between 1 and 1164.";
        InvalidPokedexNumberException ex = new InvalidPokedexNumberException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }

    /* Method Name: Test IncorrectVariableAmountException class
     * Purpose: Tests to make sure the IncorrectVariableAmountException class throws an exception
     * Parameters: None
     * Return Value: Void
     */
    @Test
    public void testIncorrectVariableAmountException() {
        String errorMessage = "There must be 7-8 variables per line.";
        IncorrectVariableAmountException ex = new IncorrectVariableAmountException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }


}
