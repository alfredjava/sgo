package sgo.datos;

import java.sql.SQLException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import sgo.entidad.DiaOperativo;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta; 
import sgo.utils.DatabaseConnector;

/**
 * Tets diaOperativoDao.java
 * 
 * @author I.B.M. DEL PERÚ - knavarro
 * @since 31/XIII/2015
 */

public class TestDiaOperativoDao extends TestCase {

	@Autowired
	private DiaOperativoDao diaOperativoDao;
	private DatabaseConnector databaseConnector;

	/**
	 * Este método se ejecuta antes de todos los test.
	 * Iniciamos una conexion a base de datos antes de cada test.
	 */
	protected void setUp() throws Exception {
		DatabaseConnector databaseConnector = new DatabaseConnector();
		this.diaOperativoDao = new DiaOperativoDao();
		this.diaOperativoDao.setDataSource(new SingleConnectionDataSource(databaseConnector.connection(), false));
	}

	/**
	 * Este método se ejecuta después de todos los test.
	 * Cerramos la conexión después de terminar cada test.
	 */
	protected void tearDown() throws SQLException {
		this.diaOperativoDao.getDataSource().getConnection().close();
	}

	/**
	 * Caso de prueba para la busqueda de los registros de Dia Operativo.
	 * @see diaOperativoDao.recuperarRegistros(argumentosListar);
	 */
	@Test
	public void testRecuperaRegistros() throws Exception {
		System.out.println("Entra en TestRecuperaRegistros...");
		ParametrosListar argumentosListar = new ParametrosListar();
		argumentosListar.setTxtFiltro("");
		argumentosListar.setRegistrosxPagina(30);
		argumentosListar.setPaginacion(1);
		argumentosListar.setInicioPaginacion(0);
		argumentosListar.setSentidoOrdenamiento("asc");
		argumentosListar.setFiltroOperacion(29);
		

		RespuestaCompuesta listaResultado = diaOperativoDao.recuperarRegistros(argumentosListar);
		
		System.out.println("assertNotNull----> " + listaResultado.contenido.carga);
		Assert.assertNotNull(listaResultado.contenido.carga);
		
		System.out.println("assertTrue-------> " + listaResultado.contenido.carga.size());
		Assert.assertTrue(listaResultado.contenido.carga.size() > 0);

		System.out.println("---------------------------------");
	}

	/**
	 * Caso de prueba para busqueda de una registro según su Identificador.
	 * @see diaOperativoDao.recuperarRegistro(ID);
	 */
	@Test
	public void testRecuperaRegistro() throws Exception {
		System.out.println("Entra en TestRecuperaRegistro...");
		int ID = 170;

		RespuestaCompuesta listaResultado = diaOperativoDao.recuperarRegistro(ID);
		
		System.out.println("assertNotNull----> " + listaResultado.contenido.carga);
		Assert.assertNotNull(listaResultado.contenido.carga);
		
		System.out.println("assertTrue-------> " + listaResultado.estado);
		Assert.assertTrue(listaResultado.estado);
		
		System.out.println("assertNotNull----> " + listaResultado.mensaje);
		Assert.assertNotNull(listaResultado.mensaje);

		ID = -1;
		listaResultado = diaOperativoDao.recuperarRegistro(ID);
		
		System.out.println("assertNull------> " + listaResultado.valor);
		Assert.assertNull(listaResultado.valor);
		System.out.println("---------------------------------");
	}
	
	/**
	 * Caso de prueba para buscar el último día operativo de una operación.
	 * @see diaOperativoDao.recuperarUltimoDiaOperativo(int idOperacion);
	 */
	@Test
	public void testRecuperarUltimoDiaOperativo() throws Exception {
		System.out.println("Entra en testRecuperarUltimoDiaOperativo...");
		int idOperacion = 29;
		Respuesta respuesta = diaOperativoDao.recuperarUltimoDiaOperativo(idOperacion);

		System.out.println("assertNotNull----> " + respuesta.valor);
		Assert.assertNotNull(respuesta.valor);

		System.out.println("assertTrue-------> " + respuesta.estado);
		Assert.assertTrue(respuesta.estado);
		System.out.println("---------------------------------");
	}

	/**
	 * Caso de prueba para guardar un registro nuevo.
	 * @see diaOperativoDao.guardarRegistro(DiaOperativo diaOperativo);
	 */
	@Test
	public void testGuardarRegistro() throws Exception {
		System.out.println("Entra en testGuardarRegistro...");
		databaseConnector = new DatabaseConnector();

		RespuestaCompuesta respuesta = diaOperativoDao.guardarRegistro(databaseConnector.entidadParaPruebasSinIdDiaOperativo());

		System.out.println("assertNotNull----> " + respuesta.valor);
		Assert.assertNotNull(respuesta.valor);

		System.out.println("assertTrue-------> " + respuesta.estado);
		Assert.assertTrue(respuesta.estado);
		System.out.println("---------------------------------");
	}

	/**
     * Caso de prueba para actualizar una Planificación.
     * @see diaOperativoDao.actualizarRegistro(Planificacion planificacion)
     */
	@Test
	public void testActualizarRegistro() throws Exception {
		System.out.println("Entra en testActualizarRegistro...");
		databaseConnector = new DatabaseConnector();
		
		DiaOperativo entidad = databaseConnector.entidadParaPruebasSinIdDiaOperativo();
		entidad.setId(18);

		RespuestaCompuesta respuesta = diaOperativoDao.actualizarRegistro(entidad);

		System.out.println("assertTrue-------> " + respuesta.estado);
		Assert.assertTrue(respuesta.estado);
		System.out.println("---------------------------------");
	}

	/**
     * Caso de prueba para eliminar un registro.
     * @see diaOperativoDao.eliminarRegistro(int idRegistro);
     */
	@Test
	public void testEliminarRegistro() throws Exception {
		System.out.println("Entra en testEliminarRegistro...");
		int ID = 19;
		RespuestaCompuesta respuesta = diaOperativoDao.eliminarRegistro(ID);

		System.out.println("assertTrue-------> " + respuesta.estado);
		Assert.assertTrue(respuesta.estado);
		
		ID = -1;
		respuesta = diaOperativoDao.eliminarRegistro(ID);

		System.out.println("---------------------------------");
	}

	/**
     * Caso de prueba para actualizar el estado de la entidad.
     * @see diaOperativoDao.ActualizarEstadoRegistro(diaOperativo);
     */
	@Test
	public void testActualizarEstadoRegistro() throws Exception {
		System.out.println("Entra en testActualizarEstadoRegistro...");
		databaseConnector = new DatabaseConnector();
		
		DiaOperativo entidad = databaseConnector.entidadParaPruebasSinIdDiaOperativo();
		entidad.setId(18);
		
		RespuestaCompuesta respuesta = diaOperativoDao.ActualizarEstadoRegistro(entidad);

		System.out.println("assertTrue-------> " + respuesta.estado);
		Assert.assertTrue(respuesta.estado);

		System.out.println("---------------------------------");
	}

	/**
     * Caso de prueba para recuperar la fecha actual en el servidor.
     * @see diaOperativoDao.recuperarFechaActual() 
     */
	@Test
	public void testRecuperarFechaActual() throws Exception {
		System.out.println("Entra en testRecuperarFechaActual...");
		Respuesta respuesta = diaOperativoDao.recuperarFechaActual();

		System.out.println("assertNotNull----> " + respuesta.valor);
		Assert.assertNotNull(respuesta.valor);

		System.out.println("assertTrue-------> " + respuesta.estado);
		Assert.assertTrue(respuesta.estado);
		System.out.println("---------------------------------");
	}

}
