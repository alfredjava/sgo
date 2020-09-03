package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Vehiculo extends EntidadBase {
	private int id_vehiculo;
	private String nombre_corto;
	private String descripcion;
	private int id_propietario;
	private int estado;
	private String sincronizado_el;
	private String fecha_referencia;
	private String codigo_referencia;
	private Propietario propietario;

	// variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_NOMBRE_CORTO = 20;
	static final int MAXIMA_LONGITUD_DESCRIPCION = 80;

	public int getId() {
		return id_vehiculo;
	}

	public void setId(int Id) {
		this.id_vehiculo = Id;
	}

	public String getNombreCorto() {
		return nombre_corto;
	}

	public void setNombreCorto(String NombreCorto) {
		this.nombre_corto = NombreCorto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.descripcion = Descripcion;
	}

	public int getIdPropietario() {
		return id_propietario;
	}

	public void setIdPropietario(int IdPropietario) {
		this.id_propietario = IdPropietario;
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

	public Propietario getPropietario() {
		return propietario;
	}

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if (this.nombre_corto.length() > MAXIMA_LONGITUD_NOMBRE_CORTO  || !Utilidades.esValido(this.nombre_corto)){		
			return false;
		}
		
		if (this.descripcion.length() > MAXIMA_LONGITUD_DESCRIPCION  || !Utilidades.esValido(this.descripcion)){			
			return false;
		}
		
		if(!Utilidades.esValido(this.id_propietario)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}

}