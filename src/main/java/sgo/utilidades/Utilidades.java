package sgo.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

import sgo.entidad.Respuesta;

/**
 * Clase base para hacer las validaciones.
 *
 * @author IBM DEL PERÚ / knavarro
 * @since  21/XIII/2015
 */
public class Utilidades {
	
	/** Formato de fecha. */
	public static JdbcTemplate jdbcTemplate;
	public static NamedParameterJdbcTemplate namedJdbcTemplate;
    public static final  String FORMATO_FECHA = "DD/MM/YYYY";
	public final static String DATOS_AUDITORIA = " t1.creado_el, t1.creado_por, t1.actualizado_por, t1.actualizado_el, t1.usuario_creacion, t1.usuario_actualizacion, t1.ip_creacion, t1.ip_actualizacion ";

    /** Constante para cadenas vacias. */
    public static final String STRVACIO = "";
	
	/** Valida si un entero es correcto
     * @param entero El entero a validar
     * @return es correcto o no
     */
	public static boolean esValido(Integer entero) {
        return entero!=null && entero.intValue()>=0;
    }
	
	 /** Valida si un Long es correcto
     * @param largo El Long a validar
     * @return es correcto o no
     */
    public static boolean esValido(Long largo)
    {
        return largo!=null && largo.longValue()>=0;
    }

    /** Valida si una cadena sea correcta
     * @param cadena La cadena a validar
     * @return es correcto o no
     */
    public static boolean esValido(String cadena)
    {
        return cadena!=null && !cadena.equals(STRVACIO);
    }

    /** Valida si un objeto esta instanciado.
     * @param objeto Objeto a validar
     * @return es correcto o no
     */
    public static boolean esValido(Object objeto)
    {
        return objeto != null;
    }
    
    /**
     * Comprueba si tiene contenido un String.
     * @param s String
     * @return boolean
     */
    public static boolean tieneContenido(String s) {
        return s != null && !s.trim().equals(STRVACIO);
    }

    /**
     * Comprueba si tiene contenido una lista.
     * @param l Lista
     * @return boolean
     */
    public static boolean tieneContenido(List l) {
        return l != null && !l.isEmpty();
    }
    
    /**
     * Comprueba si tiene contenido un ArrayList.
     * @param l Lista
     * @return boolean
     */
    public static boolean tieneContenido(ArrayList lista) {
        return lista != null && lista.size() > 0;
    }
    
    
    
    public static java.sql.Date convierteStringADate(String str){
    	java.sql.Date sql = null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    		java.util.Date resultado =  format.parse(str);
    		sql = new java.sql.Date(resultado.getTime());
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return sql;
    }
    
    public static String modificarFormatoFecha(String fecha){
    	String fechaModificada = null;
		String[] parametros = fecha.split("-");
		fechaModificada =  new String(parametros[2]+ "/" + parametros[1] + "/" + parametros[0]);
		System.out.println(fechaModificada);
	    return fechaModificada;
	}
    
    /**
     * Metodo para recuperar la fecha actual.
     * @return respuesta	Fecha actual del sistema.
     */
	public static Respuesta recuperarFechaActual() {
		Date fechaActual= null;
		StringBuilder consultaSQL= new StringBuilder();
		Respuesta respuesta =new Respuesta();
		try {
			consultaSQL.append("SELECT now() WHERE 1=?");
			fechaActual = jdbcTemplate.queryForObject(consultaSQL.toString(),new Object[] {1},Date.class);
			if (fechaActual==null){
				respuesta.estado=true;
				respuesta.valor=null;
			} else {
				respuesta.estado = true;
				respuesta.valor = fechaActual.toString();
			}
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error=  Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
		} catch (Exception excepcionGenerica) {
			excepcionGenerica.printStackTrace();
			respuesta.error= Constante.EXCEPCION_GENERICA;
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return this.jdbcTemplate.getDataSource();
	}
    
}