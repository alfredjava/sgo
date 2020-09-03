 	

package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Producto extends EntidadBase {
  private int id_producto;
  private String nombre;
  private String codigo_osinerg;
  private String abreviatura;
  private int estado;
  private String sincronizado_el;
  private String fecha_referencia;
  private String codigo_referencia;
  
	//variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_NOMBRE=80;
	static final int MAXIMA_LONGITUD_CODIGO_OSINERG=5;
	static final int MAXIMA_LONGITUD_ABREVIATURA=20;
  
  public int getId(){
    return id_producto;
  }

  public void setId(int Id ){
    this.id_producto=Id;
  }
  
  public String getNombre(){
    return nombre;
  }

  public void setNombre(String Nombre ){
    this.nombre=Nombre;
  }
  
  public String getCodigoOsinerg(){
    return codigo_osinerg;
  }

  public void setCodigoOsinerg(String CodigoOsinerg ){
    this.codigo_osinerg=CodigoOsinerg;
  }
  
  public String getAbreviatura(){
    return abreviatura;
  }

  public void setAbreviatura(String Abreviatura ){
    this.abreviatura=Abreviatura;
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

		if (this.abreviatura.length() > MAXIMA_LONGITUD_ABREVIATURA  || !Utilidades.esValido(this.abreviatura)){		
			return false;
		}
		
		if (this.codigo_osinerg.length() > MAXIMA_LONGITUD_CODIGO_OSINERG  || !Utilidades.esValido(this.codigo_osinerg)){			
			return false;
		}
		
		if (this.nombre.length() > MAXIMA_LONGITUD_NOMBRE  || !Utilidades.esValido(this.nombre)){			
			return false;
		}
	
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}
  
}