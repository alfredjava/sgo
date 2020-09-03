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

import sgo.datos.AsignacionDao;
import sgo.datos.AsignacionTransporteDao;
import sgo.datos.BitacoraDao;
import sgo.datos.CisternaDao;
import sgo.datos.ClienteDao;
import sgo.datos.DetalleTransporteDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.EventoDao;
import sgo.datos.OperacionDao;
import sgo.datos.PlanificacionDao;
import sgo.datos.ProductoDao;
import sgo.datos.TransporteDao;
import sgo.datos.EnlaceDao;
import sgo.datos.TransportistaDao;
import sgo.entidad.Asignacion;
import sgo.entidad.Bitacora;
import sgo.entidad.Cliente;
import sgo.entidad.DetalleTransporte;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Evento;
import sgo.entidad.Operacion;
import sgo.entidad.Planificacion;
import sgo.entidad.Producto;
import sgo.entidad.Respuesta;
import sgo.entidad.Transporte;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Transportista;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

@Controller
public class TransporteControlador {
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
	private AsignacionDao dAsignacion;
	@Autowired
	private PlanificacionDao dPlanificacion;
	@Autowired
	private TransportistaDao dTransportista;
	@Autowired
	private ClienteDao dCliente;
	@Autowired
	private CisternaDao dCisterna;
	@Autowired
	private OperacionDao dOperacion;
	@Autowired
	private ProductoDao dProducto;
	@Autowired
	private DetalleTransporteDao dDetalleTransporte;
	@Autowired
	private TransporteDao dTransporte;
	@Autowired
	private EventoDao dEvento;
	@Autowired
	private AsignacionTransporteDao dAsignacionTransporteDao;
	@Autowired
	private DiaOperativoControlador DiaOperativoControlador;

	//
	private DataSourceTransactionManager transaccion;//Gestor de la transaccion
	//urls generales
	private static final String URL_GESTION_COMPLETA="/admin/transporte";
	private static final String URL_GESTION_RELATIVA="/transporte";
	private static final String URL_GUARDAR_COMPLETA="/admin/transporte/crear";
	private static final String URL_GUARDAR_RELATIVA="/transporte/crear";
	private static final String URL_LISTAR_COMPLETA="/admin/transporte/listar";
	private static final String URL_LISTAR_RELATIVA="/transporte/listar";
	private static final String URL_ACTUALIZAR_COMPLETA="/admin/transporte/actualizar";
	private static final String URL_ACTUALIZAR_RELATIVA="/transporte/actualizar";
	private static final String URL_RECUPERAR_COMPLETA="/admin/transporte/recuperar";
	private static final String URL_RECUPERAR_RELATIVA="/transporte/recuperar";
	private static final String URL_LISTAR_TRANSPORTES_COMPLETA="/admin/transporte/listarTransportes";
	private static final String URL_LISTAR_TRANSPORTES_RELATIVA="/transporte/listarTransportes";
	private static final String URL_LISTAR_ASIGNACION_TRANSPORTES_COMPLETA="/admin/transporte/listarAsignacionTransportes";
	private static final String URL_LISTAR_ASIGNACION_TRANSPORTES_RELATIVA="/transporte/listarAsignacionTransportes";
	private static final String URL_ACTUALIZAR_PESAJE_COMPLETA="/admin/transporte/actualizarPesaje";
	private static final String URL_ACTUALIZAR_PESAJE_RELATIVA="/transporte/actualizarPesaje";
	private static final String URL_GUARDAR_EVENTO_COMPLETA="/admin/transporte/guardarEventoTransporte";
	private static final String URL_GUARDAR_EVENTO_RELATIVA="/transporte/guardarEventoTransporte";

	@SuppressWarnings("unchecked")
	@RequestMapping(URL_GESTION_RELATIVA)
	public ModelAndView mostrarFormulario(Locale locale){
		ModelAndView vista =null;
		AuthenticatedUserDetails principal = null;
		ArrayList<Enlace> listaEnlaces = null;
		ArrayList<Cliente> listaClientes= null;
		ArrayList<Operacion> listaOperaciones= null;
		ArrayList<Producto> listaProductos= null;
		ArrayList<Transportista> listaTransportistas= null;

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
			parametros.setFiltroEstado(2);
			respuesta = dCliente.recuperarRegistros(parametros);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
			}
			
