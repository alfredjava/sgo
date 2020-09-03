package sgo.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;
import sgo.datos.BitacoraDao;
import sgo.datos.CisternaDao;
import sgo.datos.ClienteDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.ProductoDao;
import sgo.datos.OperacionDao;
import sgo.datos.PlanificacionDao;
import sgo.datos.EnlaceDao;
import sgo.entidad.Bitacora;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Operacion;
import sgo.entidad.Cliente;
import sgo.entidad.Producto;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;

@Controller
public class CierreControlador {
	@Autowired
	private MessageSource gestorDiccionario;//Gestor del diccionario de mensajes para la internacionalizacion
	@Autowired
	private BitacoraDao dBitacora; //Clase para registrar en la bitacora (auditoria por accion)
	@Autowired
	private EnlaceDao dEnlace;
	@Autowired
	private MenuGestor menu;
	@Autowired
	private DiaOperativoDao dDiaOperativo;
	@Autowired
	private PlanificacionDao dPlanificacion;
	@Autowired
	private ClienteDao dCliente;
	@Autowired
	private OperacionDao dOperacion;
	@Autowired
	private ProductoDao dProducto;
	@Autowired
	private DiaOperativoControlador DiaOperativoControlador;

	//
	private DataSourceTransactionManager transaccion;//Gestor de la transaccion
	//urls generales
	private static final String URL_GESTION_COMPLETA="/admin/cierre";
	private static final String URL_GESTION_RELATIVA="/cierre";
//	private static final String URL_GUARDAR_COMPLETA="/admin/cierre/crear";
//	private static final String URL_GUARDAR_RELATIVA="/cierre/crear";
	private static final String URL_LISTAR_COMPLETA="/admin/cierre/listar";
	private static final String URL_LISTAR_RELATIVA="/cierre/listar";
	private static final String URL_ACTUALIZAR_ESTADO_COMPLETA="/admin/cierre/actualizarEstado";
	private static final String URL_ACTUALIZAR_ESTADO_RELATIVA="/cierre/actualizarEstado";
//	private static final String URL_ACTUALIZAR_COMPLETA="/admin/cierre/actualizar";
//	private static final String URL_ACTUALIZAR_RELATIVA="/cierre/actualizar";
//	private static final String URL_RECUPERAR_COMPLETA="/admin/cierre/recuperar";
//	private static final String URL_RECUPERAR_RELATIVA="/cierre/recuperar";
	
	@SuppressWarnings("unchecked")
	@RequestMapping(URL_GESTION_RELATIVA)
	public ModelAndView mostrarFormulario( Locale locale){
		ModelAndView vista =null;
		AuthenticatedUserDetails principal = null;
		ArrayList<Enlace> listaEnlaces = null;
		ArrayList<Cliente> listaClientes= null;
		ArrayList<Operacion> listaOperaciones= null;
		ArrayList<Producto> listaProductos= null;
		
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros= null;
		
		try {
			principal = this.getCurrentUser();
			respuesta = menu.Generar(principal.getRol().getId(),URL_GESTION_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado",null,locale));
			}
			listaEnlaces = (ArrayList<Enlace>) respuesta.contenido.carga;
			
			parametros = new ParametrosListar();
			parametros.setPaginacion(Constante.SIN_PAGINACION);			
			respuesta = dCliente.recuperarRegistros(parametros);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
			}
			//listaClientes = (ArrayList<Cliente>) respuesta.contenido.carga;
			
			parametros = new ParametrosListar();
			parametros.setPaginacion(Constante.SIN_PAGINACION);			
			//respuesta = dOperacion.recuperarRegistrosxCliente(2);			
			respuesta =dOperacion.recuperarRegistros(parametros);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
			}
			listaOperaciones = (ArrayList<Operacion>) respuesta.contenido.carga;
			
