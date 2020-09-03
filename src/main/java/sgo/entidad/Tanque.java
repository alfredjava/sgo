package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Tanque extends EntidadBase {
  private int id_tanque;
  private float volumen_total;
  private String descripcion;
  private float volumen_trabajo;
  private int id_estacion;
  private int estado;
  private String sincronizado_el;
  private String fecha_referencia;
  private String codigo_referencia;
  private Estacion estacion;

  //variables para hacer las validaciones.
  static final int MAXIMA_LONGITUD_DESCRIPCION=20;
	
  public int getId(){
    return id_tanque;
  }

  public void setId(int Id ){
    this.id_tanque=Id;
  }
  
  public float getVolumenTotal(){
    return volumen_total;
  }

  public void setVolumenTotal(float VolumenTotal ){
    this.volumen_total=VolumenTotal;
  }
  
  public String getDescripcion(){
    return descripcion;
  }

  public void setDescripcion(String Descripcion ){
    this.descripcion=Descripcion;
  }
  
  public float getVolumenTrabajo(){
    return volumen_trabajo;
  }

  public void setVolumenTrabajo(float VolumenTrabajo ){
    this.volumen_trabajo=VolumenTrabajo;
  }
  
  public int getIdEstacion(){
    return id_estacion;
  }

  public void setIdEstacion(int IdEstacion ){
    this.id_estacion=IdEstacion;
  }
  
  public int getEstado(){
    return estado;
  }

  public void setEstado(int Estado ){
    this.estado=Estado;
  }
  
  public String getSincronizadoEl(){
    return sincronizado_el;
  }

  public void setSincronizadoEl(String SincronizadoEl ){
    this.sincronizado_el=SincronizadoEl;
  }
  
  public String getFechaReferencia(){
    return fecha_referencia;
  }

  public void setFechaReferencia(String FechaReferencia ){
    this.fecha_referencia=FechaReferencia;
  }
  
  public String getCodigoReferencia(){
    return codigo_referencia;
  }

  public void setCodigoReferencia(String CodigoReferencia ){
    this.codigo_referencia=CodigoReferencia;
  }

  public Estacion getEstacion() {
	return estacion;
  }

  public void setEstacion(Estacion estacion) {
	this.estacion = estacion;
  }
  
  public boolean validar(){
		boolean resultado = true;

		if (this.descripcion.length() > MAXIMA_LONGITUD_DESCRIPCION  || !Utilidades.esValido(this.descripcion)){		
			return false;
		}
		
		if(!Utilidades.esValido(this.volumen_trabajo)){
			return false;
		}
		
		if(!Utilidades.esValido(this.volumen_total)){
			return false;
		}

		if(!Utilidades.esValido(this.estacion)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}

}