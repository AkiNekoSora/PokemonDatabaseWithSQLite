import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.pokemondatabase.PokemonTypes;
import org.pokemondatabase.UserInputHelper;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 12th, 2025
 *
 * Class Name: User Input Helper Tests
 * Purpose: Used to test that the UserInputHelper class methods work as needed. Uses Mockito to
 *          mock the UserInputHelper class and send fake user inputs to the system and uses setIn()
 *          method and ByteArrayInputStream to also fake user inputs.
 */
public class UserInputHelperTest {
    public UserInputHelper fakeUserInputHelper;
    public UserInputHelper mockUserInputHelper;

    /* Method Name: set up
     * Purpose: Used before each class to create mock userInputHelper classes.
     * Parameters: None
     * Return Value: Void
     */
    @BeforeEach
    public void setup() {
        fakeUserInputHelper = new UserInputHelper();
        mockUserInputHelper = mock(UserInputHelper.class);
    }

    /* Method Name: Test getInt() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting an int from
     *          the user.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test getInt() Method")
    public void testGetInt() {
        when(mockUserInputHelper.getInt(contains("Enter"), anyString())).thenReturn(7);

        int result = mockUserInputHelper.getInt("Enter an Int: ", "Error Message");

        assertEquals(7, result);
    }

    /* Method Name: Test getInt() Method - FAIL
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting an int from
     *          the user. Has the user enter an invalid option first before a valid one.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL - Test getInt() Method")
    public void testGetIntFAIL() {
        String input = "abc\n7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        int result = fakeUserInputHelper.getInt("Enter an Int: ", "Error Message");

        assertEquals(7, result);
    }

    /* Method Name: Test getIntInRange() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting an int
     *          within a specified range from the user. Has the user enter an invalid option
     *          first before a valid one.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test getIntInRange() Method")
    public void testGetIntInRange() {
        when(mockUserInputHelper.getIntInRange(contains("Enter"), eq(1), eq(10))).thenReturn(7);

        int result = mockUserInputHelper.getIntInRange("Enter an Int: ", 1, 10);

        assertEquals(7, result);
    }

    /* Method Name: Test getIntInRange() Method FAIL
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting an int
     *          within a specified range from the user.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL - Test getIntInRange() Method")
    public void testGetIntInRangeFAIL() {
        String input = "13\n7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        int result = fakeUserInputHelper.getIntInRange("Enter an Int: ", 1, 10);

        assertEquals(7, result);
    }

    /* Method Name: Test getBigDecimal() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a BigDecimal
     *          from the user.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test getBigDecimal() Method")
    public void testGetBigDecimal() {
        when(mockUserInputHelper.getBigDecimal(contains("Enter"), anyString())).thenReturn(new BigDecimal("7.7"));

        BigDecimal result = mockUserInputHelper.getBigDecimal("Enter an BigDecimal: ", "Error Message");

        assertEquals(new BigDecimal("7.7"), result);
    }

    /* Method Name: Test getBigDecimal() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a BigDecimal
     *          from the user. But the first attempt does not give a valid number.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL - Test getBigDecimal() Method")
    public void testGetBigDecimalFAIL() {
        String input = "ABC\n7.7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        BigDecimal result = fakeUserInputHelper.getBigDecimal("Enter an BigDecimal: ", "Error Message");

        assertEquals(new BigDecimal("7.7"), result);
    }

    /* Method Name: Test getBoolean() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a Boolean
     *          from the user.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test getBoolean() Method")
    public void testGetBoolean() {
        String input = "y";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        boolean result = fakeUserInputHelper.getBooleanInput("Enter ");

        assertEquals(true, result);
    }

    /* Method Name: Test getBoolean() Method - FAIL
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a Boolean
     *          from the user. The first attempt throws an error because it is a non-valid entry.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL - Test getBoolean() Method")
    public void testGetBooleanFAIL() {
        String input = "q\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();
        boolean result = fakeUserInputHelper.getBooleanInput("Enter ");

        assertEquals(true, result);
    }

    /* Method Name: Test getString() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a String
     *          from the user.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test getString() Method")
    public void testGetString() {
        String input = "TEST STRING";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        String result = fakeUserInputHelper.getString("Enter a String: ");

        assertEquals("TEST STRING", result);
    }

    /* Method Name: Test getValidPokemonName() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a valid
     *          Pokémon Name from the user.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test getString() Method")
    public void testGeValidPokemonName() {
        String input = "Charmander";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        String result = fakeUserInputHelper.getValidPokemonName("Enter a Name: ");

        assertEquals("Charmander", result);
    }

    /* Method Name: Test getValidPokemonName() Method - FAIL
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a valid
     *          Pokémon Name from the user. Starts with a non-valid one.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL - Test getValidPokemonName() Method")
    public void testGeValidPokemonNameFAIL() {
        String input = "2Charmander\nn\nCharmander";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        String result = fakeUserInputHelper.getValidPokemonName("Enter a Name: ");

        assertEquals("Charmander", result);
    }

    /* Method Name: Test getValidPokemonType() Method
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a valid
     *          Pokémon Type from the user.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test getValidPokemonType() Method")
    public void testGeValidPokemonType() {
        String input = "Fire";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        PokemonTypes result = fakeUserInputHelper.getValidPokemonType("Enter a Type: ");

        assertEquals(PokemonTypes.FIRE, result);
    }

    /* Method Name: Test getValidPokemonType() Method - FAIL
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test getting a valid
     *          Pokémon Type from the user. First input is not valid and throws an error.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL - Test getValidPokemonType() Method")
    public void testGeValidPokemonTypeFAIL() {
        String input = "Fir\nn\nFire\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserInputHelper fakeUserInputHelper = new UserInputHelper();

        PokemonTypes result = fakeUserInputHelper.getValidPokemonType("Enter a Type: ");

        assertEquals(PokemonTypes.FIRE, result);
    }

    /* Method Name: Test hasNoDigitsOrSpaces() Method
     * Purpose: Verifies the method passes when a valid string is given.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test hasNoDigitsOrSpaces() Method")
    public void testHasNoDigitsOrSpaces() {
        assertTrue(fakeUserInputHelper.hasNoDigitsOrSpaces("Charizard"));
    }

    /* Method Name: Test hasNoDigitsOrSpaces() Method (contains digits) FAIL
     * Purpose: Verifies the method fails when a string with digits is given.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL Test hasNoDigitsOrSpaces() Method (contains digits)")
    public void testHasNoDigitsOrSpacesContainsDigitsFAIL() {
        assertFalse(fakeUserInputHelper.hasNoDigitsOrSpaces("Charizard123"));
    }

    /* Method Name: Test hasNoDigitsOrSpaces() Method (contains spaces) FAIL
     * Purpose: Verifies the method fails when a string with spaces is given.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL Test hasNoDigitsOrSpaces() Method (contains spaces)")
    public void testHasNoDigitsOrSpacesContainsSpacesFAIL() {
        assertFalse(fakeUserInputHelper.hasNoDigitsOrSpaces("Char izard"));
    }

    /* Method Name: Test isDigitOrPeriod() Method
     * Purpose: Verifies the method passes when a valid digit is given.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test hasNoDigitsOrSpaces() Method")
    public void testIsDigitOrPeriod() {
        assertTrue(fakeUserInputHelper.isDigitOrPeriod("123.45"));
    }

    /* Method Name: Test isDigitOrPeriod() Method (Contains Letters) FAIL
     * Purpose: Verifies the method fails when a String with letters is given.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL Test hasNoDigitsOrSpaces() Method (Contains letters)")
    public void testIsDigitOrPeriodContainsLettersFAIL() {
        assertFalse(fakeUserInputHelper.isDigitOrPeriod("12a.45"));
    }

    /* Method Name: Test isDigitOrPeriod() Method (Contains Spaces) FAIL
     * Purpose: Verifies the method fails when a String with spaces is given.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("FAIL Test hasNoDigitsOrSpaces() Method (Contains spaces)")
    public void testIsDigitOrPeriodContainsSpacesFAIL() {
        assertFalse(fakeUserInputHelper.isDigitOrPeriod("123. 45"));
    }
}
