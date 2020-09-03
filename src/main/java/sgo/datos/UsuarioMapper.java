package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Rol;
import sgo.entidad.Operacion;
import sgo.entidad.Usuario;
public class UsuarioMapper implements RowMapper<Usuario>{
 	
	/*
	 * @author Rafael Reyna Camones
	 *  @param rs
	 * 
	 */
	public Usuario mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Usuario mUsuario = null;
		try {
		mUsuario = new Usuario();
		mUsuario.setId(rs.getInt("id_usuario"));
		mUsuario.setNombre(rs.getString("nombre"));
		mUsuario.setClave(rs.getString("clave"));
		mUsuario.setIdentidad(rs.getString("identidad"));
		mUsuario.setZonaHoraria(rs.getString("zona_horaria"));
		mUsuario.setEstado(rs.getInt("estado"));
		mUsuario.setEmail(rs.getString("email"));
		mUsuario.setTipo(rs.getInt("tipo"));
		mUsuario.setId_rol(rs.getInt("id_rol"));
		mUsuario.setId_operacion(rs.getInt("id_operacion"));
		mUsuario.setIdCliente(rs.getInt("id_cliente"));
		
		Rol mRol = new Rol();	
			mRol.setId(rs.getInt("id_rol"));
			mRol.setNombre(rs.getString("nombre_rol"));
			mUsuario.setRol(mRol);
			
		Operacion mOperacion = new Operacion();	
			mOperacion.setId(rs.getInt("id_operacion"));
			mOperacion.setNombre(rs.getString("nombre_operacion"));
			mOperacion.setReferenciaPlantaRecepcion(rs.getString("referencia_planta_recepcion"));
			mOperacion.setReferenciaDetinatarioMercaderia(rs.getString("referencia_destinatario_mercaderia"));
			mOperacion.setVolumenPromedioCisterna(rs.getFloat("volumen_promedio_cisterna"));
			mUsuario.setOperacion(mOperacion);
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return mUsuario;
	}
}