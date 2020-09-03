package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Contometro;
import sgo.entidad.Estacion;
public class ContometroMapper implements RowMapper<Contometro>{
	public Contometro mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Contometro eContometro = null;
		Estacion eEstacion = null;
		try {
			eContometro = new Contometro();
			eContometro.setId(rs.getInt("id_contometro"));
			eContometro.setAlias(rs.getString("alias"));
			eContometro.setEstado(rs.getInt("estado"));
			eContometro.setTipoContometro(rs.getInt("tipo_contometro"));
			eContometro.setSincronizadoEl(rs.getString("sincronizado_el"));
			eContometro.setFechaReferencia(rs.getString("fecha_referencia"));
			eContometro.setCodigoReferencia(rs.getString("codigo_referencia"));
			eContometro.setIdEstacion(rs.getInt("id_estacion"));
			
			eEstacion = new Estacion();
			eEstacion.setId(rs.getInt("id_estacion"));
			eEstacion.setNombre(rs.getString("nombre_estacion"));
			eContometro.setEstacion(eEstacion);
			
			//Parametros de auditoria
			eContometro.setCreadoPor(rs.getInt("creado_por"));
			eContometro.setCreadoEl(rs.getLong("creado_el"));
			eContometro.setActualizadoPor(rs.getInt("actualizado_por"));
			eContometro.setActualizadoEl(rs.getLong("actualizado_el"));
			eContometro.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eContometro.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eContometro.setIpCreacion(rs.getString("ip_creacion"));
			eContometro.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eContometro;
	}
}