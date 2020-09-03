package sgo.servicio;

import java.util.HashMap;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecursoControlador {
 @Autowired 
 private MessageSource gestorDiccionario;
 
 private static final String URL_GESTION_COMPLETA="/admin/recursos.js";
 private static final String URL_GESTION_RELATIVA="/recursos.js";
 
 @RequestMapping(URL_GESTION_RELATIVA)
 public ModelAndView mostrarFormulario( Locale locale){
  ModelAndView vista =null;
  HashMap<String, String> hmRecursos = new HashMap<String,String>();
  try {
   //Recursos
   hmRecursos.put("CERRAR_VISTA", gestorDiccionario.getMessage("sgo.cerrarVista",null,locale));
   hmRecursos.put("INICIAR_OPERACION", gestorDiccionario.getMessage("sgo.iniciarOperacion",null,locale));
   hmRecursos.put("CANCELAR_OPERACION", gestorDiccionario.getMessage("sgo.cancelarOperacion",null,locale));
   hmRecursos.put("PROCESANDO_PETICION", gestorDiccionario.getMessage("sgo.procesandoPeticion",null,locale));
   hmRecursos.put("ERROR_NO_CONECTADO", gestorDiccionario.getMessage("sgo.errorNoConectado",null,locale));
   hmRecursos.put("ERROR_RECURSO_NO_DISPONIBLE", gestorDiccionario.getMessage("sgo.errorRecursoNoDisponible",null,locale));
   hmRecursos.put("ERROR_TIEMPO_AGOTADO", gestorDiccionario.getMessage("sgo.errorTiempoAgotado",null,locale));
   hmRecursos.put("ERROR_GENERICO_SERVIDOR", gestorDiccionario.getMessage("sgo.errorGenericoServidor",null,locale));
   hmRecursos.put("ERROR_INTERNO_SERVIDOR", gestorDiccionario.getMessage("sgo.errorInternoServidor",null,locale));
   hmRecursos.put("LISTADO_OPERACIONES_ERRADO", gestorDiccionario.getMessage("sgo.noListadoOperaciones",null,locale));
   hmRecursos.put("LISTADO_ESTACIONES_ERRADO", gestorDiccionario.getMessage("sgo.noListadoEstaciones",null,locale));
   hmRecursos.put("LISTADO_ESTACIONES_EXITOSO", gestorDiccionario.getMessage("sgo.listadoEstacionesExitoso",null,locale));
   hmRecursos.put("SELECCIONAR_ELEMENTO", gestorDiccionario.getMessage("sgo.seleccionarElemento",null,locale));
   //
   vista = new ModelAndView("recursos");
   vista.addObject("recursos",hmRecursos);
 } catch(Exception ex){
   
 }
 return vista;
 }
 
}
