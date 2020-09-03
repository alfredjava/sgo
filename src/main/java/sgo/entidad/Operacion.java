package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Operacion extends EntidadBase {
	private int id_operacion;
	private int id_cliente;
	private String nombre;
	private float volumen_promedio_cisterna;
	private int sincronizado_el;
	private String referencia_planta_recepcion;
	private String referencia_detinatario_mercaderia;
	private int estado;
	private Cliente cliente;
	
	//variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_NOMBRE=150;
	static final int MAXIMA_LONGITUD_REF_PLANTA_REFEPCION=20;
	static final int MAXIMA_LONGITUD_REF_PLANTA_MERCADERIA=20;
	
	public String getReferenciaPlantaRecepcion() {
		return referencia_planta_recepcion;
	}

	public void setReferenciaPlantaRecepcion(String referencia_planta_recepcion) {
		this.referencia_planta_recepcion = referencia_planta_recepcion;
	}

	public String getReferenciaDetinatarioMercaderia() {
		return referencia_detinatario_mercaderia;
	}

	public void setReferenciaDetinatarioMercaderia(
			String referencia_detinatario_mercaderia) {
		this.referencia_detinatario_mercaderia = referencia_detinatario_mercaderia;
	}

	public int getId() {
		return id_operacion;
	}

	public void setId(int Id) {
		this.id_operacion = Id;
	}

	public float getVolumenPromedioCisterna() {
		return volumen_promedio_cisterna;
	}

	public void setVolumenPromedioCisterna(float VolumenPromedioCisterna) {
		this.volumen_promedio_cisterna = VolumenPromedioCisterna;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String Nombre) {
		this.nombre = Nombre;
	}

	public int getIdCliente() {
		return id_cliente;
	}

	public void setIdCliente(int IdCliente) {
		this.id_cliente = IdCliente;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int Estado) {
		this.estado = Estado;
	}

	public int getSincronizadoEl() {
		return sincronizado_el;
	}

	public void setSincronizadoEl(int SincronizadoEl) {
		this.sincronizado_el = SincronizadoEl;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if (this.nombre.length() > MAXIMA_LONGITUD_NOMBRE  || !Utilidades.esValido(this.nombre)){		
			return false;
		}
		
		if (this.referencia_detinatario_mercaderia.length() > MAXIMA_LONGITUD_REF_PLANTA_MERCADERIA  || !Utilidades.esValido(this.referencia_detinatario_mercaderia)){			
			return false;
		}
		
		if (this.referencia_planta_recepcion.length() > MAXIMA_LONGITUD_REF_PLANTA_REFEPCION  || !Utilidades.esValido(this.referencia_planta_recepcion)){			
			return false;
		}
		
		if(!Utilidades.esValido(this.id_cliente)){
			return false;
		}
		
		if(!Utilidades.esValido(this.volumen_promedio_cisterna)){
			return false;
		}

		if(!Utilidades.esValido(this.sincronizado_el)){
			return false;
		}
		
		return resultado;
	}

}