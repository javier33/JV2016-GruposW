/** 
 * Proyecto: Juego de la vida.
 * Secuencia principal de arraque del programa. 
 * @since: prototipo1.0
 * @source: JVPrincipal.java 
 * @version: 2.1 - 2017/05/17 
 * @author: ajp
 */

import accesoDatos.Datos;
import accesoDatos.test.DatosPrueba;
import accesoUsr.control.ControlMenuPrincipal;

public class JVPrincipal {

	public static void main(String[] args) {	

		//DatosPrueba.cargarDatosPrueba();
		
		//Datos fachada = new Datos();
		//System.out.println(fachada.toStringDatosUsuarios());
		//System.out.println(fachada.toStringIdSesiones());
		//System.out.println(fachada.toStringIdSimulaciones());
		
		if (args.length == 0) { 
			new ControlMenuPrincipal();
		}	
		else {
			new ControlMenuPrincipal(args[0]);
		}	
	}
	
} //class
