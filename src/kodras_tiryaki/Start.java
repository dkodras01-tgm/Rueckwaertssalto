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
		String query = "SELECT * FROM person;";
		
		Statement st;
		try {
			st = Connect.conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			ArrayList<String> colnames = new ArrayList<String>();
			
			int numberOfColumns = rsmd.getColumnCount();
		    for (int i = 1; i <= numberOfColumns; i++) {
		    	colnames.add(rsmd.getColumnName(i));
		    }
		    
			rs.close();
			rs = null;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}