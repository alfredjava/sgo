package sgo.entidad;

import java.sql.Date;
import java.util.List;

public class AutorizacionUsuario extends EntidadBase{
	private int id_ausuario;
	private int id_usuario;
	private int id_autorizacion;
	private String codigo_autorizacion;
	private Date vigente_desde;
	private Date vigente_hasta;
	private int estado;
	private String identidad;
	private Usuario eUsuario;
	private Autorizacion eAutorizacion;
	private List<Usuario> usuario;
	private List<Autorizacion> autorizacion;
	/**
	 * @return the id_ausuario
	 */
	public int getId() {
		return id_ausuario;
	}
	/**
	 * @param id_ausuario the id_ausuario to set
	 */
	public void setId(int Id) {
		this.id_ausuario = Id;
	}
	/**
	 * @return the id_usuario
	 */
	public int getIdUsuario() {
		return id_usuario;
	}
	/**
	 * @param id_usuario the id_usuario to set
	 */
	public void setIdUsuario(int IdUsuario) {
		this.id_usuario = IdUsuario;
	}
	/**
	 * @return the id_autorizacion
	 */
	public int getIdAutorizacion() {
		return id_autorizacion;
	}
	/**
	 * @param id_autorizacion the id_autorizacion to set
	 */
	public void setIdAutorizacion(int IdAutorizacion) {
		this.id_autorizacion = IdAutorizacion;
	}
	
	/**
	 * @return the codigo_autorizacion
	 */
	public String getCodigoAutorizacion() {
		return codigo_autorizacion;
	}
	/**
	 * @param codigo_autorizacion the codigo_autorizacion to set
	 */
	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigo_autorizacion = codigoAutorizacion;
	}
	/**
	 * @return the vigente_desde
	 */
	public Date getVigenteDesde() {
		return vigente_desde;
	}
	/**
	 * @param vigente_desde the vigente_desde to set
	 */
	public void setVigenteDesde(Date vigenteDesde) {
		this.vigente_desde = vigenteDesde;
	}
	/**
	 * @return the vigente_hasta
	 */
	public Date getVigenteHasta() {
		return vigente_hasta;
	}
	/**
	 * @param vigente_hasta the vigente_hasta to set
	 */
	public void setVigenteHasta(Date vigenteHasta) {
		this.vigente_hasta = vigenteHasta;
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
	/**
	 * @return the usuario
	 */
	public List<Usuario> getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the autorizacion
	 */
	public List<Autorizacion> getAutorizacion() {
		return autorizacion;
	}
	/**
	 * @param autorizacion the autorizacion to set
	 */
	public void setAutorizacion(List<Autorizacion> autorizacion) {
		this.autorizacion = autorizacion;
	}
	/**
	 * @return the eUsuario
	 */
	public Usuario geteUsuario() {
		return eUsuario;
	}
	/**
	 * @param eUsuario the eUsuario to set
	 */
	public void seteUsuario(Usuario eUsuario) {
		this.eUsuario = eUsuario;
	}
	/**
	 * @return the eAutorizacion
	 */
	public Autorizacion geteAutorizacion() {
		return eAutorizacion;
	}
	/**
	 * @param eAutorizacion the eAutorizacion to set
	 */
	public void seteAutorizacion(Autorizacion eAutorizacion) {
		this.eAutorizacion = eAutorizacion;
	}
	/**
	 * @return the identidad
	 */
	public String getIdentidad() {
		return identidad;
	}
	/**
	 * @param identidad the identidad to set
	 */
	public void setIdentidad(String identidad) {
		this.identidad = identidad;
	}
	
	
	

	
}
