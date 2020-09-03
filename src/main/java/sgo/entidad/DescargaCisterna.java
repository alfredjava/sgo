package sgo.entidad;

import java.sql.Date;

public class DescargaCisterna extends EntidadBase {
  private int id_dcisterna;
  private int id_ctanque;
  private int id_transporte;
  private Date fecha_arribo;
  private Date fecha_fiscalizacion;
  private int metodo_descarga;
  private String numero_comprobante;
  private float merma_permisible;
  private float merma_porcentaje;
  private float lectura_inicial;
  private float lectura_final;
  private float pesaje_inicial;
  private float pesaje_final;
  private float factor_conversion;
  private float peso_neto;
  private float volumen_total_descargado_observado;
  private float volumen_total_descargado_corregido;
  private float variacion_observado;
  private float variacion_corregido;
  private float volumen_excedente_observado;
  private float volumen_excedente_corregido;
  

  public int getId(){
    return id_dcisterna;
  }

  public void setId(int Id ){
    this.id_dcisterna=Id;
  }
  
  public int getIdCargaTanque(){
    return id_ctanque;
  }

  public void setIdCargaTanque(int IdCargaTanque ){
    this.id_ctanque=IdCargaTanque;
  }
  
  public int getIdTransporte(){
    return id_transporte;
  }

  public void setIdTransporte(int IdTransporte ){
    this.id_transporte=IdTransporte;
  }
  
  public Date getFechaArribo(){
    return fecha_arribo;
  }

  public void setFechaArribo(Date FechaArribo ){
    this.fecha_arribo=FechaArribo;
  }
  
  public Date getFechaFiscalizacion(){
    return fecha_fiscalizacion;
  }

  public void setFechaFiscalizacion(Date FechaFiscalizacion ){
    this.fecha_fiscalizacion=FechaFiscalizacion;
  }
  
  public int getMetodoDescarga(){
    return metodo_descarga;
  }

  public void setMetodoDescarga(int MetodoDescarga ){
    this.metodo_descarga=MetodoDescarga;
  }
  
  public String getNumeroComprobante(){
    return numero_comprobante;
  }

  public void setNumeroComprobante(String NumeroComprobante ){
    this.numero_comprobante=NumeroComprobante;
  }
  
  public float getMermaPermisible(){
    return merma_permisible;
  }

  public void setMermaPermisible(float MermaPermisible ){
    this.merma_permisible=MermaPermisible;
  }
  
  public float getMermaPorcentaje(){
    return merma_porcentaje;
  }

  public void setMermaPorcentaje(float MermaPorcentaje ){
    this.merma_porcentaje=MermaPorcentaje;
  }
  
  public float getLecturaInicial(){
    return lectura_inicial;
  }

  public void setLecturaInicial(float LecturaInicial ){
    this.lectura_inicial=LecturaInicial;
  }
  
  public float getLecturaFinal(){
    return lectura_final;
  }

  public void setLecturaFinal(float LecturaFinal ){
    this.lectura_final=LecturaFinal;
  }
  
  public float getPesajeInicial(){
    return pesaje_inicial;
  }

  public void setPesajeInicial(float PesajeInicial ){
    this.pesaje_inicial=PesajeInicial;
  }
  
  public float getPesajeFinal(){
    return pesaje_final;
  }

  public void setPesajeFinal(float PesajeFinal ){
    this.pesaje_final=PesajeFinal;
  }
  
  public float getFactorConversion(){
    return factor_conversion;
  }

  public void setFactorConversion(float FactorConversion ){
    this.factor_conversion=FactorConversion;
  }
  
  public float getPesoNeto(){
    return peso_neto;
  }

  public void setPesoNeto(float PesoNeto ){
    this.peso_neto=PesoNeto;
  }
  
  public float getVolumenTotalDescargadoObservado(){
    return volumen_total_descargado_observado;
  }

  public void setVolumenTotalDescargadoObservado(float VolumenTotalDescargadoObservado ){
    this.volumen_total_descargado_observado=VolumenTotalDescargadoObservado;
  }
  
  public float getVolumenTotalDescargadoCorregido(){
    return volumen_total_descargado_corregido;
  }

  public void setVolumenTotalDescargadoCorregido(float VolumenTotalDescargadoCorregido ){
    this.volumen_total_descargado_corregido=VolumenTotalDescargadoCorregido;
  }
  
  public float getVariacionObservado(){
    return variacion_observado;
  }

  public void setVariacionObservado(float VariacionObservado ){
    this.variacion_observado=VariacionObservado;
  }
  
  public float getVariacionCorregido(){
    return variacion_corregido;
  }

  public void setVariacionCorregido(float VariacionCorregido ){
    this.variacion_corregido=VariacionCorregido;
  }
  
  public float getVolumenExcedenteObservado(){
    return volumen_excedente_observado;
  }

  public void setVolumenExcedenteObservado(float VolumenExcedenteObservado ){
    this.volumen_excedente_observado=VolumenExcedenteObservado;
  }
  
  public float getVolumenExcedenteCorregido(){
    return volumen_excedente_corregido;
  }

  public void setVolumenExcedenteCorregido(float VolumenExcedenteCorregido ){
    this.volumen_excedente_corregido=VolumenExcedenteCorregido;
  }
  
  
}