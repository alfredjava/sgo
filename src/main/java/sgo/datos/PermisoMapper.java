package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Permiso;

public class PermisoMapper implements RowMapper<Permiso>{
 	
	/*
	 * @author Rafael Reyna Camones
	 *  @param rs
	 * 
	 */
	public Permiso mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Permiso mPermiso = new Permiso();		
		mPermiso.setId(rs.getInt("id_permiso"));
		mPermiso.setNombre(rs.getString("nombre"));
		mPermiso.setEstado(rs.getInt("estado"));	
		//Parametros de auditoria
		mPermiso.setCreadoPor(rs.getInt("creado_por"));
		mPermiso.setCreadoEl(rs.getLong("creado_el"));
		mPermiso.setActualizadoPor(rs.getInt("actualizado_por"));
		mPermiso.setActualizadoEl(rs.getLong("actualizado_el"));
		mPermiso.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
		mPermiso.setUsuarioCreacion(rs.getString("usuario_creacion"));
		mPermiso.setIpCreacion(rs.getString("ip_creacion"));
		mPermiso.setIpActualizacion(rs.getString("ip_actualizacion"));
		return mPermiso;
	}
}