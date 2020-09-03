package sgo.entidad;

public class DetalleTransporte {
	private String id_dtransporte;
	private int id_transporte;
	private int id_producto;
	private float capacidad_volumetrica_compartimento;
	private String unidad_medida_volumen;
	private int numero_compartimento;
	private float volumen_temperatura_observada;
	private float temperatura_observada;
	private float api_temperatura_base;
	private float factor_correcion;
	private float volumen_temperatura_base;
	private int altura_compartimento;

	/**
	 * @return the altura_compartimento
	 */
	public int getAlturaCompartimento() {
		return altura_compartimento;
	}

	/**
	 * @param altura_compartimento the altura_compartimento to set
	 */
	public void setAlturaCompartimento(int alturaCompartimento) {
		this.altura_compartimento = alturaCompartimento;
	}

	public String getId() {
		return id_dtransporte;
	}

	public void setId(String Id) {
		this.id_dtransporte = Id;
	}

	public int getIdTransporte() {
		return id_transporte;
	}

	public void setIdTransporte(int IdTransporte) {
		this.id_transporte = IdTransporte;
	}

	public int getIdProducto() {
		return id_producto;
	}

	public void setIdProducto(int IdProducto) {
		this.id_producto = IdProducto;
	}

	public float getCapacidadVolumetricaCompartimento() {
		return capacidad_volumetrica_compartimento;
	}

	public void setCapacidadVolumetricaCompartimento(
			float CapacidadVolumetricaCompartimento) {
		this.capacidad_volumetrica_compartimento = CapacidadVolumetricaCompartimento;
	}

	public String getUnidadMedida() {
		return unidad_medida_volumen;
	}

	public void setUnidadMedida(String UnidadMedida) {
		this.unidad_medida_volumen = UnidadMedida;
	}

	public int getNumeroCompartimento() {
		return numero_compartimento;
	}

	public void setNumeroCompartimento(int NumeroCompartimento) {
		this.numero_compartimento = NumeroCompartimento;
	}

	public float getVolumenTemperaturaObservada() {
		return volumen_temperatura_observada;
	}

	public void setVolumenTemperaturaObservada(float VolumenTemperaturaObservada) {
		this.volumen_temperatura_observada = VolumenTemperaturaObservada;
	}

	public float getTemperaturaObservada() {
		return temperatura_observada;
	}

	public void setTemperaturaObservada(float TemperaturaObservada) {
		this.temperatura_observada = TemperaturaObservada;
	}

	public float getApiTemperaturaBase() {
		return api_temperatura_base;
	}

	public void setApiTemperaturaBase(float ApiTemperaturaBase) {
		this.api_temperatura_base = ApiTemperaturaBase;
	}

	public float getFactorCorrecion() {
		return factor_correcion;
	}

	public void setFactorCorrecion(float FactorCorrecion) {
		this.factor_correcion = FactorCorrecion;
	}

	public float getVolumenTemperaturaBase() {
		return volumen_temperatura_base;
	}

	public void setVolumenTemperaturaBase(float VolumenTemperaturaBase) {
		this.volumen_temperatura_base = VolumenTemperaturaBase;
	}

}