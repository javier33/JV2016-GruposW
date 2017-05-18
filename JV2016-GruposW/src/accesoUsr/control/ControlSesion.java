/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de inicio de sesión de usuario. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlSesion.java 
 *  @version: 2.1 - 2017.05.08
 *  @author: ajp
 */

package accesoUsr.control;

import accesoDatos.DatosException;

import java.awt.List;
import java.util.ArrayList;

import accesoDatos.Datos;
import accesoUsr.consola.VistaSesion;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Simulacion;
import modelo.Usuario;
import util.Fecha;

public class ControlSesion {
	private VistaSesion vista;
	private Usuario usrSesion;
	private SesionUsuario sesion;
	private Datos fachada;

	public ControlSesion() {
		this(null);
	}

	public ControlSesion(String idUsr) {
		initControlSesion(idUsr);
	}

	private void initControlSesion(String idUsr) {
		fachada = new Datos();
		vista = new VistaSesion();
		vista.mostrarMensaje("JV-2016");
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
				credencialUsr = vista.pedirIdUsr();	
			}
			else {
				vista.mostrarMensaje(credencialUsr);
			}	
			credencialUsr = credencialUsr.toUpperCase();
			String clave = vista.pedirClaveAcceso();

			// Busca usuario coincidente con credencial.
			vista.mostrarMensaje(credencialUsr);
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
				vista.mostrarMensaje("Credenciales incorrectas...");
				vista.mostrarMensaje("Quedan " + intentos + " intentos... ");
			}
		}
		while (intentos > 0);
	
		if (intentos <= 0){
			vista.mostrarMensaje("Fin del programa...");
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
		} 
		catch (ModeloException e1) {
			e1.printStackTrace();
		}
		try {
			fachada.altaSesion(sesion);
		} catch (DatosException e) {
			e.printStackTrace();
		}	
		vista.mostrarMensaje("Sesión: " + sesion.getIdSesion()
		+ '\n' + "Iniciada por: " + usrSesion.getNombre());	
	}

} //class
