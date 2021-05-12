package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Modelo.PuntuacionModelo;
import Modelo.UsuarioModelo;

/**
 * DAO de la puntuación
 * 
 * @author Jaime
 *
 */
public class ScoreDAO {
	public ArrayList<PuntuacionModelo> listaPuntuaciones = new ArrayList<PuntuacionModelo>();
	
	private Connection connect = null;
	// private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private String host = "jaime2m1.gq";
	private String database = "AircraftCompetitionDB";
	private String user = "Aircraft";
	private String passwd = "Competition";

	private String insertPuntuacion = "INSERT INTO Puntuacion (usuarioid, tiempo, distancia, altura, penalizacion) VALUES(?, ?, ?, ?, ?)";
	private String deletePuntuacionUsuario = "DELETE FROM Competicion WHERE usuarioid = ?";
	private String getAllPuntuaciones = "SELECT p.id, u.nlicencia, u.nombre, u.apellidos, u.contrasena, p.tiempo, p.distancia, p.altura, p.penalizacion FROM Puntuacion p INNER JOIN Usuarios u ON p.usuarioid = u.nlicencia";
	private String getLastPuntuacionId = "SELECT MAX(id) id FROM Puntuacion";
	
	/**
	 * Constructor del DAO
	 */
	public ScoreDAO() {
		DBConfigDAO xmlDAO = new DBConfigDAO();
		try {
			String[] DBConfig = xmlDAO.readDBConfig();
			host = DBConfig[0];
			database = DBConfig[1];
			user = DBConfig[2];
			passwd = DBConfig[3];
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Conexión con la base de datos
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
	 * Obtienes todas las puntuaciones
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PuntuacionModelo> getAllPuntuaciones() throws SQLException {
		listaPuntuaciones.clear();
		preparedStatement = connect.prepareStatement(getAllPuntuaciones);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			PuntuacionModelo puntuacion = new PuntuacionModelo();
			puntuacion.setId(resultSet.getInt("p.id"));
			UsuarioModelo usuario = new UsuarioModelo(resultSet.getString("u.nombre"), resultSet.getString("u.apellidos"), resultSet.getInt("u.nlicencia"), resultSet.getString("u.contrasena"));
			puntuacion.setUsuario(usuario);
			puntuacion.setSegundosVuelo(resultSet.getInt("p.tiempo"));
			puntuacion.setDistanciaVuelo(resultSet.getInt("p.distancia"));
			listaPuntuaciones.add(puntuacion);
		}
		return listaPuntuaciones;
	}
	
	/**
	 * Obtiene el id de la última puntuacion
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getLastPuntuacionId() throws SQLException {
		preparedStatement = connect.prepareStatement(getLastPuntuacionId);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			return resultSet.getInt("id");
		}
		return 0;
	}
	
	/**
	 * Anade una puntuacion
	 * 
	 * @param puntuacion
	 * @return
	 * @throws SQLException
	 */
	public boolean addPuntuacion(PuntuacionModelo puntuacion) throws SQLException {
		preparedStatement = connect.prepareStatement(insertPuntuacion);
		preparedStatement.setInt(1, puntuacion.getUsuario().getNlicencia());
		preparedStatement.setInt(2, puntuacion.getSegundosVuelo());
		preparedStatement.setInt(3, puntuacion.getDistanciaVuelo());
		preparedStatement.setInt(4, puntuacion.getAlturaVuelo());
		preparedStatement.setInt(5, puntuacion.getPenalizacion());
		preparedStatement.executeUpdate();
		return true;
	}
	
	/**
	 * Borra una puntuación del usuario
	 * 
	 * @param usuarioid
	 * @return
	 * @throws SQLException
	 */
	public boolean delPuntuacionUsuario(int usuarioid) throws SQLException {
		preparedStatement = connect.prepareStatement(deletePuntuacionUsuario);
		preparedStatement.setInt(1, usuarioid);
		preparedStatement.executeUpdate();
		return true;
	}
}
