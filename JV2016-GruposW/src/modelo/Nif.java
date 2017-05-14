/** 
 * Proyecto: Juego de la vida.
 * Implementa el concepto de Nif según el modelo 2
 * Se hace validación de datos pero no se gestionan todavía los errores correspondientes.
 * @since: prototipo1.2
 * @source: Nif.java 
 * @version: 2.1 - 2017.04.25
 * @author: ajp
 */

package modelo;

import java.io.Serializable;
import util.Formato;

public class Nif implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String texto;

	public Nif(String texto) throws ModeloException {
		setTexto(texto);
	}

	public Nif() throws ModeloException {
		this("00000000T");
	}

	public Nif(Nif nif) throws ModeloException {
		this(new String(nif.texto));
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) throws ModeloException {
		if (nifValido(texto)) {
			this.texto = texto;
			return;
		}
		throw new ModeloException("El NIF: " + texto + " no es válido...");	
	}

	/**
	 * Comprueba la validez del formato.
	 * @param texto del NIF
	 * @return true si la letra es correcta.
	 */
	private boolean nifValido(String texto) {
		assert texto != null;
		return	texto.matches(Formato.PATRON_NIF) && letraNIFValida(texto);
	}

	/**
	 * Comprueba la validez de la letra de un NIF
	 * @param texto del NIF
	 * @return true si la letra es correcta.
	 */
	private boolean letraNIFValida(String texto) {
		int numeroNIF = Integer.parseInt(texto.substring(0,8));
		if (texto.charAt(8) == "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numeroNIF % 23)) {
			return true;
		}
		return false;
	} 
	  
	@Override
	public String toString() {
		return texto;
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
		final int primo = 31;
		int result = 1;
		result = primo * result + ((texto == null) ? 0 : texto.hashCode());
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
			if (texto.equals(((Nif) obj).texto)) {
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
	public Nif clone() {
		// Utiliza el constructor copia.
		Nif clon = null;
		try {
			clon = new Nif(this);
		} 
		catch (ModeloException e) { }
		return clon;
	}

} // class
