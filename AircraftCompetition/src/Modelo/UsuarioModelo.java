package Modelo;

public class UsuarioModelo {
	private String nombre;
	private String apellidos;
	private int nlicencia;
	private String contrasena;
	
	public UsuarioModelo() {
		
	}

	public UsuarioModelo(String nombre, String apellidos, int nlicencia, String contrasena) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nlicencia = nlicencia;
		this.contrasena = contrasena;
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
	
}
