/** 
 * Proyecto: Juego de la vida.
 * Implementa el concepto de Persona un sistema según el modelo 2. 
 * Se hace validación de datos pero no se gestionan todavía los errores correspondiente.
 * @since: prototipo1.0
 * @source: Persona.java 
 * @version: 2.1 - 2017.04.16 
 * @author: ajp
 */

package modelo;

import java.io.Serializable;

import util.Fecha;
import util.Formato;

public  abstract class Persona implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	protected Nif nif;
	protected String nombre;
	protected String apellidos;
	protected DireccionPostal domicilio;
	protected Correo correo;
	protected Fecha fechaNacimiento;

	/**
	 * Constructor convencional. Utiliza métodos set...()
	 * @param nif
	 * @param nombre
	 * @param apellidos
	 * @param domicilio
	 * @param correo
	 * @param fechaNacimiento
	 * @throws ModeloException 
	 */
	public Persona(Nif nif, String nombre, String apellidos,
			DireccionPostal domicilio, Correo correo, Fecha fechaNacimiento) throws ModeloException {
		setNif(nif);
		setNombre(nombre);
		setApellidos(apellidos);
		setDomicilio(domicilio);
		setCorreo(correo);
		setFechaNacimiento(fechaNacimiento);
	}

	public Nif getNif() {
		return nif;
	}

	public void setNif(Nif nif) {
		assert nif != null;
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws ModeloException {	
		if (nombreValido(nombre)) {
			this.nombre = nombre;
			return;
		}
		throw new ModeloException("El formato del nombre: " + nombre + " no es válido...");
	}

	private boolean nombreValido(String nombre) {
		assert nombre != null;
		return	nombre.matches(Formato.PATRON_NOMBRE_PERSONA);
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) throws ModeloException {
		if (apellidoValido(apellidos)) {
			this.apellidos = apellidos;
			return;
		}
		throw new ModeloException("El formato de los apellidos: " + apellidos + " no es válido...");	
	}

	private boolean apellidoValido(String apellidos) {
		assert apellidos != null;
		return	nombre.matches(Formato.PATRON_APELLIDOS);
	}

	public DireccionPostal getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DireccionPostal domicilio) {
		assert domicilio != null;
		this.domicilio = domicilio;
	}

	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Fecha fechaNacimiento) throws ModeloException {
		if (fechaNacimientoValida(fechaNacimiento)) {
			this.fechaNacimiento = fechaNacimiento;
			return;
		}
		throw new ModeloException("La fecha de nacimiento: " + fechaNacimiento + " no es válida...");
	}

	/**
	 * Comprueba validez de una fecha de nacimiento.
	 * @param fechaNacimiento.
	 * @return true si cumple.
	 */
	private boolean fechaNacimientoValida(Fecha fechaNacimiento) {
		assert fechaNacimiento != null;
		return fechaNacimientoCoherente(fechaNacimiento);
	}

	/**
	 * Comprueba coherencia de una fecha de nacimiento.
	 * @param fechaNacimiento.
	 * @return true si cumple.
	 */
	private boolean fechaNacimientoCoherente(Fecha fechaNacimiento) {
		// Comprueba que fechaNacimiento no es, por ejemplo, del futuro
		// --Pendiente--
		return true;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {	
		assert correo != null;
		this.correo = correo;
	}

	/**
	 * Redefine el método heredado de la clase Objecto.
	 * @return el texto formateado del estado -valores de atributos- de objeto de la clase Usuario.  
	 */
	@Override
	public String toString() {
		return String.format(
				"%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n", 
						"nif:", nif, "nombre:", nombre, "apellidos:", apellidos, 
						"domicilio:", domicilio, "correo:", correo, "fechaNacimiento:", fechaNacimiento);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result
				+ ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result
				+ ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			if (this == obj) {
				return true;
			}
			if (nif.equals(((Usuario)obj).nif) &&
					nombre.equals(((Usuario)obj).nombre) &&
					apellidos.equals(((Usuario)obj).apellidos) &&
					domicilio.equals(((Usuario)obj).domicilio) &&
					correo.equals(((Usuario)obj).correo) &&
					fechaNacimiento.equals(((Usuario)obj).fechaNacimiento) 
					) {
				return true;
			}
		}
		return false;
	}

} // class
