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
import sgo.datos.CargaTanqueDao;
import sgo.datos.ClienteDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.EnlaceDao;
import sgo.datos.OperacionDao;
import sgo.datos.PlanificacionDao;
import sgo.datos.ProductoDao;
import sgo.entidad.Bitacora;
import sgo.entidad.CargaTanque;
import sgo.entidad.Cliente;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.Operacion;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Planificacion;
import sgo.entidad.Producto;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;

@Controller
public class DescargaControlador {
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
 @Autowired
 private CargaTanqueDao dCargaTanque;
 //
 private DataSourceTransactionManager transaccion;//Gestor de la transaccion
 //urls generales
 private static final String URL_GESTION_COMPLETA="/admin/descarga";
 private static final String URL_GESTION_RELATIVA="/descarga";

 private static final String URL_LISTAR_CARGA_COMPLETA="/admin/descarga/listar-carga";
 private static final String URL_LISTAR_CARGA_RELATIVA="/descarga/listar-carga";
 
 private static final String URL_RECUPERAR_CARGA_COMPLETA="/admin/descarga/recuperar-carga";
 private static final String URL_RECUPERAR_CARGA_RELATIVA="/descarga/recuperar-carga";
 
 private static final String URL_GUARDAR_CARGA_COMPLETA="/admin/descarga/crear-carga";
 private static final String URL_GUARDAR_CARGA_RELATIVA="/descarga/crear-carga";
 
 private static final String URL_ACTUALIZAR_CARGA_COMPLETA="/admin/descarga/actualizar-carga";
 private static final String URL_ACTUALIZAR_CARGA_RELATIVA="/descarga/actualizar-carga";
 
 private static final String URL_ACTUALIZAR_COMPLETA="/admin/descarga/actualizar";
 private static final String URL_ACTUALIZAR_RELATIVA="/descarga/actualizar";
 
 private static final String URL_RECUPERAR_COMPLETA="/admin/descarga/recuperar";
 private static final String URL_RECUPERAR_RELATIVA="/descarga/recuperar";
 
 private static final String URL_LISTAR_DIA_OPERATIVO_COMPLETA="/admin/descarga/listar-dia-operativo";
 private static final String URL_LISTAR_DIA_OPERATIVO_RELATIVA="/descarga/listar-dia-operativo";
 
