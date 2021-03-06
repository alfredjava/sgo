package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Planificacion;
import sgo.entidad.Producto;
public class PlanificacionMapper implements RowMapper<Planificacion>{
	public Planificacion mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Planificacion ePlanificacion = null;
		Producto eProducto = null;
		try {
			ePlanificacion = new Planificacion();
			ePlanificacion.setIdDoperativo(rs.getInt("id_doperativo"));
			ePlanificacion.setId(rs.getInt("id_planificacion"));
			ePlanificacion.setIdProducto(rs.getInt("id_producto"));
			ePlanificacion.setVolumenPropuesto(rs.getFloat("volumen_propuesto"));
			ePlanificacion.setVolumenSolicitado(rs.getFloat("volumen_solicitado"));
			ePlanificacion.setCantidadCisternas(rs.getInt("cantidad_cisternas"));
			
			eProducto = new Producto();
				eProducto.setId(rs.getInt("id_producto"));
				eProducto.setNombre(rs.getString("nombre"));
				ePlanificacion.setProducto(eProducto);
			
			//Parametros de auditoria
			ePlanificacion.setActualizadoPor(rs.getInt("actualizado_por"));
			ePlanificacion.setActualizadoEl(rs.getLong("actualizado_el"));
			ePlanificacion.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			ePlanificacion.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return ePlanificacion;
	}
}