package sgo.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
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
import sgo.datos.ClienteDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.ProductoDao;
import sgo.datos.OperacionDao;
import sgo.datos.PlanificacionDao;
import sgo.datos.EnlaceDao;
import sgo.entidad.Bitacora;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Operacion;
import sgo.entidad.Producto;
import sgo.entidad.Planificacion;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

@Controller
public class PlanificacionControlador {
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

	private DataSourceTransactionManager transaccion;//Gestor de la transaccion
	//urls generales
	private static final String URL_GESTION_COMPLETA="/admin/planificacion";
	private static final String URL_GESTION_RELATIVA="/planificacion";
	private static final String URL_GUARDAR_COMPLETA="/admin/planificacion/crear";
	private static final String URL_GUARDAR_RELATIVA="/planificacion/crear";
	private static final String URL_LISTAR_COMPLETA="/admin/planificacion/listar";
	private static final String URL_LISTAR_RELATIVA="/planificacion/listar";
	private static final String URL_ACTUALIZAR_COMPLETA="/admin/planificacion/actualizar";
	private static final String URL_ACTUALIZAR_RELATIVA="/planificacion/actualizar";
	private static final String URL_RECUPERAR_COMPLETA="/admin/planificacion/recuperar";
	private static final String URL_RECUPERAR_RELATIVA="/planificacion/recuperar";
	
	@SuppressWarnings("unchecked")
	@RequestMapping(URL_GESTION_RELATIVA)
	public ModelAndView mostrarFormulario( Locale locale){
		ModelAndView vista =null;
		AuthenticatedUserDetails principal = null;
		ArrayList<Enlace> listaEnlaces = null;
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
			parametros.setFiltroEstado(Constante.FILTRO_TODOS);
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
			vista.addObject("vistaJSP","planificacion/planificacion.jsp");
			vista.addObject("vistaJS","planificacion/planificacion.js");
			vista.addObject("identidadUsuario",principal.getIdentidad());
			vista.addObject("menu",listaEnlaces);

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
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
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
			respuesta = dDiaOperativo.recuperarRegistros(parametros);
			if(!respuesta.estado){
				throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido",null,locale));
			}

			//Si respuesta tiene registros, recuperamos todas sus planificaciones.
			if(!respuesta.contenido.carga.isEmpty()){
				String descProductoVolRequerido = "";
				List<DiaOperativo> diasOperativos = new ArrayList<DiaOperativo>();
		        Iterator itr = respuesta.contenido.carga.iterator();
		        while (itr.hasNext()) {
		        	DiaOperativo eDiaOperativo = (DiaOperativo)itr.next();
		        	RespuestaCompuesta respuestaPlanificaciones = dPlanificacion.recuperarPlanificacionesPorDiaOperativo(eDiaOperativo.getId());
		        	descProductoVolRequerido = "";
		        	if(!respuestaPlanificaciones.estado){
		        		throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido",null,locale));
					}

		        	if(!respuesta.contenido.carga.isEmpty()){
			        	Iterator itrPlanifiacion = respuestaPlanificaciones.contenido.carga.iterator();
			        	List<Planificacion>  listaPlanificaciones = new ArrayList<Planificacion>();
			        	while (itrPlanifiacion.hasNext()) {
			        		Planificacion ePlanificacion = (Planificacion)itrPlanifiacion.next();
			        		//descProductoVolRequerido = "";
			        		if(descProductoVolRequerido.isEmpty()){
								descProductoVolRequerido = ePlanificacion.getDescProductoVolRequerido();
							} else {
								descProductoVolRequerido = descProductoVolRequerido + ", " +ePlanificacion.getDescProductoVolRequerido();
							}
			        		listaPlanificaciones.add(ePlanificacion);
			        	}
			        	eDiaOperativo.setPlanificaciones(listaPlanificaciones);
		        		eDiaOperativo.setDetalleProductoSolicitado(descProductoVolRequerido);
		        	}
		        	diasOperativos.add(eDiaOperativo);
		        }
		        respuesta.contenido.carga = (List) diasOperativos;
			}
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
        	respuesta= dDiaOperativo.recuperarRegistro(ID);
        	if(!respuesta.contenido.carga.isEmpty()){
				List<DiaOperativo> diasOperativos = new ArrayList<DiaOperativo>();
				
		        Iterator itr = respuesta.contenido.carga.iterator();
		        while (itr.hasNext()) {
		        	DiaOperativo eDiaOperativo = (DiaOperativo)itr.next();
		            diasOperativos.add(eDiaOperativo);
		        }
				respuesta = dPlanificacion.recuperarPlanificacionesPorDiaOperativo(diasOperativos);
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

	@RequestMapping(value = URL_GUARDAR_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta guardarRegistro(@RequestBody DiaOperativo eDiaOperativo, HttpServletRequest peticionHttp,Locale locale){		 
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		Bitacora eBitacora= null;
		String ContenidoAuditoria ="";
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		String direccionIp="";
		String ClaveGenerada="";
		Planificacion ePlanificacion= null;
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dPlanificacion.getDataSource());
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
			eDiaOperativo.setEstado(DiaOperativo.ESTADO_PLANIFICADO);
            eDiaOperativo.setCreadoEl(Calendar.getInstance().getTime().getTime());
            eDiaOperativo.setCreadoPor(principal.getID());
			eDiaOperativo.setActualizadoEl(Calendar.getInstance().getTime().getTime());
			eDiaOperativo.setActualizadoPor(principal.getID()); 
            eDiaOperativo.setIpActualizacion(direccionIp);
            eDiaOperativo.setIpCreacion(direccionIp);            

            Respuesta diaOperativo = DiaOperativoControlador.recuperaUltimoDia(eDiaOperativo.getIdOperacion(), locale);
            if (diaOperativo.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }

            eDiaOperativo.setFechaOperativa(Utilidades.convierteStringADate(diaOperativo.valor));
            
            respuesta= dDiaOperativo.guardarRegistro(eDiaOperativo);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }      
            ClaveGenerada = respuesta.valor;
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper(); 
            ContenidoAuditoria =  mapper.writeValueAsString(eDiaOperativo);
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion("/admin/dia_operativo/crear");
            eBitacora.setTabla(DiaOperativoDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(ClaveGenerada);
            eBitacora.setContenido(ContenidoAuditoria);
            eBitacora.setRealizadoEl(eDiaOperativo.getCreadoEl());
            eBitacora.setRealizadoPor(eDiaOperativo.getCreadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }           
            
            int idDoperativo = Integer.parseInt(ClaveGenerada);
            
            int numeroPlanificaciones = eDiaOperativo.getPlanificaciones().size();       
            for(int contador=0; contador < numeroPlanificaciones; contador++){
            	ePlanificacion = eDiaOperativo.getPlanificaciones().get(contador);
            	ePlanificacion.setIdDoperativo(idDoperativo);
            	ePlanificacion.setActualizadoEl(eDiaOperativo.getActualizadoEl());
            	ePlanificacion.setActualizadoPor(eDiaOperativo.getActualizadoPor());
            	ePlanificacion.setIpActualizacion(eDiaOperativo.getIpActualizacion());
            	respuesta = dPlanificacion.guardarRegistro(ePlanificacion);
                if (respuesta.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
                }
                
                ClaveGenerada = respuesta.valor;
                //Guardar en la bitacora
                mapper = new ObjectMapper(); 
                ContenidoAuditoria =  mapper.writeValueAsString(ePlanificacion);
                eBitacora.setUsuario(principal.getNombre());
                eBitacora.setAccion(URL_GUARDAR_COMPLETA);
                eBitacora.setTabla(PlanificacionDao.NOMBRE_TABLA);
                eBitacora.setIdentificador(ClaveGenerada);
                eBitacora.setContenido(ContenidoAuditoria);
                eBitacora.setRealizadoEl(ePlanificacion.getActualizadoEl());
                eBitacora.setRealizadoPor(ePlanificacion.getActualizadoPor());
                respuesta= dBitacora.guardarRegistro(eBitacora);
                if (respuesta.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
                }           
            }

        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eDiaOperativo.getFechaCreacion().substring(0, 9),eDiaOperativo.getFechaCreacion().substring(10),principal.getIdentidad() },locale);
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
	public @ResponseBody RespuestaCompuesta actualizarRegistro(@RequestBody DiaOperativo eDiaOperativo,HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		Bitacora eBitacora= null;
		String ContenidoAuditoria ="";
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		String direccionIp="";
		String ClaveGenerada="";
		Planificacion ePlanificacion= null;
		try {
			//Inicia la transaccion	
			this.transaccion = new DataSourceTransactionManager(dPlanificacion.getDataSource());
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

			eDiaOperativo.setActualizadoEl(Calendar.getInstance().getTime().getTime());
			eDiaOperativo.setActualizadoPor(principal.getID()); 
            eDiaOperativo.setIpActualizacion(direccionIp);
            
            //Primero se actualiza el diaOperativo
            respuesta= dDiaOperativo.actualizarRegistro(eDiaOperativo);
            
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }      

            Respuesta numeroRegistros = dPlanificacion.numeroRegistrosPorDiaOperativo(eDiaOperativo.getId());
            Respuesta registrosEliminados = dPlanificacion.eliminarRegistrosPorDiaOperativo(eDiaOperativo.getId());
            
			if (!numeroRegistros.valor.equals(registrosEliminados.valor)){
				throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));				
			}
			
