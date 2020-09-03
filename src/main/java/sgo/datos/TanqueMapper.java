package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Estacion;
import sgo.entidad.Tanque;
public class TanqueMapper implements RowMapper<Tanque>{
	public Tanque mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Tanque eTanque = null;
		Estacion eEstacion = null;
		try {
			eTanque = new Tanque();
			eTanque.setId(rs.getInt("id_tanque"));
			eTanque.setVolumenTotal(rs.getFloat("volumen_total"));
			eTanque.setDescripcion(rs.getString("descripcion"));
			eTanque.setVolumenTrabajo(rs.getFloat("volumen_trabajo"));
			eTanque.setIdEstacion(rs.getInt("id_estacion"));
			eTanque.setEstado(rs.getInt("estado"));
			eTanque.setSincronizadoEl(rs.getString("sincronizado_el"));
			eTanque.setFechaReferencia(rs.getString("fecha_referencia"));
			eTanque.setCodigoReferencia(rs.getString("codigo_referencia"));
			eEstacion = new Estacion();
			eEstacion.setId(rs.getInt("id_estacion"));
			eEstacion.setNombre(rs.getString("nombre_estacion"));
			eTanque.setEstacion(eEstacion);
			//Parametros de auditoria
			eTanque.setCreadoPor(rs.getInt("creado_por"));
			eTanque.setCreadoEl(rs.getLong("creado_el"));
			eTanque.setActualizadoPor(rs.getInt("actualizado_por"));
			eTanque.setActualizadoEl(rs.getLong("actualizado_el"));
			eTanque.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eTanque.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eTanque.setIpCreacion(rs.getString("ip_creacion"));
			eTanque.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eTanque;
	}
}