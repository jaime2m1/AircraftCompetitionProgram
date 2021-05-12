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
	private int penalizacion;
	
	public PuntuacionModelo(){
		
	}
			
	public PuntuacionModelo(int id, UsuarioModelo usuario, int segundosVuelo, int distanciaVuelo, int alturaVuelo) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.segundosVuelo = segundosVuelo;
		this.distanciaVuelo = distanciaVuelo;
		this.alturaVuelo = alturaVuelo;
		this.penalizacion = 0;
	}

	public PuntuacionModelo(int id, UsuarioModelo usuario, int segundosVuelo, int distanciaVuelo, int alturaVuelo, int penalizacion) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.segundosVuelo = segundosVuelo;
		this.distanciaVuelo = distanciaVuelo;
		this.alturaVuelo = alturaVuelo;
		this.penalizacion = penalizacion;
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

	public int getDistanciaVuelo() {
		return distanciaVuelo;
	}

	public void setDistanciaVuelo(int distanciaVuelo) {
		this.distanciaVuelo = distanciaVuelo;
	}

	public int getAlturaVuelo() {
		return alturaVuelo;
	}

	public void setAlturaVuelo(int alturaVuelo) {
		this.alturaVuelo = alturaVuelo;
	}


	public int getPenalizacion() {
		return penalizacion;
	}


	public void setPenalizacion(int penalizacion) {
		this.penalizacion = penalizacion;
	}
	

}
