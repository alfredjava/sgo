package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Estacion extends EntidadBase {
	private int id_estacion;
	private String nombre;
	private int tipo;
	private int estado;
	private int id_operacion;
	private String sincronizado_el;
	private String fecha_referencia;
	private String codigo_referencia;
	private Operacion operacion;

	// variable para hacer las validaciones.
	static final int MAXIMA_LONGITUD_NOMBRE = 20;

	public int getId() {
		return id_estacion;
	}

	public void setId(int Id) {
		this.id_estacion = Id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String Nombre) {
		this.nombre = Nombre;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int Tipo) {
		this.tipo = Tipo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int Estado) {
		this.estado = Estado;
	}

	public int getIdOperacion() {
		return id_operacion;
	}

	public void setIdOperacion(int IdOperacion) {
		this.id_operacion = IdOperacion;
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

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public boolean validar(){
		boolean resultado = true;

		if (this.nombre.length() > MAXIMA_LONGITUD_NOMBRE || !Utilidades.esValido(this.nombre)){			
			return false;
		}
		
		if(!Utilidades.esValido(this.tipo)){
			return false;
		}
		
		if(!Utilidades.esValido(this.id_operacion)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}
}