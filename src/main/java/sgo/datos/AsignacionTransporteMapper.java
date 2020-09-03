package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.AsignacionTransporte;

public class AsignacionTransporteMapper implements RowMapper<AsignacionTransporte>{
	public AsignacionTransporte mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		AsignacionTransporte eAsignacionTransporte = null;
		try {
			eAsignacionTransporte = new AsignacionTransporte();
			eAsignacionTransporte.setDescripcionProducto(rs.getString("nombre_producto"));
			eAsignacionTransporte.setVolumenSolicitado(rs.getFloat("volumen_solicitado"));
			eAsignacionTransporte.setCisternasSolicitadas(rs.getFloat("cantidad_cisternas"));
			eAsignacionTransporte.setVolumenAsignado(rs.getFloat("volumen_observado"));
			eAsignacionTransporte.setCisternasAsignadas(rs.getFloat("total_cisternas"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eAsignacionTransporte;
	}
}