package kodras_tiryaki;

import java.sql.Connection;
import java.sql.DriverManager;
 
public class Connect {
 
    // Diese Eintraege werden zum 
    // Verbindungsaufbau benoetigt. 
    String hostname;
    String port;
    String dbname;
    String user;
    String password;
    public static Connection conn = null;
 
    public Connect(String hostname, String dbname, String user, String password) {
    	this(hostname, "3306", dbname, user, password);
    }
    
    public Connect(String hostname, String port, String dbname, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Treiber wurde geladen.");
        } catch (Exception e) {
            System.err.println("Treiber konnte nicht geladen werden.");
        }
 
        try {
            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Es wurde sich erfolgreich an der Datenbank:" + url + " angemeldet.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Connection getConnection() {
		return conn;
	}
}