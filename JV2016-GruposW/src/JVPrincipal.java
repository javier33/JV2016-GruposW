/** 
 * Proyecto: Juego de la vida.
 * Secuencia principal de arraque del programa.  
 * @since: prototipo1.0
 * @source: JVPrincipal.java 
 * @version: 2.1 - 2017/05/14 
 * @author: ajp
 */

import accesoDatos.Datos;
import accesoDatos.test.DatosPrueba;
import accesoUsr.swing.control.ControlPrincipal;

public class JVPrincipal {

	public static void main(String[] args) {	
		
		//DatosPrueba.cargarDatosPrueba();
		
//		Datos fachada = new Datos();
//		System.out.println("USUARIOS:\n" + fachada.toStringIdUsuarios());
//		System.out.println("SESIONES:\n" + fachada.toStringIdSesiones());
//		System.out.println("SIMULACIONES:\n" + fachada.toStringIdSimulaciones());
//		System.out.println("MUNDOS:\n" + fachada.toStringIdMundos());
//		System.out.println("PATRONES:\n" + fachada.toStringIdPatrones());
		
		new ControlPrincipal();
	}
	
} //class
