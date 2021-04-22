package Modelo;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Clase Modelo de Puntuacion
 */
public class PuntuacionModelo {
	private int id;
	private UsuarioModelo usuario;
	private int segundosVuelo;
	private int distanciaVuelo;
	private int alturaVuelo;

	public PuntuacionModelo(int id, UsuarioModelo usuario, int segundosVuelo, int distanciaVuelo, int alturaVuelo) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.segundosVuelo = segundosVuelo;
		this.distanciaVuelo = distanciaVuelo;
		this.alturaVuelo = alturaVuelo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UsuarioModelo getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModelo usuario) {
		this.usuario = usuario;
	}

	public int getSegundosVuelo() {
		return segundosVuelo;
	}

	public void setSegundosVuelo(int segundosVuelo) {
		this.segundosVuelo = segundosVuelo;
	}

	public int getdistanciaVuelo() {
		return distanciaVuelo;
	}

	public void setdistanciaVuelo(int distanciaVuelo) {
		this.distanciaVuelo = distanciaVuelo;
	}

	public int getAlturaVuelo() {
		return alturaVuelo;
	}

	public void setAlturaVuelo(int alturaVuelo) {
		this.alturaVuelo = alturaVuelo;
	}

}
