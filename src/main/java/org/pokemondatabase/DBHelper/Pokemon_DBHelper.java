package org.pokemondatabase.DBHelper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * November 2nd, 2025
 *
 * Class Name: Pokemon_DB Helper
 * Purpose: Used for the Pokémon table to accept SQL queries (with execute and getExecuteResults) and
 * 			prepare the SQL queries using Select, insert, update, delete, and selectToTable.
 * 			Holds all current column types as variables.
 *
 * Created using the SQLHelper GUI
 */
public class Pokemon_DBHelper {
	private final String TABLE_NAME = "Pokemon";
	public static final String pokedex_number = "pokedex_number";
	public static final String pokemon_name = "pokemon_name";
	public static final String next_evolution_level = "next_evolution_level";
	public static final String weight = "weight";
	public static final String height = "height";
	public static final String has_been_caught = "has_been_caught";
	public static final String pokedex_entry = "pokedex_entry";
	public static final String primary_type = "primary_type";
	public static final String secondary_type = "secondary_type";

	private final DBHelper helper;

	// CONSTRUCTOR
	public Pokemon_DBHelper(DBHelper dbHelper) {
		this.helper = dbHelper;
	}

	/* Name: prepareSQL
	 * Purpose: prepares the text of a SQL "select" command.
	 * Return Type: String
	 * Arguments:
	 * 				fields: the fields to be displayed in the output
	 * 				whatField: field to search for
	 * 				whatValue: value to search for within whatField
	 * 				sort: use ASC or DESC to specify the sorting order
	 * 				softField: the field that the data will be sorted by
	 */
	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

