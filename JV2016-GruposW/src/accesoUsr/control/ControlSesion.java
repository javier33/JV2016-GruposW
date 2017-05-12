/** 
 * Proyecto: Juego de la vida.
 * Controla la comunicacion entre VistaSesionTexto y SesionUsuario
 * @since: prototipo2.0
 * @source: ControlSesion.java 
 * @version: 2.1 - 2017/04/03
 * @author: Amanda Iniesta Sepúlveda y Alejandro Sánchez Jurado
 */
package accesoUsr.control;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoUsr.Presentacion;
import accesoUsr.vista.VistaSesionTexto;
import modelo.ClaveAcceso;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.Usuario;
import util.Fecha;

public class ControlSesion {
	private VistaSesionTexto vista;
	private SesionUsuario sesion;
	private Usuario usrSesion;
	private Datos fachada;

	/**
	 * @param vistaSesionTexto
	 * @param sesion
	 * @param usrSesion
	 * @param fachada
	 */
	public ControlSesion(VistaSesionTexto vista, SesionUsuario sesion, Usuario usrSesion, Datos fachada) {
		setVista(vista);
		setSesion(sesion);
		setUsrSesion(usrSesion);
		setFachada(fachada);
	}

	public ControlSesion() throws ModeloException {
		this(new VistaSesionTexto(), new SesionUsuario(), new Usuario(), new Datos());
	}

	/**
	 * Constructor copia
	 * 
	 * @param controlSesion
	 */
	public ControlSesion(ControlSesion controlSesion) {
		this(controlSesion.vista, controlSesion.sesion, controlSesion.usrSesion, controlSesion.fachada);
	}

	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * 
	 * @return el objeto clonado.
	 */
	@Override
	public Object clone() {
		return new ControlSesion(this);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sesion == null) ? 0 : sesion.hashCode());
		result = prime * result + ((fachada == null) ? 0 : fachada.hashCode());
		result = prime * result + ((usrSesion == null) ? 0 : usrSesion.hashCode());
		result = prime * result + ((vista == null) ? 0 : vista.hashCode());
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ControlSesion other = (ControlSesion) obj;
		if (sesion == null) {
			if (other.sesion != null)
				return false;
		} else if (!sesion.equals(other.sesion))
			return false;
		if (fachada == null) {
			if (other.fachada != null)
				return false;
		} else if (!fachada.equals(other.fachada))
			return false;
		if (usrSesion == null) {
			if (other.usrSesion != null)
				return false;
		} else if (!usrSesion.equals(other.usrSesion))
			return false;
		if (vista == null) {
			if (other.vista != null)
				return false;
		} else if (!vista.equals(other.vista))
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new String(getVista() 
				+ "\n" + getSesion() 
				+ "\n" + getUsrSesion() 
				+ "\n" + getFachada());

	}

	/**
	 * @return the vistaSesionTexto
	 */
	public VistaSesionTexto getVista() {
		return vista;
	}

	/**
	 * @return the sesion
	 */
	public SesionUsuario getSesion() {
		return sesion;
	}

	/**
	 * @return the usrSesion
	 */
	public Usuario getUsrSesion() {
		return usrSesion;
	}

	/**
	 * @return the fachada
	 */
	public Datos getFachada() {
		return fachada;
	}

	/**
	 * @param vistaSesionTexto
	 *            the vistaSesionTexto to set
	 */
	public void setVista(VistaSesionTexto vista) {
		this.vista = vista;
	}

	/**
	 * @param sesion
	 *            the sesion to set
	 */
	public void setSesion(SesionUsuario sesion) {
		this.sesion = sesion;
	}

	/**
	 * @param usrSesion
	 *            the usrSesion to set
	 */
	public void setUsrSesion(Usuario usrSesion) {
		this.usrSesion = usrSesion;
	}

	/**
	 * @param fachada
	 *            the fachada to set
	 */
	public void setFachada(Datos fachada) {
		this.fachada = fachada;
	}

	private boolean initSesionUsuario() throws ModeloException, DatosException {
		boolean todoCorrecto = false; // Control de credenciales de usuario.
		Usuario usrSesion = null; // Usuario en sesión.
		int intentos = 3; // Contandor de intentos.

		do {
			// Pide usuario y contraseña.
			String idUsr = vista.pedirIdUsr();
			String clave = vista.pedirClaveAcceso();

			// obtener usuario coincidente con las credenciales.
			System.out.println(idUsr);
			usrSesion = fachada.obtenerUsuario(idUsr);
			if (usrSesion != null) {
				ClaveAcceso claveIntroducida = new ClaveAcceso(clave);
				if (usrSesion.getClaveAcceso().equals(claveIntroducida)) {
					todoCorrecto = true;
				}
			}
			if (todoCorrecto == false) {
				intentos--;
				vista.mostrar("Credenciales incorrectas...");
				vista.mostrar("Quedan " + intentos + " intentos... ");
			}
		} while (!todoCorrecto && intentos > 0);

		if (todoCorrecto) {
			// Registra sesion de usuario.
			SesionUsuario sesion = new SesionUsuario();
			sesion.setUsr(usrSesion);
			sesion.setFecha(new Fecha());// Fecha del sistema.
			fachada.altaSesion(sesion);

			vista.mostrar("Sesión: " + fachada.obtenerSesion(sesion) + '\n' + "Iniciada por: " + usrSesion.getNombre()
					+ " " + usrSesion.getApellidos());
			System.out.println();
			return true;
		}
		return false;

	}

	private void initControlSesion(String idusr) {
		vista = new VistaSesionTexto();
		fachada = new Datos();

		try {
			initSesionUsuario();
		} catch (ModeloException | DatosException e) {
			e.printStackTrace();
		}
		Presentacion present = new Presentacion();
		present.arrancarSimulacion();

		fachada.cerrar();

	}

}