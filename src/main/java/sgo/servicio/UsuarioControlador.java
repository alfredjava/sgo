package sgo.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sgo.datos.AutorizacionDao;
import sgo.datos.AutorizacionUsuarioDao;
import sgo.datos.BitacoraDao;
import sgo.datos.CisternaDao;
import sgo.datos.ClienteDao;
import sgo.datos.EnlaceDao;
import sgo.datos.UsuarioDao;
import sgo.entidad.Autorizacion;
import sgo.entidad.AutorizacionUsuario;
import sgo.entidad.Bitacora;
import sgo.entidad.Cisterna;
import sgo.entidad.Cliente;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Planificacion;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Usuario;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;

import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
public class UsuarioControlador {
	@Autowired
	private MessageSource gestorDiccionario;//Gestor del diccionario de mensajes para la internacionalizacion
	@Autowired
	private BitacoraDao dBitacora; //Clase para registrar en la bitacora (auditoria por accion)
	@Autowired
	private EnlaceDao dEnlace;
	@Autowired
	private MenuGestor menu;
	@Autowired
	private UsuarioDao dUsuario;
	@Autowired
	private AutorizacionUsuarioDao dAutorizacionUsuario;
	@Autowired
	private AutorizacionDao dAutorizacion;
	@Autowired
	private ClienteDao dCliente;
	//
	private DataSourceTransactionManager transaccion;//Gestor de la transaccion
	//urls generales
	private static final String URL_GESTION_COMPLETA="/admin/usuario";
	private static final String URL_GESTION_RELATIVA="/usuario";
	private static final String URL_GUARDAR_COMPLETA="/admin/usuario/crear";
	private static final String URL_GUARDAR_RELATIVA="/usuario/crear";
	private static final String URL_LISTAR_COMPLETA="/admin/usuario/listar";
	private static final String URL_LISTAR_RELATIVA="/usuario/listar";
	private static final String URL_ACTUALIZAR_COMPLETA="/admin/usuario/actualizar";
	private static final String URL_ACTUALIZAR_RELATIVA="/usuario/actualizar";
	private static final String URL_RECUPERAR_COMPLETA="/admin/usuario/recuperar";
	private static final String URL_RECUPERAR_RELATIVA="/usuario/recuperar";
	private static final String URL_ACTUALIZAR_ESTADO_COMPLETA="/admin/usuario/actualizarEstado";
	private static final String URL_ACTUALIZAR_ESTADO_RELATIVA="/usuario/actualizarEstado";
	private static final String URL_GUARDAR_AUTORIZACION_COMPLETA="/admin/usuario/crearAutorizaciones";
	private static final String URL_GUARDAR_AUTORIZACION_RELATIVA="/usuario/crearAutorizaciones";
	private static final String URL_RECUPERAR_AUTORIZACION_COMPLETA="/admin/usuario/recuperarAutorizaciones";
	private static final String URL_RECUPERAR_AUTORIZACION_RELATIVA="/usuario/recuperarAutorizaciones";
	
	@RequestMapping(URL_GESTION_RELATIVA)
	public ModelAndView mostrarFormulario( Locale locale){
		ModelAndView vista =null;
		AuthenticatedUserDetails principal = null;
		ArrayList<Enlace> listaEnlaces = null;
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros =null;
		HashMap<String,String> listaClientes = null;
		ArrayList<Cliente> listadoClientes=null;
		try {
		 listaClientes = new HashMap<String,String>();
		 listadoClientes = new ArrayList<Cliente>();
			principal = this.getCurrentUser();
			respuesta = menu.Generar(principal.getRol().getId(),URL_GESTION_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado",null,locale));
			}
			listaEnlaces = (ArrayList<Enlace>) respuesta.contenido.carga;
			vista = new ModelAndView("plantilla");
			
			parametros = new ParametrosListar();
			respuesta = dCliente.recuperarRegistros(parametros);
			if (respuesta.estado==false){
			 throw new Exception(gestorDiccionario.getMessage("sgo.noClientes",null,locale));
			}

			int numeroClientes = respuesta.contenido.carga.size();
			for (int contador=0;contador<numeroClientes;contador++){
			  Cliente tCliente = (Cliente) respuesta.contenido.carga.get(contador);
			  //listaClientes.put(String.valueOf(tCliente.getId()) , tCliente.getNombreCorto());
			  listadoClientes.add(tCliente);
			}			

			vista.addObject("vistaJSP","seguridad/usuario.jsp");
			vista.addObject("vistaJS","seguridad/usuario.js");
			vista.addObject("identidadUsuario",principal.getIdentidad());
			vista.addObject("menu",listaEnlaces);
			vista.addObject("listadoClientes",listadoClientes);
		} catch(Exception ex){
			
		}
		return vista;
	}
	
