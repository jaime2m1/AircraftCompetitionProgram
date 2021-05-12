package Modelo;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import Controlador.UserCompetitionDAO;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Clase Modelo de Competicion
 */
public class CompeticionModelo {
	private int id;
	public String nombre;
	private Date fecha;
	private int nParticipantes=0;

	public CompeticionModelo() {

	}

	public CompeticionModelo(int id, String nombre, Date fecha) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		setNParticipantes();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
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
	
	/**
	 * Hacemos que devuelva la fecha en formato java.sql.Date
	 * @return
	 */
	public java.sql.Date getFechaSQL() {
		java.sql.Date fechasql = new java.sql.Date(fecha.getTime());
		return fechasql;
	}
	
	public LocalDate getFechaLD() {
		LocalDate fechaLD = Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return fechaLD;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public int getNParticipantes() {
		return nParticipantes;
	}
	
	public String getNParticipantesSTR() {
		return String.valueOf(nParticipantes);
	}
	
	public void setNParticipantes() {
		UserCompetitionDAO dao = new UserCompetitionDAO();
		try {
			dao.connectDB();
			this.nParticipantes = dao.getUsuariosDeCompeticion(id).size();
			//System.out.println("nParticipantes de "+this.nombre+" con id "+this.id+" es "+this.nParticipantes);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
