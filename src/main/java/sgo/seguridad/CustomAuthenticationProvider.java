package sgo.seguridad;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
 
	   @Autowired
	    private UserDetailsServiceImpl userService;
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	GestorDirectorio gestorDirectorio=null;
        String nombreUsuario = authentication.getName();
        String clave = authentication.getCredentials().toString();
        String claveEncriptada ="";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        claveEncriptada = passwordEncoder.encode(clave);
        AuthenticatedUserDetails usuario =  (AuthenticatedUserDetails) userService.loadUserByUsername(nombreUsuario);
        gestorDirectorio = new GestorDirectorio();
        
        if (usuario == null) {
            throw new BadCredentialsException("Username not found.");
        }
         
        if (!passwordEncoder.matches(clave, usuario.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        
        //Boolean gf= gestorDirectorio.validarUsuario("acabeza", clave);
        
        
        return new UsernamePasswordAuthenticationToken(usuario, clave,  usuario.getAuthorities());
    }
 

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}