	@RequestMapping(value = URL_LISTAR_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale){
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros = null;
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
			respuesta = dUsuario.recuperarRegistros(parametros);
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
        	respuesta= dUsuario.recuperarRegistro(ID);
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
	public @ResponseBody RespuestaCompuesta guardarRegistro(@RequestBody Usuario eUsuario,HttpServletRequest peticionHttp,Locale locale){		 
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
			this.transaccion = new DataSourceTransactionManager(dUsuario.getDataSource());
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
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			eUsuario.setClave(passwordEncoder.encode(eUsuario.getClave()));
        	eUsuario.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eUsuario.setActualizadoPor(principal.getID()); 
           	eUsuario.setCreadoEl(Calendar.getInstance().getTime().getTime());
            eUsuario.setCreadoPor(principal.getID());
            eUsuario.setIpActualizacion(direccionIp);
            eUsuario.setIpCreacion(direccionIp);
            respuesta= dUsuario.guardarRegistro(eUsuario);
            //Verifica si la accion se ejecuto de forma satisfactoria
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }
            ClaveGenerada = respuesta.valor;
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
            ContenidoAuditoria =  mapper.writeValueAsString(eUsuario);
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_GUARDAR_COMPLETA);
            eBitacora.setTabla(UsuarioDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(ClaveGenerada);
            eBitacora.setContenido(ContenidoAuditoria);
            eBitacora.setRealizadoEl(eUsuario.getCreadoEl());
            eBitacora.setRealizadoPor(eUsuario.getCreadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }           
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eUsuario.getFechaCreacion().substring(0, 9),eUsuario.getFechaCreacion().substring(10),principal.getIdentidad() },locale);
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
	public @ResponseBody RespuestaCompuesta actualizarRegistro(@RequestBody Usuario eUsuario,HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dUsuario.getDataSource());
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
        	eUsuario.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eUsuario.setActualizadoPor(principal.getID()); 
            eUsuario.setIpActualizacion(direccionIp);
            respuesta= dUsuario.actualizarRegistro(eUsuario);
            if (respuesta.estado==false){          	
            	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
            }
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper();
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
            eBitacora.setTabla(ClienteDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(String.valueOf( eUsuario.getId()));
            eBitacora.setContenido( mapper.writeValueAsString(eUsuario));
            eBitacora.setRealizadoEl(eUsuario.getActualizadoEl());
            eBitacora.setRealizadoPor(eUsuario.getActualizadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }  
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {  eUsuario.getFechaActualizacion().substring(0, 9),eUsuario.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);;
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
	
	@RequestMapping(value = URL_ACTUALIZAR_ESTADO_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta actualizarEstadoRegistro(@RequestBody Usuario eUsuario,HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dUsuario.getDataSource());
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
			eUsuario.setActualizadoEl(Calendar.getInstance().getTime().getTime());
			eUsuario.setActualizadoPor(principal.getID()); 
			eUsuario.setIpActualizacion(direccionIp);
            respuesta= dUsuario.ActualizarEstadoRegistro(eUsuario);
            if (respuesta.estado==false){          	
            	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
            }
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper();
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_ACTUALIZAR_ESTADO_COMPLETA);
            eBitacora.setTabla(CisternaDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(String.valueOf( eUsuario.getId()));
            eBitacora.setContenido( mapper.writeValueAsString(eUsuario));
            eBitacora.setRealizadoEl(eUsuario.getActualizadoEl());
            eBitacora.setRealizadoPor(eUsuario.getActualizadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }  
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {  eUsuario.getFechaActualizacion().substring(0, 9),eUsuario.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);;
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
	
	@RequestMapping(value = URL_RECUPERAR_AUTORIZACION_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperaRegistroAutorizacion(int ID, Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		try {			
			//Recupera el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_RECUPERAR_AUTORIZACION_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
    		}
			//Recuperar registros
			respuesta = dAutorizacion.recuperarRegistros(null);
			if(!respuesta.estado){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			if(!respuesta.contenido.carga.isEmpty()){
	        	Iterator itrAutorizacion = respuesta.contenido.carga.iterator();
	        	while (itrAutorizacion.hasNext()) {
	        		Autorizacion eAutorizacion = (Autorizacion)itrAutorizacion.next();
	        		eAutorizacion.setTieneAutorizacion(0);
	        	}
	        	
	        	RespuestaCompuesta respuestaAutorizacionUsuario = dAutorizacionUsuario.recuperarAutorizacionesPorUsuario(ID);
				if(!respuestaAutorizacionUsuario.estado){
					throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
				}
				if(!respuestaAutorizacionUsuario.contenido.carga.isEmpty()){
		        	Iterator itrAutorizacionUsuario = respuestaAutorizacionUsuario.contenido.carga.iterator();
		        	while (itrAutorizacionUsuario.hasNext()) {
		        		AutorizacionUsuario eAutorizacionUsuario = (AutorizacionUsuario)itrAutorizacionUsuario.next();
		        		itrAutorizacion = respuesta.contenido.carga.iterator();
			        	while (itrAutorizacion.hasNext()) {
			        		Autorizacion eAutorizacion = (Autorizacion)itrAutorizacion.next();
			        		if(eAutorizacionUsuario.getIdAutorizacion() == eAutorizacion.getId()){
				        		eAutorizacion.setTieneAutorizacion(1);
			        		}
			        	}
		        	}
	        	}
        	}
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
	
	@RequestMapping(value = URL_GUARDAR_AUTORIZACION_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta guardarRegistroAutorizacion(@RequestBody AutorizacionUsuario eAutorizacionUsuario, HttpServletRequest peticionHttp,Locale locale){		 
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
			this.transaccion = new DataSourceTransactionManager(dUsuario.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_AUTORIZACION_COMPLETA);
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
			
			String mensaje = ""; 

            //METODO PARA GUARDAR LAS AUTORIZACIONES DEL USUARIO
			AutorizacionUsuario eAutorizacionDelUsuario;
			Iterator itrAutorizaciones = eAutorizacionUsuario.getAutorizacion().iterator();
	        while (itrAutorizaciones.hasNext()) {
	        	Autorizacion eAutorizacion = (Autorizacion)itrAutorizaciones.next();
	        	
	        	//Primero verificamos si existe el registro y sólo le modificamos el estado.
	        	RespuestaCompuesta existeRegistro = dAutorizacionUsuario.recuperarAutorizacionesPorUsuarioYAutorizacion(eAutorizacionUsuario.getIdUsuario(), eAutorizacion.getId());
	        	if(!existeRegistro.estado){
	        		throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
				}
	        	
	        	if(!existeRegistro.contenido.carga.isEmpty()){
	        		Iterator itrRegistrosExistentes = existeRegistro.contenido.carga.iterator();
	    	        while (itrRegistrosExistentes.hasNext()) {
	    	        	AutorizacionUsuario eAutorizacionExistente = (AutorizacionUsuario)itrRegistrosExistentes.next();
	    	        	eAutorizacionExistente.setActualizadoEl(Calendar.getInstance().getTime().getTime());
	    	        	eAutorizacionExistente.setActualizadoPor(principal.getID()); 
	    	        	eAutorizacionExistente.setIpActualizacion(direccionIp);
	    	        	eAutorizacionExistente.setEstado(eAutorizacion.getEstado());
	    	        	RespuestaCompuesta modificaEstadoRegistro = dAutorizacionUsuario.actualizarEstadoRegistro(eAutorizacionExistente);
	    	        	if(!modificaEstadoRegistro.estado){
	    	        		throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
	    				}
	    	        	//Guardar en la bitacora
	    	            ObjectMapper mapper = new ObjectMapper();
	    	            eBitacora.setUsuario(principal.getNombre());
	    	            eBitacora.setAccion(URL_GUARDAR_AUTORIZACION_COMPLETA);
	    	            eBitacora.setTabla(AutorizacionUsuarioDao.NOMBRE_TABLA);
	    	            eBitacora.setIdentificador(String.valueOf( eAutorizacionExistente.getId()));
	    	            eBitacora.setContenido( mapper.writeValueAsString(eAutorizacionExistente));
	    	            eBitacora.setRealizadoEl(eAutorizacionUsuario.getActualizadoEl());
	    	            eBitacora.setRealizadoPor(eAutorizacionUsuario.getActualizadoPor());
	    	            respuesta= dBitacora.guardarRegistro(eBitacora);
	    	            if (respuesta.estado==false){     	
	    	              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
	    	            }  
	    	            mensaje = gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eAutorizacionExistente.getFechaActualizacion().substring(0, 9), eAutorizacionExistente.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);
	    	        	
	    	        } 
	    	    //Si no existen registros crearemos uno nuevo
	        	} else {
	        		eAutorizacionDelUsuario = new AutorizacionUsuario();
	        		eAutorizacionDelUsuario.setIdUsuario(eAutorizacionUsuario.getIdUsuario());
	        		eAutorizacionDelUsuario.setIdAutorizacion(eAutorizacion.getId());
	        		eAutorizacionDelUsuario.setCodigoAutorizacion("");
	        		eAutorizacionDelUsuario.setCreadoEl(Calendar.getInstance().getTime().getTime());
	        		eAutorizacionDelUsuario.setCreadoPor(principal.getID());
	        		eAutorizacionDelUsuario.setIpCreacion(direccionIp);
	        		eAutorizacionDelUsuario.setActualizadoEl(Calendar.getInstance().getTime().getTime());
	        		eAutorizacionDelUsuario.setActualizadoPor(principal.getID()); 
	        		eAutorizacionDelUsuario.setIpActualizacion(direccionIp);
	        		eAutorizacionDelUsuario.setEstado(eAutorizacion.getEstado()); //estado activo
	        		
	        		RespuestaCompuesta agregaRegistro = dAutorizacionUsuario.guardarRegistro(eAutorizacionDelUsuario);
	        		if (respuesta.estado==false){     	
	                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
	                }
	        		
	                ClaveGenerada = agregaRegistro.valor;
	                //Guardar en la bitacora
	                ObjectMapper mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
	                ContenidoAuditoria =  mapper.writeValueAsString(eAutorizacionDelUsuario);
	                eBitacora.setUsuario(principal.getNombre());
	                eBitacora.setAccion(URL_GUARDAR_AUTORIZACION_COMPLETA);
	                eBitacora.setTabla(AutorizacionUsuarioDao.NOMBRE_TABLA);
	                eBitacora.setIdentificador(ClaveGenerada);
	                eBitacora.setContenido(ContenidoAuditoria);
	                eBitacora.setRealizadoEl(eAutorizacionDelUsuario.getCreadoEl());
	                eBitacora.setRealizadoPor(eAutorizacionDelUsuario.getCreadoPor());
	                respuesta= dBitacora.guardarRegistro(eBitacora);
	                if (respuesta.estado==false){     	
	                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
	                }
	                mensaje = gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eAutorizacionDelUsuario.getFechaCreacion().substring(0, 9), eAutorizacionDelUsuario.getFechaCreacion().substring(10),principal.getIdentidad() },locale);
	        	}
	        }
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }
            respuesta.mensaje = mensaje;
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

	private  AuthenticatedUserDetails getCurrentUser(){
		return  (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
