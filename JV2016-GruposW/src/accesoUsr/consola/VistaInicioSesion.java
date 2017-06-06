/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la presentación
 *  del inicio de sesión de usuario. 
 *  Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: VistaInicioSesion.java 
 *  @version: 2.1 - 2017.05.16
 *  @author: ajp
 */

package accesoUsr.consola;

import java.io.Console;
import java.util.Scanner;

import accesoUsr.OperacionesVistaSesion;

public class VistaInicioSesion implements OperacionesVistaSesion {
	
	private Console consola;
	
	public VistaInicioSesion() {
		consola = System.console();
	}
	
	@Override
	public String pedirIdUsr() {	
		this.mostrarMensaje("Introduce el idUsr: ");
		if (consola != null) {
			return consola.readLine();
		}
		// Desde entorno Eclipse la consola falla.
		return new Scanner(System.in).nextLine();
	}
	
	@Override
	public String pedirClaveAcceso() {
		this.mostrarMensaje("Introduce clave acceso: ");
		if (consola != null) {
			return new String(consola.readPassword());
		}
		// Desde entorno Eclipse la consola falla.
		return new Scanner(System.in).nextLine();
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		if (consola != null) {
			consola.writer().println(mensaje);
			return;
		}
		System.out.println(mensaje);
	}

} //class
