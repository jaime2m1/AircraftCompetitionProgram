package Modelo;

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
	private double puntuacion = 0;

	public UsuarioModelo() {

	}
	
	public UsuarioModelo(String nombre, String apellidos, int nlicencia, String contrasena) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nlicencia = nlicencia;
		this.contrasena = contrasena;
		this.puesto = 0;
		this.puntuacion = 0;
	}

	public UsuarioModelo(String nombre, String apellidos, int nlicencia, String contrasena, int puesto, double puntuacion) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nlicencia = nlicencia;
		this.contrasena = contrasena;
		this.puesto = puesto;
		this.puntuacion = puntuacion;
		
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

	public double getPuntuacion() {
		return puntuacion;
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

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}


}
