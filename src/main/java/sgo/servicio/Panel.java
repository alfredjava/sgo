/**
 * 
 */
package sgo.servicio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author rafael
 *
 */
@Controller
public class Panel {
	@RequestMapping("/panel")
	public ModelAndView mostrarFormularioPanel(){
		ModelAndView vista =null;
		try {
			vista = new ModelAndView("plantilla");
			vista.addObject("vistaJSP","panel.jsp");
		} catch(Exception ex){
			
		}
		return vista;
	}
}