            for(int contador=0; contador < eDiaOperativo.getPlanificaciones().size(); contador++){
            	ePlanificacion = eDiaOperativo.getPlanificaciones().get(contador);
            	ePlanificacion.setIdDoperativo(eDiaOperativo.getId());
            	ePlanificacion.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            	ePlanificacion.setActualizadoPor(principal.getID());
            	ePlanificacion.setIpActualizacion(direccionIp);
            	respuesta = dPlanificacion.guardarRegistro(ePlanificacion);
                if (respuesta.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
                }
                
                ClaveGenerada = respuesta.valor;
                //Guardar en la bitacora
                ObjectMapper mapper = new ObjectMapper(); 
                ContenidoAuditoria =  mapper.writeValueAsString(ePlanificacion);
                eBitacora.setUsuario(principal.getNombre());
                eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
                eBitacora.setTabla(PlanificacionDao.NOMBRE_TABLA);
                eBitacora.setIdentificador(ClaveGenerada);
                eBitacora.setContenido(ContenidoAuditoria);
                eBitacora.setRealizadoEl(ePlanificacion.getActualizadoEl());
                eBitacora.setRealizadoPor(ePlanificacion.getActualizadoPor());
                respuesta= dBitacora.guardarRegistro(eBitacora);
                if (respuesta.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
                }        
            }
	            respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  ePlanificacion.getFechaActualizacion().substring(0, 9),ePlanificacion.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);
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
