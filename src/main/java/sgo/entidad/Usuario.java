package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Usuario extends EntidadBase {
	private int id_usuario;
	private String nombre;
	private String clave;
	private String identidad;
	private String zona_horaria;
	private int estado;
	private String email;
	private int cambio_clave;
	private int id_rol;
	private int id_operacion;
	private int id_cliente;
	private Rol rol;
	private int tipo;
	private Operacion operacion;
	

	// variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_NOMBRE = 16;
	static final int MAXIMA_LONGITUD_IDENTIDAD = 120;
	static final int MAXIMA_LONGITUD_CLAVE = 64;
	static final int MAXIMA_LONGITUD_ZONA_HORARIA = 20;
	static final int MAXIMA_LONGITUD_EMAIL = 250;

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public int getId() {
		return id_usuario;
	}

	public void setId(int Id) {
		this.id_usuario = Id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String Nombre) {
		this.nombre = Nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String Clave) {
		this.clave = Clave;
	}

	public String getIdentidad() {
		return identidad;
	}

	public void setIdentidad(String Identidad) {
		this.identidad = Identidad;
	}

	public String getZonaHoraria() {
		return zona_horaria;
	}

	public void setZonaHoraria(String ZonaHoraria) {
		this.zona_horaria = ZonaHoraria;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int Estado) {
		this.estado = Estado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String Email) {
		this.email = Email;
	}

	public int getCambioClave() {
		return cambio_clave;
	}

	public void setCambioClave(int CambioClave) {
		this.cambio_clave = CambioClave;
	}

	public Rol getRol() {
		return rol;
	}

	/**
	 * @param _rol the _rol to set
	 */
	public void setRol(Rol _rol) {
		this.rol = _rol;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int Tipo) {
		this.tipo = Tipo;
	}

	public int getId_operacion() {
		return id_operacion;
	}

	public void setId_operacion(int id_operacion) {
		this.id_operacion = id_operacion;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if (this.nombre.length() > MAXIMA_LONGITUD_NOMBRE|| !Utilidades.esValido(this.nombre)){		
			return false;
		}
		
		if (this.identidad.length() > MAXIMA_LONGITUD_IDENTIDAD  || !Utilidades.esValido(this.identidad)){			
			return false;
		}
		
		if (this.zona_horaria.length() > MAXIMA_LONGITUD_ZONA_HORARIA  || !Utilidades.esValido(this.zona_horaria)){			
			return false;
		}
		
		if (this.clave.length() > MAXIMA_LONGITUD_CLAVE || !Utilidades.esValido(this.clave)){			
			return false;
		}
		
		if (this.email.length() > MAXIMA_LONGITUD_EMAIL || !Utilidades.esValido(this.email)){			
			return false;
		}
		
		if(!Utilidades.esValido(this.id_rol)){
			return false;
		}
		
		if(!Utilidades.esValido(this.id_operacion)){
			return false;
		}

		if(!Utilidades.esValido(this.tipo)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}

 /**
  * @return the id_cliente
  */
 public int getIdCliente() {
  return id_cliente;
 }

 /**
  * @param id_cliente the id_cliente to set
  */
 public void setIdCliente(int id_cliente) {
  this.id_cliente = id_cliente;
 }

}