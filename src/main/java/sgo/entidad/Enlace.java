package sgo.entidad;

import java.util.ArrayList;
import java.util.List;

import sgo.utilidades.Utilidades;

public class Enlace extends EntidadBase {
	private int id_enlace;
	private String url_completa;
	private String url_relativa;
	private int orden;
	private int padre;
	private String titulo;
	private int tipo;
	private int id_permiso;
	private String claseCss = "";
	private Boolean enlaceActual = false;
	private List<Enlace> enlaces = null;
	private Permiso entidadPermiso;
	
	//variable para hacer las validaciones.
	static final int MAXIMA_LONGITUD_TITULO=80;
	static final int MAXIMA_LONGITUD_URL_COMPLETA=100;
	static final int MAXIMA_LONGITUD_URL_RELATIVA=100;

	public Enlace() {
		this.enlaces = new ArrayList<Enlace>();
	}

	public void setEnlaces(List<Enlace> enlaces) {
		this.enlaces = enlaces;
	}

	public List<Enlace> getEnlaces() {
		return this.enlaces;
	}

	public void agregarEnlace(Enlace enlace) {
		this.enlaces.add(enlace);
	}

	public int getId() {
		return id_enlace;
	}

	public void setId(int Id) {
		this.id_enlace = Id;
	}

	public String getUrlCompleta() {
		return url_completa;
	}

	public void setUrlCompleta(String UrlCompleta) {
		this.url_completa = UrlCompleta;
	}

	public String getUrlRelativa() {
		return url_relativa;
	}

	public void setUrlRelativa(String UrlRelativa) {
		this.url_relativa = UrlRelativa;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int Orden) {
		this.orden = Orden;
	}

	public int getPadre() {
		return padre;
	}

	public void setPadre(int Padre) {
		this.padre = Padre;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int Tipo) {
		this.tipo = Tipo;
	}

	public int getPermiso() {
		return id_permiso;
	}

	public void setPermiso(int Permiso) {
		this.id_permiso = Permiso;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the claseCss
	 */
	public String getClaseCss() {
		return claseCss;
	}

	/**
	 * @param claseCss
	 *            the claseCss to set
	 */
	public void setClaseCss(String claseCss) {
		this.claseCss = claseCss;
	}

	/**
	 * @return the enlaceActual
	 */
	public Boolean getEnlaceActual() {
		return enlaceActual;
	}

	/**
	 * @param enlaceActual
	 *            the enlaceActual to set
	 */
	public void setEnlaceActual(Boolean enlaceActual) {
		this.enlaceActual = enlaceActual;
	}

	public Permiso getEntidadPermiso() {
		return entidadPermiso;
	}

	public void setEntidadPermiso(Permiso entidadPermiso) {
		this.entidadPermiso = entidadPermiso;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if (this.titulo.length() > MAXIMA_LONGITUD_TITULO || !Utilidades.esValido(this.titulo)){			
			return false;
		}
		
		if (this.url_completa.length() > MAXIMA_LONGITUD_URL_COMPLETA || !Utilidades.esValido(this.url_completa)){
			return false;
		}
		
		if (this.url_relativa.length() > MAXIMA_LONGITUD_URL_RELATIVA || !Utilidades.esValido(this.url_relativa)){
			return false;
		}
		
		if(!Utilidades.esValido(this.orden)){
			return false;
		}
		
		if(!Utilidades.esValido(this.padre)){
			return false;
		}
		
		if(!Utilidades.esValido(this.tipo)){
			return false;
		}
		
		if(!Utilidades.esValido(this.id_permiso)){
			return false;
		}

		return resultado;
	}


	

}