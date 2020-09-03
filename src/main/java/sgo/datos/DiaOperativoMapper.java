package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Operacion;
import sgo.entidad.Cliente;
public class DiaOperativoMapper implements RowMapper<DiaOperativo>{
	public DiaOperativo mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		DiaOperativo eDiaOperativo = null;
		Operacion eOperacion = null;
		Cliente eCliente = null;
		try {
			eDiaOperativo = new DiaOperativo();
			eDiaOperativo.setId(rs.getInt("id_doperativo"));
			eDiaOperativo.setFechaOperativa(rs.getDate("fecha_operativa"));
			eDiaOperativo.setIdOperacion(rs.getInt("id_operacion"));
			eDiaOperativo.setEstado(rs.getInt("estado"));
			eDiaOperativo.setTotalCisternas(rs.getInt("total_cant_cisternas"));
			eDiaOperativo.setTotalVolumenSolicitado(rs.getInt("tot_vol_solicitado"));
			eDiaOperativo.setTotalVolumenPropuesto(rs.getInt("tot_vol_propuesto"));
			
			eOperacion = new Operacion();
			eOperacion.setId(rs.getInt("id_operacion"));
			eOperacion.setNombre(rs.getString("nombre"));
			eOperacion.setIdCliente(rs.getInt("id_cliente"));
			eOperacion.setVolumenPromedioCisterna(rs.getFloat("volumen_promedio_cisterna"));;

				eCliente = new Cliente();
				eCliente.setId(rs.getInt("id_cliente"));
				eCliente.setRazonSocial(rs.getString("razon_social"));
				eOperacion.setCliente(eCliente);

			eDiaOperativo.setOperacion(eOperacion);

			//Parametros de auditoria
			eDiaOperativo.setCreadoPor(rs.getInt("creado_por"));
			eDiaOperativo.setCreadoEl(rs.getLong("creado_el"));
			eDiaOperativo.setActualizadoPor(rs.getInt("actualizado_por"));
			eDiaOperativo.setActualizadoEl(rs.getLong("actualizado_el"));
			eDiaOperativo.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
			eDiaOperativo.setUsuarioCreacion(rs.getString("usuario_creacion"));
			eDiaOperativo.setIpCreacion(rs.getString("ip_creacion"));
			eDiaOperativo.setIpActualizacion(rs.getString("ip_actualizacion"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eDiaOperativo;
	}
}