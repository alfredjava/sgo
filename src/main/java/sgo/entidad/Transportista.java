package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Transportista extends EntidadBase {
	private int id_transportista;
	private String razon_social;
	private String nombre_corto;
	private String ruc;
	private int estado;
	private String sincronizado_el;
	private String fecha_referencia;
	private String codigo_referencia;

	// variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_RAZON_SOCIAL = 150;
	static final int MAXIMA_LONGITUD_NOMBRE_CORTO = 20;
	static final int MAXIMA_LONGITUD_RUC = 11;

	public int getId() {
		return id_transportista;
	}

	public void setId(int Id) {
		this.id_transportista = Id;
	}

	public String getRazonSocial() {
		return razon_social;
	}

	public void setRazonSocial(String RazonSocial) {
		this.razon_social = RazonSocial;
	}

	public String getNombreCorto() {
		return nombre_corto;
	}

	public void setNombreCorto(String NombreCorto) {
		this.nombre_corto = NombreCorto;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String Ruc) {
		this.ruc = Ruc;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int Estado) {
		this.estado = Estado;
	}

	public String getSincronizadoEl() {
		return sincronizado_el;
	}

	public void setSincronizadoEl(String SincronizadoEl) {
		this.sincronizado_el = SincronizadoEl;
	}

	public String getFechaReferencia() {
		return fecha_referencia;
	}

	public void setFechaReferencia(String FechaReferencia) {
		this.fecha_referencia = FechaReferencia;
	}

	public String getCodigoReferencia() {
		return codigo_referencia;
	}

	public void setCodigoReferencia(String CodigoReferencia) {
		this.codigo_referencia = CodigoReferencia;
	}

	public boolean validar(){
		boolean resultado = true;
		
		if (this.razon_social.length()> MAXIMA_LONGITUD_RAZON_SOCIAL  || !Utilidades.esValido(this.razon_social)){			
			return false;
		}
		
		if (this.nombre_corto.length()> MAXIMA_LONGITUD_NOMBRE_CORTO  || !Utilidades.esValido(this.nombre_corto)){
			return false;
		}
		
		if ((this.ruc.length() != MAXIMA_LONGITUD_RUC  || !Utilidades.esValido(this.ruc))){
			return false;
		}
		
		if (!Utilidades.esValido(this.estado)){
			return false;
		}
		
		return resultado;
	}
	
	
}