import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.pokemondatabase.PokemonTypes;
import org.pokemondatabase.PokemonTypesManager;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 13th, 2025
 *
 * Class Name: Pokémon Class Tests
 * Purpose: Used to test that the Pokémon class methods work as needed. And that a Pokémon can
 *          successfully be added using the class.
 */
class TypesManagerClassTests {
    private final PokemonTypesManager pokemonTypesManager = new PokemonTypesManager(PokemonTypes.FIRE);

    private PokemonTypes expectedPrimaryType;
    private PokemonTypes expectedSecondaryType;

    private PokemonTypesManager testingTypesWithTwoTypes;
    private PokemonTypesManager testingTypesWithOneType;

    /* Method Name: set up
     * Purpose: Used before each class to create a two new PokemonTypesManagers.
     *          One with one type and another with two types.
     * Parameters: None
     * Return Value: Void
     */
    @BeforeEach
    public void setUp() throws Exception {
        expectedPrimaryType = PokemonTypes.FIRE;
        expectedSecondaryType = PokemonTypes.FLYING;

        testingTypesWithTwoTypes = new PokemonTypesManager(expectedPrimaryType, expectedSecondaryType);
        testingTypesWithOneType = new PokemonTypesManager(expectedPrimaryType);
    }

    /* Method Name: PokémonTypes constructor test
     * Purpose: Tests to verify all Pokémon type variables have been assigned correctly and the
     *          getters work as required.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Are Fields assigned correctly? And do the getters work?")
    void testTypesConstructor() {
        assertEquals(expectedPrimaryType, testingTypesWithTwoTypes.getPokemonPrimaryType());
        assertEquals(expectedSecondaryType, testingTypesWithTwoTypes.getPokemonSecondaryType());
    }

    /* Method Name: PokémonTypes constructor test 2
     * Purpose: Tests to verify all Pokémon type variables have been assigned correctly and the
     *          getters work as required. Verifying the overloaded constructor.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Are Fields assigned correctly - Overloaded Constructor? And do the getters work?")
    void testTypesConstructor2() {
        assertEquals(expectedPrimaryType, testingTypesWithOneType.getPokemonPrimaryType());
        assertNull(testingTypesWithOneType.getPokemonSecondaryType());
    }

    /* Method Name: PokémonTypes toString test
     * Purpose: Tests to verify the toString method works correctly with one or two types.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Does the toString work correctly with a single or double type Pokemon?")
    void testToString() {
        assertEquals("Fire & Flying", testingTypesWithTwoTypes.toString());
        assertEquals("Fire", testingTypesWithOneType.toString());
    }

    /* Method Name: PokémonTypes noDuplicateTypes test
     * Purpose: Tests to verify noDuplicateTypes fails corrections
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Does the NoDuplicateTypes throw correctly?")
    void testNoDuplicateTypesReturnsFalse() {
        assertEquals(false, pokemonTypesManager.verifyNoDuplicatePokemonTypes(expectedSecondaryType
                , expectedSecondaryType));
    }

    /* Method Name: PokémonTypes noDuplicateTypes test
     * Purpose: Tests to verify noDuplicateTypes passes corrections
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Does the NoDuplicateTypesException not get thrown when type is not duplicated?")
    void testNoDuplicateTypesReturnsTrue() {
        assertEquals(true, pokemonTypesManager.verifyNoDuplicatePokemonTypes(expectedPrimaryType
                , expectedSecondaryType));
    }

    /* Method Name: PokémonTypes getAllPokemonTypes method
     * Purpose: Tests to verify getAllPokemonTypes works correctly with 1 or 2 types.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Testing getAllPokemonTypes method with One or Two Types")
    void testGetAllPokemonTypes() {
        assertEquals("Fire", testingTypesWithOneType.getAllPokemonTypes());
        assertEquals("Fire & Flying", testingTypesWithTwoTypes.getAllPokemonTypes());
    }
}
