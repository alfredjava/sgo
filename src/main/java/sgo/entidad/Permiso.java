package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Permiso extends EntidadBase {
	private int id_permiso;
	private String nombre;
	private int estado;
	
	//variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_NOMBRE=40;

	public int getId() {
		return id_permiso;
	}

	public void setId(int Id) {
		this.id_permiso = Id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String Nombre) {
		this.nombre = Nombre;
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if (this.nombre.length() > MAXIMA_LONGITUD_NOMBRE  || !Utilidades.esValido(this.nombre)){		
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}

}