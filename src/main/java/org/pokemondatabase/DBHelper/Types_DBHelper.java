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
public class Types_DBHelper {
	private final String TABLE_NAME = "Types";
	public static final String type_id = "type_id";
	public static final String type_name = "type_name";

	private final DBHelper helper;

	// CONSTRUCTOR
	public Types_DBHelper(DBHelper dbHelper) {
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
	public void insert(Integer type_id, String type_name) {
		type_name = type_name != null ? "\"" + type_name + "\"" : null;
		
		Object[] values_ar = {type_id, type_name};
		String[] fields_ar = {Types_DBHelper.type_id, Types_DBHelper.type_name};
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
			helper.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ")" +
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
		helper.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" " +
				"where" +
				" " + whereField + " = \"" + whereValue + "\";");
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

	/* Name: getTypeIDByName
	 * Purpose: returns the id of the type when the name of the type is given
	 * Return Type: Integer (Type ID)
	 * Arguments: String with the Type name
	 */
	public Integer getTypeIdByName(String typeName) {
		if (typeName == null || typeName.trim().isEmpty()) {return 0;}

		ArrayList<ArrayList<Object>> result = select("type_id", "type_name", typeName, null, null);

		// Checks to verify the result is not empty before returning
		if (result != null && !result.isEmpty() && !result.get(0).isEmpty()) {
			Object value = result.get(0).get(0);
			if (value instanceof Number) {
				return ((Number) value).intValue();
			} else {
				try {
					return Integer.parseInt(value.toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}

		return 0;
	}

	/* Name: getTypeNameByID
	 * Purpose: returns the type name when the ID of the type is given
	 * Return Type: String (Type Name)
	 * Arguments: Int with the Type ID
	 */
	public String getTypeNameByID(Integer typeID) {
		if (typeID == null) {return null;}

		ArrayList<ArrayList<Object>> result = select("type_name", "type_id",
				String.valueOf(typeID), null, null);

		// Checks to verify the result is not empty before returning
		if (result != null && !result.isEmpty()) {
			ArrayList<Object> row = result.get(0);
			if (row != null && !row.isEmpty() && row.get(0) != null) {
				return row.get(0).toString();
			}
		}

		return null;
	}

}