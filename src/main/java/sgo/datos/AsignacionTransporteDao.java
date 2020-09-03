package sgo.datos;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import sgo.entidad.AsignacionTransporte;
import sgo.entidad.Contenido;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

@Repository
public class AsignacionTransporteDao {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	public static final String NOMBRE_VISTA = Constante.ESQUEMA_APLICACION	+ "v_asignacion_transporte";
	public final static String NOMBRE_CAMPO_CLAVE = "id_asignacion";
	public final static String NOMBRE_CAMPO_CLAVE_DOPERATIVO = "id_doperativo";
	public final static String NOMBRE_CAMPO_CLAVE_TRANSPORTE = "id_transporte";
	public final static String NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO = "id_doperativo";

	public final static String O = " OR ";
	public final static String Y = " AND ";
	public final static String ENTRE = " BETWEEN ";

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public DataSource getDataSource() {
		return this.jdbcTemplate.getDataSource();
	}

	public String mapearCampoOrdenamiento(String propiedad) {
		String campoOrdenamiento = "";
		try {
			if (propiedad.equals("descripcionProducto")) {
				campoOrdenamiento = "nombre_producto";
			}
			if (propiedad.equals("volumenSolicitado")) {
				campoOrdenamiento = "volumen_solicitado";
			}
			if (propiedad.equals("cisternasSolicitadas")) {
				campoOrdenamiento = "cisternas_solicitadas";
			}
			if (propiedad.equals("volumenAsignado")) {
				campoOrdenamiento = "volumen_asignado";
			}
			if (propiedad.equals("cisternasAsignadas")) {
				campoOrdenamiento = "cisternas_asignadas";
			}
		} catch (Exception excepcion) {

		}
		return campoOrdenamiento;
	}

	public RespuestaCompuesta recuperarRegistrosPorDiaOperativo(int idDoperativo) {
		StringBuilder consultaSQL = new StringBuilder();
		List<AsignacionTransporte> listaRegistros = new ArrayList<AsignacionTransporte>();
		Contenido<AsignacionTransporte> contenido = new Contenido<AsignacionTransporte>();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();

		if(!Utilidades.esValido(idDoperativo)){
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido = null;
			return respuesta;
		}
		try{
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("dpt.nombre_producto,  ");
			consultaSQL.append("dpt.volumen_solicitado,  ");
			consultaSQL.append(" dpt.cantidad_cisternas, ");
			consultaSQL.append("ast.volumen_observado, ");
			consultaSQL.append("ast.total_cisternas ");
			consultaSQL.append(" FROM sgo.v_dia_planificado_transporte dpt ");				
			consultaSQL.append(" LEFT OUTER JOIN  sgo.v_asignacion_transporte_producto ast ");
			consultaSQL.append(" ON  dpt.id_doperativo = ast.id_doperativo ");
			consultaSQL.append(" AND dpt.id_producto = ast.id_producto ");
			consultaSQL.append(" WHERE dpt.id_doperativo = ?");

			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),new Object[] {idDoperativo}, new AsignacionTransporteMapper());

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

}