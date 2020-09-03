package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Planta extends EntidadBase {
  private int id_planta;
  private String descripcion;
  //private String abreviatura;
  private int estado;
  private String sincronizado_el;
  private String fecha_referencia;
  private String codigo_referencia;
  
	//variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_DESCRIPCION=150;

  public int getId(){
    return id_planta;
  }

  public void setId(int Id ){
    this.id_planta=Id;
  }
  
  public String getDescripcion(){
    return descripcion;
  }

  public void setDescripcion(String Descripcion ){
    this.descripcion=Descripcion;
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
  
  public boolean validar(){
		boolean resultado = true;

		if (this.descripcion.length() > MAXIMA_LONGITUD_DESCRIPCION  || !Utilidades.esValido(this.descripcion)){		
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}
  
  
}