 @RequestMapping(value = URL_LISTAR_DIA_OPERATIVO_RELATIVA ,method = RequestMethod.GET)
 public @ResponseBody RespuestaCompuesta recuperarDias(HttpServletRequest httpRequest, Locale locale){
   RespuestaCompuesta respuesta = null;
   ParametrosListar parametros= null;
   AuthenticatedUserDetails principal = null;
   try {
     //Recuperar el usuario actual
     principal = this.getCurrentUser(); 
     //Recuperar el enlace de la accion
     respuesta = dEnlace.recuperarRegistro(URL_LISTAR_DIA_OPERATIVO_COMPLETA);
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
     
     //Recuperar registros
     //Recuperamos los dias operativos
     respuesta = dDiaOperativo.recuperarRegistros(parametros);
     if(!respuesta.estado){
       respuesta.estado=false;
       respuesta.contenido = null;
       respuesta.mensaje=("Error en la búsqueda de Días Operativos.");
       return respuesta;
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
     parametros.setFiltroEstado(Constante.FILTRO_TODOS);
     //respuesta = dOperacion.recuperarRegistrosxCliente(2);     
     respuesta =dOperacion.recuperarRegistros(parametros);
     if (respuesta.estado==false){
       throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
     }
     listaOperaciones = (ArrayList<Operacion>) respuesta.contenido.carga;
     
     parametros = new ParametrosListar();
     parametros.setPaginacion(Constante.SIN_PAGINACION);     
     parametros.setFiltroEstado(Constante.FILTRO_TODOS);
     respuesta = dProducto.recuperarRegistros(parametros);
     if (respuesta.estado==false){
       throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles",null,locale));
     }
     listaProductos = (ArrayList<Producto>) respuesta.contenido.carga;
     
     vista = new ModelAndView("plantilla");
     vista.addObject("vistaJSP","descarga/descarga.jsp");
     vista.addObject("vistaJS","descarga/descarga.js");
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
 
 @RequestMapping(value = URL_LISTAR_CARGA_RELATIVA ,method = RequestMethod.GET)
 public @ResponseBody RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale){
   RespuestaCompuesta respuesta = null;
   ParametrosListar parametros= null;
   AuthenticatedUserDetails principal = null;
   String mensajeRespuesta="";
   try {
     //Recuperar el usuario actual
     principal = this.getCurrentUser(); 
     //Recuperar el enlace de la accion
     respuesta = dEnlace.recuperarRegistro(URL_LISTAR_CARGA_COMPLETA);
     if (respuesta.estado==false){
       throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
     }
     //Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
     //Verificar si cuenta con el permiso necesario   
     /*
     if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
       mensajeRespuesta = gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale);
       throw new Exception(mensajeRespuesta);
       }
       */
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
     
    /* if (httpRequest.getParameter("filtroEstacion") != null) {
      parametros.setFiltroEstacion(( httpRequest.getParameter("filtroEstacion")));
    }
     */
     if (httpRequest.getParameter("filtroFechaPlanificada") != null) {
      parametros.setFiltroFechaPlanificada(( httpRequest.getParameter("filtroFechaPlanificada")));
    }
     
     //Recuperar registros
     respuesta = dCargaTanque.recuperarRegistros(parametros);
     respuesta.mensaje= gestorDiccionario.getMessage("sgo.listarExitoso",null,locale);
   } catch(Exception ex){
	   ex.printStackTrace();
     respuesta.estado=false;
     respuesta.contenido = null;
     respuesta.mensaje=ex.getMessage();
   }
   return respuesta;
 }
 
 @RequestMapping(value = URL_RECUPERAR_CARGA_RELATIVA ,method = RequestMethod.GET)
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
         respuesta= dCargaTanque.recuperarRegistro(ID);
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
 
 @RequestMapping(value = URL_GUARDAR_CARGA_RELATIVA ,method = RequestMethod.POST)
 public @ResponseBody RespuestaCompuesta guardarRegistro(@RequestBody CargaTanque eCargaTanque,HttpServletRequest peticionHttp,Locale locale){    
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
     this.transaccion = new DataSourceTransactionManager(dCargaTanque.getDataSource());
     definicionTransaccion = new DefaultTransactionDefinition();
     estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
     eBitacora = new Bitacora();
     //Recuperar el usuario actual
     principal = this.getCurrentUser(); 
     //Recuperar el enlace de la accion
     respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_CARGA_COMPLETA);
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
         eCargaTanque.setActualizadoEl(Calendar.getInstance().getTime().getTime());
           eCargaTanque.setActualizadoPor(principal.getID()); 
           eCargaTanque.setCreadoEl(Calendar.getInstance().getTime().getTime());
           eCargaTanque.setCreadoPor(principal.getID());
           eCargaTanque.setIpActualizacion(direccionIp);
           eCargaTanque.setIpCreacion(direccionIp);
           respuesta= dCargaTanque.guardarRegistro(eCargaTanque);
           //Verifica si la accion se ejecuto de forma satisfactoria
           if (respuesta.estado==false){       
               throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido",null,locale));
           }
           ClaveGenerada = respuesta.valor;
           //Guardar en la bitacora
           ObjectMapper mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
           ContenidoAuditoria =  mapper.writeValueAsString(eCargaTanque);
           eBitacora.setUsuario(principal.getNombre());
           eBitacora.setAccion(URL_GUARDAR_CARGA_COMPLETA);
           eBitacora.setTabla(CargaTanqueDao.NOMBRE_TABLA);
           eBitacora.setIdentificador(ClaveGenerada);
           eBitacora.setContenido(ContenidoAuditoria);
           eBitacora.setRealizadoEl(eCargaTanque.getCreadoEl());
           eBitacora.setRealizadoPor(eCargaTanque.getCreadoPor());
           respuesta= dBitacora.guardarRegistro(eBitacora);
           if (respuesta.estado==false){       
               throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
           }           
         respuesta.mensaje=gestorDiccionario.getMessage("sgo.guardarExitoso",new Object[] {  eCargaTanque.getFechaCreacion().substring(0, 9),eCargaTanque.getFechaCreacion().substring(10),principal.getIdentidad() },locale);
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
 
 @RequestMapping(value = URL_ACTUALIZAR_CARGA_RELATIVA ,method = RequestMethod.POST)
 public @ResponseBody RespuestaCompuesta actualizarRegistro(@RequestBody CargaTanque eCargaTanque,HttpServletRequest peticionHttp,Locale locale){
   RespuestaCompuesta respuesta = null;
   AuthenticatedUserDetails principal = null;
   TransactionDefinition definicionTransaccion = null;
   TransactionStatus estadoTransaccion = null;
   Bitacora eBitacora=null;
   String direccionIp="";
   try {
     //Inicia la transaccion
     this.transaccion = new DataSourceTransactionManager(dCargaTanque.getDataSource());
     definicionTransaccion = new DefaultTransactionDefinition();
     estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
     eBitacora = new Bitacora();
     //Recuperar el usuario actual
     principal = this.getCurrentUser();
     //Recuperar el enlace de la accion
     respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_CARGA_COMPLETA);
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
         eCargaTanque.setActualizadoEl(Calendar.getInstance().getTime().getTime());
           eCargaTanque.setActualizadoPor(principal.getID()); 
           eCargaTanque.setIpActualizacion(direccionIp);
           respuesta= dCargaTanque.actualizarRegistro(eCargaTanque);
           if (respuesta.estado==false){           
             throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
           }
           //Guardar en la bitacora
           ObjectMapper mapper = new ObjectMapper();
           eBitacora.setUsuario(principal.getNombre());
           eBitacora.setAccion(URL_ACTUALIZAR_CARGA_COMPLETA);
           eBitacora.setTabla(CargaTanqueDao.NOMBRE_TABLA);
           eBitacora.setIdentificador(String.valueOf( eCargaTanque.getId()));
           eBitacora.setContenido( mapper.writeValueAsString(eCargaTanque));
           eBitacora.setRealizadoEl(eCargaTanque.getActualizadoEl());
           eBitacora.setRealizadoPor(eCargaTanque.getActualizadoPor());
           respuesta= dBitacora.guardarRegistro(eBitacora);
           if (respuesta.estado==false){       
               throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
           }  
         respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {  eCargaTanque.getFechaActualizacion().substring(0, 9),eCargaTanque.getFechaActualizacion().substring(10),principal.getIdentidad() },locale);;
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
