/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con la simulación y la interacción de usuario.
 *  @since: prototipo1.2
 *  @source: Presentacion.java 
 *  @version: 2.0 - 2017.03.22
 *  @author: ajp
 */

package accesoDatos;

import java.util.Scanner;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.ClaveAcceso;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.Usuario;
import util.Fecha;

public class Presentacion {
	
	// Atributos
	final static int TAMAÑO = 12;
	final static int CICLOS = 120;
	private static Scanner teclado = new Scanner(System.in);	//Entrada por consola
	private byte[][] mundo;
	private Datos fachada;
	/**
	 * Constructor
	 */
	public Presentacion() {	
		fachada = new Datos();
		teclado = new Scanner(System.in);

		// En este array los 0 indican celdas con célula muerta y los 1 vivas
		mundo =  new byte[][]{ 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0 }, //
				{ 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
				{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // 
				{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // Given:
				{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
				{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  // 1x Still Life
		};
	}
	
	/**
	 * Controla el acceso de usuario 
	 * y registro de la sesión correspondiente. 
	 * @return true si la sesión de usuario es válida.
	 */
	public boolean iniciarSesionCorrecta() {
		boolean todoCorrecto = false;				// Control de credenciales de usuario.
		Usuario usrSesion = null;					// Usuario en sesión.
		int intentos = 3;							// Contandor de intentos.
	
		do {
			// Pide usuario y contraseña.
			System.out.print("Introduce el idUsr: ");
			String idUsr = teclado.nextLine();
			System.out.print("Introduce clave acceso: ");
			String clave = teclado.nextLine();
			
			// Busca usuario coincidente con las credenciales.
			System.out.println(idUsr);
			usrSesion = fachada.obtenerUsuario(idUsr);
			if ( usrSesion != null) {	
				ClaveAcceso claveIntroducida;
				try {
					claveIntroducida = new ClaveAcceso(clave);
					if (usrSesion.getClaveAcceso().equals(claveIntroducida)) {
						todoCorrecto = true;
					}
				} 
				catch (ModeloException e) { }
			}
			if (todoCorrecto == false) {
				intentos--;
				System.out.println("Credenciales incorrectas...");
				System.out.println("Quedan " + intentos + " intentos... ");
			}
		}
		while (!todoCorrecto && intentos > 0);

		if (todoCorrecto) {
			// Registra sesion de usuario.
			SesionUsuario sesion = null;
			try {
				sesion = new SesionUsuario();
			sesion.setUsr(usrSesion);					
			sesion.setFecha(new Fecha());			    
				fachada.altaSesion(sesion);
			}
			catch (ModeloException e) { }
			catch (DatosException e) { }	
			System.out.println("Sesión: " + fachada.obtenerSesion(usrSesion.getIdUsr() + sesion.getFecha().hashCode()) 
			+ '\n' + "Iniciada por: " + usrSesion.getNombre() + " " + usrSesion.getApellidos());
			return true;
		}
		return false;
	}
	
	
	/**
	 * Ejecuta una simulación del juego de la vida en la consola.
	 * Utiliza la configuración por defecto.
	 */
	public void arrancarSimulacion() {
		int generacion = 0; 
		do {
			System.out.println("\nGeneración: " + generacion);
			mostrarMundo();
			mundo = actualizarMundo();
			generacion++;
		}
		while (generacion <= CICLOS);
	}

	/**
	 * Despliega en la consola el estado almacenado, corresponde
	 * a una generación del Juego de la vida.
	 */
	private void mostrarMundo() {

		for (int i = 0; i < TAMAÑO; i++) {
			for (int j = 0; j < TAMAÑO; j++) {
				System.out.print((mundo[i][j] == 1) ? "|o" : "| ");
			}
			System.out.println("|");
		}
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * @return nuevoEstado, el array con los cambios de la siguiente generación.
	 */
	private byte[][] actualizarMundo()  {     					
		byte[][] nuevoEstado = new byte[TAMAÑO][TAMAÑO];

		for (int i = 0; i < TAMAÑO; i++) {
			for (int j = 0; j <= 11; j++) {
				int vecinas = 0;						// Celdas adyacentes.

				// Las celdas situadas fuera del mundo, con índices fuera de rango, hay que controlarlas
				if (i-1 >= 0)	
					vecinas += mundo[i-1][j];			// Celda N			NO | N | NE
														//					-----------
				if (i-1 >= 0 && j+1 < TAMAÑO)			// 					 O | * | E
					vecinas += mundo[i-1][j+1];			// Celda NE			----------- 
														//					SO | S | SE
				if (j+1 < TAMAÑO)
					vecinas += mundo[i][j+1];			// Celda E			 

				if (i+1 < TAMAÑO && j+1 < TAMAÑO)
					vecinas += mundo[i+1][j+1];			// Celda SE          

				if (i+1 < TAMAÑO)
					vecinas += mundo[i+1][j]; 			// Celda S           

				if (i+1 < TAMAÑO && j-1 >= 0)
					vecinas += mundo[i+1][j-1]; 		// Celda SO 

				if (j-1 >= 0)
					vecinas += mundo[i][j-1];			// Celda O           			                                     	

				if (i-1 >= 0 && j-1 >= 0)
					vecinas += mundo[i-1][j-1]; 		// Celda NO

				if (vecinas < 2) 
					nuevoEstado[i][j] = 0; 				// Subpoblación, muere...

				if (vecinas > 3) 
					nuevoEstado[i][j] = 0; 				// Sobrepoblación, muere...

				if (vecinas == 3) 
					nuevoEstado[i][j] = 1; 				// Pasa a estar viva o se mantiene.

				if (vecinas == 2 && mundo[i][j] == 1) 						
					nuevoEstado[i][j] = 1; 				// Se mantiene viva...
			}
		}
		return nuevoEstado;
	}

	public void mostrar(String texto) {
		System.out.println(texto);
	}
	
} //class

