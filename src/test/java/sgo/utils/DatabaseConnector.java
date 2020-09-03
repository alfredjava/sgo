package sgo.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Properties;
//import org.junit.Test;
//import static org.junit.Assert.*;

import sgo.entidad.DiaOperativo;
import sgo.entidad.Planificacion;
import sgo.utilidades.Utilidades;

public class DatabaseConnector {

	// @Test
	public Connection connection() throws Exception {
		Class.forName("org.postgresql.Driver").newInstance();

		final String url = "jdbc:postgresql://10.1.18.42:5432/sgo";
		final Properties datosConexion = new Properties();
		datosConexion.setProperty("user", "postgres");
		datosConexion.setProperty("password", "p3tr0m1n");

		Connection connection = DriverManager.getConnection(url, datosConexion);
		// assertFalse(connection.isClosed());
		// System.out.println(connection);
		// assertTrue("se conecto correctamente", true);

		return connection;

		// connection.close();
	}

	public Planificacion entidadParaPruebasSinIdPlanificacion() {
		Planificacion entidad = new Planificacion();
		entidad.setIdDoperativo(191);
		entidad.setIdProducto(16);
		entidad.setVolumenPropuesto(0);
		entidad.setCantidadCisternas(30);
		entidad.setVolumenSolicitado(10);
		// para bitacora
		entidad.setActualizadoPor(2); // usuario PEDRO
		entidad.setActualizadoEl(Calendar.getInstance().getTime().getTime());
		entidad.setIpActualizacion("0:0:0:0:0:0:0:1");

		return entidad;
	}
	
	public DiaOperativo entidadParaPruebasSinIdDiaOperativo() {
		DiaOperativo entidad = new DiaOperativo();
		entidad.setEstado(1);
		entidad.setIdOperacion(29);
		entidad.setFechaOperativa(Utilidades.convierteStringADate("30/08/2015"));
		// para bitacora
		entidad.setActualizadoPor(2); // usuario PEDRO
		entidad.setActualizadoEl(Calendar.getInstance().getTime().getTime());
		entidad.setIpActualizacion("0:0:0:0:0:0:0:1");
		entidad.setCreadoEl(Calendar.getInstance().getTime().getTime());
		entidad.setCreadoPor(2);
		entidad.setIpCreacion("0:0:0:0:0:0:0:1");

		return entidad;
	}
}