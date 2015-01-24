package kodras_tiryaki;

import org.apache.commons.cli2.*;
import org.apache.commons.cli2.builder.*;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.util.HelpFormatter;

/**
 * 
 * @author Dominik Kodras
 * @version 18.01.2015
 */
public class MyCLI {
	
	/**
	 * Der URL des Servers.
	 * Kurzform: -h
	 */
	private String host = "";
	
	/**
	 * Der Username.
	 * Kurzform: -u
	 */
	private String user = "";
	
	/**
	 * Das Passwort des Users.
	 * Kurzform: -p
	 */
	private String passwd = "";
	
	/**
	 * Datenbank.
	 * Kurzform: -d
	 */
	private String db = "";
	
	
	/**
	 * Ueberprueft die uebergebenen Optionen und Argumente
	 * @param args Optionen und Argumente die uebergeben werden
	 */
	public MyCLI(String[] args) {
		DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
		ArgumentBuilder abuilder = new ArgumentBuilder();
		GroupBuilder gbuilder = new GroupBuilder();
		
		Option host = obuilder.withShortName("h").withRequired(true)
				.withArgument(abuilder.withName("Hostname des DBMS. Standard: localhost")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option user = obuilder.withShortName("u").withRequired(true)
				.withArgument(abuilder.withName("Benutzername")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option passwd = obuilder.withShortName("p").withRequired(false)
				.withArgument(abuilder.withName("Passwort. Standard: keins")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option db = obuilder.withShortName("d").withRequired(true)
				.withArgument(abuilder.withName("Name der Datenbank")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Group options = gbuilder.withName("options").withOption(host).withOption(user).withOption(passwd)
				.withOption(db).create();
		
		Parser parser = new Parser();
		parser.setGroup(options);
		
		HelpFormatter hf = new HelpFormatter();
		hf.setShellCommand("Exporter");
		hf.setGroup(options);
		hf.getFullUsageSettings().add(DisplaySetting.DISPLAY_GROUP_NAME);
		hf.getFullUsageSettings().add(DisplaySetting.DISPLAY_GROUP_ARGUMENT);
		hf.getFullUsageSettings().remove(DisplaySetting.DISPLAY_GROUP_EXPANDED);
		hf.getDisplaySettings().remove(DisplaySetting.DISPLAY_GROUP_ARGUMENT);
		hf.getLineUsageSettings().add(DisplaySetting.DISPLAY_PROPERTY_OPTION);
		hf.getLineUsageSettings().add(DisplaySetting.DISPLAY_PARENT_ARGUMENT);
		hf.getLineUsageSettings().add(DisplaySetting.DISPLAY_ARGUMENT_BRACKETED);
		//http://commons.apache.org/sandbox/commons-cli2/examples/ant.html
		
		
		/*
		 * Hier werden die Optionen und Argumente aus der args-Variable ausgelesen, und mit entsprechenden
		 * Meldungen und Exceptions verwaltet.
		 */
		try {
			CommandLine cl = parser.parse(args);
			if(cl.hasOption(host)) {
				try {
					this.host = (String) cl.getValue(host);
				} catch(Exception e) {
					//wenn catsen fehlschlaegt, wird Hilfe/Beschreibung ausgegeben und Programm beendet
					hf.print();
					System.exit(1);
				}
			}
			
			if(cl.hasOption(user)) {
				try {
					this.user = (String) cl.getValue(user);
				} catch(Exception e) {
					//wenn catsen fehlschlaegt, wird Hilfe/Beschreibung ausgegeben und Programm beendet
					hf.print();
					System.exit(1);
				}
			}
			
			if(cl.hasOption(passwd)) {
				try {
					this.passwd = (String) cl.getValue(passwd);
				} catch(Exception e) {
					//wenn Passwort nicht angegeben wurde dann Standard keines
					this.passwd = "";
				}
			}
			
			if(cl.hasOption(db)) {
				try {
					this.db = (String) cl.getValue(db);
				} catch(Exception e) {
					//wenn catsen fehlschlaegt, wird Hilfe/Beschreibung ausgegeben und Programm beendet
					hf.print();
					System.exit(1);
				}
			}
			
			if(cl.hasOption(db)) {
				try {
					this.db = (String) cl.getValue(db);
				} catch(Exception e) {
					//wenn catsen fehlschlaegt, wird Hilfe/Beschreibung ausgegeben und Programm beendet
					hf.print();
					System.exit(1);
				}
			}
		} catch(OptionException e) {
			//wenn Verarbeiten der Optionen und Argmente fehlschlaegt, wird Hilfe/Beschreibung ausgegeben und Programm beendet
			//System.out.println(this.felder);
			hf.print();
			System.exit(1);
		}
	}
	
	/**
	 * Gibt die Hostadresse zurueck
	 * @return die Hostadresse
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Gibt den Benutzernamen zurueck
	 * @return der Benutzername
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Gibt das Passwort zurueck
	 * @return das Passwort
	 */
	public String getPasswd() {
		return passwd;
	}
	
	/**
	 * Gibt den Namen der Datenbank zurueck
	 * @return die Datenank
	 */
	public String getDB() {
		return db;
	}
}