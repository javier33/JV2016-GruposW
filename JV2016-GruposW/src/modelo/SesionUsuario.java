/** 
 * Proyecto: Juego de la vida.
 * Implementa el concepto de SesionUsuario según el modelo 2
 * @since: prototipo1.0
 * @source: SesionUsuario.java 
 * @version: 2.1 - 2017.04.20
 * @author: ajp
 */

package modelo;

import java.io.Serializable;

import util.Fecha;

public class SesionUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Usuario usr;  
	private Fecha fecha; 
	public enum EstadoSesion { EN_PREPARACION, ACTIVA, CERRADA }
	private EstadoSesion estado;
	
	/**
	 * @param usr
	 * @param fecha
	 * @throws ModeloException 
	 */
	public SesionUsuario(Usuario usr, Fecha fecha, EstadoSesion estado) throws ModeloException {
		setUsr(usr);
		setFecha(fecha);
		setEstado(estado);
	}
	
	public SesionUsuario() throws ModeloException {
		this(new Usuario(), new Fecha(), EstadoSesion.EN_PREPARACION);
	}

	public SesionUsuario(SesionUsuario sesion) throws ModeloException {
		this(sesion.usr, new Fecha(sesion.fecha), sesion.estado);
	}
	
	public Usuario getUsr() {
		return usr;
	}
	
	public Fecha getFecha() {
		return fecha;
	}

	public EstadoSesion getEstado() {
		return estado;
	}
	/**
	 * Obtiene idSesion concatenando idUsr + un número como texto:
	 * @return idSesion único generado.
	 */
	public String getIdSesion() {
		return	usr.getIdUsr() + "-" + fecha.toTexto();
	}
	
	public void setUsr(Usuario usr) {
		assert usr != null;
		this.usr = usr;
	}
	
	public void setFecha(Fecha fecha) throws ModeloException {
		if (fechaValida(fecha)) {
			this.fecha = fecha;
			return;
		}
		throw new ModeloException("La fecha: " + fecha + " no es válida...");
	}

	/**
	 * Comprueba validez de una fecha de sesión.
	 * @param fecha.
	 * @return true si cumple.
	 */
	private boolean fechaValida(Fecha fecha) {
		assert fecha != null;
		return fechaCoherente(fecha);
	}
	
	/**
	 * Comprueba coherencia de una fecha.
	 * @param fecha.
	 * @return true si cumple.
	 */
	private boolean fechaCoherente(Fecha fecha) {
		// Comprueba que fecha no es, por ejemplo, del futuro
		// --Pendiente--
		return true;
	}
	
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoSesion estado) {
		assert estado != null;
		this.estado = estado;
	}
	
	/**
	 * Redefine el método heredado de la clase Objecto.
	 * @return el texto formateado del estado (valores de atributos) 
	 * del objeto de la clase SesionUsuario  
	 */
	@Override
	public String toString() {
		return String.format("SesionUsuario \n[usr=%s, \nfecha=%s, \nestado=%s]",
				usr, fecha, estado);
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
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((usr == null) ? 0 : usr.hashCode());
		return result;
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
			if (usr.equals(((SesionUsuario)obj).usr) &&
					fecha.equals(((SesionUsuario)obj).fecha) &&
					estado.equals(((SesionUsuario)obj).estado) 
					) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	*/
	@Override
	public SesionUsuario clone() {
		// Utiliza el constructor copia.
		SesionUsuario clon = null;
		try {
			clon = new SesionUsuario(this);
		} catch (ModeloException e) { }
		return clon;
	}

} // class
