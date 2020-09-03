package sgo.datos;

import java.sql.Connection;
import java.sql.SQLException;

import sgo.entidad.Bitacora;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Usuario;
import sgo.utils.DatabaseConnector;

import junit.framework.TestCase;
import junit.framework.Assert;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Tets BitacoraDao.java
 * 
 * @author I.B.M. DEL PERÚ - knavarro
 * @since 28/XIII/2015
 */
public class TestBitacoraDao extends TestCase {

	@Autowired
	private BitacoraDao bitacoraDao;

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	/**
	 * Este método se ejecuta antes de todos los test.
	 * Iniciamos una conexion a base de datos antes de cada te
	 */
	protected void setUp() throws Exception {
		DatabaseConnector conexionSGO = new DatabaseConnector();
		this.bitacoraDao = new BitacoraDao();
		this.bitacoraDao.setDataSource(new SingleConnectionDataSource(conexionSGO.connection(), false));
	}

	/**
	 * Este método se ejecuta después de todos los test.
	 * Cerramos la conexión después de terminar cada test.
	 */
	protected void tearDown() throws SQLException {
		this.bitacoraDao.getDataSource().getConnection().close();
	}

	/**
	 * Caso de prueba para la busqueda de los registros de Bitacora.
	 * 
	 * @see bitacoraDao.recuperarRegistros(argumentosListar);
	 */
	@Test
	public void testRecuperaRegistros() throws Exception {
		System.out.println("Entra en TestRecuperaRegistros...");
		ParametrosListar argumentosListar = new ParametrosListar();
		argumentosListar.setTxtFiltro("");
		argumentosListar.setFiltroUsuario("todos");
		argumentosListar.setFiltroTabla("todos");
		argumentosListar.setRegistrosxPagina(15);
		argumentosListar.setPaginacion(1);
		argumentosListar.setInicioPaginacion(0);
		argumentosListar.setSentidoOrdenamiento("asc");

		RespuestaCompuesta listaResultado = bitacoraDao.recuperarRegistros(argumentosListar);
		Assert.assertNotNull(listaResultado.contenido.carga);
		Assert.assertTrue(listaResultado.contenido.carga.size() > 0);
	}

	/**
	 * Caso de prueba para busqueda de una Bitacora según su Identificador.
	 * 
	 * @see bitacoraDao.recuperarRegistro(ID);
	 */
	@Test
	public void testRecuperaRegistro() throws Exception {
		System.out.println("Entra en TestRecuperaRegistro...");
		int ID = 1;

		RespuestaCompuesta listaResultado = bitacoraDao.recuperarRegistro(ID);
		Assert.assertNotNull(listaResultado.contenido.carga);
		Assert.assertTrue(listaResultado.contenido.carga.size() > 0);

		ID = -1;
		listaResultado = bitacoraDao.recuperarRegistro(ID);
		Assert.assertNull(listaResultado.valor);
		Assert.assertFalse(listaResultado.estado);
	}
	
	/**
	 * Caso de prueba para guardar una Bitacora nueva.
	 * 
	 * @see bitacoraDao.guardarRegistro(bitacora);
	 */
	@Test
	public void testGuardarRegistro() throws Exception {
		System.out.println("Entra en testGuardarRegistro...");
		
		Bitacora entidad = new Bitacora();
			Usuario usuario = new Usuario();
				usuario.setId(2);
				usuario.setNombre("pedro");
		entidad.setUsuario("pedro");
		entidad.setIdentificador("pedro");

		RespuestaCompuesta respuesta = bitacoraDao.guardarRegistro(entidad);
		Assert.assertNotNull(respuesta.valor);
		Assert.assertTrue(respuesta.estado);

	}

}
