package kodras_tiryaki;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.ERModelEditor;
import com.change_vision.jude.api.inf.editor.ModelEditorFactory;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.LicenseNotFoundException;
import com.change_vision.jude.api.inf.exception.ProjectLockedException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IERAttribute;
import com.change_vision.jude.api.inf.model.IERDatatype;
import com.change_vision.jude.api.inf.model.IEREntity;
import com.change_vision.jude.api.inf.model.IERRelationship;
import com.change_vision.jude.api.inf.model.IERSchema;
import com.change_vision.jude.api.inf.model.IModel;


public class Start {
	private static Connect con;
	private static ArrayList<String> tables;
	private static ERAdapter adapter = new ERAdapter();
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		try {
			MyCLI cli = new MyCLI(args);
			con = new Connect(cli.getHost(), cli.getDB(), cli.getUser(), cli.getPasswd());
			
			IModel project = createProject(cli);
            System.out.println("Creating new elements in the project ...");
           
            //Damit eine Message von Astah nicht aus der Konsole ausgegeben wird
            PrintStream original = System.out;
            System.setOut(new PrintStream(new OutputStream() {public void write(int b) {}}));
            
            //starten der Transaktion und dem Model
            TransactionManager.beginTransaction();
            
            //erstellen eines Editors fuer eine ER-Model
            adapter.setEditor(ModelEditorFactory.getERModelEditor());
            
            //erstellen eines ER-Models
            adapter.setModel(adapter.getEditor().createERModel(project, cli.getDB()));
            
            //erstellen eines Schemas, zuzeit gibt es nur eines in Astah
            adapter.setSchema(adapter.getModel().getSchemata()[0]);
            
            //Setzen des Standartoutput auf die urspruengliche Konsole
            System.setOut(original);
            
            System.out.println("Creating new Entities ...");
            
            //erstellen der Entitaeten
            tables = getTableNames(con);
            adapter.setEntities(getEntities(tables, adapter.getEntities(), adapter.getEditor(), adapter.getSchema()));
            
			IERRelationship[] bez = createRelations();
            IERAttribute attributes[][] = createAttributes();
            
            TransactionManager.endTransaction();
            
            adapter.getPrjAccessor().save();
            adapter.getPrjAccessor().close();
            
            con.getConnection().close();
            
            System.out.println("Finished");
            
	    } catch (LicenseNotFoundException e) {
	     	System.out.println("Keine glueltig Lizenz.\nnaehere Informationen: " + e.getMessage());
	    } catch (ProjectNotFoundException e) {
	       	System.out.println("Projekt nicht vorhanden\nnaehere Informationen: " + e.getMessage());
	    } catch (ProjectLockedException e) {
	       	System.out.println("Projekt kann nicht abgespeichert werden da es schon offen ist, oder gesperrt.\nnaehere Informationen: " + e.getMessage());
	    } catch (IllegalArgumentException e) {
	       	System.out.println("Es sind nicht alle notwendigen Klassen vorhanden.\nnaehere Informationen: " + e.getMessage());
	    } catch (InvalidEditingException e) {
	        //abbrechen der Transaktion
	        TransactionManager.abortTransaction();
	        System.out.println("Es ist ein Fehler beim erstellen des Models aufgetreten.\nnaehere Informationen: " + e.getMessage());
	    } catch (IOException e) {
	       	System.out.println("Etwas ist beim Ein- und Auslesen schief gegangen.\nnaehere Informationen: " + e.getMessage());
	    } catch (IllegalAccessError e) {
	       	System.out.println("Es ist ein unbekannter Fehler aufgetreten.\nnaehere Informationen:" + e.getMessage());
	    } catch (SQLException e) {
			System.out.println("Es ist ein Fehler beim Verbindungsaufbau aufgetreten: " + e.getMessage());
		}
	}
	
	private static IModel createProject(MyCLI cli) throws ClassNotFoundException, IOException, ProjectNotFoundException {
		 System.out.println("Creating new project ...");
		 adapter.setPrjAccessor(AstahAPI.getAstahAPI().getProjectAccessor());
         adapter.getPrjAccessor().create("./" + cli.getDB() + ".asta");
         return adapter.getPrjAccessor().getProject();
	}
	
	private static IERRelationship[] createRelations() throws InvalidEditingException {
		System.out.println("Creating new Relationships ...");
		IERRelationship[] bez = null;
		EntityFinder ef = new EntityFinder(adapter.getSchema());
		for(int i = 0; i < tables.size(); i++) {
        	ArrayList<ArrayList<String>> temp = getKeys(con, tables.get(i));
        	bez = new IERRelationship[temp.size()];
        	for(int j = 0; j < temp.size(); j++) {
        		IEREntity parent = ef.find(temp.get(j).get(0));
        		IEREntity child = ef.find(temp.get(j).get(1));
        		bez[j] = adapter.getEditor().createNonIdentifyingRelationship(parent, child, 
        				tables.get(i)+"."+temp.get(j).get(0)+"."+temp.get(j).get(1), tables.get(i)+"."+temp.get(j).get(0)+"."+temp.get(j).get(1));
            }
        }
        return bez;
	}
	
	private static IERAttribute[][] createAttributes() throws InvalidEditingException {
		System.out.println("Creating new Attributes ...");
		IERAttribute attributes[][] = new IERAttribute[adapter.getEntities().length][];
		AttributeFinder af = new AttributeFinder();
		DatatypeFinder df = new DatatypeFinder(adapter.getSchema());
        for(int i = 0; i < adapter.getEntities().length; i++) {
        	ArrayList<ArrayList<String>> temp = getAttributes(con, tables.get(i));
        	attributes[i] = new IERAttribute[temp.size()];
        	for(int j = 0; j < temp.size(); j++) {
        		IERDatatype data;
        		if((data = df.find(temp.get(j).get(1))) == null) data = adapter.getEditor().createERDatatype(adapter.getModel(), temp.get(j).get(1));
        		IERAttribute tempa;
        		if((tempa = af.find(adapter.getEntities()[i], temp.get(j).get(0))) == null && (!temp.get(j).get(3).equalsIgnoreCase("MUL") || !temp.get(j).get(3).equalsIgnoreCase("MUL"))) {
            		attributes[i][j] = adapter.getEditor().createERAttribute(adapter.getEntities()[i], temp.get(j).get(0), temp.get(j).get(0), data);
        		} else attributes[i][j] = tempa;
            	if(temp.get(j).get(3).equalsIgnoreCase("PRI")) attributes[i][j].setPrimaryKey(true);
            }
        }
		return attributes;
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
	
	public static ArrayList<ArrayList<String>> getKeys(Connect con, String table) {
		ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>();
		try {
			Statement st = con.getConnection().createStatement();
			ResultSet rs = st.executeQuery(
					"select referenced_table_name as parent, referenced_column_name, table_name as child,column_name from information_schema.key_column_usage "
					+ "where table_name='" + table + "' AND referenced_table_name IS NOT NULL AND referenced_column_name IS NOT NULL;");
			
			while(rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString("parent"));
				temp.add(rs.getString("child"));
				keys.add(temp);
			}
			
			rs.close();
		} catch (SQLException e) {
			//Ausgabe der Fehlermeldung falls eine Auftritt
			JOptionPane.showMessageDialog(null, e.getStackTrace());
		}
		return keys;
	}
	
	public static ArrayList<ArrayList<String>> getAttributes(Connect con, String tables) {
		ArrayList<ArrayList<String>> attributes = new ArrayList<ArrayList<String>>();
		try {
			PreparedStatement st = con.getConnection().prepareStatement("DESC " + tables + ";");
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
					temp.add(rs.getString(i+1));
				attributes.add(temp);
			}
			
			rs.close();
		} catch (SQLException e) {
			//Ausgabe der Fehlermeldung falls eine Auftritt
			JOptionPane.showMessageDialog(null, e.getStackTrace());
		}
		return attributes;
	}
	
	public static IEREntity[] getEntities(ArrayList<String> tables, IEREntity[] entities, ERModelEditor editor, IERSchema schema) throws InvalidEditingException {
		entities = new IEREntity[tables.size()];
		for(int i = 0; i < entities.length; i++) {
        	entities[i] = editor.createEREntity(schema, tables.get(i), tables.get(i));
        }
		return entities;
	}
}