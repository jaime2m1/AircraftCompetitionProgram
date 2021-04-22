package Controlador;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Modelo.UsuarioModelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jaime,Pablo,Juan
 *
 *         Esta clase es el DAO de {@link Modelo.UsuarioModelo} 
 *         Con las funciones de: 
 *         {@link #connectDB()} 
 *         {@link #getAllUsuarios()}
 *         {@link #getUsuario()} 
 *         {@link #delUsuario()} 
 *         {@link #addUsuario()}
 *         {@link #modUsuario()}
 */
public class UserDAO {

	public ArrayList<UsuarioModelo> listaUsuarios = new ArrayList<UsuarioModelo>();

	private Connection connect = null;
	// private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private String host = "jaime2m1.gq";
	private String database = "AircraftCompetitionDB";
	private String user = "Aircraft";
	private String passwd = "Competition";

	private String insertUsuario = "INSERT INTO Usuarios (nombre, apellidos, nlicencia, contrasena) VALUES(?, ?, ?, ?)";
	private String deleteUsuario = "DELETE FROM Usuarios WHERE nlicencia = ?";
	private String updateUsuario = "UPDATE Usuarios SET nombre = ?, apellidos = ?, contrasena = ? WHERE nlicencia = ?";
	private String getAllUsuarios = "SELECT nombre, apellidos, nlicencia, contrasena FROM Usuarios";
	private String getUsuario = "SELECT nombre, apellidos, nlicencia, contrasena FROM Usuarios WHERE nlicencia = ?";

	/**
	 * Constructor que obtiene la configuración del XML
	 */
	public UserDAO() {
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
	 * Método que devuelve un ArrayList de Usuarios
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<UsuarioModelo> getAllUsuarios() throws SQLException {
		listaUsuarios.clear();
		preparedStatement = connect.prepareStatement(getAllUsuarios);
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
	 * Método que obtiene un usuario específico
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public UsuarioModelo getUsuario(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(getUsuario);
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		return null;
	}

	/**
	 * Método encargado de borrar un usuario específico
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean delUsuario(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(deleteUsuario);
		preparedStatement.setInt(1, id);
		preparedStatement.executeQuery();
		return true;
	}

	/**
	 * Método encargado de añadir un usuario
	 * 
	 * @param usuario
	 * @return
	 * @throws SQLException
	 */
	public boolean addUsuario(UsuarioModelo usuario) throws SQLException {
		preparedStatement = connect.prepareStatement(insertUsuario);
		preparedStatement.setString(1, usuario.getNombre());
		preparedStatement.setString(2, usuario.getApellidos());
		preparedStatement.setInt(3, usuario.getNlicencia());
		preparedStatement.setString(4, usuario.getContrasena());
		preparedStatement.executeUpdate();
		return true;
	}

	/**
	 * Método encargado de modificar un usuario específico
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 * @throws SQLException
	 */
	public boolean modUsuario(int id, UsuarioModelo usuario) throws SQLException {
		preparedStatement = connect.prepareStatement(updateUsuario);
		preparedStatement.setString(1, usuario.getNombre());
		preparedStatement.setString(2, usuario.getApellidos());
		preparedStatement.setString(3, usuario.getContrasena());
		preparedStatement.setInt(4, id);
		preparedStatement.executeUpdate();
		return true;
	}
}
