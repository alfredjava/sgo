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
import sgo.datos.TractoDao;
import sgo.datos.EnlaceDao;
import sgo.entidad.Bitacora;
import sgo.entidad.Cisterna;
import sgo.entidad.Tracto;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;

@Controller
public class TractoControlador {
	@Autowired
	private MessageSource gestorDiccionario;//Gestor del diccionario de mensajes para la internacionalizacion
	@Autowired
	private BitacoraDao dBitacora; //Clase para registrar en la bitacora (auditoria por accion)
	@Autowired
	private EnlaceDao dEnlace;
	@Autowired
	private MenuGestor menu;
	@Autowired
	private TractoDao dTracto;
	//
	private DataSourceTransactionManager transaccion;//Gestor de la transaccion
	//urls generales
	private static final String URL_GESTION_COMPLETA="/admin/tracto";
	private static final String URL_GESTION_RELATIVA="/tracto";
	private static final String URL_GUARDAR_COMPLETA="/admin/tracto/crear";
	private static final String URL_GUARDAR_RELATIVA="/tracto/crear";
	private static final String URL_LISTAR_COMPLETA="/admin/tracto/listar";
	private static final String URL_LISTAR_RELATIVA="/tracto/listar";
	private static final String URL_ACTUALIZAR_COMPLETA="/admin/tracto/actualizar";
	private static final String URL_ACTUALIZAR_RELATIVA="/tracto/actualizar";
	private static final String URL_RECUPERAR_COMPLETA="/admin/tracto/recuperar";
	private static final String URL_RECUPERAR_RELATIVA="/tracto/recuperar";
	private static final String URL_ACTUALIZAR_ESTADO_COMPLETA="/admin/tracto/actualizarEstado";
	private static final String URL_ACTUALIZAR_ESTADO_RELATIVA="/tracto/actualizarEstado";
	
	@RequestMapping(URL_GESTION_RELATIVA)
	public ModelAndView mostrarFormulario( Locale locale){
		ModelAndView vista =null;
		AuthenticatedUserDetails principal = null;
		ArrayList<Enlace> listaEnlaces = null;
		RespuestaCompuesta respuesta = null;
		try {
			principal = this.getCurrentUser();
			respuesta = menu.Generar(principal.getRol().getId(),URL_GESTION_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado",null,locale));
			}
			listaEnlaces = (ArrayList<Enlace>) respuesta.contenido.carga;
			vista = new ModelAndView("plantilla");
			vista.addObject("vistaJSP","mantenimiento/tracto.jsp");
			vista.addObject("vistaJS","mantenimiento/tracto.js");
			vista.addObject("identidadUsuario",principal.getIdentidad());
			vista.addObject("menu",listaEnlaces);
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
				parametros.setRegistrosxPagina(Integer.parseInt( httpRequest.getParameter("registrosxPagina")));
			}
			
			if (httpRequest.getParameter("inicioPagina") != null) {
				parametros.setInicioPaginacion(Integer.parseInt( httpRequest.getParameter("inicioPagina")));
			}
			
			if (httpRequest.getParameter("campoOrdenamiento") != null) {
				parametros.setCampoOrdenamiento(( httpRequest.getParameter("campoOrdenamiento")));
			}
			
			if (httpRequest.getParameter("sentidoOrdenamiento") != null) {
				parametros.setSentidoOrdenamiento(( httpRequest.getParameter("sentidoOrdenamiento")));
			}
			if (httpRequest.getParameter("valorBuscado") != null) {
				parametros.setValorBuscado(( httpRequest.getParameter("valorBuscado")));
			}
			if (httpRequest.getParameter("txtFiltro") != null) {
				parametros.setTxtFiltro((httpRequest.getParameter("txtFiltro")));
			}
			parametros.setFiltroEstado(Constante.FILTRO_TODOS);
			if (httpRequest.getParameter("filtroEstado") != null) {
				parametros.setFiltroEstado(Integer.parseInt(httpRequest.getParameter("filtroEstado")));
			}
			//Recuperar registros
			respuesta = dTracto.recuperarRegistros(parametros);
			respuesta.mensaje= gestorDiccionario.getMessage("sgo.listarExitoso",null,locale);
		} catch(Exception ex){
			ex.printStackTrace();
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}

