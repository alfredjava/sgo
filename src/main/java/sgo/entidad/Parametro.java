package sgo.entidad;

import sgo.utilidades.Utilidades;

public class Parametro extends EntidadBase {
	private int id_parametro;
	private String valor;
	private String alias;
	
	//variables para hacer las validaciones.
	static final int MAXIMA_LONGITUD_VALOR=80;
	static final int MAXIMA_LONGITUD_ALIAS=20;

	public int getId() {
		return id_parametro;
	}

	public void setId(int Id) {
		this.id_parametro = Id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String Valor) {
		this.valor = Valor;
	}
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String Alias) {
		this.alias = Alias;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if (this.alias.length() > MAXIMA_LONGITUD_ALIAS  || !Utilidades.esValido(this.alias)){		
			return false;
		}
		
		if (this.valor.length() > MAXIMA_LONGITUD_VALOR  || !Utilidades.esValido(this.valor)){			
			return false;
		}
		
		return resultado;
	}
}