package sgo.seguridad;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
/*import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;*/
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import sgo.utilidades.Constante;

public class GestorDirectorio {
	public Boolean validarUsuario(String nombreUsuario, String clave){
			Boolean resultado=false;
	        DirContext contextoDirectorio = null;  
	        Hashtable<String,String> parametros = new Hashtable<String,String>();
	        String usuarioDirectorio = "";
	        try {
	        	usuarioDirectorio = recuperarUsuarioDirectorio(nombreUsuario);
	        	if (usuarioDirectorio==null){
	        		throw new Exception("Usuario no encontrado");
	        	}
		        parametros.put(Context.INITIAL_CONTEXT_FACTORY, Constante.LDAP_CONTEXTO_FACTORIA);  
		        parametros.put(Context.PROVIDER_URL, Constante.LDAP_URL_PROVEEDOR);  
		        //env.put(Context.SECURITY_PROTOCOL, "ssl");  
		        parametros.put(Context.SECURITY_AUTHENTICATION, Constante.LDAP_METODO_AUTENTICACION);  
				parametros.put(Context.SECURITY_PRINCIPAL,usuarioDirectorio);  
				parametros.put(Context.SECURITY_CREDENTIALS,clave);
	            contextoDirectorio = new InitialDirContext(parametros);  
	            resultado=true;
	            contextoDirectorio.close();
	        }// end try  
	        catch (AuthenticationException autenticacionExcepcion){
	        	autenticacionExcepcion.printStackTrace();
	        }
	        catch (Exception generalExcepcion) {  
	        	generalExcepcion.printStackTrace();  
	        }  
	        return resultado;
	}
	
	public String recuperarUsuarioDirectorio(String nombreUsuario) {
		String respuesta=null;
		try {
			DirContext contextoDirectorio;
			Hashtable<String,String> parametros = new Hashtable<String,String>();
			parametros.put(Context.INITIAL_CONTEXT_FACTORY,Constante.LDAP_CONTEXTO_FACTORIA);
			parametros.put(Context.PROVIDER_URL, Constante.LDAP_URL_PROVEEDOR);			
			contextoDirectorio = new InitialDirContext(parametros);
			SearchControls controlesBusqueda = new SearchControls();
			controlesBusqueda.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filtro = "(&(uid="+nombreUsuario+")( mail="+nombreUsuario+Constante.LDAP_CORREO+"))";
			NamingEnumeration respuestaDirectorio = contextoDirectorio.search(Constante.LDAP_BASE_DN, filtro, controlesBusqueda);
			if(respuestaDirectorio.hasMoreElements()) {
				SearchResult resultadoBusqueda = (SearchResult)respuestaDirectorio.next();
				respuesta= resultadoBusqueda.getNameInNamespace();				
			}		
			contextoDirectorio.close();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
		return respuesta;
	}
}
