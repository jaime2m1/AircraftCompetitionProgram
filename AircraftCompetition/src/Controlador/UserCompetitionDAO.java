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

import Modelo.CompeticionModelo;
import Modelo.UsuarioModelo;

/**
 * @author Jaime,Pablo,Juan
 *
 * Esta clase es el dao de UserCompetition
 */
public class UserCompetitionDAO {
	
	public ArrayList<UsuarioModelo> listaUsuarios = new ArrayList<UsuarioModelo>();
	public ArrayList<CompeticionModelo> listaCompeticiones = new ArrayList<CompeticionModelo>();
	
	private Connection connect = null;
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private String host = "jaime2m1.gq";
	private String database = "AircraftCompetitionDB";
	private String user = "Aircraft";
	private String passwd = "Competition";
	
	private String insertUsuarioCompeticion = "INSERT INTO UsuarioCompeticion (usuarioid, competicionid) VALUES(?, ?)";
	private String deleteUsuarioCompeticion = "DELETE FROM UsuarioCompeticion WHERE competicionid = ?";
	private String deleteUsuarioCompeticionIds = "DELETE FROM UsuarioCompeticion WHERE usuarioid = ? and competicionid = ?";
	private String getUsuariosDeCompeticion = "SELECT u.nombre, u.apellidos, u.nlicencia, u.contrasena "
			+ "FROM Usuarios u "
			+ "INNER JOIN UsuarioCompeticion uc "
			+ "ON u.nlicencia = uc.usuarioid "
			+ "WHERE uc.competicionid = ? ";
	private String getCompeticionesDeUsuario = "SELECT c.id, c.nombre, c.fecha "
			+ "FROM Competicion c "
			+ "INNER JOIN UsuarioCompeticion uc "
			+ "ON c.id = uc.competicionid "
			+ "WHERE uc.usuarioid = ? ";
	
	/**
	 * Constructor que obtiene la configuración del XML
	 */
	public UserCompetitionDAO() {
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
	 * Método que establece la conexión a la base de datos
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
	 * Método que obtiene los Usuarios de una Competición
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<UsuarioModelo> getUsuariosDeCompeticion(int id) throws SQLException {
		listaUsuarios.clear();
		preparedStatement = connect.prepareStatement(getUsuariosDeCompeticion);
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			UsuarioModelo usuario = new UsuarioModelo();
			usuario.setNombre(resultSet.getString("nombre"));
			usuario.setApellidos(resultSet.getString("apellidos"));
			usuario.setNlicencia(resultSet.getInt("nlicencia"));
			usuario.setContrasena(resultSet.getString("contrasena"));
			listaUsuarios.add(usuario);
		}
		return listaUsuarios;
	}
	
	/**
	 * Método que obtiene las Competiciones de un Usuario
	 * 
	 * @param nlicencia
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CompeticionModelo> getCompeticionesDeUsuario(int nlicencia) throws SQLException{
		listaCompeticiones.clear();
		preparedStatement = connect.prepareStatement(getCompeticionesDeUsuario);
		preparedStatement.setInt(1, nlicencia);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			CompeticionModelo competicion = new CompeticionModelo();
			competicion.setId(resultSet.getInt("c.id"));
			competicion.setNombre(resultSet.getString("c.nombre"));
			competicion.setFecha(resultSet.getDate("c.fecha"));
			System.out.println("Anadido id="+ competicion.getId()+" nombre="+competicion.getNombre()+" fecha="+competicion.getFecha());
			listaCompeticiones.add(competicion);
		}
		return listaCompeticiones;
	}
	
	/**
	 * Método para ańadir una relación a la tabla
	 * 
	 * @param usuarioid
	 * @param competicionid
	 * @return
	 * @throws SQLException
	 */
	public boolean addUsuarioCompeticion(int usuarioid, int competicionid) throws SQLException {
		preparedStatement = connect.prepareStatement(insertUsuarioCompeticion);
		preparedStatement.setInt(1, usuarioid);
		preparedStatement.setInt(2, competicionid);
		preparedStatement.executeUpdate();
		return true;
	}
	
	/**
	 * Método encargado de borrar una relación específica
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean delUsuarioCompeticion(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(deleteUsuarioCompeticion);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		return true;
	}
	
	/**
	 * Se borra la relación del usuario con x competición
	 * 
	 * @param nlicencia
	 * @param competicionid
	 * @return
	 * @throws SQLException
	 */
	public boolean delUsuarioCompeticion(int nlicencia, int competicionid) throws SQLException {
		preparedStatement = connect.prepareStatement(deleteUsuarioCompeticionIds);
		preparedStatement.setInt(1, nlicencia);
		preparedStatement.setInt(2, competicionid);
		preparedStatement.executeUpdate();
		return true;
	}
	
	
	
}
