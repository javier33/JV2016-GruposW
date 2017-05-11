/** Proyecto: Juego de la vida.
 *  Clase del modelo vista controlador.
 *  @since: 2.1
 *  @source: vistaSimulacionTexto.java 
 *  @version: 2.1 - 2017.05.11
 *  @author: Alfonso Pozo Sánchez
 */

package accesoUsr.vista;

import java.util.ResourceBundle.Control;
import java.util.Scanner;

public class vistaSimulacionTexto {

private	Scanner teclado ;

public vistaSimulacionTexto() {
	teclado = new Scanner(System.in);
	

}

/**
 * Despliega en la consola el estado almacenado, corresponde
 * a una generación del Juego de la vida.
 */
private void mostrarMundo(ControlSimulacion control) {
	byte[][] espacio = control.getControl.getEspacio();
	for (int i = 0; i < espacio.length; i++) {
		for (int j = 0; j < espacio.length; j++) {
			System.out.print((espacio[i][j] == 1) ? "|o" : "| ");
		}
		System.out.println("|");
	}

}
/**
 * 
 * Método para mostrar cualquier mensaje por consola
 */
public void mostrar(String texto) {
	System.out.println(texto);
}

}


