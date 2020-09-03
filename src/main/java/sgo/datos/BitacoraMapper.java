package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Bitacora;
public class BitacoraMapper implements RowMapper<Bitacora>{
	public Bitacora mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Bitacora eBitacora = null;
		try {
			eBitacora = new Bitacora();
			eBitacora.setId(rs.getInt("id_bitacora"));
			eBitacora.setUsuario(rs.getString("usuario"));
			eBitacora.setAccion(rs.getString("accion"));
			eBitacora.setTabla(rs.getString("tabla"));
			
			String validarContenido = rs.getString("contenido");
			
			if(validarContenido.isEmpty()){
				eBitacora.setContenido(" ");
			}
			else{
				eBitacora.setContenido(rs.getString("contenido"));
			}
			
			
			eBitacora.setRealizadoEl(rs.getLong("realizado_el"));
			eBitacora.setRealizadoPor(rs.getInt("realizado_por"));
			eBitacora.setIdentificador(rs.getString("identificador"));
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eBitacora;
	}
}