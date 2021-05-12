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
import Modelo.GrupoModelo;
import Modelo.MangaModelo;
import Modelo.PuntuacionModelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MangaGruposDAO {
	public ArrayList<CompeticionModelo> listaUsuarios = new ArrayList<CompeticionModelo>();

	private Connection connect = null;
	// private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private String host = "jaime2m1.gq";
	private String database = "AircraftCompetitionDB";
	private String user = "Aircraft";
	private String passwd = "Competition";

	private String insertManga = "INSERT INTO Manga (competicionid, nmanga) VALUES(?, ?)";
	private String insertGrupo = "INSERT INTO Grupos (mangaid, puntuacionid, ngrupo) VALUES(?, ?, ?)";
	private String deleteMangaCompeticion = "DELETE FROM Manga WHERE competicionid = ?";
	private String updateCompeticion = "UPDATE Competicion SET nombre = ?, fecha = ? WHERE id = ?";
	private String getAllCompeticiones = "SELECT id, nombre, fecha FROM Competicion";
	private String getCompeticion = "SELECT id, nombre, fecha FROM Competicion WHERE nlicencia = ?";
	private String getMangaCompeticion = "SELECT id, competicionid, nmanga FROM Manga WHERE competicionid = ? and nmanga = ?";

	/**
	 * Constructor del DAO que usa la configuración del XML
	 */
	public MangaGruposDAO() {
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
	 * Método para esrtablecer la conexión
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
	
	public boolean createMangas(CompeticionModelo competicion) throws SQLException {
		for(int i=1; i<=6; i++) {
			MangaModelo manga = new MangaModelo(i, competicion);
			addManga(manga);
		}
		return true;
	}
	public boolean createGrupos(MangaModelo manga, PuntuacionModelo puntuacion) throws SQLException {
		for(int i=1; i<=2; i++) {
			GrupoModelo grupo = new GrupoModelo(manga, puntuacion, i);
			addGrupo(grupo);
		}
		return true;
	}

	/**
	 * Método que devuelve todas las competiciones en formato ArrayList
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CompeticionModelo> getAllMangas() throws SQLException {
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
	public boolean delMangaCompeticion(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(deleteMangaCompeticion);
		preparedStatement.setInt(1, id);
		preparedStatement.executeQuery();
		return true;
	}

	/**
	 * Método para añadir una competición
	 * 
	 * @param competicion
	 * @return
	 * @throws SQLException
	 */
	public boolean addManga(MangaModelo manga) throws SQLException {
		preparedStatement = connect.prepareStatement(insertManga);
		preparedStatement.setInt(1, manga.getCompeticion().getId());
		preparedStatement.setInt(2, manga.getNmanga());
		preparedStatement.executeUpdate();
		return true;
	}
	
	public boolean addGrupo(GrupoModelo grupo) throws SQLException {
		preparedStatement = connect.prepareStatement(insertGrupo);
		preparedStatement.setInt(1, grupo.getManga().getId());
		preparedStatement.setInt(2, grupo.getPuntuacion().getId());
		preparedStatement.setInt(3, grupo.getNgrupo());
		preparedStatement.executeUpdate();
		return true;
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

	public MangaModelo getMangaCompeticion(CompeticionModelo competicion, int nmanga) throws SQLException {
		preparedStatement = connect.prepareStatement(getMangaCompeticion);
		preparedStatement.setInt(1, competicion.getId());
		preparedStatement.setInt(2, nmanga);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			MangaModelo manga = new MangaModelo(resultSet.getInt("id"), competicion, resultSet.getInt("nmanga"));
			return manga;
		}
		MangaModelo manga = new MangaModelo();
		return manga;
	}
}
