package sgo.datos;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sgo.entidad.Planificacion;
import sgo.entidad.Contenido;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.DiaOperativo;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

/**
 * Funcionalidades para el modulo de Planificación.
 *
 * @author I.B.M. DEL PERÚ - knavarro
 * @since  13/XIII/2015
 */
@Repository
public class PlanificacionDao {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	private JdbcTemplate jscoreJdbcTemplate;
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION + "planificacion";
	public static final String NOMBRE_VISTA = Constante.ESQUEMA_APLICACION + "v_planificacion";
	public static final String NOMBRE_CAMPO_CLAVE = "id_planificacion";
	public final static String NOMBRE_CAMPO_FILTRO = "id_planificacion";
	public final static String NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO = "id_planificacion";
	
	public final static String O = " OR ";
	public final static String Y = " AND ";
	public final static String ENTRE = " BETWEEN ";

	/*
	 * DataSource
     */
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return this.jdbcTemplate.getDataSource();
	}
	
	/**
     * Método que retorna el campo de la tabla para ordenar el listado.
     * @param propiedad         	Contiene los filtros para ordenar las columnas del listado.
     * @return campoOrdenamiento	Variable que retorna el campo de la tabla.
     */
	public String mapearCampoOrdenamiento(String propiedad){
		String campoOrdenamiento="";
		try {
			if (propiedad.equals("id")){
				campoOrdenamiento="id_planificacion";
			}
			if (propiedad.equals("idProducto")){
				campoOrdenamiento="id_producto";
			}
			if (propiedad.equals("idDoperativo")){
				campoOrdenamiento="id_doperativo";
			}
			if (propiedad.equals("volumenPropuesto")){
				campoOrdenamiento="volumen_propuesto";
			}
			if (propiedad.equals("volumenSolicitado")){
				campoOrdenamiento="volumen_solicitado";
			}
			if (propiedad.equals("cantidadCisternas")){
				campoOrdenamiento="cantidad_cisternas";
			}
			//Campos de auditoria
		}catch(Exception excepcion){
			
		}
		return campoOrdenamiento;
	}

	/**
     * Metodo para recuperar los registros según los filtros.
     * @param argumentosListar      Contiene los filtros para el where de la consulta.
     * @return respuesta			Resultado de la consulta.
     */
	public RespuestaCompuesta recuperarRegistros(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		String sqlOrderBy="";
		List<String> filtrosWhere= new ArrayList<String>();
		String sqlWhere="";
		int totalRegistros = 0, 
		totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<Planificacion> contenido = new Contenido<Planificacion>();
		List<Planificacion> listaRegistros = new ArrayList<Planificacion>();
		
		List<Object> parametros = new ArrayList<Object>();
		try {
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}

			//sqlOrderBy= " ORDER BY " + this.mapearCampoOrdenamiento(argumentosListar.getCampoOrdenamiento()) + " "  + argumentosListar.getSentidoOrdenamiento();
			
			StringBuilder consultaSQL = new StringBuilder();

			if (!argumentosListar.getValorBuscado().isEmpty()){
				filtrosWhere.add(" t1.id_planificacion = " + argumentosListar.getValorBuscado());
			}

			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Y);

				consultaSQL.append("SELECT count(t1." + NOMBRE_CAMPO_CLAVE+ ") as total FROM " + NOMBRE_VISTA + " t1 " + sqlWhere);
				totalEncontrados = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			}

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_doperativo,");        
			consultaSQL.append("t1.id_planificacion,");     
			consultaSQL.append("t1.id_producto,");          
			consultaSQL.append("t1.nombre,");               
			consultaSQL.append("t1.volumen_propuesto,");     
			consultaSQL.append("t1.volumen_solicitado,");    
			consultaSQL.append("t1.cantidad_cisternas,");   
			//Campos de auditoria                           
			consultaSQL.append("t1.actualizado_el,");       
			consultaSQL.append("t1.actualizado_por,");      
			consultaSQL.append("t1.usuario_actualizacion,");
			consultaSQL.append("t1.ip_actualizacion");      
			consultaSQL.append(" FROM ");
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(sqlWhere);
			consultaSQL.append(sqlOrderBy);
			consultaSQL.append(sqlLimit);

			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new PlanificacionMapper());

			totalEncontrados = listaRegistros.size();
			totalRegistros   = listaRegistros.size();
			contenido.carga  = listaRegistros;
			respuesta.estado = true;
			respuesta.contenido = contenido;
			respuesta.contenido.totalRegistros = totalRegistros;
			respuesta.contenido.totalEncontrados = totalEncontrados;

		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error=  Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido=null;
		} catch (Exception excepcionGenerica) {
			excepcionGenerica.printStackTrace();
			respuesta.error= Constante.EXCEPCION_GENERICA;
			respuesta.contenido=null;
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	/**
     * Metodo para recuperar una entidad Planificacion.
     * @param ID      		Identificador de la entidad.
     * @return respuesta	Resultado de la búsqueda de la entidad.
     */
	public RespuestaCompuesta recuperarRegistro(int ID){
		StringBuilder consultaSQL= new StringBuilder();		
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<Planificacion> contenido = new Contenido<Planificacion>();
		List<Planificacion> listaRegistros = new ArrayList<Planificacion>();
		if(!Utilidades.esValido(ID)){
			respuesta.estado=false;
			respuesta.contenido=null;
			return respuesta;
		}
		try {
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_doperativo,");        
			consultaSQL.append("t1.id_planificacion,");     
			consultaSQL.append("t1.id_producto,");          
			consultaSQL.append("t1.nombre,");    
			consultaSQL.append("t1.volumen_propuesto,");     
			consultaSQL.append("t1.volumen_solicitado,"); 
			consultaSQL.append("t1.cantidad_cisternas,");   
			//Campos de auditoria                           
			consultaSQL.append("t1.actualizado_el,");       
			consultaSQL.append("t1.actualizado_por,");      
			consultaSQL.append("t1.usuario_actualizacion,");
			consultaSQL.append("t1.ip_actualizacion");      
			consultaSQL.append(" FROM ");				
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(" WHERE t1.");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=?");

			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),new Object[] {ID}, new PlanificacionMapper());

			contenido.totalRegistros=listaRegistros.size();
			contenido.totalEncontrados=listaRegistros.size();
			contenido.carga= listaRegistros;
			respuesta.mensaje="OK";
			respuesta.estado=true;
			respuesta.contenido = contenido;			
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
			respuesta.contenido=null;
		}
		return respuesta;
	}
	
	/**
     * Metodo para guardar la entidad Planificacion.
     * @param planificacion      Entidad que se va a insertar.
     * @return respuesta		 resultado de la inserción.
     */
	public RespuestaCompuesta guardarRegistro(Planificacion planificacion){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL = new StringBuilder();
		KeyHolder claveGenerada = null;
		int cantidadFilasAfectadas = 0;
		try {
			consultaSQL.append("INSERT INTO ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" (id_doperativo,id_producto,volumen_propuesto,volumen_solicitado,cantidad_cisternas,actualizado_por,actualizado_el,ip_actualizacion) ");
			consultaSQL.append(" VALUES (:IdDoperativo,:IdProducto,:VolumenPropuesto,:VolumenSolicitado,:CantidadCisternas,:ActualizadoPor,:ActualizadoEl,:IpActualizacion) ");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("IdDoperativo", planificacion.getIdDoperativo());
			listaParametros.addValue("IdProducto", planificacion.getIdProducto());
			listaParametros.addValue("VolumenPropuesto", planificacion.getVolumenPropuesto());
			listaParametros.addValue("VolumenSolicitado", planificacion.getVolumenSolicitado());
			listaParametros.addValue("CantidadCisternas", planificacion.getCantidadCisternas());
			//datos auditoria
			listaParametros.addValue("ActualizadoPor", planificacion.getActualizadoPor());
			listaParametros.addValue("ActualizadoEl", planificacion.getActualizadoEl());
			listaParametros.addValue("IpActualizacion", planificacion.getIpActualizacion());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			claveGenerada = new GeneratedKeyHolder();
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters,claveGenerada,new String[] {NOMBRE_CAMPO_CLAVE});		
			if (cantidadFilasAfectadas > 1){
				respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			respuesta.valor= claveGenerada.getKey().toString();
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			excepcionAccesoDatos.printStackTrace();
			respuesta.error=Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	/**
     * Metodo para actualizar la entidad Planificacion.
     * @param planificacion      Entidad que se va a modificar.
     * @return respuesta		 resultado de la modificación.
     */
	public RespuestaCompuesta actualizarRegistro(Planificacion planificacion){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("id_producto=:IdProducto,");
			consultaSQL.append("volumen_propuesto=:VolumenPropuesto,");
			consultaSQL.append("cantidad_cisternas=:CantidadCisternas,");
			consultaSQL.append("volumen_solicitado=:VolumenSolicitado,");
			//Datos auditoria
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:idPlanificacion ");
			consultaSQL.append(Y);
			consultaSQL.append(" id_doperativo ");
			consultaSQL.append("=:idDoperacion ");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("IdProducto", planificacion.getIdProducto());
			listaParametros.addValue("VolumenPropuesto", planificacion.getVolumenPropuesto());
			listaParametros.addValue("CantidadCisternas", planificacion.getCantidadCisternas());
			listaParametros.addValue("VolumenSolicitado", planificacion.getVolumenSolicitado());
			//Valores Auditoria
			listaParametros.addValue("ActualizadoEl", planificacion.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", planificacion.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", planificacion.getIpActualizacion());
			listaParametros.addValue("idPlanificacion", planificacion.getId());
			listaParametros.addValue("idDoperacion", planificacion.getIdDoperativo());

			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta eliminarRegistro(int idRegistro){		
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		int cantidadFilasAfectadas=0;	
		String consultaSQL="";
		Object[] parametros = {idRegistro};
		
		if(!Utilidades.esValido(idRegistro)){
			respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
			respuesta.estado=false;
			return respuesta;
		}
		try {
			consultaSQL="DELETE FROM " + NOMBRE_TABLA + " WHERE " + NOMBRE_CAMPO_CLAVE + "=?";
        	cantidadFilasAfectadas = jdbcTemplate.update(consultaSQL, parametros);
			if (cantidadFilasAfectadas > 1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){	
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	/**
     * Metodo para recuperar las planificaciones del listado de Dias Operativos.
     * @param diasOperativos      Listado de diasOperativos a los que hay que buscar sus planificaciones.
     * @return respuesta		  Listado de diasOperativos con sus respectivas planificaciones.
     */
	public RespuestaCompuesta recuperarPlanificacionesPorDiaOperativo(List<DiaOperativo> diasOperativos) {
		StringBuilder consultaSQL = new StringBuilder();
		List<Planificacion> listaRegistros = new ArrayList<Planificacion>();
		Contenido<DiaOperativo> contenido = new Contenido<DiaOperativo>();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		String descProductoVolRequerido = "";

		if(!Utilidades.tieneContenido(diasOperativos)){
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
			respuesta.contenido=null;
			return respuesta;
		}
		
		try{
			for(int k=0; k< diasOperativos.size(); k++){
				int idOperativo = diasOperativos.get(k).getId();
				descProductoVolRequerido = "";
				consultaSQL.setLength(0);
				consultaSQL.append("SELECT ");
				consultaSQL.append("t1.id_doperativo,");        
				consultaSQL.append("t1.id_planificacion,");     
				consultaSQL.append("t1.id_producto,");          
				consultaSQL.append("t1.nombre,");               
				consultaSQL.append("t1.volumen_propuesto,");     
				consultaSQL.append("t1.volumen_solicitado,");    
				consultaSQL.append("t1.cantidad_cisternas,");   
				//Campos de auditoria                           
				consultaSQL.append("t1.actualizado_el,");       
				consultaSQL.append("t1.actualizado_por,");      
				consultaSQL.append("t1.usuario_actualizacion,");
				consultaSQL.append("t1.ip_actualizacion");      
				consultaSQL.append(" FROM ");				
				consultaSQL.append(NOMBRE_VISTA);
				consultaSQL.append(" t1 ");
				consultaSQL.append(" WHERE t1.");
				consultaSQL.append(" id_doperativo ");
				consultaSQL.append("=?");
	
				listaRegistros = jdbcTemplate.query(consultaSQL.toString(),new Object[] {idOperativo}, new PlanificacionMapper());
	
				for(int r=0; r < listaRegistros.size(); r++){
					if(descProductoVolRequerido.isEmpty()){
						descProductoVolRequerido = listaRegistros.get(r).getDescProductoVolRequerido();
					} else {
						descProductoVolRequerido = descProductoVolRequerido + ", " + listaRegistros.get(r).getDescProductoVolRequerido();
					}
				}
				diasOperativos.get(k).setPlanificaciones(listaRegistros);
				diasOperativos.get(k).setDetalleProductoSolicitado(descProductoVolRequerido);
			}

			contenido.totalRegistros = diasOperativos.size();
			contenido.totalEncontrados = diasOperativos.size();
			contenido.carga = diasOperativos;
			respuesta.mensaje="OK";
			respuesta.estado=true;
			respuesta.contenido = contenido;			
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
			respuesta.contenido=null;
		}
		return respuesta;
	}
	
	/**
     * Metodo para recuperar las planificaciones de un día operativo.
     * @param diasOperativos      Identificador del día operativo.
     * @return respuesta		  Listado de planificaciones.
     */
	public RespuestaCompuesta recuperarPlanificacionesPorDiaOperativo(int idDOperativo) {
		StringBuilder consultaSQL = new StringBuilder();
		List<Planificacion> listaRegistros = new ArrayList<Planificacion>();
		Contenido<Planificacion> contenido = new Contenido<Planificacion>();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();

		if(!Utilidades.esValido(idDOperativo)){
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido = null;
			return respuesta;
		}
		try{
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_doperativo,");        
			consultaSQL.append("t1.id_planificacion,");     
			consultaSQL.append("t1.id_producto,");          
			consultaSQL.append("t1.nombre,");               
			consultaSQL.append("t1.volumen_propuesto,");     
			consultaSQL.append("t1.volumen_solicitado,");    
			consultaSQL.append("t1.cantidad_cisternas,");   
			//Campos de auditoria                           
			consultaSQL.append("t1.actualizado_el,");       
			consultaSQL.append("t1.actualizado_por,");      
			consultaSQL.append("t1.usuario_actualizacion,");
			consultaSQL.append("t1.ip_actualizacion");      
			consultaSQL.append(" FROM ");				
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(" WHERE t1.");
			consultaSQL.append(" id_doperativo ");
			consultaSQL.append("=?");

			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),new Object[] {idDOperativo}, new PlanificacionMapper());

			contenido.totalRegistros = listaRegistros.size();
			contenido.totalEncontrados = listaRegistros.size();
			contenido.carga = listaRegistros;
			respuesta.mensaje="OK";
			respuesta.estado=true;
			respuesta.contenido = contenido;			
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
			respuesta.contenido=null;
		}
		return respuesta;
	}
	
	/**
     * Metodo para eliminar todas las planificaciones de un día operativo.
     * @param idDiaOperativo      Identificador del día operativo.
     * @return respuesta		  Contiene el valor de los registros eliminados.
     */
	public Respuesta eliminarRegistrosPorDiaOperativo(int idDiaOperativo){		
		Respuesta respuesta= new Respuesta();
		String consultaSQL="";
		Object[] parametros = {idDiaOperativo};
		try {
			consultaSQL="DELETE FROM " + NOMBRE_TABLA + " WHERE id_doperativo = ?";
			int registrosEliminados = jdbcTemplate.update(consultaSQL, parametros);
			if (!Utilidades.esValido(registrosEliminados)){
				respuesta.estado=true;
				respuesta.valor=null;
			} else {
				respuesta.estado = true;
				respuesta.valor= String.valueOf(registrosEliminados);
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
	

	/**
     * Metodo para cuenta todas las planificaciones de un día operativo.
     * @param idDiaOperativo      Identificador del día operativo.
     * @return respuesta		  Número de planificaciones que existen del día operativo. .
     */
	public Respuesta numeroRegistrosPorDiaOperativo(int idDiaOperativo){		
		Respuesta respuesta = new Respuesta();
		StringBuilder consultaSQL= new StringBuilder();	
		int cantidadRegistros = 0;

		try {
			consultaSQL=new StringBuilder();
			consultaSQL.append("SELECT ");
			consultaSQL.append(" count(*) ");
			consultaSQL.append(" FROM ");				
			consultaSQL.append(" sgo.planificacion ");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(" id_doperativo = ");
			consultaSQL.append( idDiaOperativo );
			
			cantidadRegistros = jdbcTemplate.queryForInt(consultaSQL.toString());
			respuesta.valor = String.valueOf(cantidadRegistros);
			respuesta.estado = true;
			
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
		} catch (Exception excepcionGenerica) {
			excepcionGenerica.printStackTrace();
			respuesta.error= Constante.EXCEPCION_GENERICA;
			respuesta.estado = false;
		}
		
		return respuesta;
	}
	
	public int getVolPromedioCisterna(int idCliente, int idOperacion){
		StringBuilder consultaSQL= new StringBuilder();	
		int volPromedioCisternas = 0;

		RespuestaCompuesta respuesta= new RespuestaCompuesta();
		try {
			consultaSQL=new StringBuilder();
			consultaSQL.append("SELECT ");
			consultaSQL.append(" t1.volumen_promedio_cisterna ");
			consultaSQL.append(" FROM ");				
			consultaSQL.append(" sgo.operacion ");
			consultaSQL.append(" t1 ");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(" t1.id_cliente ");
			consultaSQL.append(" = " + idCliente);
			consultaSQL.append(Y);
			consultaSQL.append(" t1.id_operacion ");
			consultaSQL.append(" = "+ idOperacion);
			
			volPromedioCisternas = jdbcTemplate.queryForInt(consultaSQL.toString());
			System.out.println("volPromedioCisternas " + volPromedioCisternas);
			
		} catch (DataAccessException excepcionAccesoDatos) {
			System.out.println("No encontro registros para la consulta " + volPromedioCisternas);
			//excepcionAccesoDatos.printStackTrace();
		} catch (Exception excepcionGenerica) {
			excepcionGenerica.printStackTrace();
			respuesta.error= Constante.EXCEPCION_GENERICA;
			respuesta.contenido=null;
			respuesta.estado = false;
		}
		return volPromedioCisternas;
	}


}