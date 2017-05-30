package accesoUsr.swing;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Color;

public class VistaPrincipal extends JFrame implements OperacionesVista{
	private static final long serialVersionUID = 1L;
	private JMenuBar MenuBar;
	
	private JMenu mnFicheros;
	private JMenuItem mntnGuardar;
	private JMenuItem mntnSalir;
	
	private JMenu mnSimulaciones;
	private JMenuItem mntmCrearNuevaSimulacion;
	private JMenuItem mntmModificarSimulacion;
	private JMenuItem mntnEliminarSimulacion;
	private JMenuItem mntnMostrarDatosSimulacion;
	private JMenuItem mntnDemoSimulacion;
	
	private JMenu mnMundos;
	private JMenuItem mntnCrearNuevoMundo;
	private JMenuItem mntnModificarMundo;
	private JMenuItem mntnEliminarMundo;
	private JMenuItem mntnMostrarDatosMundo;
	
	private JMenu mnUsuarios;
	private JMenuItem mntnCrearUsuario;
	private JMenuItem mntnModificarUsuario;
	private JMenuItem mntnEliminarUsuario;
	private JMenuItem mntnMostrarDatosUsuario;
	
	private JMenu mnSesiones;
	private JMenuItem mntnCrearNuevaSesion;
	private JMenuItem mntnModificarSesion;
	private JMenuItem mntnEliminarSesion;
	private JMenuItem mntnMostrarDatosSesion;
	
	private JMenu mnAcercade;

	public JMenu getMnFicheros() {
		return mnFicheros;
	}

	public JMenuItem getMntnGuardar() {
		return mntnGuardar;
	}


	public JMenuItem getMntnSalir() {
		return mntnSalir;
	}


	public JMenu getMnSimulaciones() {
		return mnSimulaciones;
	}


	public JMenuItem getMntmCrearNuevaSimulacion() {
		return mntmCrearNuevaSimulacion;
	}


	public JMenuItem getMntmModificarSimulacion() {
		return mntmModificarSimulacion;
	}


	public JMenuItem getMntnEliminarSimulacion() {
		return mntnEliminarSimulacion;
	}


	public JMenuItem getMntnMostrarDatosSimulacion() {
		return mntnMostrarDatosSimulacion;
	}

	public JMenuItem getMntnDemoSimulacion() {
		return mntnDemoSimulacion;
	}

	public JMenu getMnMundos() {
		return mnMundos;
	}


	public JMenuItem getMntnCrearNuevoMundo() {
		return mntnCrearNuevoMundo;
	}

	public JMenuItem getMntnModificarMundo() {
		return mntnModificarMundo;
	}

	public JMenuItem getMntnEliminarMundo() {
		return mntnEliminarMundo;
	}

	public JMenuItem getMntnMostrarDatosMundo() {
		return mntnMostrarDatosMundo;
	}

	public JMenu getMnUsuarios() {
		return mnUsuarios;
	}

	public JMenuItem getMntnCrearUsuario() {
		return mntnCrearUsuario;
	}

	public JMenuItem getMntnModificarUsuario() {
		return mntnModificarUsuario;
	}

	public JMenuItem getMntnEliminarUsuario() {
		return mntnEliminarUsuario;
	}

	public JMenuItem getMntnMostrarDatosUsuario() {
		return mntnMostrarDatosUsuario;
	}

	public JMenu getMnSesiones() {
		return mnSesiones;
	}

	public JMenuItem getMntnCrearNuevaSesion() {
		return mntnCrearNuevaSesion;
	}

	public JMenuItem getMntnModificarSesion() {
		return mntnModificarSesion;
	}

	public JMenuItem getMntnEliminarSesion() {
		return mntnEliminarSesion;
	}

	public JMenuItem getMntnMostrarDatosSesion() {
		return mntnMostrarDatosSesion;
	}

	public JMenu getMnAcercade() {
		return mnAcercade;
	}
	
}
