package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Cisterna;
import sgo.entidad.Tracto;
import sgo.entidad.Transportista;
public class CisternaMapper implements RowMapper<Cisterna>{
	public Cisterna mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Cisterna eCisterna = null;
		Tracto eTracto = null;
		Transportista eTransportista = null;
		try {
			eCisterna = new Cisterna();
			eCisterna.setId(rs.getInt("id_cisterna"));
			eCisterna.setPlaca(rs.getString("placa"));
			eCisterna.setIdTracto(rs.getInt("id_tracto"));
			eCisterna.setIdTransportista(rs.getInt("id_transportista"));
			eCisterna.setEstado(rs.getInt("estado"));
			eCisterna.setSincronizadoEl(rs.getString("sincronizado_el"));
			eCisterna.setFechaReferencia(rs.getString("fecha_referencia"));
			eCisterna.setCodigoReferencia(rs.getString("codigo_referencia"));
			eCisterna.setPlacaTracto(rs.getString("placa_tracto"));
			eCisterna.setPlacaCisternaTracto(rs.getString("cisternatracto"));
			eCisterna.setCantidadCompartimentos(rs.getInt("cantidad_compartimentos"));
			
			eTracto = new Tracto();
			eTracto.setId(rs.getInt("id_tracto"));
			eTracto.setPlaca(rs.getString("placa_tracto"));
			eCisterna.setTracto(eTracto);
			
			eTransportista = new Transportista();
			eTransportista.setId(rs.getInt("id_transportista"));
			eTransportista.setRazonSocial(rs.getString("razon_social"));
			eCisterna.setTransportista(eTransportista);
			
			//Parametros de auditoria
			eCisterna.setCreadoPor(rs.getInt("creado_por"));
			eCisterna.setCreadoEl(rs.getLong("creado_el"));
			eCisterna.setActualizadoPor(rs.getInt("actualizado_por"));
			eCisterna.setActualizadoEl(rs.getLong("actualizado_el"));
			eCisterna.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eCisterna.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eCisterna.setIpCreacion(rs.getString("ip_creacion"));
			eCisterna.setIpActualizacion(rs.getString("ip_actualizacion"));
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eCisterna;
	}
}