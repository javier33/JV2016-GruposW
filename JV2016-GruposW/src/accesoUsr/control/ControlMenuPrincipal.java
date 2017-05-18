/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  del menú principal del programa. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlMenuPrincipal.java 
 *  @version: 2.1 - 2017.05.17
 *  @author: ajp
 */
package accesoUsr.control;

import java.util.ArrayList;

import accesoDatos.Datos;
import accesoUsr.consola.VistaMenuPrincipal;
import modelo.SesionUsuario;
import modelo.Simulacion;

public class ControlMenuPrincipal {

	private VistaMenuPrincipal vistaMenu;
	private SesionUsuario sesionUsr;
	private Datos fachada;

	/**
	 * @param sesionUsr (SesionUsuario)
	 */
	public ControlMenuPrincipal(String idUsr) {
		initMenuPrincipal(idUsr);	
	}

	public ControlMenuPrincipal() {
		this(null);
	}

	private void initMenuPrincipal(String idUsr) {
		fachada = new Datos();
		vistaMenu = new VistaMenuPrincipal();
		this.sesionUsr = new ControlSesion(idUsr).getSesion();
		secuenciaPrincipal();
	}

	private void secuenciaPrincipal() {
		do {
			vistaMenu.mostrar();
			vistaMenu.pedirOpcion();
			procesarOpcion();	
		} while (true);
		
	}

	private void procesarOpcion() {
		switch (vistaMenu.getOpcionActiva()) {
		case 0:
			salir();
		
		// Simulaciones
		case 1:
			crearNuevaSimulacion();
			break;
		case 2:
			modificarSimulacion();
			break;
		case 3:
			eliminarSimulacion();
			break;
		case 4:
			MostrarSimulaciones();
			break;
		case 5:
			MostrarIdSimulaciones();
			break;
		case 6:
			ejecutarDemoSimulacion();
			break;
		
		// Simulaciones
		case 7:
			crearNuevoMundo();
			break;
		case 8:
			modificarMundo();
			break;
		case 9:
			eliminarMundo();
			break;
		case 10:
			MostrarMundos();
			break;
		
		// Usuarios
		case 11:
			crearNuevoUsuario();
			break;
		case 12:
			modificarUsuario();
			break;
		case 13:
			eliminarUsuario();
			break;
		case 14:
			MostrarUsuarios();
			break;
		
		// Sesiones
		case 15:
			modificarSesion();
			break;
		case 16:
			eliminarSesion();
			break;
		case 17:
			mostrarSesiones();
			break;
		case 18:
			mostrarIdSesiones();
			break;
		default:
			vistaMenu.mostrarMensaje("Opción incorrecta...");
			break;
		}	
	}

	private void salir() {
		fachada.cerrar();
		vistaMenu.mostrarMensaje("\nFin de programa...");	
		System.exit(1); 
	}	
	
	// Simulaciones
	private void crearNuevaSimulacion() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void modificarSimulacion() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarSimulacion() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void MostrarSimulaciones() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void MostrarIdSimulaciones() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}

	private void ejecutarDemoSimulacion() {
		ArrayList<Simulacion> simulacionesUsrActivo 
		= new ArrayList<Simulacion>(fachada.obtenerSimulacionesUsuario(sesionUsr.getUsr().getIdUsr()));
		// La simulación predeterminada-demo es la primera del usuario predeterminado Invitado
		new ControlSimulacion(simulacionesUsrActivo.get(0));		
	}
	
	// Mundos
	private void crearNuevoMundo() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}

	private void modificarMundo() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarMundo() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void MostrarMundos() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	// Usuarios	
	private void crearNuevoUsuario() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void modificarUsuario() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarUsuario() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void MostrarUsuarios() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	// Sesiones	
	private void modificarSesion() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void eliminarSesion() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void mostrarSesiones() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void mostrarIdSesiones() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}

} // class
