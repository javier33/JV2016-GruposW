/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  principal del programa con un menú. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlPrincipal.java 
 *  @version: 2.1 - 2017.05.17
 *  @author: ajp
 */

package accesoUsr.control;

import java.util.ArrayList;

import accesoDatos.Datos;
import accesoUsr.consola.VistaPrincipal;
import modelo.SesionUsuario;
import modelo.Simulacion;

public class ControlPrincipal {

	private VistaPrincipal vistaMenu;
	private SesionUsuario sesionUsr;
	private Datos fachada;

	public ControlPrincipal(String idUsr) {
		initMenuPrincipal(idUsr);	
	}

	public ControlPrincipal() {
		this(null);
	}

	private void initMenuPrincipal(String idUsr) {
		fachada = new Datos();
		vistaMenu = new VistaPrincipal();
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
			mostrarSimulaciones();
			break;
		case 5:
			mostrarIdSimulaciones();
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
			mostrarMundos();
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
			mostrarUsuarios();
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
	
	private void mostrarSimulaciones() {
		vistaMenu.mostrarMensaje("Opción no disponible...");
		
	}
	
	private void mostrarIdSimulaciones() {
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
	
	private void mostrarMundos() {
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
	
	private void mostrarUsuarios() {
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
