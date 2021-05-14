package Controlador;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Modelo.CompeticionModelo;
import Modelo.MangaModelo;
import Modelo.PuntuacionModelo;
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
	private String getUsuariosCompeticion = "SELECT u.nombre nombre, u.apellidos apellidos, u.nlicencia nlicencia, u.contrasena contrasena, uc.usuarioid, uc.competicionid FROM Usuarios u INNER JOIN UsuarioCompeticion uc ON u.nlicencia = uc.usuarioid WHERE uc.competicionid = ?";
	//private String getAllUsuariosOrdered = "SELECT nombre, apellidos, nlicencia, contrasena FROM Usuarios ORDER BY puntuaciont";
	private String getPuntuacionesUsuario = "SELECT id, usuarioid, tiempo, distancia, altura FROM Puntuacion WHERE usuarioid = ?";
	private String getUsuario = "SELECT nombre, apellidos, nlicencia, contrasena FROM Usuarios WHERE nlicencia = ?";

	/**
	 * Constructor que obtiene la configuraciï¿½n del XML
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
	 * Mï¿½todo que establece la conexiï¿½n a la base de datos
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
	 * Mï¿½todo que devuelve un ArrayList de Usuarios
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
	 * Mï¿½todo que devuelve todos los usuarios en formato ObservableList
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
			usuario.setPuntuacionTotal(0);
			usuario.setPuntuacionCompe(0);
			listaUsuariosOL.add(usuario);
		}
		return listaUsuariosOL;
	}
	
	/**
	 * Obtenemos los usuarios de x competición
	 * 
	 * @param competicion
	 * @return
	 * @throws SQLException
	 */
	public ObservableList<UsuarioModelo> getUsuariosOLCompeticion(CompeticionModelo competicion) throws SQLException {
		ObservableList<UsuarioModelo> listaUsuariosOL = FXCollections.observableArrayList();
		preparedStatement = connect.prepareStatement(getUsuariosCompeticion);
		preparedStatement.setInt(1, competicion.getId());
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			UsuarioModelo usuario = new UsuarioModelo();
			usuario.setPuesto(0);
			usuario.setNombre(resultSet.getString("nombre"));
			usuario.setApellidos(resultSet.getString("apellidos"));
			usuario.setNlicencia(resultSet.getInt("nlicencia"));
			usuario.setPuntuacionTotal(0);
			usuario.setPuntuacionCompe(0);
			listaUsuariosOL.add(usuario);
		}
		return listaUsuariosOL;
	}

	/**
	 * Mï¿½todo que obtiene un usuario especï¿½fico
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
	 * Mï¿½todo encargado de borrar un usuario específico
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
	 * Mï¿½todo encargado de anadir un usuario
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
	
	/**
	 * Obtenemos las puntuaciones del usuario
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PuntuacionModelo> getPuntuacionesUsuario(int id) throws SQLException{
		ArrayList<PuntuacionModelo> puntuaciones = new ArrayList<PuntuacionModelo>();
		
		preparedStatement = connect.prepareStatement(getPuntuacionesUsuario);
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			PuntuacionModelo puntuacion = new PuntuacionModelo();
			puntuacion.setId(resultSet.getInt("id"));
			puntuacion.setSegundosVuelo(resultSet.getInt("id"));
			puntuacion.setDistanciaVuelo(resultSet.getInt("id"));
			puntuacion.setAlturaVuelo(resultSet.getInt("id"));
			puntuacion.setPenalizacion(resultSet.getInt("id"));
			
			puntuaciones.add(puntuacion);
		}
		return puntuaciones;
	}
	
	public ArrayList<PuntuacionModelo> getPuntuacionesUsuarioCompeticion(int usuarioid, int competicion) throws SQLException, ClassNotFoundException{
		System.out.println("UserDAO - Ejecutamos la consulta de las puntuacionbes de usuario"+usuarioid+" en competicion"+competicion);
		ArrayList<PuntuacionModelo> puntuacionesUsuarioCompeticion = new ArrayList<PuntuacionModelo>();
		ArrayList<PuntuacionModelo> puntuacionesUsuario = new ArrayList<PuntuacionModelo>();
		ArrayList<Integer> idPuntuacionesMangas = new ArrayList<>();
		ArrayList<Integer> idPuntuacionesManga = new ArrayList<>();
		
		//Obtenemos puntuaciones de un usuario
		preparedStatement = connect.prepareStatement(getPuntuacionesUsuario);
		preparedStatement.setInt(1, usuarioid);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			PuntuacionModelo puntuacion = new PuntuacionModelo();
			puntuacion.setId(resultSet.getInt("id"));
			puntuacion.setSegundosVuelo(resultSet.getInt("tiempo"));
			puntuacion.setDistanciaVuelo(resultSet.getInt("distancia"));
			puntuacion.setAlturaVuelo(resultSet.getInt("altura"));
			puntuacion.setPenalizacion(0);
			
			puntuacionesUsuario.add(puntuacion);
		}
		//System.out.println("UserDAO - Obtenidas las puntuaciones del usuario");
		
		//Obtener puntuaciones de una competicion
		MangaGruposDAO mangagruposDAO = new MangaGruposDAO();
		mangagruposDAO.connectDB();
		//Obtenemos las mangas de x competicion
		ArrayList<MangaModelo> mangas = mangagruposDAO.getMangasCompeticion(competicion);
		//System.out.println("UserDAO - Obtenidas las mangas de la competición");
		
		for(int i=0; i<mangas.size(); i++) {
			idPuntuacionesManga=(mangagruposDAO.getIDPuntuacionesManga(mangas.get(i).getId()));
			//System.out.println("UserDAO - Las puntuaciones de la manga"+mangas.get(i).getId()+" son "+idPuntuacionesManga.size());
			for(int o=0; o<idPuntuacionesManga.size(); o++) {
				idPuntuacionesMangas.add(idPuntuacionesManga.get(o));
			}
		}
		//System.out.println("UserDAO - Recorrido la reasignación de los ids de las puntuaciones de las mangas");
		//System.out.println("UserDAO - Puntuaciones de usuario => "+puntuacionesUsuario.size()+" puntuaciones de mangas => "+idPuntuacionesMangas.size());
		for(int i=0; i<puntuacionesUsuario.size(); i++) {
			for(int o=0; o<idPuntuacionesMangas.size(); o++) {
				//System.out.println("Puntuacion de usuario"+usuarioid+" es id"+puntuacionesUsuario.get(i).getId()+" el recorrido de las puntuaciones de manga es id"+idPuntuacionesMangas.get(o));
				if(puntuacionesUsuario.get(i).getId()==idPuntuacionesMangas.get(o)) {
					puntuacionesUsuarioCompeticion.add(puntuacionesUsuario.get(i));
				}
				else {
					
				}
			}
		}
		//System.out.println("UserDAO - Recorrido la asignación de puntuaciones coincidentes");
		
		return puntuacionesUsuarioCompeticion;
	}
}
