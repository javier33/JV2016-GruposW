/** 
 * Proyecto: Juego de la vida.
 * Secuencia principal de arraque del programa.
 * Implementación del control de inicio de sesión y ejecución de la simulación por defecto.  
 * @since: prototipo1.0
 * @source: JVPrincipal.java 
 * @version: 2.1 - 2017/05/09 
 * @author: ajp
 */

import accesoDatos.Datos;
import accesoDatos.test.DatosPrueba;
import accesoUsr.control.ControlSesion;

public class JVPrincipal {

	public static void main(String[] args) {	
		
		//Datos fachada = new Datos();
		//DatosPrueba.cargarDatosPrueba();	
		//System.out.println(fachada.toStringDatosUsuarios());
		//System.out.println(fachada.toStringIdSesiones());
		//System.out.println(fachada.toStringIdSimulaciones());
		
		if (args.length == 0) { 
			new ControlSesion();
		}	
		else {
			new ControlSesion(args[0]);
		}	
	}
	
} //class
