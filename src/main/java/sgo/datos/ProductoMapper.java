 	

package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Producto;
public class ProductoMapper implements RowMapper<Producto>{
	public Producto mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Producto eProducto = null;
		try {
			eProducto = new Producto();
			eProducto.setId(rs.getInt("id_producto"));
			eProducto.setNombre(rs.getString("nombre"));
			eProducto.setCodigoOsinerg(rs.getString("codigo_osinerg"));
			eProducto.setAbreviatura(rs.getString("abreviatura"));
			eProducto.setEstado(rs.getInt("estado"));
			eProducto.setSincronizadoEl(rs.getString("sincronizado_el"));
			eProducto.setFechaReferencia(rs.getString("fecha_referencia"));
			eProducto.setCodigoReferencia(rs.getString("codigo_referencia"));
			//Parametros de auditoria
			eProducto.setCreadoPor(rs.getInt("creado_por"));
			eProducto.setCreadoEl(rs.getLong("creado_el"));
			eProducto.setActualizadoPor(rs.getInt("actualizado_por"));
			eProducto.setActualizadoEl(rs.getLong("actualizado_el"));
			eProducto.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eProducto.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eProducto.setIpCreacion(rs.getString("ip_creacion"));
			eProducto.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eProducto;
	}
}