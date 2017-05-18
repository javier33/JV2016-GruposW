/** 
 * Proyecto: Juego de la vida.
 * Maneja los errores de acceso del usuario.
 *  @since: prototipo2.1
 *  @source: UsrException.java 
 *  @version: 2.1 - 2017.04.5
 *  @author: ajp
 */

package accesoUsr;

public class UsrException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Excepción por defecto sin mensaje
	 */
	public UsrException() {
		super();
	}
	
	/**
	 * Excepción con mensaje
	 * @param msg - el mensaje de error asociado
	 */
	public UsrException(String msg) {
		super(msg);
	}
}
