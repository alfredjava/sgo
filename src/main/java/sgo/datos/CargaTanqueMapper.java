package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.CargaTanque;
import sgo.entidad.Estacion;
import sgo.entidad.Tanque;
public class CargaTanqueMapper implements RowMapper<CargaTanque>{
  public CargaTanque mapRow(ResultSet rs, int arg1) throws SQLException 
  {
    CargaTanque eCargaTanque = null;
    Tanque eTanque = null;
    Estacion eEstacion = null;
    try {
      eCargaTanque = new CargaTanque();
      eCargaTanque.setId(rs.getInt("id_ctanque"));
      eCargaTanque.setIdTanque(rs.getInt("id_tanque"));
      eCargaTanque.setFechaHoraInicial(rs.getDate("fecha_hora_inicio"));
      eCargaTanque.setFechaHoraFinal(rs.getDate("fecha_hora_fin"));
      eCargaTanque.setAlturaInicial(rs.getInt("altura_inicial_producto"));
      eCargaTanque.setAlturaFinal(rs.getInt("altura_final_producto"));
      eCargaTanque.setTemperaturaInicialCentro(rs.getFloat("temperatura_inicial_centro"));
      eCargaTanque.setTemperaturaFinalCentro(rs.getFloat("temperatura_final_centro"));
      eCargaTanque.setTemperaturaIniciaProbeta(rs.getFloat("temperatura_inicial_probeta"));
      eCargaTanque.setTemperaturaFinalProbeta(rs.getFloat("temperatura_final_probeta"));
      eCargaTanque.setApiObservadoInicial(rs.getFloat("api_observado_inicial"));
      eCargaTanque.setApiObservadoFinal(rs.getFloat("api_observado_final"));
      eCargaTanque.setFactorCorreccionInicial(rs.getFloat("factor_correccion_inicial"));
      eCargaTanque.setFactorCorreccionFinal(rs.getFloat("factor_correccion_final"));
      eCargaTanque.setVolumenObservadoInicial(rs.getFloat("volumen_observado_inicial"));
      eCargaTanque.setVolumenObservadoFinal(rs.getFloat("volumen_observado_final"));
      eCargaTanque.setVolumenCorregidoInicial(rs.getFloat("volumen_corregido_inicial"));
      eCargaTanque.setVolumenCorregidoFinal(rs.getFloat("volumen_corregido_final"));
      //Parametros de auditoria
      eCargaTanque.setCreadoPor(rs.getInt("creado_por"));
      eCargaTanque.setCreadoEl(rs.getLong("creado_el"));
      eCargaTanque.setActualizadoPor(rs.getInt("actualizado_por"));
      eCargaTanque.setActualizadoEl(rs.getLong("actualizado_el"));
      eCargaTanque.setUsuarioActualizacion(rs.getString("usuario_actualizacion"));
      eCargaTanque.setUsuarioCreacion(rs.getString("usuario_creacion"));
      //
      eEstacion = new Estacion();
      eEstacion.setId(eCargaTanque.getIdEstacion());
      eEstacion.setNombre(rs.getString("nombre_estacion"));
      eCargaTanque.setEstacion(eEstacion);
      
      eTanque=  new Tanque();
      eTanque.setId(eCargaTanque.getIdTanque());
      eTanque.setDescripcion(rs.getString("nombre_tanque"));
      eCargaTanque.setTanque(eTanque);
      
    } catch(Exception ex){
      ex.printStackTrace();
    }
    return eCargaTanque;
  }
}