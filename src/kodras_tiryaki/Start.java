package kodras_tiryaki;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Start {
	
	public static void main(String[] args) {
		MyCLI cli = new MyCLI(args);
		Connect con = new Connect(cli.getHost(), cli.getDB(), cli.getUser(), cli.getPasswd());
		
		getTableNames(con);
		getColumnDefinitions(con, getTableNames(con).get(1));
	}
	
	public static ArrayList<String> getTableNames(Connect con){
		String query = "show tables;";
		Statement st;
		ArrayList<String> tablenames = new ArrayList<String>();
		try {
			st = con.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				tablenames.add(rs.getString(1));
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return tablenames;
	}
	
	public static ArrayList<ColumnDefinition> getColumnDefinitions(Connect con, String tableName) {
		String query = "DESC " + tableName + ";";
		Statement st;
		ArrayList<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
		try {
			st = con.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				ColumnDefinition columnDefinition = new ColumnDefinition(rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				columnDefinitions.add(columnDefinition);
				System.out.println(columnDefinition);
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return columnDefinitions;
	}
}