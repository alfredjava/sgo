package sgo.entidad;

import java.sql.Date;
import java.util.List;

public class Transporte extends EntidadBase {
	private int id_transporte;
	private String numero_guia_remision;
	private String numero_orden_entrega;
	private String numero_factura;
	private String codigo_scop;
	private Date fecha_emision;
	private int id_planta_despacho;
	private int id_planta_recepcion;
	private int id_cliente;
	private int id_conductor;
	private String brevete_conductor;
	private int id_cisterna;
	private String placa_cisterna;
	private String tarjeta_cubicacion_cisterna;
	private int id_tracto;
	private String placa_tracto;
	private int id_transportista;
	private float volumen_total_observado;
	private float volumen_total_corregido;
	private int estado;
	private String sincronizado_el;
	private String cisterna_tracto;
	private String precintos_seguridad;
	private String tipo_registro;
	private float peso_bruto;
	private float peso_tara;
	private float peso_neto;
	private Planta plantaDespacho;
	private Planta plantaRecepcion;
	private Conductor conductor;
	private Cisterna cisterna;
	private Transportista transportista;
	private List<DetalleTransporte> detalles;
	private List<Evento> eventos;

	public int getId() {
		return id_transporte;
	}

	public void setId(int Id) {
		this.id_transporte = Id;
	}

	/**
	 * @return the precintos_seguridad
	 */
	public String getPrecintosSeguridad() {
		return precintos_seguridad;
	}

	/**
	 * @param precintos_seguridad
	 *            the precintos_seguridad to set
	 */
	public void setPrecintosSeguridad(String precintos_seguridad) {
		this.precintos_seguridad = precintos_seguridad;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return tipo_registro;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.tipo_registro = origen;
	}

	public String getCisternaTracto() {
		return this.cisterna_tracto;
		// return this.placa_cisterna + " / " + this.placa_tracto;
	}

	public void setCisternaTracto(String CisternaTracto) {
		this.cisterna_tracto = CisternaTracto;
	}

	public String getNumeroGuiaRemision() {
		return numero_guia_remision;
	}

	public void setNumeroGuiaRemision(String NumeroGuiaRemision) {
		this.numero_guia_remision = NumeroGuiaRemision;
	}

	public String getNumeroOrdenCompra() {
		return numero_orden_entrega;
	}

	public void setNumeroOrdenCompra(String NumeroOrdenCompra) {
		this.numero_orden_entrega = NumeroOrdenCompra;
	}

	public String getNumeroFactura() {
		return numero_factura;
	}

	public void setNumeroFactura(String NumeroFactura) {
		this.numero_factura = NumeroFactura;
	}

	public String getCodigoScop() {
		return codigo_scop;
	}

	public void setCodigoScop(String CodigoScop) {
		this.codigo_scop = CodigoScop;
	}

	public Date getFechaEmisionGuia() {
		return fecha_emision;
	}

	public void setFechaEmisionGuia(Date FechaEmisionGuia) {
		this.fecha_emision = FechaEmisionGuia;
	}

	public int getIdPlantaDespacho() {
		return id_planta_despacho;
	}

	public void setIdPlantaDespacho(int IdPlantaDespacho) {
		this.id_planta_despacho = IdPlantaDespacho;
	}

	public int getIdPlantaRecepcion() {
		return id_planta_recepcion;
	}

	public void setIdPlantaRecepcion(int IdPlantaRecepcion) {
		this.id_planta_recepcion = IdPlantaRecepcion;
	}

	public int getIdCliente() {
		return id_cliente;
	}

	public void setIdCliente(int IdCliente) {
		this.id_cliente = IdCliente;
	}

	public int getIdConductor() {
		return id_conductor;
	}

	public void setIdConductor(int IdConductor) {
		this.id_conductor = IdConductor;
	}
	
	/**
	 * @return the cisterna
	 */
	public Cisterna getCisterna() {
		return cisterna;
	}

	/**
	 * @param cisterna the cisterna to set
	 */
	public void setCisterna(Cisterna cisterna) {
		this.cisterna = cisterna;
	}

	public String getBreveteConductor() {
		return brevete_conductor;
	}

