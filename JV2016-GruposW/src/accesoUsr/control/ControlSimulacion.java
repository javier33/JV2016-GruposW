/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de una simulación. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlSimulación.java 
 *  @version: 2.1 - 2017.05.08
 *  @author: ajp
 */

package accesoUsr.control;

import accesoDatos.Datos;
import accesoUsr.consola.VistaSimulacion;
import modelo.Mundo;
import modelo.Simulacion;

public class ControlSimulacion {
	Datos datos = new Datos();
	final int CICLOS = 120;
	VistaSimulacion vista;
	Simulacion simulacion;
	Mundo mundo;
	
	public ControlSimulacion(Simulacion simulacion) {
		this.simulacion = simulacion;
		initControlSimulacion();
	}
	
	private void initControlSimulacion() {	
		mundo = simulacion.getMundo();	
		vista = new VistaSimulacion();
		arrancarSimulacion();
		vista.confirmar();
	}

	/**
	 * Ejecuta una simulación del juego de la vida, en la consola,
	 * durante un número de CICLOS.
	 */
	public void arrancarSimulacion() {
		int gen = 0; 		//Generaciones
		do {
			vista.mostrarMensaje("\nGeneración: " + gen);
			vista.mostrarMundo(this);
			mundo.actualizarMundo();
			gen++;
		}
		while (gen <= CICLOS);
	}
	
	public Simulacion getSimulacion() {
		return simulacion;
	}
	
	public Mundo getMundo() {
		return mundo;
	}
	
} // class
