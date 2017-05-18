/** 
 * Proyecto: Juego de la vida.
 * Maneja los errores de las clases del modelo.
 *  @since: prototipo2.1
 *  @source: ModeloException.java 
 * @version: 2.1 - 2017.04.05
 *  @author: ajp
 */

package modelo;

public class ModeloException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Excepción por defecto sin mensaje
	 */
	public ModeloException() {
		super();
	}
	
	/**
	 * Excepción con mensaje
	 * @param msg - el mensaje de error asociado
	 */
	public ModeloException(String msg) {
		super(msg);
	}
}
