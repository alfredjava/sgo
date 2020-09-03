package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Propietario;
public class PropietarioMapper implements RowMapper<Propietario>{
	public Propietario mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Propietario ePropietario = null;
		try {
			ePropietario = new Propietario();
			ePropietario.setId(rs.getInt("id_propietario"));
			ePropietario.setRazonSocial(rs.getString("razon_social"));
			ePropietario.setNombreCorto(rs.getString("nombre_corto"));
			ePropietario.setRuc(rs.getString("ruc"));
			ePropietario.setTipo(rs.getInt("tipo"));
			ePropietario.setEstado(rs.getInt("estado"));
			ePropietario.setSincronizadoEl(rs.getString("sincronizado_el"));
			ePropietario.setFechaReferencia(rs.getString("fecha_referencia"));
			ePropietario.setCodigoReferencia(rs.getString("codigo_referencia"));
			//Parametros de auditoria
			ePropietario.setCreadoPor(rs.getInt("creado_por"));
			ePropietario.setCreadoEl(rs.getLong("creado_el"));
			ePropietario.setActualizadoPor(rs.getInt("actualizado_por"));
			ePropietario.setActualizadoEl(rs.getLong("actualizado_el"));
			ePropietario.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			ePropietario.setUsuarioCreacion(rs.getString("usuario_creacion"));
			ePropietario.setIpCreacion(rs.getString("ip_creacion"));
			ePropietario.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return ePropietario;
	}
}