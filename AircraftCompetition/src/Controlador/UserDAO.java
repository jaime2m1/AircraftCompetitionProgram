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
	
	private String insertUsuario = "INSERT INTO Usuarios (nombre, apellidos, nlicencia, contrasena) VALUES(?,?,?,?)";
	private String deleteUsuario = "DELETE FROM Usuarios WHERE nlicencia = ?";
	private String getAllUsuarios = "Select nombre, apellidos, nlicencia, contrasena FROM Usuarios";
	
	
	public boolean connectDB() throws SQLException {
		connect = DriverManager.getConnection("jdbc:mysql://" + host + "/feedback?"
		              + "user=" + user + "&password=" + passwd );
		return true;
	}
	public ArrayList<UsuarioModelo> getAllUsuarios() throws SQLException{
		listaUsuarios.clear();
		preparedStatement = connect.prepareStatement(getAllUsuarios);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			UsuarioModelo usuario = new UsuarioModelo();
			usuario.setNombre(resultSet.getString("nombre"));
			usuario.setApellidos(resultSet.getString("apellidos"));
			usuario.setNlicencia(resultSet.getString("nlicencia"));
			usuario.setContrasena(resultSet.getString("contrasena"));
			listaUsuarios.add(usuario);
		}
		return listaUsuarios;
	}
	public UsuarioModelo getUsuario(int id) {
		return null;
	}
	public boolean delUsuario(int id) throws SQLException {
		preparedStatement = connect.prepareStatement(deleteUsuario);
		return true;
	}
	public boolean addUsuario(UsuarioModelo usuario) throws SQLException {
		preparedStatement = connect.prepareStatement(insertUsuario);
		preparedStatement.setString(0, usuario.getNombre());
		preparedStatement.setString(1, usuario.getApellidos());
		preparedStatement.setString(2, usuario.getNlicencia());
		preparedStatement.setString(3, usuario.getContrasena());
		preparedStatement.executeUpdate();
		return true;
	}
	public boolean modUsuario(int id, UsuarioModelo usuario) {
		return true;
	}
}
