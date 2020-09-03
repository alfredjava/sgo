package sgo.servicio;

import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sgo.datos.BitacoraDao;
import sgo.datos.EnlaceDao;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;


@Controller
public class BitacoraControlador {
	@Autowired
	private MessageSource gestorDiccionario;//Gestor del diccionario de mensajes para la internacionalizacion
	@Autowired
	private BitacoraDao dBitacora; //Clase para registrar en la bitacora (auditoria por accion)
	@Autowired
	private EnlaceDao dEnlace;
	@Autowired
	private MenuGestor menu;
	
	//
	//private DataSourceTransactionManager transaccion;//Gestor de la transaccion
	//urls generales
	private static final String URL_GESTION_COMPLETA="/admin/bitacora";
	private static final String URL_GESTION_RELATIVA="/bitacora";
	private static final String URL_LISTAR_COMPLETA="/admin/bitacora/listar";
	private static final String URL_LISTAR_RELATIVA="/bitacora/listar";
	private static final String URL_RECUPERAR_COMPLETA="/admin/bitacora/recuperar";
	private static final String URL_RECUPERAR_RELATIVA="/bitacora/recuperar";
	
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
			vista.addObject("vistaJSP","seguridad/bitacora.jsp");
			vista.addObject("vistaJS","seguridad/bitacora.js");
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
				parametros.setValorBuscado((httpRequest.getParameter("valorBuscado")));
			}
			if (httpRequest.getParameter("txtFiltro") != null) {
				parametros.setTxtFiltro((httpRequest.getParameter("txtFiltro")));
			}
			if (httpRequest.getParameter("filtroUsuario") != null) {
				parametros.setFiltroUsuario((httpRequest.getParameter("filtroUsuario")));
			}
			
			if (httpRequest.getParameter("filtroTabla") != null) {
				parametros.setFiltroTabla((httpRequest.getParameter("filtroTabla")));
			}

			if (httpRequest.getParameter("filtroFechaInicio") != null) {
				parametros.setFiltroFechaInicio((httpRequest.getParameter("filtroFechaInicio")));
			}
			
			if (httpRequest.getParameter("filtroFechaFinal") != null) {
				parametros.setFiltroFechaFinal((httpRequest.getParameter("filtroFechaFinal")));
			}

			//Recuperar registros
			respuesta = dBitacora.recuperarRegistros(parametros);
			respuesta.mensaje= gestorDiccionario.getMessage("sgo.listarExitoso",null,locale);
		} catch(Exception ex){
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
        	respuesta= dBitacora.recuperarRegistro(ID);
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
	
	private  AuthenticatedUserDetails getCurrentUser(){
		return  (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}


}