package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Cisterna extends EntidadBase {
	private int id_cisterna;
	private String placa;
	private int id_tracto;
	private int id_transportista;
	private int estado;
	private String sincronizado_el;
	private String fecha_referencia;
	private String codigo_referencia;
	private String placa_tracto;
	private int cantidad_compartimentos;
	private Tracto tracto;
	private Transportista transportista;
	private String placaCisternaTracto;
	
	public int getCantidadCompartimentos() {
		return cantidad_compartimentos;
	}

	public void setCantidadCompartimentos(int cantidadCompartimentos) {
		this.cantidad_compartimentos = cantidadCompartimentos;
	}
	
	//variables para hacer las validaciones
	static final int MAXIMA_LONGITUD_PLACA=15;

	public int getId() {
		return id_cisterna;
	}

	public void setId(int Id) {
		this.id_cisterna = Id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String Placa) {
		this.placa = Placa;
	}

	public int getIdTracto() {
		return id_tracto;
	}

	public void setIdTracto(int idTracto) {
		this.id_tracto = idTracto;
	}

	/**
	 * @return the placa_tracto
	 */
	public String getPlacaTracto() {
		return placa_tracto;
	}

	/**
	 * @param placa_tracto the placa_tracto to set
	 */
	public void setPlacaTracto(String placaTracto) {
		this.placa_tracto = placaTracto;
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

	public Tracto getTracto() {
		return tracto;
	}

	public void setTracto(Tracto tracto) {
		this.tracto = tracto;
	}

	public Transportista getTransportista() {
		return transportista;
	}

	public void setTransportista(Transportista transportista) {
		this.transportista = transportista;
	}
	
	public String getPlacaCisternaTracto() {
		return this.placaCisternaTracto;
	}

	/**
	 * @param placaCisternaTracto the placaCisternaTracto to set
	 */
	public void setPlacaCisternaTracto(String placaCisternaTracto) {
		this.placaCisternaTracto = placaCisternaTracto;
	}

	public boolean validar(){
		boolean resultado = true;

		if (this.placa.length() > MAXIMA_LONGITUD_PLACA || !Utilidades.esValido(this.placa)){			
			return false;
		}
		
		if(!Utilidades.esValido(this.id_tracto)){
			return false;
		}
		
		if(!Utilidades.esValido(this.id_transportista)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}
	
	

}