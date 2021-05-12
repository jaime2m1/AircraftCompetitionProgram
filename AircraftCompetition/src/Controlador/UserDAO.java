package Controlador;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Modelo.UsuarioModelo;
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
	//private String getAllUsuariosOrdered = "SELECT nombre, apellidos, nlicencia, contrasena FROM Usuarios ORDER BY puntuaciont";
	private String getPuntuacionesUsuario = "SELECT usuarioid, tiempo, distancia, altura FROM Puntuacion WHERE usuarioid = ?";
	private String getUsuario = "SELECT nombre, apellidos, nlicencia, contrasena FROM Usuarios WHERE nlicencia = ?";

	/**
	 * Constructor que obtiene la configuraci�n del XML
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
			e.printStackTrace();
		}
	}

	/**
	 * M�todo que establece la conexi�n a la base de datos
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
	 * M�todo que devuelve un ArrayList de Usuarios
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
	 * M�todo que devuelve todos los usuarios en formato ObservableList
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ObservableList<UsuarioModelo> getAllUsuariosOL() throws SQLException {
		ObservableList<UsuarioModelo> listaUsuariosOL = FXCollections.observableArrayList();
		preparedStatement = connect.prepareStatement(getAllUsuarios);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			UsuarioModelo usuario = new UsuarioModelo();
			usuario.setPuesto(0);
			usuario.setNombre(resultSet.getString("nombre"));
			usuario.setApellidos(resultSet.getString("apellidos"));
			usuario.setNlicencia(resultSet.getInt("nlicencia"));
			usuario.setPuntuacion(0);
			listaUsuariosOL.add(usuario);
		}
		return listaUsuariosOL;
	}

	/**
	 * M�todo que obtiene un usuario espec�fico
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
	 * M�todo encargado de borrar un usuario espec�fico
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
	 * M�todo encargado de a�adir un usuario
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
	 * M�todo encargado de modificar un usuario espec�fico
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
	
	public ArrayList<Integer> getPuntuacionesUsuario(int id) throws SQLException{
		ArrayList<Integer> puntuaciones = new ArrayList<Integer>();
		Integer total = 0;
		preparedStatement = connect.prepareStatement(getPuntuacionesUsuario);
		resultSet = preparedStatement.executeQuery();
		
		puntuaciones.add(resultSet.getInt("tiempo"));
		puntuaciones.add(resultSet.getInt("distancia"));
		puntuaciones.add(resultSet.getInt("altura"));
		
		total += puntuaciones.get(0);
		
		switch(puntuaciones.get(1)){
			case 0:
				total += 50;
				break;
			case 1:
				total += 45;
				break;
			case 2:
				total += 40;
				break;
			case 3: case 4:
				total += 35;
				break;
			case 5: case 6: case 7: case 8: case 9:
				total += 30;
				break;
			default:
				total += 0;
				break;
		}
		
		if(puntuaciones.get(2) <= 200) {
			total -= (puntuaciones.get(2)/2);
		}else {
			total -= 100;
			total -= ((puntuaciones.get(2) - 200) * 3);
		}

		puntuaciones.add(0, total);
		
		return puntuaciones;
		
	}
}