	public void setBreveteConductor(String BreveteConductor) {
		this.brevete_conductor = BreveteConductor;
	}

	public int getIdCisterna() {
		return id_cisterna;
	}

	public void setIdCisterna(int IdCisterna) {
		this.id_cisterna = IdCisterna;
	}

	public String getPlacaCisterna() {
		return placa_cisterna;
	}

	public void setPlacaCisterna(String PlacaCisterna) {
		this.placa_cisterna = PlacaCisterna;
	}

	public String getTarjetaCubicacionCompartimento() {
		return tarjeta_cubicacion_cisterna;
	}

	public void setTarjetaCubicacionCompartimento(
			String TarjetaCubicacionCompartimento) {
		this.tarjeta_cubicacion_cisterna = TarjetaCubicacionCompartimento;
	}

	public int getIdTracto() {
		return id_tracto;
	}

	public void setIdTracto(int IdTracto) {
		this.id_tracto = IdTracto;
	}

	public String getPlacaTracto() {
		return placa_tracto;
	}

	public void setPlacaTracto(String PlacaTracto) {
		this.placa_tracto = PlacaTracto;
	}

	public int getIdTransportista() {
		return id_transportista;
	}

	public void setIdTransportista(int IdTransportista) {
		this.id_transportista = IdTransportista;
	}

	public float getVolumenTotalObservado() {
		return volumen_total_observado;
	}

	public void setVolumenTotalObservado(float VolumenTotalObservado) {
		this.volumen_total_observado = VolumenTotalObservado;
	}

	public float getVolumenTotalCorregido() {
		return volumen_total_corregido;
	}

	public void setVolumenTotalCorregido(float VolumenTotalCorregido) {
		this.volumen_total_corregido = VolumenTotalCorregido;
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

	/**
	 * @return the peso_bruto
	 */
	public float getPesoBruto() {
		return peso_bruto;
	}

	/**
	 * @param peso_bruto
	 *            the peso_bruto to set
	 */
	public void setPesoBruto(float pesoBruto) {
		this.peso_bruto = pesoBruto;
	}

	/**
	 * @return the peso_tara
	 */
	public float getPesoTara() {
		return peso_tara;
	}

	/**
	 * @param peso_tara
	 *            the peso_tara to set
	 */
	public void setPesoTara(float pesoTara) {
		this.peso_tara = pesoTara;
	}

	/**
	 * @return the peso_neto
	 */
	public float getPesoNeto() {
		return peso_neto;
	}

	/**
	 * @param peso_neto
	 *            the peso_neto to set
	 */
	public void setPesoNeto(float pesoNeto) {
		this.peso_neto = pesoNeto;
	}

	/**
	 * @return the plantaDespacho
	 */
	public Planta getPlantaDespacho() {
		return plantaDespacho;
	}

	/**
	 * @param plantaDespacho
	 *            the plantaDespacho to set
	 */
	public void setPlantaDespacho(Planta plantaDespacho) {
		this.plantaDespacho = plantaDespacho;
	}

	/**
	 * @return the plantaRecepcion
	 */
	public Planta getPlantaRecepcion() {
		return plantaRecepcion;
	}

	/**
	 * @param plantaRecepcion
	 *            the plantaRecepcion to set
	 */
	public void setPlantaRecepcion(Planta plantaRecepcion) {
		this.plantaRecepcion = plantaRecepcion;
	}

	/**
	 * @return the conductor
	 */
	public Conductor getConductor() {
		return conductor;
	}

	/**
	 * @param conductor
	 *            the conductor to set
	 */
	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	/**
	 * @return the transportista
	 */
	public Transportista getTransportista() {
		return transportista;
	}

	/**
	 * @param transportista
	 *            the transportista to set
	 */
	public void setTransportista(Transportista transportista) {
		this.transportista = transportista;
	}

	/**
	 * @return the detalles
	 */
	public List<DetalleTransporte> getDetalles() {
		return detalles;
	}

	/**
	 * @param detalles
	 *            the detalles to set
	 */
	public void setDetalles(List<DetalleTransporte> detalles) {
		this.detalles = detalles;
	}

	/**
	 * @return the eventos
	 */
	public List<Evento> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos
	 *            the eventos to set
	 */
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

}