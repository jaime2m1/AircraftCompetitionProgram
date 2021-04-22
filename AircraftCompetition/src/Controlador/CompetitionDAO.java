package Controlador;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Modelo.CompeticionModelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Date;

public class CompetitionDAO {
	
	public ArrayList<CompeticionModelo> listaUsuarios =  new ArrayList<CompeticionModelo>();
	
	private Connection connect = null;
	//private Statement statement = null;
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
	
	
	public boolean connectDB() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"+database+"?"
		              + "user=" + user + "&password=" + passwd );
		return true;
	}
	public ArrayList<CompeticionModelo> getAllCompeticiones() throws SQLException{
		listaUsuarios.clear();
		preparedStatement = connect.prepareStatement(getAllCompeticiones);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CompeticionModelo competicion = new CompeticionModelo();
			competicion.setNombre(resultSet.getString("nombre"));
			competicion.setFecha(resultSet.getDate("fecha"));
			listaUsuarios.add(competicion);
		}
		return listaUsuarios;
	}
	
	public ObservableList<CompeticionModelo> getAllCompeticionesOL() throws SQLException{
		ObservableList<CompeticionModelo> listaCompeticionesOL = FXCollections.observableArrayList();
		preparedStatement = connect.prepareStatement(getAllCompeticiones);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CompeticionModelo competicion = new CompeticionModelo();
			competicion.setNombre(resultSet.getString("nombre"));
			competicion.setFecha(resultSet.getDate("fecha"));
			listaCompeticionesOL.add(competicion);
		}
		return listaCompeticionesOL;
	}
	
	public CompeticionModelo getCompeticion(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(getCompeticion);
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		return null;
	}
	public boolean delUsuario(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(deleteCompeticion);
		preparedStatement.setInt(1, id);
		preparedStatement.executeQuery();
		return true;
	}
	public boolean addCompeticion(CompeticionModelo competicion) throws SQLException {
		preparedStatement = connect.prepareStatement(insertCompeticion);
		preparedStatement.setString(1, competicion.getNombre());
		preparedStatement.setDate(2, (Date) competicion.getFecha());
		preparedStatement.executeUpdate();
		return true;
	}
	public boolean modUsuario(int id, CompeticionModelo competicion) throws SQLException {
		preparedStatement = connect.prepareStatement(updateCompeticion);
		preparedStatement.setString(1, competicion.getNombre());
		preparedStatement.setDate(2, (Date) competicion.getFecha());
		preparedStatement.setInt(3, id);
		preparedStatement.executeUpdate();
		return true;
	}
}
