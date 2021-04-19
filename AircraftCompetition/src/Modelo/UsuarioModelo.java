package Modelo;

public class UsuarioModelo {
	private String nombre;
	private String apellidos;
	private String nlicencia;
	private String contrasena;
	
	public UsuarioModelo() {
		
	}

	public UsuarioModelo(String nombre, String apellidos, String nlicencia, String contrasena) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nlicencia = nlicencia;
		this.contrasena = contrasena;
	}


	private String getNombre() {
		return nombre;
	}

	private String getApellidos() {
		return apellidos;
	}

	private String getNlicencia() {
		return nlicencia;
	}

	private String getContrasena() {
		return contrasena;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	private void setNlicencia(String nlicencia) {
		this.nlicencia = nlicencia;
	}

	private void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
}
