package Modelo;

import java.util.Date;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Clase Modelo de Competicion
 */
public class CompeticionModelo {
	private int id;
	public String nombre;
	private Date fecha;

	public CompeticionModelo() {

	}

	public CompeticionModelo(int id, String nombre, Date fecha) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
