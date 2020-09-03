package sgo.seguridad;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import sgo.entidad.Rol;
import sgo.entidad.Operacion;
import sgo.entidad.Usuario;

public class AuthenticatedUserDetails extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _id;
	private String identidad;
	private String _email;
	private Rol _rol;
	private int _tipo;
	private String _zonaHoraria;
	private Operacion _operacion;

	public AuthenticatedUserDetails(Usuario fUsuario, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {  
		super(fUsuario.getNombre(), fUsuario.getClave().trim(),true, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);  
		this._id = fUsuario.getId();  
		this.identidad = fUsuario.getIdentidad();
		this._email = fUsuario.getEmail();
		this._tipo= fUsuario.getTipo();
		this._zonaHoraria = fUsuario.getZonaHoraria();
		this._rol= fUsuario.getRol();
		this._operacion = fUsuario.getOperacion();
	}
	
	public String getNombre(){
		return super.getUsername();
	}
	
	public String getEmail(){
		return this._email;
	}
	
	public String getZonaHoraria(){
		return this._zonaHoraria;
	}
	
	
	public int getTipo(){
		return this._tipo;
	}
	public Rol getRol(){
		return this._rol;	
	}
	
	public Operacion getOperacion(){
		return this._operacion;	
	}
	
	public int getID(){
		return this._id;
	}

	/**
	 * @return the identidad
	 */
	public String getIdentidad() {
		return identidad;
	}
}