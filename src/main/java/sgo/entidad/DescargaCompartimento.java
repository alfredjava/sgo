package sgo.entidad;

public class DescargaCompartimento extends EntidadBase {
  private int id_dcompartimento;
  private int id_dcisterna;
  private int id_producto;
  private float capacidad_volumetrica_compartimento;
  private int altura_compartimento;
  private int altura_producto;
  private String unidad_medida_volumen;
  private int numero_compartimento;
  private float temperatura_centro_cisterna;
  private float temperatura_probeta;
  private float api_temperatura_observada;
  private float api_temperatura_base;
  private float factor_correccion;
  private float volumen_recibido_observado;
  private float volumen_recibido_corregido;
  

  public int getId(){
    return id_dcompartimento;
  }

  public void setId(int Id ){
    this.id_dcompartimento=Id;
  }
  
  public int getIdCisterna(){
    return id_dcisterna;
  }

  public void setIdCisterna(int IdCisterna ){
    this.id_dcisterna=IdCisterna;
  }
  
  public int getIdProducto(){
    return id_producto;
  }

  public void setIdProducto(int IdProducto ){
    this.id_producto=IdProducto;
  }
  
  public float getCapacidadVolumetricaCompartimento(){
    return capacidad_volumetrica_compartimento;
  }

  public void setCapacidadVolumetricaCompartimento(float CapacidadVolumetricaCompartimento ){
    this.capacidad_volumetrica_compartimento=CapacidadVolumetricaCompartimento;
  }
  
  public int getAlturaCompartimento(){
    return altura_compartimento;
  }

  public void setAlturaCompartimento(int AlturaCompartimento ){
    this.altura_compartimento=AlturaCompartimento;
  }
  
  public int getAlturaProducto(){
    return altura_producto;
  }

  public void setAlturaProducto(int AlturaProducto ){
    this.altura_producto=AlturaProducto;
  }
  
  public String getUnidadMedida(){
    return unidad_medida_volumen;
  }

  public void setUnidadMedida(String UnidadMedida ){
    this.unidad_medida_volumen=UnidadMedida;
  }
  
  public int getNumeroCompartimento(){
    return numero_compartimento;
  }

  public void setNumeroCompartimento(int NumeroCompartimento ){
    this.numero_compartimento=NumeroCompartimento;
  }
  
  public float getTemperaturaObservada(){
    return temperatura_centro_cisterna;
  }

  public void setTemperaturaObservada(float TemperaturaObservada ){
    this.temperatura_centro_cisterna=TemperaturaObservada;
  }
  
  public float getTemperaturaProbeta(){
    return temperatura_probeta;
  }

  public void setTemperaturaProbeta(float TemperaturaProbeta ){
    this.temperatura_probeta=TemperaturaProbeta;
  }
  
  public float getApiTemperaturaObservada(){
    return api_temperatura_observada;
  }

  public void setApiTemperaturaObservada(float ApiTemperaturaObservada ){
    this.api_temperatura_observada=ApiTemperaturaObservada;
  }
  
  public float getApiTemperaturaBase(){
    return api_temperatura_base;
  }

  public void setApiTemperaturaBase(float ApiTemperaturaBase ){
    this.api_temperatura_base=ApiTemperaturaBase;
  }
  
  public float getFactorCorrecion(){
    return factor_correccion;
  }

  public void setFactorCorrecion(float FactorCorrecion ){
    this.factor_correccion=FactorCorrecion;
  }
  
  public float getVolumenTemperaturaObservada(){
    return volumen_recibido_observado;
  }

  public void setVolumenTemperaturaObservada(float VolumenTemperaturaObservada ){
    this.volumen_recibido_observado=VolumenTemperaturaObservada;
  }
  
  public float getVolumenTemperaturaBase(){
    return volumen_recibido_corregido;
  }

  public void setVolumenTemperaturaBase(float VolumenTemperaturaBase ){
    this.volumen_recibido_corregido=VolumenTemperaturaBase;
  }
  
  
}