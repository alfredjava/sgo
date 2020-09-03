package sgo.entidad;

import java.util.List;

import sgo.utilidades.Utilidades;

public class Rol extends EntidadBase{
	private int _id;
	private String _nombre;
	private int estado;
	private List<Permiso> _permisos;
	
	//variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_NOMBRE=20;
  
	public void setPermisos (List<Permiso> permisos){
		this._permisos = permisos;
	}

	public List<Permiso> getPermisos(){
		return _permisos;
	}

	/**
	 * @return the _id
	 */
	public int getId() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void setId(int _id) {
		this._id = _id;
	}
	/**
	 * @return the _nombre
	 */
	public String getNombre() {
		return _nombre;
	}
	/**
	 * @param _nombre the _nombre to set
	 */
	public void setNombre(String _nombre) {
		this._nombre = _nombre;
	}
	
	public boolean searchPermiso(String fPermiso){
		boolean found=false;
		for (Permiso mPermiso : this._permisos) {
			if (mPermiso.getNombre().equals(fPermiso)) {
				found=true;
				return found;
			}
		}
		return found;
	}
	
	public boolean searchPermiso(int idPermiso){
		boolean found=false;
		for (Permiso mPermiso : this._permisos) {
			if (mPermiso.getId()==(idPermiso)) {
				found=true;
				return found;
			}
		}
		return found;
	}


	public Respuesta validate() {		
		Respuesta response= new Respuesta();
		response.mensaje="OK";
		response.estado=true;
		response.valor=null;
//		if (!this.validateNombre()){
//			response.message=constants.INVALID_FIELD.replace("%", "nombre");
//			response.status=false;
//		}
//		if (!this.validatePermisos()){
//			response.message=constants.INVALID_FIELD.replace("%", "permisos");
//			response.status=false;
//		}
		return response;
	}
	
	private boolean validateNombre(){
		return ((this._nombre.length()>=3) && (this._nombre.length()<=25));
	}
	
	private boolean validatePermisos(){		
		return (this._permisos.size()!=0);
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}	
	
	public boolean validar(){
		boolean resultado = true;

		if (this._nombre.length() > MAXIMA_LONGITUD_NOMBRE || !Utilidades.esValido(this._nombre)){				
			return false;
		}
				
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}
}
