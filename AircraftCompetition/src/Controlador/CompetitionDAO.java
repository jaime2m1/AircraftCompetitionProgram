package Controlador;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Modelo.CompeticionModelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Esta clase es el dao del {@link Modelo.CompeticionModelo} 
 *         Con las funciones de: 
 *         {@link #connectDB()} 
 *         {@link #getAllCompeticiones()}
 *         {@link #getAllCompeticionesOL()} 
 *         {@link #getCompeticion()}
 *         {@link #delCompeticion()} 
 *         {@link #addCompeticion()}
 *         {@link #modCompeticion()}
 */
public class CompetitionDAO {

	public ArrayList<CompeticionModelo> listaUsuarios = new ArrayList<CompeticionModelo>();

	private Connection connect = null;
	// private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private String host = "jaime2m1.gq";
	private String database = "AircraftCompetitionDB";
	private String user = "Aircraft";
	private String passwd = "Competition";

	private String insertCompeticion = "INSERT INTO Competicion (nombre, fecha) VALUES(?, ?)";
	private String deleteCompeticion = "DELETE FROM Competicion WHERE id = ?";
	private String updateCompeticion = "UPDATE Competicion SET nombre = ?, fecha = ? WHERE id = ?";
	private String getAllCompeticiones = "SELECT id, nombre, fecha FROM Competicion";
	private String getCompeticion = "SELECT id, nombre, fecha FROM Competicion WHERE nlicencia = ?";
	private String getLastCompeticionID = "SELECT MAX(id) id FROM Competicion";
	

	/**
	 * Constructor del DAO que usa la configuración del XML
	 */
	public CompetitionDAO() {
		DBConfigDAO xmlDAO = new DBConfigDAO();
		try {
			String[] DBConfig = xmlDAO.readDBConfig();
			host = DBConfig[0];
			database = DBConfig[1];
			user = DBConfig[2];
			passwd = DBConfig[3];
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Método para establecer la conexión
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean connectDB() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connect = DriverManager
				.getConnection("jdbc:mysql://" + host + "/" + database + "?" + "user=" + user + "&password=" + passwd);
		return true;
	}

	/**
	 * Método que devuelve todas las competiciones en formato ArrayList
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CompeticionModelo> getAllCompeticiones() throws SQLException {
		listaUsuarios.clear();
		preparedStatement = connect.prepareStatement(getAllCompeticiones);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			CompeticionModelo competicion = new CompeticionModelo();
			competicion.setNombre(resultSet.getString("nombre"));
			competicion.setFecha(resultSet.getDate("fecha"));
			listaUsuarios.add(competicion);
		}
		return listaUsuarios;
	}

	/**
	 * Método que devuelve todas las competiciones en formato ObservableList
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ObservableList<CompeticionModelo> getAllCompeticionesOL() throws SQLException {
		ObservableList<CompeticionModelo> listaCompeticionesOL = FXCollections.observableArrayList();
		preparedStatement = connect.prepareStatement(getAllCompeticiones);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			CompeticionModelo competicion = new CompeticionModelo();
			competicion.setId(resultSet.getInt("id"));
			competicion.setNombre(resultSet.getString("nombre"));
			competicion.setFecha(resultSet.getDate("fecha"));
			listaCompeticionesOL.add(competicion);
		}
		return listaCompeticionesOL;
	}

	/**
	 * Método para obtener un {@link Modelo.CompeticionModelo} específico
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public CompeticionModelo getCompeticion(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(getCompeticion);
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		return null;
	}

	/**
	 * Método para borrar una competición específica
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean delCompeticion(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(deleteCompeticion);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		return true;
	}

	/**
	 * Método para añadir una competición
	 * 
	 * @param competicion
	 * @return
	 * @throws SQLException
	 */
	public boolean addCompeticion(CompeticionModelo competicion) throws SQLException {
		preparedStatement = connect.prepareStatement(insertCompeticion);
		preparedStatement.setString(1, competicion.getNombre());
		preparedStatement.setDate(2, competicion.getFechaSQL());
		preparedStatement.executeUpdate();
		
		competicion.setId(getLastCompeticionID());
		MangaGruposDAO dao = new MangaGruposDAO();
		try {
			dao.connectDB();
			dao.createMangas(competicion);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Método para obtener el id de la última competición
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getLastCompeticionID() throws SQLException {
		preparedStatement = connect.prepareStatement(getLastCompeticionID);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			return resultSet.getInt("id");
		}
		return 0;
	}

	/**
	 * Método para actualizar una competición específica
	 * 
	 * @param id
	 * @param competicion
	 * @return
	 * @throws SQLException
	 */
	public boolean modCompeticion(int id, CompeticionModelo competicion) throws SQLException {
		preparedStatement = connect.prepareStatement(updateCompeticion);
		preparedStatement.setString(1, competicion.getNombre());
		preparedStatement.setDate(2, competicion.getFechaSQL());
		preparedStatement.setInt(3, id);
		preparedStatement.executeUpdate();
		return true;
	}
}
