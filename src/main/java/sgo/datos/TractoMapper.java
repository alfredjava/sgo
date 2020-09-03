package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Tracto;
import sgo.entidad.Transportista;
public class TractoMapper implements RowMapper<Tracto>{
	public Tracto mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Tracto eTracto = null;
		Transportista eTransportista = null;
		try {
			eTracto = new Tracto();
			eTracto.setId(rs.getInt("id_tracto"));
			eTracto.setPlaca(rs.getString("placa"));
			eTracto.setEstado(rs.getInt("estado"));
			eTracto.setIdTransportista(rs.getInt("id_transportista"));
			eTracto.setSincronizadoEl(rs.getString("sincronizado_el"));
			eTracto.setFechaReferencia(rs.getString("fecha_referencia"));
			eTracto.setCodigoReferencia(rs.getString("codigo_referencia"));
			eTransportista = new Transportista();
			eTransportista.setId(rs.getInt("id_transportista"));
			eTransportista.setRazonSocial(rs.getString("razon_social_transportista"));
			eTracto.setTransportista(eTransportista);
			//Parametros de auditoria
			eTracto.setCreadoPor(rs.getInt("creado_por"));
			eTracto.setCreadoEl(rs.getLong("creado_el"));
			eTracto.setActualizadoPor(rs.getInt("actualizado_por"));
			eTracto.setActualizadoEl(rs.getLong("actualizado_el"));
			eTracto.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eTracto.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eTracto.setIpCreacion(rs.getString("ip_creacion"));
			eTracto.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eTracto;
	}
}