	@RequestMapping(value = URL_RECUPERAR_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperaRegistro(int ID,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		try {			
			//Recupera el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_RECUPERAR_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
    		}
			//Recuperar el registro
        	respuesta= dTracto.recuperarRegistro(ID);
        	//Verifica el resultado de la accion
            if (respuesta.estado==false){        	
            	throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido",null,locale));
            }
         	respuesta.mensaje=gestorDiccionario.getMessage("sgo.recuperarExitoso",null,locale);
		} catch (Exception ex){
			ex.printStackTrace();
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}
	
	@RequestMapping(value = URL_GUARDAR_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta guardarRegistro(@RequestBody Tracto eTracto,HttpServletRequest peticionHttp,Locale locale){		 
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		Bitacora eBitacora= null;
		String ContenidoAuditoria ="";
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		String direccionIp="";
		String ClaveGenerada="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dTracto.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
    		}			
			//Actualiza los datos de auditoria local
			direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");  
			if (direccionIp == null) {  
				direccionIp = peticionHttp.getRemoteAddr();  
			}
        	eTracto.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eTracto.setActualizadoPor(principal.getID()); 
           	eTracto.setCreadoEl(Calendar.getInstance().getTime().getTime());
            eTracto.setCreadoPor(principal.getID());
            eTracto.setIpActualizacion(direccionIp);
            eTracto.setIpCreacion(direccionIp);
            respuesta= dTracto.guardarRegistro(eTracto);
            //Verifica si la accion se ejecuto de forma satisfactoria
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }
            ClaveGenerada = respuesta.valor;
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
            ContenidoAuditoria =  mapper.writeValueAsString(eTracto);
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_GUARDAR_COMPLETA);
            eBitacora.setTabla(TractoDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(ClaveGenerada);
            eBitacora.setContenido(ContenidoAuditoria);
            eBitacora.setRealizadoEl(eTracto.getCreadoEl());
            eBitacora.setRealizadoPor(eTracto.getCreadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }           
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eTracto.getFechaCreacion().substring(0, 9),eTracto.getFechaCreacion().substring(10),principal.getIdentidad() },locale);
        	this.transaccion.commit(estadoTransaccion);
		} catch (Exception ex){
			this.transaccion.rollback(estadoTransaccion);
			ex.printStackTrace();
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}
	
	@RequestMapping(value = URL_ACTUALIZAR_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta actualizarRegistro(@RequestBody Tracto eTracto,HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dTracto.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser();
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_COMPLETA);
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
        	eTracto.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eTracto.setActualizadoPor(principal.getID()); 
            eTracto.setIpActualizacion(direccionIp);
            respuesta= dTracto.actualizarRegistro(eTracto);
            if (respuesta.estado==false){          	
            	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
            }
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper();
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
            eBitacora.setTabla(TractoDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(String.valueOf( eTracto.getId()));
            eBitacora.setContenido( mapper.writeValueAsString(eTracto));
            eBitacora.setRealizadoEl(eTracto.getActualizadoEl());
            eBitacora.setRealizadoPor(eTracto.getActualizadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }  
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {  eTracto.getFechaActualizacion().substring(0, 9),eTracto.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);;
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
	
	@RequestMapping(value = URL_ACTUALIZAR_ESTADO_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta actualizarEstadoRegistro(@RequestBody Tracto eTracto,HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dTracto.getDataSource());
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
        	eTracto.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eTracto.setActualizadoPor(principal.getID()); 
            eTracto.setIpActualizacion(direccionIp);
            respuesta= dTracto.ActualizarEstadoRegistro(eTracto);
            if (respuesta.estado==false){          	
            	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
            }
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper();
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_ACTUALIZAR_ESTADO_COMPLETA);
            eBitacora.setTabla(TractoDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(String.valueOf( eTracto.getId()));
            eBitacora.setContenido( mapper.writeValueAsString(eTracto));
            eBitacora.setRealizadoEl(eTracto.getActualizadoEl());
            eBitacora.setRealizadoPor(eTracto.getActualizadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }  
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {  eTracto.getFechaActualizacion().substring(0, 9),eTracto.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);;
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
}