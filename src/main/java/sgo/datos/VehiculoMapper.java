package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Propietario;
import sgo.entidad.Vehiculo;
public class VehiculoMapper implements RowMapper<Vehiculo>{
	public Vehiculo mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Vehiculo eVehiculo = null;
		Propietario ePropietario =null;
		try {
			eVehiculo = new Vehiculo();
			eVehiculo.setId(rs.getInt("id_vehiculo"));
			eVehiculo.setNombreCorto(rs.getString("nombre_corto"));
			eVehiculo.setDescripcion(rs.getString("descripcion"));
			eVehiculo.setIdPropietario(rs.getInt("id_propietario"));
			eVehiculo.setEstado(rs.getInt("estado"));
			eVehiculo.setSincronizadoEl(rs.getString("sincronizado_el"));
			eVehiculo.setFechaReferencia(rs.getString("fecha_referencia"));
			eVehiculo.setCodigoReferencia(rs.getString("codigo_referencia"));
			ePropietario = new Propietario();
			ePropietario.setId(rs.getInt("id_propietario"));
			ePropietario.setRazonSocial(rs.getString("razon_social_propietario"));
			eVehiculo.setPropietario(ePropietario);
			//Parametros de auditoria
			eVehiculo.setCreadoPor(rs.getInt("creado_por"));
			eVehiculo.setCreadoEl(rs.getLong("creado_el"));
			eVehiculo.setActualizadoPor(rs.getInt("actualizado_por"));
			eVehiculo.setActualizadoEl(rs.getLong("actualizado_el"));
			eVehiculo.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eVehiculo.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eVehiculo.setIpCreacion(rs.getString("ip_creacion"));
			eVehiculo.setIpActualizacion(rs.getString("ip_actualizacion"));
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eVehiculo;
	}
}