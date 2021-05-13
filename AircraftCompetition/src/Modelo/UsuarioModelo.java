package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;

import Controlador.UserDAO;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Clase Modelo de Usuario
 */
public class UsuarioModelo {
	private String nombre;
	private String apellidos;
	private int nlicencia;
	private String contrasena;
	private int puesto = 0;
	private double puntuaciontotal = 0;
	private double puntuacioncompe = 0;

	public UsuarioModelo() {

	}
	
	public UsuarioModelo(String nombre, String apellidos, int nlicencia, String contrasena) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nlicencia = nlicencia;
		this.contrasena = contrasena;
		this.puesto = 0;
		this.puntuaciontotal = 0;
		this.puntuacioncompe = 0;
	}

	public UsuarioModelo(String nombre, String apellidos, int nlicencia, String contrasena, int puesto, double puntuaciontotal, double puntuacioncompe) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nlicencia = nlicencia;
		this.contrasena = contrasena;
		this.puesto = puesto;
		this.puntuaciontotal = puntuaciontotal;
		this.puntuacioncompe = puntuacioncompe;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public int getNlicencia() {
		return nlicencia;
	}

	public String getContrasena() {
		return contrasena;
	}

	public int getPuesto() {
		return puesto;
	}

	public double getPuntuacionTotal() {
		return puntuaciontotal;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setNlicencia(int nlicencia) {
		this.nlicencia = nlicencia;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public void setPuesto(int puesto) {
		this.puesto = puesto;
	}

	public void setPuntuacionTotal(double puntuaciontotal) {
		this.puntuaciontotal = puntuaciontotal;
	}
	
	public double getPuntuacionCompe() {
		return puntuacioncompe;
	}

	public void setPuntuacionCompe(double puntuacioncompe) {
		this.puntuacioncompe = puntuacioncompe;
	}

	public void updatePuntuacionTotal() {
		UserDAO dao = new UserDAO();
		ArrayList<PuntuacionModelo> puntuaciones = new ArrayList<PuntuacionModelo>();
		try {
			dao.connectDB();
			puntuaciones = dao.getPuntuacionesUsuario(nlicencia);
			for(int i=0; i<puntuaciones.size(); i++) {
				this.puntuaciontotal += puntuaciones.get(i).getPuntuacionTotal();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updatePuntuacionCompe(int competicionId) {
		UserDAO dao = new UserDAO();
		ArrayList<PuntuacionModelo> puntuaciones = new ArrayList<PuntuacionModelo>();
		try {
			dao.connectDB();
			puntuaciones = dao.getPuntuacionesUsuarioCompeticion(this.nlicencia, competicionId);
			for(int i=0; i<puntuaciones.size(); i++) {
				this.puntuacioncompe += puntuaciones.get(i).getPuntuacionTotal();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
