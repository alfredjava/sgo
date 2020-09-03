package sgo.entidad;

public class Cliente extends EntidadBase {
	private int id_cliente;
	private String nombre_corto;
	private String razon_social;
	private String ruc;
	private int estado;

	static final int MAXIMA_LONGITUD_NOMBRE_CORTO=20;
	static final int MAXIMA_LONGITUD_RAZON_SOCIAL=150;
	static final int MAXIMA_LONGITUD_RUC=11;

	public int getId(){
	  return id_cliente;
	}

	public void setId(int Id ){
	  this.id_cliente=Id;
	}

	public String getNombreCorto(){
	  return nombre_corto;
	}

	public void setNombreCorto(String Nombre ){
	  this.nombre_corto=Nombre;
	}

	public String getRazonSocial(){
	  return razon_social;
	}

	public void setRazonSocial(String RazonSocial ){
	  this.razon_social=RazonSocial;
	}

	public String getRuc(){
	  return ruc;
	}

	public void setRuc(String Ruc ){
	  this.ruc=Ruc;
	}

	public int getEstado(){
	  return estado;
	}

	public void setEstado(int Estado ){
	  this.estado=Estado;
	}
	
	public boolean validar(){
		boolean resultado = true;
		
		if (this.razon_social.length()> MAXIMA_LONGITUD_RAZON_SOCIAL){			
			return false;
		}
		
		if (this.nombre_corto.length()> MAXIMA_LONGITUD_NOMBRE_CORTO){
			return false;
		}
		
		if ((this.ruc.length() != MAXIMA_LONGITUD_RUC)){
			return false;
		}
		
		return resultado;
	}
	
}