package Controlador;

import java.util.ArrayList;

import Modelo.UsuarioModelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class UserDAO {
	
	public ArrayList<UsuarioModelo> listaUsuarios =  new ArrayList<UsuarioModelo>();
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	final private String host = "jaime2m1.gq";
	final private String user = "Aircraft";
	final private String passwd = "Competition";
	
	public boolean connectDB() throws SQLException {
		connect = DriverManager.getConnection("jdbc:mysql://" + host + "/feedback?"
		              + "user=" + user + "&password=" + passwd );
		return true;
	}
	public ArrayList<UsuarioModelo> getAllUsuarios() throws SQLException{
		preparedStatement = connect.prepareStatement("Select nombre, apellido, nlicencia, contrasena from Users");
		return listaUsuarios;
	}
	public UsuarioModelo getUsuario(int id) {
		return null;
	}
	public boolean delUsuario(int id) {
		return true;
	}
	public boolean addUsuario(UsuarioModelo usuario) {
		return true;
	}
	public boolean modUsuario(int id, UsuarioModelo usuario) {
		return true;
	}
}
