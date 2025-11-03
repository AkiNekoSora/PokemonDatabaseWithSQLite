package org.pokemondatabase.DBHelper;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * November 2nd, 2025
 *
 * Class Name: DB Helper
 * Purpose: Used as a base to be able to connect and close the database, and has methods that can be called by both
 * 			subclasses to interact with database tables.
 *          Contains:
 *              execute, executeQueryToTable, and executeQuery methods that both subclasses use to be able
 * 				to interact with the database. (Both other classes use "super" to use these methods.
 *
 * Created using the SQLHelper GUI. Changes made to make it work for my needs!
 */
public class DBHelper {
	private String DATABASE_NAME;
	private Connection connection;
	public Boolean connected = false;

	public final Pokemon_DBHelper pokemon;
	public final Types_DBHelper types;

	// CONSTRUCTOR (used to call the other helpers)
	public DBHelper() {
		this.pokemon = new Pokemon_DBHelper(this);
		this.types = new Types_DBHelper(this);
	}

	/* Name: Connect
	 * Purpose: Attempts to connect to the database catching if there are any exceptions thrown.
	 * 			Called by methods in this class.
	 * Return Type: Void. Just starts the database connection.
	 * Arguments: String of database path
	 * Notes: Changed to public so the user can choose what database to connect to. And changed
	 * to synchronized to keep it working with all DB_helpers
	 */
	public synchronized void connect(String databasePath) {
		// Try-Catch used to connect to the database using the user provided path
		try {
			this.DATABASE_NAME = databasePath;
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
			connected = true;
		} catch (ClassNotFoundException | SQLException e) {
			connected = false;
            e.printStackTrace();
		}
	}

	/* Name: close
	 * Purpose: Attempts to close to the database catching if there are any exceptions thrown.
	 * 			Called by methods in this class.
	 * Return Type: Void. Just ends the database connection.
	 * Arguments: NONE
	 * Notes: Changed to public so the user can choose what database to connect to. And changed
	 * to synchronized to keep it working with all DB_helpers
	 */
	public synchronized void close() {
		if (connection != null) {
            try {
				connection.close();
			} catch (SQLException e) {
			}
            connection = null;
        }
        connected = false;
	}

	/* Method Name: getConnectionOrThrow
	 * Purpose: Checks to see if there is a valid connection
	 * Parameters: NONE
	 * Return Value: Connection
	 */
	Connection getConnectionOrThrow() {
        if (connection == null) throw new IllegalStateException("DB not connected");
        return connection;
    }

	/* Name: arrayListTo2DArray
	 * Purpose: used to return a 2D array created by accepting an ArrayList of ArrayLists and goes through each
	 * 			row (based on the list size) getting the row and turing the row into an array object of the row size.
	 * 			This makes it possible to print.
	 * Return Type: A 2D object arrayList (Object[][])
	 * Arguments: An ArrayList of ArrayLists.
	 */
	private Object[][] arrayListTo2DArray(ArrayList<ArrayList<Object>> list) {
		Object[][] array = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Object> row = list.get(i);
			array[i] = row.toArray(new Object[row.size()]);
		}
		return array;
	}

	/* Name: execute
	 * Purpose: takes the SQL string that you created or was created with the prepareSQL method and executes it to
	 * 			the database.
	 * Return Type: void
	 * Arguments: query - this is the SQL command that would be entered at the command line for a SQL query
	 * Notes: Protected method, can be called by this class or any subclasses (which it is).
	 */
	public void execute(String sql) {
		try {
			Statement stmt = getConnectionOrThrow().createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Name: executeQueryToTable
	 * Purpose: Used to make a 2D array list of the results from the objects and an Array list for columns. Then
	 * 			used both to creat the Table Model it returns.
	 * Return Type: DefaultTableModel - uses a vector of vectors (A table) to store data.
	 * Arguments: String SQL query
	 * Notes: This method can be called by this class and all subclasses.
	 */
	public DefaultTableModel executeQueryToTable(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> columns = new ArrayList<Object>();
		try {
			Statement stmt = getConnectionOrThrow().createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++)
				columns.add(rs.getMetaData().getColumnName(i));
			while (rs.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++)
					subresult.add(rs.getObject(i));
				result.add(subresult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new DefaultTableModel(arrayListTo2DArray(result), columns.toArray());
	}

	/* Name: executeQuery
	 * Purpose: Connects to the database to call a pre-made query. Collects the results and returns them as an
	 * 			ArrayList of ArrayList Objects.
	 * Return Type: ArrayList<ArrayList<Object>>
	 * Arguments: The SQL query as a string. Either created by the programmer or using the prepareSQL method.
	 */
	public ArrayList<ArrayList<Object>> executeQuery(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		try {
			Statement stmt = getConnectionOrThrow().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
			int columnCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					subresult.add(rs.getObject(i));
				}
				result.add(subresult);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return result;
	}

}