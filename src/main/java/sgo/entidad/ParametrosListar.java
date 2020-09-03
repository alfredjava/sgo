package sgo.entidad;

import java.sql.Date;

import sgo.utilidades.Constante;

public class ParametrosListar {
	private int paginacion = Constante.CON_PAGINACION;
	private int inicioPaginacion = 0;
	private int registrosPagina = Constante.CANTIDAD_PAGINACION;
	private String campoOrdenamiento = "";
	private String sentidoOrdenamiento = "ASC";
	private String valorBuscado = "";
	private int filtroEstado = Constante.FILTRO_TODOS;
	private String filtroFechaInicio = "";
	private String filtroFechaFinal = "";
	private String filtroUsuario = "";
	private String filtroTabla = "";
	private String txtFiltro = "";
	private int filtroOperacion;
	private String filtroFechaPlanificada = "";
	private int filtroEstacion =0;

	/**
	 * @return the filtroFechaPlanificada
	 */
	public String getFiltroFechaPlanificada() {
		return filtroFechaPlanificada;
	}

	/**
	 * @param filtroFechaPlanificada the filtroFechaPlanificada to set
	 */
	public void setFiltroFechaPlanificada(String filtroFechaPlanificada) {
		this.filtroFechaPlanificada = filtroFechaPlanificada;
	}

	/**
	 * @return the filtroOperacion
	 */
	public int getFiltroOperacion() {
		return filtroOperacion;
	}

	/**
	 * @param filtroOperacion the filtroOperacion to set
	 */
	public void setFiltroOperacion(int filtroOperacion) {
		this.filtroOperacion = filtroOperacion;
	}

	/**
	 * @return the txtFiltro
	 */
	public String getTxtFiltro() {
		return txtFiltro;
	}

	/**
	 * @param txtFiltro the txtFiltro to set
	 */
	public void setTxtFiltro(String txtFiltro) {
		this.txtFiltro = txtFiltro;
	}

	/**
	 * @return the filtroEstado
	 */
	public int getFiltroEstado() {
		return filtroEstado;
	}

	/**
	 * @param filtroEstado the filtroEstado to set
	 */
	public void setFiltroEstado(int filtroEstado) {
		this.filtroEstado = filtroEstado;
	}

	public String getFiltroFechaInicio() {
		return filtroFechaInicio;
	}

	public void setFiltroFechaInicio(String filtroFechaInicio) {
		this.filtroFechaInicio = filtroFechaInicio;
	}

	public String getFiltroFechaFinal() {
		return filtroFechaFinal;
	}

	public void setFiltroFechaFinal(String filtroFechaFinal) {
		this.filtroFechaFinal = filtroFechaFinal;
	}

	public String getFiltroUsuario() {
		return filtroUsuario;
	}

	public void setFiltroUsuario(String filtroUsuario) {
		this.filtroUsuario = filtroUsuario;
	}

	public String getFiltroTabla() {
		return filtroTabla;
	}

	public void setFiltroTabla(String filtroTabla) {
		this.filtroTabla = filtroTabla;
	}

	/**
	 * @return the paginacion
	 */
	public int getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public void setPaginacion(int paginacion) {
		this.paginacion = paginacion;
	}

	/**
	 * @return the cantidadPaginacion
	 */
	public int getRegistrosxPagina() {
		return registrosPagina;
	}

	/**
	 * @param cantidadPaginacion the cantidadPaginacion to set
	 */
	public void setRegistrosxPagina(int numeroRegistros) {
		this.registrosPagina = numeroRegistros;
	}

	/**
	 * @return the inicioPaginacion
	 */
	public int getInicioPaginacion() {
		return inicioPaginacion;
	}

	/**
	 * @param inicioPaginacion
	 *            the inicioPaginacion to set
	 */
	public void setInicioPaginacion(int inicioPaginacion) {
		this.inicioPaginacion = inicioPaginacion;
	}

	/**
	 * @return the sentidoOrdenamiento
	 */
	public String getSentidoOrdenamiento() {
		return sentidoOrdenamiento;
	}

	/**
	 * @param sentidoOrdenamiento
	 *            the sentidoOrdenamiento to set
	 */
	public void setSentidoOrdenamiento(String sentidoOrdenamiento) {
		this.sentidoOrdenamiento = sentidoOrdenamiento;
	}

	/**
	 * @return the campoOrdenamiento
	 */
	public String getCampoOrdenamiento() {
		return campoOrdenamiento;
	}

	/**
	 * @param campoOrdenamiento
	 *            the campoOrdenamiento to set
	 */
	public void setCampoOrdenamiento(String campoOrdenamiento) {
		this.campoOrdenamiento = campoOrdenamiento;
	}

	/**
	 * @return the valorBuscado
	 */
	public String getValorBuscado() {
		return valorBuscado;
	}

	/**
	 * @param valorBuscado the valorBuscado to set
	 */
	public void setValorBuscado(String valorBuscado) {
		this.valorBuscado = valorBuscado;
	}

 /**
  * @return the filtroEstacion
  */
 public int getFiltroEstacion() {
  return filtroEstacion;
 }

 /**
  * @param filtroEstacion the filtroEstacion to set
  */
 public void setFiltroEstacion(int filtroEstacion) {
  this.filtroEstacion = filtroEstacion;
 }

}
