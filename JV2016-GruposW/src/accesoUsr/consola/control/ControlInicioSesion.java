/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de inicio de sesión de usuario. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlInicioSesion.java 
 *  @version: 2.1 - 2017.05.08
 *  @author: ajp
 */

package accesoUsr.consola.control;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoUsr.consola.VistaInicioSesion;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Usuario;
import util.Fecha;

public class ControlInicioSesion {
	private VistaInicioSesion vistaSesion;
	private Usuario usrSesion;
	private SesionUsuario sesion;
	private Datos fachada;

	public ControlInicioSesion() {
		this(null);
	}

	public ControlInicioSesion(String idUsr) {
		initControlSesion(idUsr);
	}

	private void initControlSesion(String idUsr) {
		fachada = new Datos();
		vistaSesion = new VistaInicioSesion();
		vistaSesion.mostrarMensaje("JV-2016");
		iniciarSesionUsuario(idUsr);
	}

	/**
	 * Controla el acceso de usuario 
	 * y registro de la sesión correspondiente.
	 * @param credencialUsr ya obtenida, puede ser null.
	 */
	private void iniciarSesionUsuario(String idUsr) {
		int intentos = new Integer(Configuracion.get().getProperty("sesion.intentosFallidos"));			// Contandor de intentos.
		String credencialUsr = idUsr;
		do {
			if (idUsr == null) {
				// Pide credencial usuario si llega null.
				credencialUsr = vistaSesion.pedirIdUsr();	
			}
			else {
				vistaSesion.mostrarMensaje(credencialUsr);
			}	
			credencialUsr = credencialUsr.toUpperCase();
			String clave = vistaSesion.pedirClaveAcceso();

			// Busca usuario coincidente con credencial.
			vistaSesion.mostrarMensaje(credencialUsr);
			usrSesion = fachada.obtenerUsuario(credencialUsr);
			if ( usrSesion != null) {			
				try {
					if (usrSesion.getClaveAcceso().equals(new ClaveAcceso(clave))) {
						registrarSesion();
						break;
					}
				} 
				catch (ModeloException e) {
					e.printStackTrace();
				}
				intentos--;
				vistaSesion.mostrarMensaje("Credenciales incorrectas...");
				vistaSesion.mostrarMensaje("Quedan " + intentos + " intentos... ");
			}
		}
		while (intentos > 0);
	
		if (intentos <= 0){
			vistaSesion.mostrarMensaje("Fin del programa...");
			fachada.cerrar();
			System.exit(0);	
		}
	}

	public SesionUsuario getSesion() {
		return sesion;
	}
	
	/**
	 * Crea la sesion de usuario 
	 */
	private void registrarSesion() {
		// Registra sesión.
		// Crea la sesión de usuario en el sistema.
		try {
			sesion = new SesionUsuario(usrSesion, new Fecha(), EstadoSesion.ACTIVA);
			fachada.altaSesion(sesion);
			
		} 
		catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}	
		vistaSesion.mostrarMensaje("Sesión: " + sesion.getIdSesion()
		+ '\n' + "Iniciada por: " + usrSesion.getNombre());	
	}

} //class
