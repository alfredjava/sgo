package sgo.datos;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
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
import sgo.entidad.Transporte;
import sgo.entidad.Contenido;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

@Repository
public class TransporteDao {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION + "transporte";
	public static final String NOMBRE_VISTA = Constante.ESQUEMA_APLICACION + "v_transporte";
	public final static String NOMBRE_CAMPO_CLAVE = "id_transporte";
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return this.jdbcTemplate.getDataSource();
	}

	public String mapearCampoOrdenamiento(String propiedad){
		String campoOrdenamiento="";
		try {
			if (propiedad.equals("id")){
				campoOrdenamiento="id_transporte";
			}
			if (propiedad.equals("numeroGuiaRemision")){
				campoOrdenamiento="numero_guia_remision";
			}
			if (propiedad.equals("numeroOrdenCompra")){
				campoOrdenamiento="numero_orden_entrega";
			}
			if (propiedad.equals("numeroFactura")){
				campoOrdenamiento="numero_factura";
			}
			if (propiedad.equals("codigoScop")){
				campoOrdenamiento="codigo_scop";
			}
			if (propiedad.equals("fechaEmisionGuia")){
				campoOrdenamiento="fecha_emision";
			}
			if (propiedad.equals("plantaDespacho")){
				campoOrdenamiento="planta_despacho";
			}
			if (propiedad.equals("plantaRecepcion")){
				campoOrdenamiento="planta_recepcion";
			}
			if (propiedad.equals("idCliente")){
				campoOrdenamiento="id_cliente";
			}
			if (propiedad.equals("idConductor")){
				campoOrdenamiento="id_conductor";
			}
			if (propiedad.equals("breveteConductor")){
				campoOrdenamiento="brevete_conductor";
			}
			if (propiedad.equals("idCisterna")){
				campoOrdenamiento="id_cisterna";
			}
			if (propiedad.equals("placaCisterna")){
				campoOrdenamiento="placa_cisterna";
			}
			if (propiedad.equals("tarjetaCubicacionCompartimento")){
				campoOrdenamiento="tarjeta_cubicacion_cisterna";
			}
			if (propiedad.equals("idTracto")){
				campoOrdenamiento="id_tracto";
			}
			if (propiedad.equals("placaTracto")){
				campoOrdenamiento="placa_tracto";
			}
			if (propiedad.equals("idTransportista")){
				campoOrdenamiento="id_transportista";
			}
			if (propiedad.equals("volumenTotalObservado")){
				campoOrdenamiento="volumen_total_observado";
			}
			if (propiedad.equals("volumenTotalCorregido")){
				campoOrdenamiento="volumen_total_corregido";
			}
			if (propiedad.equals("estado")){
				campoOrdenamiento="estado";
			}
			if (propiedad.equals("sincronizadoEl")){
				campoOrdenamiento="sincronizado_el";
			}
			//Campos de auditoria
		}catch(Exception excepcion){
			
		}
		return campoOrdenamiento;
	}

	public RespuestaCompuesta recuperarRegistros(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		int totalRegistros = 0, totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<Transporte> contenido = new Contenido<Transporte>();
		List<Transporte> listaRegistros = new ArrayList<Transporte>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.append("SELECT count(" + NOMBRE_CAMPO_CLAVE+ ") as total FROM " + NOMBRE_TABLA);
			totalRegistros = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_transporte, ");
			consultaSQL.append("t1.numero_guia_remision, ");
			consultaSQL.append("t1.planta_despacho, ");
			consultaSQL.append("t1.descripcionPlantaDespacho, ");
			consultaSQL.append("t1.cliente_transporte, ");
			consultaSQL.append("t1.id_conductor, ");
			consultaSQL.append("t1.apellidosconductor, ");
			consultaSQL.append("t1.nombresconductor, ");
			consultaSQL.append("t1.fecha_emision, ");
			consultaSQL.append("t1.id_cisterna, ");
			consultaSQL.append("t1.placa_cisterna, ");
			consultaSQL.append("t1.volumen_total_observado, ");
			consultaSQL.append("t1.volumen_total_corregido, ");
			consultaSQL.append("t1.id_transportista, ");
			consultaSQL.append("t1.razonsocialtransportista, ");
			consultaSQL.append("t1.brevete_conductor, ");
			consultaSQL.append("t1.numero_orden_entrega, ");
			consultaSQL.append("t1.codigo_scop, ");
			consultaSQL.append("t1.estado_transporte, ");
			consultaSQL.append("t1.sincronizado_el, ");
			consultaSQL.append("t1.numero_factura, ");
			consultaSQL.append("t1.planta_recepcion, ");
			consultaSQL.append("t1.descripcionPlantaRecepcion, ");
			consultaSQL.append("t1.id_tracto, ");
			consultaSQL.append("t1.placa_tracto, ");
			consultaSQL.append("t1.tarjeta_cubicacion_cisterna, ");
			consultaSQL.append("t1.cisterna_tracto,");
			consultaSQL.append("t1.precintos_seguridad,");
			consultaSQL.append("t1.tipo_registro,");
			consultaSQL.append("t1.peso_bruto,");
			consultaSQL.append("t1.peso_tara,");
			consultaSQL.append("t1.peso_neto,");
			//Campos de auditoria 
			consultaSQL.append("t1.creado_el_transporte, ");
			consultaSQL.append("t1.creado_por_transporte, ");
			consultaSQL.append("t1.usuario_creacion_transporte, ");
			consultaSQL.append("t1.actualizado_por_transporte, ");
			consultaSQL.append("t1.usuario_actualizacion_transporte, ");
			consultaSQL.append("t1.actualizado_el_transporte, ");
			consultaSQL.append("t1.ip_creacion_transporte,");
			consultaSQL.append("t1.ip_actualizacion_transporte ");
			consultaSQL.append(" FROM ");
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(sqlLimit);
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new TransporteMapper());
			totalEncontrados =totalRegistros;
			contenido.carga = listaRegistros;
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
	 * Metodo para recuperar trnsportes por su identificador.
	 * @param diasOperativos      Identificador del transporte.
	 * @return respuesta		  Resgitro Transporte.
	 */
	public RespuestaCompuesta recuperarRegistro(int ID){
		StringBuilder consultaSQL= new StringBuilder();		
		List<Transporte> listaRegistros=new ArrayList<Transporte>();
		Contenido<Transporte> contenido = new Contenido<Transporte>();
		RespuestaCompuesta respuesta= new RespuestaCompuesta();
		try {
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_transporte, ");
			consultaSQL.append("t1.numero_guia_remision, ");
			consultaSQL.append("t1.planta_despacho, ");
			consultaSQL.append("t1.descripcionPlantaDespacho, ");
			consultaSQL.append("t1.cliente_transporte, ");
			consultaSQL.append("t1.id_conductor, ");
			consultaSQL.append("t1.apellidosconductor, ");
			consultaSQL.append("t1.nombresconductor, ");
			consultaSQL.append("t1.fecha_emision, ");
			consultaSQL.append("t1.id_cisterna, ");
			consultaSQL.append("t1.placa_cisterna, ");
			consultaSQL.append("t1.volumen_total_observado, ");
			consultaSQL.append("t1.volumen_total_corregido, ");
			consultaSQL.append("t1.id_transportista, ");
			consultaSQL.append("t1.razonsocialtransportista, ");
			consultaSQL.append("t1.brevete_conductor, ");
			consultaSQL.append("t1.numero_orden_entrega, ");
			consultaSQL.append("t1.codigo_scop, ");
			consultaSQL.append("t1.estado_transporte, ");
			consultaSQL.append("t1.sincronizado_el, ");
			consultaSQL.append("t1.numero_factura, ");
			consultaSQL.append("t1.planta_recepcion, ");
			consultaSQL.append("t1.descripcionPlantaRecepcion, ");
			consultaSQL.append("t1.id_tracto, ");
			consultaSQL.append("t1.placa_tracto, ");
			consultaSQL.append("t1.tarjeta_cubicacion_cisterna, ");
			consultaSQL.append("t1.cisterna_tracto,");
			consultaSQL.append("t1.precintos_seguridad,");
			consultaSQL.append("t1.tipo_registro,");
			consultaSQL.append("t1.peso_bruto,");
			consultaSQL.append("t1.peso_tara,");
			consultaSQL.append("t1.peso_neto,");
			//Campos de auditoria 
			consultaSQL.append("t1.creado_el_transporte, ");
			consultaSQL.append("t1.creado_por_transporte, ");
			consultaSQL.append("t1.usuario_creacion_transporte, ");
			consultaSQL.append("t1.actualizado_por_transporte, ");
			consultaSQL.append("t1.usuario_actualizacion_transporte, ");
			consultaSQL.append("t1.actualizado_el_transporte, ");
			consultaSQL.append("t1.ip_creacion_transporte,");
			consultaSQL.append("t1.ip_actualizacion_transporte ");
			consultaSQL.append(" FROM ");				
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=?");
			listaRegistros= jdbcTemplate.query(consultaSQL.toString(),new Object[] {ID},new TransporteMapper());
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
     * Metodo para recuperar trnsportes por su identificador.
     * @param diasOperativos      Identificador del transporte.
     * @return respuesta		  Resgitro Transporte.
     */
	public RespuestaCompuesta recuperaTransportesAsignados(int ID){
		StringBuilder consultaSQL= new StringBuilder();		
		List<Transporte> listaRegistros=new ArrayList<Transporte>();
		Contenido<Transporte> contenido = new Contenido<Transporte>();
		RespuestaCompuesta respuesta= new RespuestaCompuesta();
		try {
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_transporte, ");
			consultaSQL.append("t1.numero_guia_remision, ");
			consultaSQL.append("t1.planta_despacho, ");
			consultaSQL.append("t1.descripcionPlantaDespacho, ");
			consultaSQL.append("t1.cliente_transporte, ");
			consultaSQL.append("t1.id_conductor, ");
			consultaSQL.append("t1.apellidosconductor, ");
			consultaSQL.append("t1.nombresconductor, ");
			consultaSQL.append("t1.fecha_emision, ");
			consultaSQL.append("t1.id_cisterna, ");
			consultaSQL.append("t1.placa_cisterna, ");
			consultaSQL.append("t1.volumen_total_observado, ");
			consultaSQL.append("t1.volumen_total_corregido, ");
			consultaSQL.append("t1.id_transportista, ");
			consultaSQL.append("t1.razonsocialtransportista, ");
			consultaSQL.append("t1.brevete_conductor, ");
			consultaSQL.append("t1.numero_orden_entrega, ");
			consultaSQL.append("t1.codigo_scop, ");
			consultaSQL.append("t1.estado_transporte, ");
			consultaSQL.append("t1.sincronizado_el, ");
			consultaSQL.append("t1.numero_factura, ");
			consultaSQL.append("t1.planta_recepcion, ");
			consultaSQL.append("t1.descripcionPlantaRecepcion, ");
			consultaSQL.append("t1.id_tracto, ");
			consultaSQL.append("t1.placa_tracto, ");
			consultaSQL.append("t1.tarjeta_cubicacion_cisterna, ");
			consultaSQL.append("t1.cisterna_tracto,");
			consultaSQL.append("t1.precintos_seguridad,");
			consultaSQL.append("t1.tipo_registro,");
			consultaSQL.append("t1.peso_bruto,");
			consultaSQL.append("t1.peso_tara,");
			consultaSQL.append("t1.peso_neto,");
			//Campos de auditoria 
			consultaSQL.append("t1.creado_el_transporte, ");
			consultaSQL.append("t1.creado_por_transporte, ");
			consultaSQL.append("t1.usuario_creacion_transporte, ");
			consultaSQL.append("t1.actualizado_por_transporte, ");
			consultaSQL.append("t1.usuario_actualizacion_transporte, ");
			consultaSQL.append("t1.actualizado_el_transporte, ");
			consultaSQL.append("t1.ip_creacion_transporte,");
			consultaSQL.append("t1.ip_actualizacion_transporte ");
			consultaSQL.append(" FROM ");				
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1, sgo.v_asignacion t2");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(" t1.id_transporte = t2.id_transporte ");
			consultaSQL.append(" AND ");
			consultaSQL.append(" t2.id_doperativo = ?");
			listaRegistros= jdbcTemplate.query(consultaSQL.toString(),new Object[] {ID},new TransporteMapper());
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
	
	public RespuestaCompuesta guardarRegistro(Transporte transporte){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		KeyHolder claveGenerada = null;
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("INSERT INTO ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" (numero_guia_remision,numero_orden_entrega,numero_factura,codigo_scop,fecha_emision,planta_despacho,planta_recepcion,id_cliente,id_conductor,brevete_conductor,id_cisterna,placa_cisterna,tarjeta_cubicacion_cisterna,id_tracto,placa_tracto,id_transportista,volumen_total_observado,volumen_total_corregido,estado,precintos_seguridad,tipo_registro,peso_bruto,peso_tara,peso_neto,creado_el,creado_por,actualizado_por,actualizado_el,ip_creacion,ip_actualizacion) ");
			consultaSQL.append(" VALUES (:NumeroGuiaRemision,:NumeroOrdenCompra,:NumeroFactura,:CodigoScop,:FechaEmisionGuia,:PlantaDespacho,:PlantaRecepcion,:IdCliente,:IdConductor,:BreveteConductor,:IdCisterna,:PlacaCisterna,:TarjetaCubicacionCompartimento,:IdTracto,:PlacaTracto,:IdTransportista,:VolumenTotalObservado,:VolumenTotalCorregido,:Estado,:PrecintosSeguridad,:TipoRegistro,:PesoBruto,:PesoTara,:PesoNeto,:CreadoEl,:CreadoPor,:ActualizadoPor,:ActualizadoEl,:IpCreacion,:IpActualizacion) ");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();   
			listaParametros.addValue("NumeroGuiaRemision", transporte.getNumeroGuiaRemision());
			listaParametros.addValue("NumeroOrdenCompra", transporte.getNumeroOrdenCompra());
			listaParametros.addValue("NumeroFactura", transporte.getNumeroFactura());
			listaParametros.addValue("CodigoScop", transporte.getCodigoScop());
			listaParametros.addValue("FechaEmisionGuia", transporte.getFechaEmisionGuia());
			listaParametros.addValue("PlantaDespacho", transporte.getIdPlantaDespacho());
			listaParametros.addValue("PlantaRecepcion", transporte.getIdPlantaRecepcion());
			listaParametros.addValue("IdCliente", transporte.getIdCliente());
			listaParametros.addValue("IdConductor", transporte.getIdConductor());
			listaParametros.addValue("BreveteConductor", transporte.getBreveteConductor());
			listaParametros.addValue("IdCisterna", transporte.getIdCisterna());
			listaParametros.addValue("PlacaCisterna", transporte.getPlacaCisterna());
			listaParametros.addValue("TarjetaCubicacionCompartimento", transporte.getTarjetaCubicacionCompartimento());
			listaParametros.addValue("IdTracto", transporte.getIdTracto());
			listaParametros.addValue("PlacaTracto", transporte.getPlacaTracto());
			listaParametros.addValue("IdTransportista", transporte.getIdTransportista());
			listaParametros.addValue("VolumenTotalObservado", transporte.getVolumenTotalObservado());
			listaParametros.addValue("VolumenTotalCorregido", transporte.getVolumenTotalCorregido());
			listaParametros.addValue("Estado", transporte.getEstado());
			listaParametros.addValue("PrecintosSeguridad", transporte.getPrecintosSeguridad());
			listaParametros.addValue("TipoRegistro", transporte.getOrigen());
			listaParametros.addValue("PesoBruto", transporte.getPesoBruto());
			listaParametros.addValue("PesoTara", transporte.getPesoTara());
			listaParametros.addValue("PesoNeto", transporte.getPesoNeto());
			//listaParametros.addValue("SincronizadoEl", transporte.getSincronizadoEl());
			listaParametros.addValue("CreadoEl", transporte.getCreadoEl());
			listaParametros.addValue("CreadoPor", transporte.getCreadoPor());
			listaParametros.addValue("ActualizadoPor", transporte.getActualizadoPor());
			listaParametros.addValue("ActualizadoEl", transporte.getActualizadoEl());
			listaParametros.addValue("IpCreacion", transporte.getIpCreacion());
			listaParametros.addValue("IpActualizacion", transporte.getIpActualizacion());
			
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			claveGenerada = new GeneratedKeyHolder();
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters,claveGenerada,new String[] {NOMBRE_CAMPO_CLAVE});		
			if (cantidadFilasAfectadas>1){
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
	
	public RespuestaCompuesta actualizarRegistro(Transporte transporte){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("numero_guia_remision	=:NumeroGuiaRemision,");
			consultaSQL.append("numero_orden_entrega	=:NumeroOrdenCompra,");
			consultaSQL.append("numero_factura			=:NumeroFactura,");
			consultaSQL.append("codigo_scop				=:CodigoScop,");
			consultaSQL.append("fecha_emision			=:FechaEmisionGuia,");
			consultaSQL.append("planta_despacho			=:PlantaDespacho,");
			//consultaSQL.append("planta_recepcion		=:PlantaRecepcion,");
			consultaSQL.append("id_cliente				=:IdCliente,");
			consultaSQL.append("id_conductor			=:IdConductor,");
			consultaSQL.append("brevete_conductor		=:BreveteConductor,");
			consultaSQL.append("id_cisterna				=:IdCisterna,");
			consultaSQL.append("placa_cisterna			=:PlacaCisterna,");
			//consultaSQL.append("tarjeta_cubicacion_cisterna=:TarjetaCubicacionCompartimento,");
			consultaSQL.append("id_tracto				=:IdTracto,");
			consultaSQL.append("placa_tracto			=:PlacaTracto,");
			consultaSQL.append("id_transportista		=:IdTransportista,");
			consultaSQL.append("volumen_total_observado	=:VolumenTotalObservado,");
			consultaSQL.append("volumen_total_corregido	=:VolumenTotalCorregido,");
			//consultaSQL.append("estado					=:Estado,");
			//consultaSQL.append("sincronizado_el			=:SincronizadoEl,");
			consultaSQL.append("actualizado_por			=:ActualizadoPor,");
			consultaSQL.append("actualizado_el			=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion		=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("NumeroGuiaRemision", transporte.getNumeroGuiaRemision());
			listaParametros.addValue("NumeroOrdenCompra", transporte.getNumeroOrdenCompra());
			listaParametros.addValue("NumeroFactura", transporte.getNumeroFactura());
			listaParametros.addValue("CodigoScop", transporte.getCodigoScop());
			listaParametros.addValue("FechaEmisionGuia", transporte.getFechaEmisionGuia());
			listaParametros.addValue("PlantaDespacho", transporte.getIdPlantaDespacho());
			//listaParametros.addValue("PlantaRecepcion", transporte.getIdPlantaRecepcion());
			listaParametros.addValue("IdCliente", transporte.getIdCliente());
			listaParametros.addValue("IdConductor", transporte.getIdConductor());
			listaParametros.addValue("BreveteConductor", transporte.getBreveteConductor());
			listaParametros.addValue("IdCisterna", transporte.getIdCisterna());
			listaParametros.addValue("PlacaCisterna", transporte.getPlacaCisterna());
			//listaParametros.addValue("TarjetaCubicacionCompartimento", transporte.getTarjetaCubicacionCompartimento());
			listaParametros.addValue("IdTracto", transporte.getIdTracto());
			listaParametros.addValue("PlacaTracto", transporte.getPlacaTracto());
			listaParametros.addValue("IdTransportista", transporte.getIdTransportista());
			listaParametros.addValue("VolumenTotalObservado", transporte.getVolumenTotalObservado());
			listaParametros.addValue("VolumenTotalCorregido", transporte.getVolumenTotalCorregido());
		//	listaParametros.addValue("Estado", transporte.getEstado());
			//listaParametros.addValue("SincronizadoEl", transporte.getSincronizadoEl());
			//Valores Auditoria
			listaParametros.addValue("ActualizadoEl", transporte.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", transporte.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", transporte.getIpActualizacion());
			listaParametros.addValue("Id", transporte.getId());
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
	
	public RespuestaCompuesta actualizarPesajes(Transporte transporte){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("peso_bruto=:PesoBruto,");
			consultaSQL.append("peso_tara=:PesoTara,");
			consultaSQL.append("peso_neto=:PesoNeto,");
			//datos auditoria
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("PesoBruto", transporte.getPesoBruto());
			listaParametros.addValue("PesoTara", transporte.getPesoTara());
			listaParametros.addValue("PesoNeto", transporte.getPesoNeto());
			//datos auditoria
			listaParametros.addValue("ActualizadoEl", transporte.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", transporte.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", transporte.getIpActualizacion());
			listaParametros.addValue("Id", transporte.getId());
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
	
}