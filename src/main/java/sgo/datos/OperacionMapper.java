package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Cliente;
import sgo.entidad.Operacion;
public class OperacionMapper implements RowMapper<Operacion>{
	public Operacion mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Operacion eOperacion = null;
		Cliente eCliente = null;
		try {
			eOperacion = new Operacion();
			eOperacion.setId(rs.getInt("id_operacion"));
			eOperacion.setIdCliente(rs.getInt("id_cliente"));
			eOperacion.setNombre(rs.getString("nombre"));
			eOperacion.setVolumenPromedioCisterna(rs.getFloat("volumen_promedio_cisterna"));
			eOperacion.setSincronizadoEl(rs.getInt("sincronizado_el"));
			eOperacion.setReferenciaPlantaRecepcion(rs.getString("referencia_planta_recepcion"));
			eOperacion.setReferenciaDetinatarioMercaderia(rs.getString("referencia_destinatario_mercaderia"));
			eOperacion.setEstado(rs.getInt("estado"));			
			//Parametros de auditoria
			eOperacion.setCreadoPor(rs.getInt("creado_por"));
			eOperacion.setCreadoEl(rs.getLong("creado_el"));
			eOperacion.setActualizadoPor(rs.getInt("actualizado_por"));
			eOperacion.setActualizadoEl(rs.getLong("actualizado_el"));
			eOperacion.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eOperacion.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eOperacion.setIpCreacion(rs.getString("ip_creacion"));
			eOperacion.setIpActualizacion(rs.getString("ip_actualizacion"));			
			eCliente = new Cliente();
			eCliente.setId(rs.getInt("id_cliente"));
			eCliente.setRazonSocial(rs.getString("razon_social_cliente"));
			eCliente.setNombreCorto(rs.getString("nombre_corto_cliente"));
			eOperacion.setCliente(eCliente);
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eOperacion;
	}
}