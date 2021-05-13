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
	
	private int puntuacionTotal;
	
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
	
	public void setPuntuacionTotal() {
		this.puntuacionTotal = getPuntuacionTotal();
	}
	
	public int getPuntuacionTotal() {
		int total;
		total = this.segundosVuelo;
		
		switch(this.distanciaVuelo){
		case 0:
			total += 50;
			break;
		case 1:
			total += 45;
			break;
		case 2:
			total += 40;
			break;
		case 3: case 4:
			total += 35;
			break;
		case 5: case 6: case 7: case 8: case 9:
			total += 30;
			break;
		default:
			total += 0;
			break;
		}
		
		if(this.alturaVuelo <= 200) {
			total -= (this.alturaVuelo/2);
		}else {
			total -= 100;
			total -= ((this.alturaVuelo - 200) * 3);
		}
		return total;
	}
}
