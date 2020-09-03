package sgo.entidad;

import java.sql.Date;

import sgo.utilidades.Utilidades;

public class Conductor extends EntidadBase {
	private int id_conductor;
	private String brevete;
	private String nombres;
	private String apellidos;
	private String dni;
	private Date fecha_nacimiento;
	private int id_transportista;
	private int estado;
	private String sincronizado_el;
	private String fecha_referencia;
	private String codigo_referencia;
	private Transportista transportista;

	//variables para hacer las validaciones
	static final int MAXIMA_LONGITUD_BREVETE=20;
	static final int MAXIMA_LONGITUD_APELLIDOS=150;
	static final int MAXIMA_LONGITUD_NOMBRES=150;
	static final int MAXIMA_LONGITUD_DNI=8;
	
	public int getId() {
		return id_conductor;
	}

	public void setId(int Id) {
		this.id_conductor = Id;
	}

	public String getBrevete() {
		return brevete;
	}

	public void setBrevete(String Brevete) {
		this.brevete = Brevete;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String Apellidos) {
		this.apellidos = Apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String Nombres) {
		this.nombres = Nombres;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String Dni) {
		this.dni = Dni;
	}

	public Date getFechaNacimiento() {
		return fecha_nacimiento;
	}

	public void setFechaNacimiento(Date FechaNacimiento) {
		this.fecha_nacimiento = FechaNacimiento;
	}

	public int getIdTransportista() {
		return id_transportista;
	}

	public void setIdTransportista(int IdTransportista) {
		this.id_transportista = IdTransportista;
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

	public Transportista getTransportista() {
		return transportista;
	}

	public void setTransportista(Transportista transportista) {
		this.transportista = transportista;
	}
	
	public String getNombreCompleto() {
		String apellidosNombre = this.apellidos + ", " + this.nombres;
		return apellidosNombre;
	}

	public boolean validar(){
		boolean resultado = true;

		if (this.brevete.length() > MAXIMA_LONGITUD_BREVETE  || !Utilidades.esValido(this.brevete)){		
			return false;
		}
		
		if (this.apellidos.length() > MAXIMA_LONGITUD_APELLIDOS  || !Utilidades.esValido(this.apellidos)){			
			return false;
		}
		
		if (this.nombres.length() > MAXIMA_LONGITUD_NOMBRES  || !Utilidades.esValido(this.nombres)){			
			return false;
		}
		
		if (this.dni.length() > MAXIMA_LONGITUD_DNI || !Utilidades.esValido(this.dni)){			
			return false;
		}
		
		if(!Utilidades.esValido(this.id_transportista)){
			return false;
		}
		
		if(!Utilidades.esValido(this.fecha_nacimiento)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}

}