/** 
 * Proyecto: Juego de la vida.
 * Implementa el concepto de Usuario de un sistema según el modelo 2. 
 * Se hace validación de datos pero no se gestionan todavía los errores correspondientes.
 * @since: prototipo1.0
 * @source: Usuario.java 
 * @version: 2.1 - 2017.04.16 
 * @author: ajp
 */

package modelo;

import util.Fecha;

public class Usuario extends Persona {
	private static final long serialVersionUID = 1L;
	private String idUsr;
	private Fecha fechaAlta;
	private ClaveAcceso claveAcceso;
	public enum RolUsuario { INVITADO, NORMAL, ADMINISTRADOR }
	private RolUsuario rol;

	/**
	 * Constructor convencional. Utiliza métodos set...()
	 * @param nif
	 * @param nombre
	 * @param apellidos
	 * @param domicilio
	 * @param correo
	 * @param fechaNacimiento
	 * @param fechaAlta
	 * @param claveAcceso
	 * @param rol
	 * @throws ModeloException 
	 */
	public Usuario(Nif nif, String nombre, String apellidos,
			DireccionPostal domicilio, Correo correo, Fecha fechaNacimiento,
			Fecha fechaAlta, ClaveAcceso claveAcceso, RolUsuario rol) throws ModeloException {
		super(nif, nombre, apellidos, domicilio, correo, fechaNacimiento);
		generarIdUsr();
		setDomicilio(domicilio);
		setCorreo(correo);
		setFechaNacimiento(fechaNacimiento);
		setFechaAlta(fechaAlta);
		setClaveAcceso(claveAcceso);
		setRol(rol);
	}

	/**
	 * Genera el idUsr con las letras iniciales del nombre, 
	 * primer y segundo apellido; seguido del el último dígito 
	 * del dni y la letra del nif. 
	 */
	private void generarIdUsr() {	
		if (nombre != null && apellidos != null && nif != null) {
			String idUsr = "" + nombre.charAt(0) + apellidos.charAt(0) +
					apellidos.charAt(apellidos.indexOf(" ")+1) +
					nif.getTexto().substring(7);
			this.idUsr = idUsr;
		}
	}

	/**
	 * Genera una variante cambiando la última letra del idUsr 
	 * por la siguiente en el alfabeto.
	 */
	public void generarVarianteIdUsr() {
		String alfabetoNif = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		String letraNueva;
		if (idUsr != null) {
			String letraAnterior = "" + idUsr.charAt(4);
			if (idUsr.charAt(4) == 'Z') {
				letraNueva = "" + 'A';
			}
			else {
				letraNueva = "" + alfabetoNif.charAt(alfabetoNif.indexOf(letraAnterior) + 1);
			}
			idUsr = idUsr.substring(0, 4) + letraNueva;
		}
	}

	/**
	 * Constructor por defecto. Utiliza constructor convencional.
	 * @throws ModeloException 
	 */
	public Usuario() throws ModeloException {
		this(new Nif(), "Nombre", "Apellidos1 Apellido2", new DireccionPostal(), new Correo(), 
				new Fecha(), new Fecha(), new ClaveAcceso(), RolUsuario.NORMAL);
	}

	/**
	 * Constructor copia. Utiliza constructor convencional.
	 * @param usr
	 * @throws ModeloException 
	 */
	public Usuario(Usuario usr) throws ModeloException {
		this(new Nif(usr.nif), usr.nombre, usr.apellidos, usr.domicilio, usr.correo,
				usr.fechaNacimiento, usr.fechaAlta, new ClaveAcceso(usr.claveAcceso), usr.rol);
	}

	public String getIdUsr() {
		return idUsr;
	}
	
	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) throws ModeloException {
		if (fechaAltaValida(fechaAlta)) {
		this.fechaAlta = fechaAlta;
		return;
		}
		throw new ModeloException("La fecha de alta: " + fechaAlta + " no es válida...");
	}

	/**
	 * Comprueba validez de una fecha de alta.
	 * @param fechaAlta.
	 * @return true si cumple.
	 */
	private boolean fechaAltaValida(Fecha fechaAlta) {
		assert fechaAlta != null;
		return fechaAltaCoherente(fechaAlta);
	}

	/**
	 * Comprueba coherencia de una fecha de alta.
	 * @param fechaAlta.
	 * @return true si cumple.
	 */
	private boolean fechaAltaCoherente(Fecha fechaAlta) {
		// Comprueba que fechaAlta no es, por ejemplo, del futuro
		// --Pendiente--
		return true;
	}

	public ClaveAcceso getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(ClaveAcceso claveAcceso) {
		assert claveAcceso != null;
		this.claveAcceso = claveAcceso;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		assert rol != null;
		this.rol = rol;
	}

	/**
	 * Redefine el método heredado de la clase Objecto.
	 * @return el texto formateado del estado -valores de atributos- de objeto de la clase Usuario.  
	 */
	@Override
	public String toString() {
		return String.format("%s%-16s %s\n%-16s %s\n%-16s %s\n%-16s %s\n", 
						super.toString(), "idUsr:", idUsr, "fechaAlta:", fechaAlta, "claveAcceso:", claveAcceso, "rol:", rol);		
	}
	
	/**
	 * Dos objetos son iguales si: 
	 * Son de la misma clase.
	 * Tienen los mismos valores en los atributos; o son el mismo objeto.
	 * @return falso si no cumple las condiciones.
	*/
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			if (this == obj) {
				return true;
			}
			if (super.equals(obj) &&
					idUsr.equals(((Usuario)obj).idUsr) &&
					fechaAlta.equals(((Usuario)obj).fechaAlta) &&
					claveAcceso.equals(((Usuario)obj).claveAcceso) &&
					rol.equals(((Usuario)obj).rol) 
					) {
				return true;
			}
		}
		return false;
	}

	/**
	 * hashCode() complementa al método equals y sirve para comparar objetos de forma 
	 * rápida en estructuras Hash. 
	 * Cuando Java compara dos objetos en estructuras de tipo hash (HashMap, HashSet etc)
	 * primero invoca al método hashcode y luego el equals.
	 * @return un número entero de 32 bit.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result
				+ ((claveAcceso == null) ? 0 : claveAcceso.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result
				+ ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result
				+ ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result
				+ ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((idUsr == null) ? 0 : idUsr.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	*/
	@Override
	public Usuario clone() {
		// Utiliza el constructor copia.
		Usuario clon = null;
		try {
			clon = new Usuario(this);
		} catch (ModeloException e) { }
		return clon;
	}

} // class

