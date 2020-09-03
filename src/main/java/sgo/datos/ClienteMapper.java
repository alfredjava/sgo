package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Cliente;
public class ClienteMapper implements RowMapper<Cliente>{
	public Cliente mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Cliente eCliente = null;
		try {
			eCliente = new Cliente();
			eCliente.setId(rs.getInt("id_cliente"));
			eCliente.setNombreCorto(rs.getString("nombre_corto"));
			eCliente.setRazonSocial(rs.getString("razon_social"));
			eCliente.setEstado(rs.getInt("estado"));
			eCliente.setRuc(rs.getString("ruc"));
			//Parametros de auditoria
			eCliente.setCreadoPor(rs.getInt("creado_por"));
			eCliente.setCreadoEl(rs.getLong("creado_el"));
			eCliente.setActualizadoPor(rs.getInt("actualizado_por"));
			eCliente.setActualizadoEl(rs.getLong("actualizado_el"));
			eCliente.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eCliente.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eCliente.setIpCreacion(rs.getString("ip_creacion"));
			eCliente.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eCliente;
	}
}