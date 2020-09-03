package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Autorizacion;
import sgo.entidad.AutorizacionUsuario;
import sgo.entidad.Usuario;
public class AutorizacionUsuarioMapper implements RowMapper<AutorizacionUsuario>{
	public AutorizacionUsuario mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		AutorizacionUsuario eAutorizacionUsuario = null;
		Usuario eUsuario = null;
		Autorizacion eAutorizacion = null;
		try {
			eAutorizacionUsuario = new AutorizacionUsuario();
			eAutorizacionUsuario.setId(rs.getInt("id_ausuario"));
			eAutorizacionUsuario.setIdUsuario(rs.getInt("id_usuario"));
			eAutorizacionUsuario.setIdAutorizacion(rs.getInt("id_autorizacion"));
			eAutorizacionUsuario.setCodigoAutorizacion(rs.getString("codigo_autorizacion"));
			eAutorizacionUsuario.setIdentidad(rs.getString("identidad"));
			
			eAutorizacionUsuario.setVigenteDesde(rs.getDate("vigente_desde"));
			eAutorizacionUsuario.setVigenteHasta(rs.getDate("vigente_hasta"));
			eAutorizacionUsuario.setEstado(rs.getInt("estado"));
			
			eUsuario = new Usuario();
			eUsuario.setId(rs.getInt("id_ausuario"));
			eUsuario.setIdentidad(rs.getString("identidad"));
			eUsuario.setNombre(rs.getString("nombre"));
			eAutorizacionUsuario.seteUsuario(eUsuario);
			
			eAutorizacion = new Autorizacion();
			eAutorizacion.setId(rs.getInt("id_autorizacion"));
			eAutorizacion.setNombre(rs.getString("nombre_autorizacion"));
			eAutorizacion.setCodigoInterno(rs.getString("codigo_interno"));
			eAutorizacion.setEstado(rs.getInt("estado_autorizacion"));
			eAutorizacionUsuario.seteAutorizacion(eAutorizacion);
			
			//Parametros de auditoria
			eAutorizacionUsuario.setCreadoPor(rs.getInt("creado_por"));
			eAutorizacionUsuario.setCreadoEl(rs.getLong("creado_el"));
			eAutorizacionUsuario.setActualizadoPor(rs.getInt("actualizado_por"));
			eAutorizacionUsuario.setActualizadoEl(rs.getLong("actualizado_el"));
			eAutorizacionUsuario.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eAutorizacionUsuario.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eAutorizacionUsuario.setIpCreacion(rs.getString("ip_creacion"));
			eAutorizacionUsuario.setIpActualizacion(rs.getString("ip_actualizacion"));

		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eAutorizacionUsuario;
	}
}