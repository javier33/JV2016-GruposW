/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la visualización e interacción.
 *  @since: prototipo1.2
 *  @source: VistaSesionTexto.java 
 *  @version: 2.1 - 2017.05.09
 *  @author: Yeray Pérez Pancorbo
 */

package accesoUsr.vista;

import java.util.Scanner;

public class VistaSesionTexto {

	private Scanner teclado;

	/**
	 * Constructor por defecto.
	 */
	public VistaSesionTexto() {
		teclado = new Scanner(System.in);
	}

	/**
	 * Pide un idUsr por teclado. 
	 * @return - el idUsr introducido.
	 */	
	public String pedirIdUsr() {
		System.out.println("Introduce el IDUsr: ");
		return teclado.nextLine();
	}

	/**
	 * Pide una clave de acceso por teclado. 
	 * @return - la clave introducido.
	 */
	public String pedirClaveAcceso() {
		System.out.println("Introduce la clave de acceso: ");
		return teclado.nextLine();
	}

	/**
	 *Muestra cualquier mensaje por consola.
	 *@param - el mensaje a mostrar.
	 */
	public void mostrar(String mensaje) {
		System.out.println(mensaje);
	}

}
