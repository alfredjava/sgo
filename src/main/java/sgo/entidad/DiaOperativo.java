package sgo.entidad;
import java.sql.Date;
import java.util.List;

import sgo.utilidades.Utilidades;

public class DiaOperativo extends EntidadBase {
	private int id_doperativo;
	private Date fecha_operativa;
	private int id_operacion;
	private int estado;
	private Operacion operacion;
	private List<Planificacion> planificaciones;
	private String detalleProductoSolicitado;
	private int totalCisternas;
	private int totalVolumenSolicitado;
	private int totalVolumenPropuesto;

	public static final int ESTADO_PLANIFICADO=1;
	public static final int ESTADO_ASIGNADO=2;
	public static final int ESTADO_DESCARGANDO=3;
	public static final int ESTADO_CERRADO=4;
	public static final int ESTADO_LIQUIDADO=5;
	
	public String getDetalleProductoSolicitado() {
		return detalleProductoSolicitado;
	}

	public void setDetalleProductoSolicitado(String detalleProductoSolicitado) {
		this.detalleProductoSolicitado = detalleProductoSolicitado;
	}
	
	/**
	 * @return the totalVolumenSolicitado
	 */
	public int getTotalVolumenSolicitado() {
		return totalVolumenSolicitado;
	}

	/**
	 * @param totalVolumenSolicitado the totalVolumenSolicitado to set
	 */
	public void setTotalVolumenSolicitado(int totalVolumenSolicitado) {
		this.totalVolumenSolicitado = totalVolumenSolicitado;
	}

	/**
	 * @return the totalVolumenPropuesto
	 */
	public int getTotalVolumenPropuesto() {
		return totalVolumenPropuesto;
	}

	/**
	 * @param totalVolumenPropuesto the totalVolumenPropuesto to set
	 */
	public void setTotalVolumenPropuesto(int totalVolumenPropuesto) {
		this.totalVolumenPropuesto = totalVolumenPropuesto;
	}

	public int getTotalCisternas() {
		return totalCisternas;
	}

	public void setTotalCisternas(int totalCisternas) {
		this.totalCisternas = totalCisternas;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public void setPlanificaciones(List<Planificacion> planificacion) {
		this.planificaciones = planificacion;
	}

	public List<Planificacion> getPlanificaciones() {
		return planificaciones;
	}

	public int getId() {
		return id_doperativo;
	}

	public void setId(int Id) {
		this.id_doperativo = Id;
	}

	public Date getFechaOperativa() {
		return fecha_operativa;
	}

	public void setFechaOperativa(Date FechaOperativa) {
		this.fecha_operativa = FechaOperativa;
	}

	public int getIdOperacion() {
		return id_operacion;
	}

	public void setIdOperacion(int id_operacion) {
		this.id_operacion = id_operacion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int Estado) {
		this.estado = Estado;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if(!Utilidades.esValido(this.id_operacion)){
			return false;
		}
		
		if(!Utilidades.esValido(this.fecha_operativa)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}

}