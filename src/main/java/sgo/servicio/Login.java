package sgo.servicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Login {
	@RequestMapping("/login")
	public ModelAndView mostrarLogin(){
		ModelAndView vista = null;
		try{
			vista= new ModelAndView("login");
		}catch(Exception ex){
			
		}
		return vista;
	}
	
	@RequestMapping("/invalida")
	public ModelAndView mostrarSesionInvalida(){
		ModelAndView vista = null;
		try{
			vista= new ModelAndView("login");
		}catch(Exception ex){
			
		}
		return vista;
	}
}