			parametros = new ParametrosListar();
			parametros.setPaginacion(Constante.SIN_PAGINACION);			
			respuesta =dOperacion.recuperarRegistros(parametros);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
			}
			listaOperaciones = (ArrayList<Operacion>) respuesta.contenido.carga;
			
			parametros = new ParametrosListar();
			parametros.setPaginacion(Constante.SIN_PAGINACION);			
			respuesta = dTransportista.recuperarRegistros(parametros);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
			}
			listaTransportistas = (ArrayList<Transportista>) respuesta.contenido.carga;
			
			parametros = new ParametrosListar();
			parametros.setPaginacion(Constante.SIN_PAGINACION);			
			respuesta = dProducto.recuperarRegistros(parametros);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
			}
			listaProductos = (ArrayList<Producto>) respuesta.contenido.carga;

			vista = new ModelAndView("plantilla");
			vista.addObject("vistaJSP","transporte/transporte.jsp");
			vista.addObject("vistaJS","transporte/transporte.js");
			vista.addObject("identidadUsuario",principal.getIdentidad());
			vista.addObject("menu",listaEnlaces);
			
			vista.addObject("clientes",listaClientes);
			vista.addObject("operaciones",listaOperaciones);
			vista.addObject("transportistas",listaTransportistas);
			vista.addObject("productos",listaProductos);
			vista.addObject("fechaActual", dDiaOperativo.recuperarFechaActual().valor);
		} catch(Exception ex){
			
		}
		return vista;
	}

	@RequestMapping(value = URL_LISTAR_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarDiasOperativosTransporte(HttpServletRequest httpRequest, Locale locale){
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros = null;
		AuthenticatedUserDetails principal = null;
		String mensajeRespuesta = "";
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

			//Recuperamos los dias operativos
			respuesta = dDiaOperativo.recuperarRegistros(parametros);
			if(!respuesta.estado){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}

			//Si respuesta tiene registros, recuperamos todas sus planificaciones.
			if(!respuesta.contenido.carga.isEmpty()){
				String descProductoVolRequerido = "";
				List<DiaOperativo> diasOperativos = new ArrayList<DiaOperativo>();
		        Iterator itr = respuesta.contenido.carga.iterator();
		        while (itr.hasNext()) {
		        	DiaOperativo eDiaOperativo = (DiaOperativo)itr.next();
		        	RespuestaCompuesta respuestaPlanificaciones = dPlanificacion.recuperarPlanificacionesPorDiaOperativo(eDiaOperativo.getId());

		        	if(!respuestaPlanificaciones.estado){
		        		throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
					}
		        	
		        	if(!respuestaPlanificaciones.contenido.carga.isEmpty()){
			        	Iterator itrPlanifiacion = respuestaPlanificaciones.contenido.carga.iterator();
			        	List<Planificacion>  listaPlanificaciones = new ArrayList<Planificacion>();
			        	while (itrPlanifiacion.hasNext()) {
			        		Planificacion ePlanificacion = (Planificacion)itrPlanifiacion.next();
			        		descProductoVolRequerido = "";
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
	public @ResponseBody RespuestaCompuesta recuperaRegistro(int ID, int IDTransporte, Locale locale){
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

			if(!Utilidades.esValido(ID)){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}

			if(!Utilidades.esValido(IDTransporte)){
				System.out.println("recuperarRegistrosPorDiaOperativo");
				respuesta = dAsignacion.recuperarRegistrosPorDiaOperativo(ID);
				System.out.print(respuesta.contenido.carga);
			} else {
				System.out.println("recuperarRegistro");
				respuesta = dAsignacion.recuperarRegistro(ID, IDTransporte);
				System.out.print(respuesta.contenido.carga);
			}

			if(!respuesta.estado){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			
        	//Si respuesta tiene registros, recuperamos los transportes.
			if(!respuesta.contenido.carga.isEmpty()){
				List<Asignacion> listaAsignaciones = new ArrayList<Asignacion>();
				List<Transporte> listaTransportes = new ArrayList<Transporte>();
				List<DiaOperativo> listaDiaOperativo = new ArrayList<DiaOperativo>();
				Iterator iteraAsignacion = respuesta.contenido.carga.iterator();
		        while (iteraAsignacion.hasNext()) {
		        	Asignacion eAsignacion = (Asignacion)iteraAsignacion.next();

		        	//buscamos el día operativo de la asignación
		        	RespuestaCompuesta respuestaDiaOperativo = dDiaOperativo.recuperarRegistro(eAsignacion.getIdDoperativo());
		        	if(!respuestaDiaOperativo.estado){
		        		throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
					}

		        	if(!respuestaDiaOperativo.contenido.carga.isEmpty()){
		        		List<Planificacion> listaPlanificaciones = new ArrayList<Planificacion>();
			        	Iterator iteraDiaOperativo = respuestaDiaOperativo.contenido.carga.iterator();
			        	while (iteraDiaOperativo.hasNext()) {
			        		DiaOperativo eDiaOperativo = (DiaOperativo)iteraDiaOperativo.next();
			        		
			        		//buscamos las planificaciones para el día operativo
			        		RespuestaCompuesta respuestaPlanificaciones = dPlanificacion.recuperarPlanificacionesPorDiaOperativo(eDiaOperativo.getId());
			        		if(!respuestaPlanificaciones.estado){
			        			throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
							}

				        	if(!respuestaPlanificaciones.contenido.carga.isEmpty()){
					        	Iterator iteraPlanificaciones = respuestaPlanificaciones.contenido.carga.iterator();
					        	while (iteraPlanificaciones.hasNext()) {
					        		Planificacion ePlanificacion = (Planificacion)iteraPlanificaciones.next();
					        		listaPlanificaciones.add(ePlanificacion);
					        	}
				        	}
			        		eDiaOperativo.setPlanificaciones(listaPlanificaciones);
			        		listaDiaOperativo.add(eDiaOperativo);
			        	}
		        	}
		        	
		        	//buscamos los transportes del día operativo
		        	RespuestaCompuesta respuestaTransportes = dTransporte.recuperarRegistro(eAsignacion.getIdTransporte());
		        	if(!respuestaTransportes.estado){
		        		throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
					}

		        	if(!respuestaTransportes.contenido.carga.isEmpty()){
		        		List<DetalleTransporte> listaDetalleTransporte = new ArrayList<DetalleTransporte>();
		        		List<Evento> listaEventos = new ArrayList<Evento>();
			        	Iterator iteraTransportes = respuestaTransportes.contenido.carga.iterator();
			        	while (iteraTransportes.hasNext()) {
			        		Transporte eTransporte = (Transporte)iteraTransportes.next();
			        		
			        		//buscamos los detalles de los transportes de día operativo
			        		RespuestaCompuesta respuestaDetalleTransporte = dDetalleTransporte.recuperarRegistrosPorIdTransporte(eTransporte.getId());
			        		if(!respuestaDetalleTransporte.estado){
			        			throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
							}

				        	if(!respuestaDetalleTransporte.contenido.carga.isEmpty()){
					        	Iterator iteraDetalleTransportes = respuestaDetalleTransporte.contenido.carga.iterator();
					        	while (iteraDetalleTransportes.hasNext()) {
					        		DetalleTransporte eDetalleTransporte = (DetalleTransporte)iteraDetalleTransportes.next();
					        		listaDetalleTransporte.add(eDetalleTransporte);
					        	}
				        	}
				        	
				        	//buscamos los eventos del transporte
			        		RespuestaCompuesta respuestaEvento = dEvento.recuperarRegistroPorIdRegistro(eTransporte.getId(), 1); //se envía 1 porque el tipo de registro es transporte
			        		if(!respuestaEvento.estado){
			        			throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
							}

				        	if(!respuestaEvento.contenido.carga.isEmpty()){
					        	Iterator iteraEvento = respuestaEvento.contenido.carga.iterator();
					        	while (iteraEvento.hasNext()) {
					        		Evento eEvento = (Evento)iteraEvento.next();
					        		listaEventos.add(eEvento);
					        	}
				        	}

				        	eTransporte.setEventos(listaEventos);
				        	eTransporte.setDetalles(listaDetalleTransporte);
				        	listaTransportes.add(eTransporte);
			        	}
		        	}
		        	eAsignacion.setDiaOperativo(listaDiaOperativo);
		        	eAsignacion.setTransportes(listaTransportes);
		        	listaAsignaciones.add(eAsignacion);
		        }
		        respuesta.contenido.carga = (List) listaAsignaciones;
			}
			respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso",null,locale);
		} catch (Exception ex){
			ex.printStackTrace();
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}

	@RequestMapping(value = URL_LISTAR_TRANSPORTES_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperaTransportesAsignados(int ID, Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		try {			
			//Recupera el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_LISTAR_TRANSPORTES_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
    		}
			//Recuperar el registro
        	respuesta= dTransporte.recuperaTransportesAsignados(ID);
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
	
	@RequestMapping(value = URL_LISTAR_ASIGNACION_TRANSPORTES_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperaAsignacionTransporte(int ID, Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		try {			
			//Recupera el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_LISTAR_ASIGNACION_TRANSPORTES_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
    		}
			//Recuperar el registro
        	respuesta= dAsignacionTransporteDao.recuperarRegistrosPorDiaOperativo(ID);
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
	
	@RequestMapping(value = URL_GUARDAR_EVENTO_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta guardarRegistroEvento(@RequestBody Evento eEvento, HttpServletRequest peticionHttp,Locale locale){		 
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
			this.transaccion = new DataSourceTransactionManager(dTransporte.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser(); 
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_EVENTO_COMPLETA);
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
			eEvento.setCreadoEl(Calendar.getInstance().getTime().getTime());
			eEvento.setCreadoPor(principal.getID());
			eEvento.setIpCreacion(direccionIp);
            respuesta= dEvento.guardarRegistro(eEvento);
            //Verifica si la accion se ejecuto de forma satisfactoria
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }
            ClaveGenerada = respuesta.valor;
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
            ContenidoAuditoria =  mapper.writeValueAsString(eEvento);
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_GUARDAR_EVENTO_COMPLETA);
            eBitacora.setTabla(EventoDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(ClaveGenerada);
            eBitacora.setContenido(ContenidoAuditoria);
            eBitacora.setRealizadoEl(eEvento.getCreadoEl());
            eBitacora.setRealizadoPor(eEvento.getCreadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }           
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eEvento.getFechaCreacion().substring(0, 9),eEvento.getFechaCreacion().substring(10),principal.getIdentidad() },locale);
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

	@RequestMapping(value = URL_GUARDAR_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta guardarRegistro(@RequestBody Asignacion eAsignacion, HttpServletRequest peticionHttp,Locale locale){	
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		Bitacora eBitacora= null;
		String ContenidoAuditoria ="";
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		String direccionIp="";
		String ClaveGenerada="";
		DetalleTransporte eDetalleTransporte = null;
		int idTransporteCreado;
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dTransporte.getDataSource());
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
            //TRANSPORTE
            Transporte eTransporte = (Transporte)eAsignacion.getTransportes().get(0);
            eTransporte.setCreadoEl(Calendar.getInstance().getTime().getTime());
            eTransporte.setCreadoPor(principal.getID()); 
            eTransporte.setIpCreacion(direccionIp);
            eTransporte.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eTransporte.setActualizadoPor(principal.getID()); 
            eTransporte.setIpActualizacion(direccionIp);
            eTransporte.setPesoBruto(0);
            eTransporte.setPesoNeto(0);
            eTransporte.setPesoTara(0);
            eTransporte.setEstado(1);
            eTransporte.setIdPlantaRecepcion(Integer.parseInt(principal.getOperacion().getReferenciaPlantaRecepcion()));
            eTransporte.setOrigen("M");
            //TODO
            //FALTA VER LA PLANTA DE RECEPCION Y tarjeta_cubicacion_cisterna
            respuesta = dTransporte.guardarRegistro(eTransporte);

            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }
            ClaveGenerada = respuesta.valor;
    		idTransporteCreado = Integer.valueOf(respuesta.valor);
            //Guardar en la bitacora para transporte
    		ObjectMapper mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
            ContenidoAuditoria =  mapper.writeValueAsString(eTransporte);
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_GUARDAR_EVENTO_COMPLETA);
            eBitacora.setTabla(TransporteDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(ClaveGenerada);
            eBitacora.setContenido(ContenidoAuditoria);
            eBitacora.setRealizadoEl(eTransporte.getCreadoEl());
            eBitacora.setRealizadoPor(eTransporte.getCreadoPor());

            RespuestaCompuesta respuestaBitacoraTransporte = dBitacora.guardarRegistro(eBitacora);
            if (respuestaBitacoraTransporte.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }

            //DETALLE DE TRANSPORTE
            for(int contador=0; contador < eTransporte.getDetalles().size(); contador++){
            	eDetalleTransporte = eTransporte.getDetalles().get(contador);
            	eDetalleTransporte.setIdTransporte(idTransporteCreado);
            	RespuestaCompuesta respuestaDetalleTransporte = dDetalleTransporte.guardarRegistro(eDetalleTransporte);
                if (respuestaDetalleTransporte.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
                }

                ClaveGenerada = respuestaDetalleTransporte.valor;
                //Guardar en la bitacora
                mapper = new ObjectMapper(); 
                ContenidoAuditoria =  mapper.writeValueAsString(eDetalleTransporte);
                eBitacora.setUsuario(principal.getNombre());
                eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
                eBitacora.setTabla(DetalleTransporteDao.NOMBRE_TABLA);
                eBitacora.setIdentificador(ClaveGenerada);
                eBitacora.setContenido(ContenidoAuditoria);
                eBitacora.setRealizadoEl(eTransporte.getActualizadoEl());
                eBitacora.setRealizadoPor(principal.getID());
                RespuestaCompuesta respuestaBitacoraDetalleTransporte = dBitacora.guardarRegistro(eBitacora);
                if (respuestaBitacoraDetalleTransporte.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
                }        
            }

            //ASIGNACION
            eAsignacion.setIdTransporte(idTransporteCreado);
            eAsignacion.setActualizadoEl(Calendar.getInstance().getTime().getTime());
			eAsignacion.setActualizadoPor(principal.getID());
			eAsignacion.setIpActualizacion(direccionIp);

			RespuestaCompuesta respuestaAsignacion = dAsignacion.guardarRegistro(eAsignacion);

            if (respuestaAsignacion.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }
            ClaveGenerada = respuestaAsignacion.valor;
            //Guardar en la bitacora para asignacion
            mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
            ContenidoAuditoria =  mapper.writeValueAsString(eAsignacion);
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_GUARDAR_EVENTO_COMPLETA);
            eBitacora.setTabla(EventoDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(ClaveGenerada);
            eBitacora.setContenido(ContenidoAuditoria);
            eBitacora.setRealizadoEl(eAsignacion.getCreadoEl());
            eBitacora.setRealizadoPor(eAsignacion.getCreadoPor());
            RespuestaCompuesta respuestaBitacoraAsignacion= dBitacora.guardarRegistro(eBitacora);
            if (respuestaBitacoraAsignacion.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }

        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eAsignacion.getFechaCreacion().substring(0, 9),eAsignacion.getFechaCreacion().substring(10),principal.getIdentidad() },locale);
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
	public @ResponseBody RespuestaCompuesta actualizarRegistro(@RequestBody Asignacion eAsignacion,HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		String ContenidoAuditoria ="";
		Bitacora eBitacora=null;
		String direccionIp="";
		String ClaveGenerada="";
		DetalleTransporte eDetalleTransporte = null;
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

//			eAsignacion.setActualizadoEl(Calendar.getInstance().getTime().getTime());
//			eAsignacion.setActualizadoPor(principal.getID()); 
//			eAsignacion.setIpActualizacion(direccionIp);
//            
//			  Primero se actualiza la asignacion
//            respuesta= dAsignacion.actualizarRegistro(eAsignacion);
//            
//            if (respuesta.estado==false){     	
//              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
//            }
            Transporte eTransporte = (Transporte)eAsignacion.getTransportes().get(0);
            eTransporte.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eTransporte.setActualizadoPor(principal.getID()); 
            eTransporte.setIpActualizacion(direccionIp);
            eTransporte.setOrigen("M");
            //eTransporte.setIdPlantaRecepcion(Integer.valueOf(principal.getOperacion().getReferenciaPlantaRecepcion()));

            RespuestaCompuesta respuestaTransporte = dTransporte.actualizarRegistro(eTransporte);
            
            if (respuestaTransporte.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
            }
            
            Respuesta numeroRegistros = dDetalleTransporte.numeroRegistrosPorTransporte(eTransporte.getId());
            Respuesta registrosEliminados = dDetalleTransporte.eliminarRegistrosPorTransporte(eTransporte.getId());
            
			if (!numeroRegistros.valor.equals(registrosEliminados.valor)){
				throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));				
			}
			
            for(int contador=0; contador < eTransporte.getDetalles().size(); contador++){
            	eDetalleTransporte = eTransporte.getDetalles().get(contador);
            	
            	RespuestaCompuesta respuestaDetalleTransporte = dDetalleTransporte.guardarRegistro(eDetalleTransporte);
                if (respuestaDetalleTransporte.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
                }
                ClaveGenerada = respuestaDetalleTransporte.valor;
                //Guardar en la bitacora
                ObjectMapper mapper = new ObjectMapper(); 
                ContenidoAuditoria =  mapper.writeValueAsString(eDetalleTransporte);
                eBitacora.setUsuario(principal.getNombre());
                eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
                eBitacora.setTabla(DetalleTransporteDao.NOMBRE_TABLA);
                eBitacora.setIdentificador(ClaveGenerada);
                eBitacora.setContenido(ContenidoAuditoria);
                eBitacora.setRealizadoEl(eTransporte.getActualizadoEl());
                eBitacora.setRealizadoPor(principal.getID());
                RespuestaCompuesta respuestaBitacora = dBitacora.guardarRegistro(eBitacora);
                if (respuestaBitacora.estado==false){     	
                  	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
                }        
            }
            respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eTransporte.getFechaActualizacion().substring(0, 9),eTransporte.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);
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

	@RequestMapping(value = URL_ACTUALIZAR_PESAJE_RELATIVA ,method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta actualizarPesosDelRegistro(@RequestBody Transporte eTransporte, HttpServletRequest peticionHttp,Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dTransporte.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser();
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_PESAJE_COMPLETA);
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
        	eTransporte.setActualizadoEl(Calendar.getInstance().getTime().getTime());
            eTransporte.setActualizadoPor(principal.getID()); 
            eTransporte.setIpActualizacion(direccionIp);
            respuesta= dTransporte.actualizarPesajes(eTransporte);
            if (respuesta.estado==false){          	
            	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
            }
            //Guardar en la bitacora
            ObjectMapper mapper = new ObjectMapper();
            eBitacora.setUsuario(principal.getNombre());
            eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
            eBitacora.setTabla(TransporteDao.NOMBRE_TABLA);
            eBitacora.setIdentificador(String.valueOf( eTransporte.getId()));
            eBitacora.setContenido( mapper.writeValueAsString(eTransporte));
            eBitacora.setRealizadoEl(eTransporte.getActualizadoEl());
            eBitacora.setRealizadoPor(eTransporte.getActualizadoPor());
            respuesta= dBitacora.guardarRegistro(eBitacora);
            if (respuesta.estado==false){     	
              	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
            }  
        	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {  eTransporte.getFechaActualizacion().substring(0, 9),eTransporte.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);;
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