	/* Name: insert
	 * Purpose: insert a new record into the database
	 * Return Type: void
	 * Arguments: each field listed in the table from the database, in order
	 */
	public void insert(Integer pokedex_number, String pokemon_name, Integer next_evolution_level, String weight, String height, Integer has_been_caught, String pokedex_entry, Integer primary_type, Integer secondary_type) {
		pokemon_name = pokemon_name != null ? "\"" + pokemon_name + "\"" : null;
		weight = weight != null ? "\"" + weight + "\"" : null;
		height = height != null ? "\"" + height + "\"" : null;
		pokedex_entry = pokedex_entry != null ? "\"" + pokedex_entry + "\"" : null;
		
		Object[] values_ar = {pokedex_number, pokemon_name, next_evolution_level, weight, height, has_been_caught, pokedex_entry, primary_type, secondary_type};
		String[] fields_ar = {Pokemon_DBHelper.pokedex_number, Pokemon_DBHelper.pokemon_name, Pokemon_DBHelper.next_evolution_level, Pokemon_DBHelper.weight, Pokemon_DBHelper.height, Pokemon_DBHelper.has_been_caught, Pokemon_DBHelper.pokedex_entry, Pokemon_DBHelper.primary_type, Pokemon_DBHelper.secondary_type};
		String values = "", fields = "";
		for (int i = 0; i < values_ar.length; i++) {
			if (values_ar[i] != null) {
				values += values_ar[i] + ", ";
				fields += fields_ar[i] + ", ";
			}
		}
		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 2);
			fields = fields.substring(0, fields.length() - 2);
			helper.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values +
					")" +
					";");
		}
	}

	/* Name: delete
	 * Purpose: remove a record from the database
	 * Return Type: void
	 * Arguments: the field(key) used to determine if a row should be deleted, and the value removed.
	 */
	public void delete(String whatField, String whatValue) {
		helper.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue +
				";");
	}

	/* Name: update
	 * Purpose: update a record in the database
	 * Return Type: void
	 * Arguments: the field(key) used to determine if a row should be updated, the value it will be updated to.
	 * 			  Where the field is and where the value is.
	 */
	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		helper.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue +
				"\"" +
				" " +
				"where" +
				" " + whereField + " = \"" + whereValue + "\";");
	}

	/* Name: updateCaughtStatur
	 * Purpose: update the caught status in the database
	 * Return Type: void
	 * Arguments: boolean with the current caught status and the where (Pokédex number)
	 */
	public void updateCaughtStatus(Boolean currentCaughtStatus, String whereValue) {
		if (currentCaughtStatus){
			helper.execute("UPDATE " + TABLE_NAME + " set has_been_caught = \"0\" " +
					"where pokedex_number = \"" + whereValue + "\";");
		} else {
			helper.execute("UPDATE " + TABLE_NAME + " set has_been_caught = \"1\" " +
					"where pokedex_number = \"" + whereValue + "\";");
		}
	}

	/* Name: updateCaughtStatus
	 * Purpose: updates all columns in the database
	 * Return Type: void
	 * Arguments: new Pokédex number, Pokémon name, next evolution level, weight, height, caught
	 * status, Pokédex entry, primary and secondary type, and the current Pokédex number
	 */
	public void updateAll(String newPokedexNumber, String newPokemonName, String newEvoLevel,
						  String newWeight, String newHeight, String newCaughtStatus,
						  String newEntry, String newPrimaryType,
						  String newSecondaryType, String currentPokedexNumber) {

		//NAME
		helper.execute("UPDATE " + TABLE_NAME + " set pokemon_name = \"" + newPokemonName +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		// POKEDEX NUMBER
		helper.execute("UPDATE " + TABLE_NAME + " set next_evolution_level = \"" + newEvoLevel +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		// WEIGHT
		helper.execute("UPDATE " + TABLE_NAME + " set weight = \"" + newWeight +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		//HEIGHT
		helper.execute("UPDATE " + TABLE_NAME + " set height = \"" + newHeight +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		//CAUGHT STATUS
		helper.execute("UPDATE " + TABLE_NAME + " set has_been_caught = \"" + newCaughtStatus +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		//POKEDEX ENTRY
		helper.execute("UPDATE " + TABLE_NAME + " set pokedex_entry = \"" + newEntry +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		//PRIMARY TYPE
		helper.execute("UPDATE " + TABLE_NAME + " set primary_type = \"" + newPrimaryType +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		//SECONDARY TYPE
		helper.execute("UPDATE " + TABLE_NAME + " set secondary_type = \"" + newSecondaryType +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");

		//POKEDEX NUMBER
		helper.execute("UPDATE " + TABLE_NAME + " set pokedex_number = \"" + newPokedexNumber +
				"\" where pokedex_number = \"" + currentPokedexNumber + "\";");
	}

	/* Name: select - specifically made to run a select statement
	 * Purpose: completes a SQL "select" command
	 * Return Type: ArrayList<ArrayList<Object>> - this means it returns a 2D Array of object, can be any type.
	 * Arguments(same as prepareSQL method because it calls it):
	 * 				fields: the fields to be displayed in the output
	 * 				whatField: the field to search within
	 * 				whatValue: value to search for within whatField
	 * 				sort: use ASC or DESC to specify the sorting order
	 * 				sortField: the field that the data will be sorted by
	 */
	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return helper.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	/* Name: getExecuteResult
	 *		- used to run all other queries that do not fit into the select statement.
	 *      - used when you expect a return to the screen
	 * Purpose: performs a search of the database, where String "query" is the command that would be entered on
	 * 			the command line.
	 * Return Type: ArrayList<ArrayList<Object>> - this means it returns a 2D Array of object, can be any type.
	 * Arguments: query - this is the SQL command that would be entered at the command line for a SQL query
	 */
	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return helper.executeQuery(query);
	}

	/* Name: execute
	 * 		   - does the same thing as the getExecuteResult method but does not return anything.
	 * 		   - used when you are not expecting anything to be returned to the screen
	 * Purpose: performs a search of the database, where String "query" is the command that would be entered on
	 * 			the command line.
	 * Return Type: void
	 * Arguments: query - this is the SQL command that would be entered at the command line for a SQL query
	 */
	public void execute(String query) {
		helper.execute(query);
	}

	/* Name: selectToTable - Similar to the select method
	 * Purpose: performs a search of the database, where String query is the SQL command that would be entered
	 * 			on the command line.
	 * Return Type: DefaultTableModel - uses a vector of vectors (A table) to store data
	 * Arguments(same as prepareSQL method because it calls it):
	 * 				fields: the fields to be displayed in the output
	 * 				whatField: the field to search within
	 * 				whatValue: value to search for within whatField
	 * 				sort: use ASC or DESC to specify the sorting order
	 * 				sortField: the field that the data will be sorted by
	 */
	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return helper.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField,
				sort));
	}

}