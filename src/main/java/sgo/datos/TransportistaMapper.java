package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Transportista;
public class TransportistaMapper implements RowMapper<Transportista>{
	public Transportista mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Transportista eTransportista = null;
		try {
			eTransportista = new Transportista();
			eTransportista.setId(rs.getInt("id_transportista"));
			eTransportista.setRazonSocial(rs.getString("razon_social"));
			eTransportista.setNombreCorto(rs.getString("nombre_corto"));
			eTransportista.setRuc(rs.getString("ruc"));
			eTransportista.setEstado(rs.getInt("estado"));
			eTransportista.setSincronizadoEl(rs.getString("sincronizado_el"));
			eTransportista.setFechaReferencia(rs.getString("fecha_referencia"));
			eTransportista.setCodigoReferencia(rs.getString("codigo_referencia"));
			//Parametros de auditoria
			eTransportista.setCreadoPor(rs.getInt("creado_por"));
			eTransportista.setCreadoEl(rs.getLong("creado_el"));
			eTransportista.setActualizadoPor(rs.getInt("actualizado_por"));
			eTransportista.setActualizadoEl(rs.getLong("actualizado_el"));
			eTransportista.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eTransportista.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eTransportista.setIpCreacion(rs.getString("ip_creacion"));
			eTransportista.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eTransportista;
	}
}