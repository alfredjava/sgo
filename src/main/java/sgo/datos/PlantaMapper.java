 	

package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Planta;
public class PlantaMapper implements RowMapper<Planta>{
	public Planta mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Planta ePlanta = null;
		try {
			ePlanta = new Planta();
			ePlanta.setId(rs.getInt("id_planta"));
			ePlanta.setDescripcion(rs.getString("descripcion"));
//			ePlanta.setAbreviatura(rs.getString("abreviatura"));
			ePlanta.setEstado(rs.getInt("estado"));
			ePlanta.setSincronizadoEl(rs.getString("sincronizado_el"));
			ePlanta.setFechaReferencia(rs.getString("fecha_referencia"));
			ePlanta.setCodigoReferencia(rs.getString("codigo_referencia"));
			//Parametros de auditoria
			ePlanta.setCreadoPor(rs.getInt("creado_por"));
			ePlanta.setCreadoEl(rs.getLong("creado_el"));
			ePlanta.setActualizadoPor(rs.getInt("actualizado_por"));
			ePlanta.setActualizadoEl(rs.getLong("actualizado_el"));
			ePlanta.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			ePlanta.setUsuarioCreacion(rs.getString("usuario_creacion"));
			ePlanta.setIpCreacion(rs.getString("ip_creacion"));
			ePlanta.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return ePlanta;
	}
}