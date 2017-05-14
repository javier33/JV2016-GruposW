/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  del menú principal del programa. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlMenuPrincipal.java 
 *  @version: 2.1 - 2017.05.12
 *  @author: ajp
 */
package accesoUsr.control;

import modelo.SesionUsuario;

public class ControlMenuPrincipal {

	private SesionUsuario sesionUsr;

	/**
	 * @param sesionUsr (SesionUsuario)
	 */
	public ControlMenuPrincipal(SesionUsuario sesionUsr) {
		this.sesionUsr = sesionUsr;
	}

	public SesionUsuario getSesion() {
		return sesionUsr;
	}

	public void altaUsuario() {
		//Usuario de la sesión actual
		this.sesionUsr.getUsr();
	}

	public void ModificacionesUsuario() {
		// TODO Auto-generated method stub
		
	}

	public void bajaUsuario() {
		// TODO Auto-generated method stub
		
	}



		

}
