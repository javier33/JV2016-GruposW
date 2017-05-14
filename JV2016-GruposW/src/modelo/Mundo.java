/** 
 * Proyecto: Juego de la vida.
 * Representa el espacio y las leyes que determinan un mundo de simulación del según el modelo 2.
 * @since: prototipo2.0
 * @source: Mundo.java 
 * @version: 2.1 - 2017.05.05
 * @author: ajp
 */

package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import config.Configuracion;
import util.Formato;

public class Mundo implements Leyes, Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private static int sizePredeterminado = new Integer(Configuracion.get().getProperty("mundo.sizePredeterminado"));
	private String nombre;
	private List<Integer> constantes;
	private Map<Patron, Posicion> distribucion;
	private byte[][] espacio;	

	/**
	 * Constructor convencional.
	 * Establece el valor inicial de cada uno de los atributos.
	 * Recibe parámetros que se corresponden con los atributos.
	 * Utiliza métodos set... para la posible verificación.
	 * @param nombre
	 * @param constantes
	 * @param distribucion
	 * @param espacio
	 * @throws ModeloException 
	 */
	public Mundo(String nombre, List<Integer> constantes, 
			Map<Patron, Posicion> distribucion, byte[][] espacio) throws ModeloException {
		setNombre(nombre);
		setConstantes(constantes);
		setDistribucion(distribucion);
		try {
			setEspacio(espacio);
		} 
		catch (ModeloException e) {
			this.espacio = new byte[sizePredeterminado][sizePredeterminado];
		}
	}

	/**
	 * Constructor por defecto.
	 * Establece el valor inicial, por defecto, de cada uno de los atributos.
	 * Llama al constructor convencional de la propia clase.
	 * @throws ModeloException 
	 */
	public Mundo() throws ModeloException {
		this("MundoDefecto", new ArrayList<Integer>(), new Hashtable<Patron, Posicion>(), 
				new byte[sizePredeterminado][sizePredeterminado]);
	}

	/**
	 * Constructor copia.
	 * Establece el valor inicial de cada uno de los atributos a partir de
	 * los valores obtenidos de un objeto de su misma clase.
	 * Llama al constructor convencional utilizando objetos obtenidos
	 * con los contructores copia de los atributos.
	 * El atributo espacio es clonado utilizando utilidades de clonación de arrays.
	 * @param mundo - el Mundo a clonar
	 * @throws ModeloException 
	 */
	public Mundo(Mundo mundo) throws ModeloException {
		this(mundo.nombre, new ArrayList<Integer>(mundo.constantes), 
				new Hashtable<Patron,Posicion>(mundo.distribucion), 
				new byte[mundo.espacio.length][mundo.espacio.length]);

		for (int i=0; i <mundo.espacio.length; i++) {
			this.espacio[i] = Arrays.copyOf(mundo.espacio[i], mundo.espacio[i].length);
			//System.arraycopy(mundo.espacio[i], 0, this.espacio[i], 0, mundo.espacio[i].length);
		}
	}

	public String getNombre() {
		return nombre;
	}

	public List<Integer> getConstantes() {
		return constantes;
	}

	public Map<Patron, Posicion> getDistribucion() {
		return distribucion;
	}

	public byte[][] getEspacio() {
		return espacio;
	}

	public void setNombre(String nombre) throws ModeloException {	
		if (nombreValido(nombre)) {
			this.nombre = nombre;
			return;
		}
		throw new ModeloException("El nombre: " + nombre + " no es válido...");
	}

	/**
	 * Comprueba que el nombre sea correcto.
	 * @param nombre.
	 * @return true si cumple.
	 */
	private boolean nombreValido(String nombre) {
		assert nombre != null;
		return	nombre.matches(Formato.PATRON_NOMBRE_MUNDO_JV);
	}

	public void setConstantes(List<Integer> constantes) throws ModeloException {
		if (constantesValidas(constantes)) {
			this.constantes = constantes;
			return;
		}
		throw new ModeloException("Las constantes del mundo: " + constantes + " no son válidas...");
	}

	/**
	 * Comprueba que hay al menos una constante.
	 * @param constantes.
	 * @return true si cumple.
	 */
	private boolean constantesValidas(List<Integer> constantes) {
		assert constantes != null;
		return constantes.isEmpty();
	}

	public void setDistribucion(Map<Patron, Posicion> distribucion) throws ModeloException {
		if (distribucionValida(distribucion)) {
			this.distribucion = distribucion;
			return;
		}
		throw new ModeloException("La distribucion del mundo: " + distribucion + " no es válida...");
	}

	/**
	 * 
	 * Comprueba que hay al menos un patrón en una posición.
	 * @param distribucion.
	 * @return true si cumple.
	 */
	private boolean distribucionValida(Map<Patron, Posicion> distribucion) {
		assert distribucion != null;
		return distribucion.isEmpty();
	}

	public void setEspacio(byte[][] espacio) throws ModeloException {
		if (espacioValido(espacio)) {
			this.espacio = espacio;
			return;
		}
		throw new ModeloException("El espacio del mundo: " + espacio + " no es válido...");
	}

	/**
	 * Comprueba que espacio tiene tamaño.
	 * @param espacio.
	 * @return true si cumple.
	 */
	private boolean espacioValido(byte[][] espacio) {
		assert espacio != null;
		if (espacio.length > 0) {
			return true;
		}
		return false;
	}

	//Métodos de la interface Leyes
	/**
	 * Establece la manera en que actualiza el estado de un mundo. 
	 * Responde a la regla: El tiempo transcurre y se producen cambios...
	 */
	@Override
	public void actualizarMundo()  {     					
		int tam = espacio.length; 
		byte[][] nuevaRealidad = new byte[tam][tam];

		for (int i = 0; i < tam; i++) {
			for (int j = 0; j <= 11; j++) {
				int vecinas = 0;					//células adyacentes
				// las celdas situadas fuera del mundo, con índices fuera de rango, darán
				// ArrayIndexOutOfBoundsException y son equivalentes a células vacías a efectos de 
				// vecindad.
				try {
					vecinas += espacio[i-1][j];	    //celda N			NO | N | NE
					vecinas += espacio[i-1][j+1];	//celda NE			----------- 
					vecinas += espacio[i][j+1];	    //celda E			 O |   | E
					vecinas += espacio[i+1][j+1];	//celda SE          -----------
					vecinas += espacio[i+1][j]; 	//celda S           SO | S | SE
					vecinas += espacio[i+1][j-1];    //celda SO 
					vecinas += espacio[i][j-1];	    //celda O           			                                     	
					vecinas += espacio[i-1][j-1]; 	//celda NO
				} 
				catch (java.lang.ArrayIndexOutOfBoundsException e) { } 

				if (vecinas < 2) {
					nuevaRealidad[i][j] = 0; 				// subpoblación, muere
				}
				if (vecinas > 3) {
					nuevaRealidad[i][j] = 0; 				// sobrepoblación, muere
				}
				if (vecinas == 3) {
					nuevaRealidad[i][j] = 1; 				// pasa a estar viva o se mantiene
				}
				if (vecinas == 2 && espacio[i][j] == 1) {						
					nuevaRealidad[i][j] = 1; 				// se mantiene viva
				}
			}
		}
		espacio = nuevaRealidad;
	}

	/**
	 * @return el texto formateado del estado -valores de atributos- deL objeto de la clase Mundo.  
	 */
	@Override
	public String toString() {
		return String
				.format("Mundo [nombre=%s, parametros=%s, distribucion=%s, espacio=%s]",
						nombre, constantes, distribucion,
						Arrays.toString(espacio));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constantes == null) ? 0 : constantes.hashCode());
		result = prime * result + ((distribucion == null) ? 0 : distribucion.hashCode());
		result = prime * result + Arrays.deepHashCode(espacio);
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
			if (nombre.equals(((Mundo)obj).nombre) &&
					constantes.equals(((Mundo)obj).constantes) &&
					distribucion.equals(((Mundo)obj).distribucion) &&
					equalsEspacios(((Mundo)obj).espacio) 
					) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si el espacio recibido como parámetro es igual al
	 * atributo espacio.
	 * @return falso si no cumple las condiciones.
	 */
	private boolean equalsEspacios(byte[][] espacio ) {	
		for (int i = 0; i < this.espacio.length; i++) {
			if (!Arrays.equals(this.espacio[i], espacio[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	 */
	@Override
	public Object clone() {
		// Utiliza el constructor copia.
		Object clon = null;
		try {
			clon = new Mundo(this);
		} catch (ModeloException e) { }
		return clon;
	}

	public Mundo actualizarEstado() {
		// TODO Auto-generated method stub
		return null;
	}

} //class
