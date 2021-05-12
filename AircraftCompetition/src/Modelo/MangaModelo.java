package Modelo;

public class MangaModelo {
	private int id;
	private CompeticionModelo competicion;
	private int nmanga;
	
	public MangaModelo() {
		
	}
	
	public MangaModelo(int nmanga, CompeticionModelo competicion) {
		this.competicion = competicion;
		this.nmanga = nmanga;
	}

	public MangaModelo(int id, CompeticionModelo competicion, int nmanga) {
		this.id = id;
		this.competicion = competicion;
		this.nmanga = nmanga;
	}

	public int getId() {
		return id;
	}

	public CompeticionModelo getCompeticion() {
		return competicion;
	}

	public int getNmanga() {
		return nmanga;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCompeticion(CompeticionModelo competicion) {
		this.competicion = competicion;
	}

	public void setNmanga(int nmanga) {
		this.nmanga = nmanga;
	}
	
	
	
}
