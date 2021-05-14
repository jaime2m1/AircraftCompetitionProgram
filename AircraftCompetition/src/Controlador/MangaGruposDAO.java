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
	private String getAllMangas = "SELECT id, competicionid, nmanga FROM Competicion";
	private String getMangaCompeticion = "SELECT id, competicionid, nmanga FROM Manga WHERE competicionid = ? and nmanga = ?";
	private String getMangasCompeticion = "SELECT id, competicionid, nmanga FROM Manga WHERE competicionid = ?";
	private String getNGruposManga = "SELECT COUNT(id) ngrupos FROM Grupos WHERE mangaid = ? AND ngrupo = ?";
	private String getIDPuntuacionesManga = "SELECT puntuacionid FROM Grupos WHERE mangaid = ?";

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
	 * Método que crea las 6 mangas de la competición
	 * 
	 * @param competicion
	 * @return
	 * @throws SQLException
	 */
	public boolean createMangas(CompeticionModelo competicion) throws SQLException {
		for(int i=1; i<=6; i++) {
			MangaModelo manga = new MangaModelo(i, competicion);
			addManga(manga);
		}
		return true;
	}
	/**
	 * Método para crear los grupos de la manga
	 * 
	 * @param manga
	 * @param puntuacion
	 * @return
	 * @throws SQLException
	 */
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
		preparedStatement = connect.prepareStatement(getAllMangas);
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
	 * Método para añadir una manga
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
	
	/**
	 * Método para anadir un grupo
	 * 
	 * @param grupo
	 * @return
	 * @throws SQLException
	 */
	public boolean addGrupo(GrupoModelo grupo) throws SQLException {
		preparedStatement = connect.prepareStatement(insertGrupo);
		preparedStatement.setInt(1, grupo.getManga().getId());
		preparedStatement.setInt(2, grupo.getPuntuacion().getId());
		preparedStatement.setInt(3, grupo.getNgrupo());
		preparedStatement.executeUpdate();
		return true;
	}

	/**
	 * Obtienes específica manga de una competición
	 * 
	 * @param competicion
	 * @param nmanga
	 * @return
	 * @throws SQLException
	 */
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
	
	public ArrayList<MangaModelo> getMangasCompeticion(int competicionid) throws SQLException {
		ArrayList<MangaModelo> mangas = new ArrayList<MangaModelo>();
		preparedStatement = connect.prepareStatement(getMangasCompeticion);
		preparedStatement.setInt(1, competicionid);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			MangaModelo manga = new MangaModelo();
			CompeticionModelo competicion = new CompeticionModelo();
			manga.setId(resultSet.getInt("id"));
			competicion.setId(competicionid);
			manga.setCompeticion(competicion);
			manga.setNmanga(resultSet.getInt("nmanga"));
			mangas.add(manga);
		}
		return mangas;
	}
	
	public ArrayList<Integer> getIDPuntuacionesManga(int manga) throws SQLException {
		ArrayList<Integer> puntuacionesID = new ArrayList<>();
		preparedStatement = connect.prepareStatement(getIDPuntuacionesManga);
		preparedStatement.setInt(1, manga);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			int IDpuntuacion = resultSet.getInt("puntuacionid");
			puntuacionesID.add(IDpuntuacion);
		}
		return puntuacionesID;
	}
	
	public int getNGruposManga(int manga, int grupo) throws SQLException {
		preparedStatement = connect.prepareStatement(getNGruposManga);
		preparedStatement.setInt(1, manga);
		preparedStatement.setInt(2, grupo);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			int nGrupo = resultSet.getInt("ngrupos");
			return nGrupo;
		}
		return 0;
	}
	
	
}