			parametros = new ParametrosListar();
			parametros.setPaginacion(Constante.SIN_PAGINACION);			
			respuesta = dProducto.recuperarRegistros(parametros);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
			}
			listaProductos = (ArrayList<Producto>) respuesta.contenido.carga;

			vista = new ModelAndView("plantilla");
			vista.addObject("vistaJSP","cierre/cierre.jsp");
			vista.addObject("vistaJS","cierre/cierre.js");
			vista.addObject("identidadUsuario",principal.getIdentidad());
			vista.addObject("menu",listaEnlaces);

			vista.addObject("clientes",listaClientes);
			vista.addObject("operaciones",listaOperaciones);
			vista.addObject("productos",listaProductos);
			vista.addObject("fechaActual", dDiaOperativo.recuperarFechaActual().valor);
		} catch(Exception ex){

		}
		return vista;
	}
	
	@RequestMapping(value = URL_LISTAR_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale){
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros= null;
		AuthenticatedUserDetails principal = null;
		String mensajeRespuesta="";
		try {
			//Recuperar el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_LISTAR_COMPLETA);
			if (respuesta.estado==false){
				mensajeRespuesta = gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale);
				throw new Exception(mensajeRespuesta);
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				mensajeRespuesta = gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale);
				throw new Exception(mensajeRespuesta);
    		}
			//Recuperar parametros
			 parametros = new ParametrosListar();
			if (httpRequest.getParameter("paginacion") != null) {
				parametros.setPaginacion(Integer.parseInt( httpRequest.getParameter("paginacion")));
			}
      
			if (httpRequest.getParameter("registrosxPagina") != null) {
				parametros.setRegistrosxPagina(Integer.parseInt(httpRequest.getParameter("registrosxPagina")));
			}
			
			if (httpRequest.getParameter("inicioPagina") != null) {
				parametros.setInicioPaginacion(Integer.parseInt(httpRequest.getParameter("inicioPagina")));
			}
			
			if (httpRequest.getParameter("campoOrdenamiento") != null) {
				parametros.setCampoOrdenamiento((httpRequest.getParameter("campoOrdenamiento")));
			}
			
			if (httpRequest.getParameter("sentidoOrdenamiento") != null) {
				parametros.setSentidoOrdenamiento((httpRequest.getParameter("sentidoOrdenamiento")));
			}
			
			if (httpRequest.getParameter("valorBuscado") != null) {
				parametros.setValorBuscado((httpRequest.getParameter("valorBuscado")));
			}

			if (httpRequest.getParameter("filtroOperacion") != null) {
				parametros.setFiltroOperacion(Integer.parseInt(httpRequest.getParameter("filtroOperacion")));
			}

			if (httpRequest.getParameter("filtroFechaPlanificada") != null) {
				parametros.setFiltroFechaPlanificada((httpRequest.getParameter("filtroFechaPlanificada")));
			}
			
			if (httpRequest.getParameter("filtroFechaInicio") != null) {
		       parametros.setFiltroFechaInicio((httpRequest.getParameter("filtroFechaInicio")));
		     }

		     if (httpRequest.getParameter("filtroFechaFinal") != null) {
		       parametros.setFiltroFechaFinal((httpRequest.getParameter("filtroFechaFinal")));
		     }
			
			//Recuperar registros
			//Recuperamos los dias operativos
			respuesta = dDiaOperativo.recuperarRegistrosParaCierre(parametros);
			respuesta.mensaje= gestorDiccionario.getMessage("sgo.listarExitoso",null,locale);
		} catch(Exception ex){
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}	
	
	@RequestMapping(value = URL_ACTUALIZAR_ESTADO_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta actualizarEstadoRegistro(@RequestBody DiaOperativo entidad, HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dDiaOperativo.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser();
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_ESTADO_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
    		}			
			//Auditoria local (En el mismo registro)
			direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");  
			if (direccionIp == null) {  
				direccionIp = peticionHttp.getRemoteAddr();  
			}
        	entidad.setActualizadoEl(Calendar.getInstance().getTime().getTime());
        	entidad.setActualizadoPor(principal.getID()); 
        	entidad.setIpActualizacion(direccionIp);
            respuesta= dDiaOperativo.ActualizarEstadoRegistro(entidad);
            if (respuesta.estado==false){          	
            	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
            }
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper();
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_ACTUALIZAR_ESTADO_COMPLETA);
            eBitacora.setTabla(CisternaDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(String.valueOf( entidad.getId()));
            eBitacora.setContenido( mapper.writeValueAsString(entidad));
            eBitacora.setRealizadoEl(entidad.getActualizadoEl());
            eBitacora.setRealizadoPor(entidad.getActualizadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }  
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {  entidad.getFechaActualizacion().substring(0, 9),entidad.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);;
        	this.transaccion.commit(estadoTransaccion);
		} catch (Exception ex){
			ex.printStackTrace();
			this.transaccion.rollback(estadoTransaccion);
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}

	private  AuthenticatedUserDetails getCurrentUser(){
		return  (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}


	
}
