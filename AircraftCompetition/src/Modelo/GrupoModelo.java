package Modelo;

public class GrupoModelo {
	private int id;
	private MangaModelo manga;
	private PuntuacionModelo puntuacion;
	private int ngrupo;
	
	public GrupoModelo() {
		
	}

	public GrupoModelo(MangaModelo manga, PuntuacionModelo puntuacion, int ngrupo) {
		this.manga = manga;
		this.puntuacion = puntuacion;
		this.ngrupo = ngrupo;
	}

	public GrupoModelo(int id, MangaModelo manga, PuntuacionModelo puntuacion, int ngrupo) {
		super();
		this.id = id;
		this.manga = manga;
		this.puntuacion = puntuacion;
		this.ngrupo = ngrupo;
	}

	public int getId() {
		return id;
	}

	public MangaModelo getManga() {
		return manga;
	}

	public PuntuacionModelo getPuntuacion() {
		return puntuacion;
	}

	public int getNgrupo() {
		return ngrupo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setManga(MangaModelo manga) {
		this.manga = manga;
	}

	public void setPuntuacion(PuntuacionModelo puntuacion) {
		this.puntuacion = puntuacion;
	}

	public void setNgrupo(int ngrupo) {
		this.ngrupo = ngrupo;
	}
	
}
