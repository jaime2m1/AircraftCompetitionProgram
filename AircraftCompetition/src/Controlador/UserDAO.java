package Controlador;

import java.util.ArrayList;

import Modelo.UsuarioModelo;

public class UserDAO {
	public ArrayList<UsuarioModelo> listaUsuarios =  new ArrayList<UsuarioModelo>();
	
	
	public ArrayList<UsuarioModelo> getAllUsuarios(){
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
