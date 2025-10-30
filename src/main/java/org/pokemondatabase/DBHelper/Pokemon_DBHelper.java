package org.pokemondatabase.DBHelper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Pokemon_DBHelper extends DBHelper {
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

	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

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
			super.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ");");
		}
	}

	public void delete(String whatField, String whatValue) {
		super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
	}

	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
	}

	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return super.executeQuery(query);
	}

	public void execute(String query) {
		super.execute(query);
	}

	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

}