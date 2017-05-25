/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Patron utilizando base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: PatronesDAO.java 
 * @version: 2.2 - 2017.05.16
 * @author: ajp
 */

package accesoDatos.db4o;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.ModeloException;
import modelo.Patron;

public class PatronesDAO implements OperacionesDAO {

	// Requerido por el Singleton 
	private static PatronesDAO instancia = null;

	// Elementos de almacenamiento.
	// Base datos db4o
	private ObjectContainer db;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static PatronesDAO getInstancia() {
		if (instancia == null) {
			instancia = new PatronesDAO();
		}
		return instancia;
	}
	
	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private PatronesDAO() {
		db = Conexion.getDB();
		if (obtener("Demo0") == null) {
			cargarPredeterminados();
		}
	}
	
	/**
	 *  Método para generar datos predeterminados.
	 */
	private void cargarPredeterminados() {
		byte[][] esquemaDemo =  new byte[][]{ 
			{ 0, 0, 0, 0 }, 
			{ 1, 0, 1, 0 }, 
			{ 0, 0, 0, 1 }, 
			{ 0, 1, 1, 1 }, 
			{ 0, 0, 0, 0 }
		};
		
		try {
			Patron patronDemo = new Patron("Demo0", esquemaDemo);
			alta(patronDemo);
		} 
		catch (DatosException | ModeloException e) { }
	}

	/**
	 *  Cierra conexión.
	 */
	@Override
	public void cerrar() {
		// No hay que cerrar nada si la conexión es compartida por todos los DAO.
	}
	
	//OPERACIONES DAO
	/**
	 * Obtiene un Patron dado su identificador.
	 * @param nombre - el nombre del Patron a recuperar.
	 * @return - el Patron encontrado; null si no existe.
	 */	
	@Override
	public Patron obtener(String nombre) {
		ObjectSet<Patron>  result;
		Query consulta = db.query();
		consulta.constrain(Patron.class);
		consulta.descend("nombre").constrain(nombre).equal();
		result = consulta.execute();
		if (result.size()  > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * Búsqueda de Patron dado un objeto, reenvía al método que utiliza nombre.
	 * @param obj - el Patron a buscar.
	 * @return - el Patron encontrado; null si no existe.
	 */
	@Override
	public Patron obtener(Object obj)  {
		return this.obtener(((Patron) obj).getNombre());
	}
	
	/**
	 * Obtiene todos los objetos Patron almacenados.
	 * @return - la List con todos los encontrados.
	 */
	@Override
	public List<Patron> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(Patron.class);
		return consulta.execute();
	}
	
	/**
	 *  Alta de un nuevo Patron sin repeticiones según el campo 
	 *  nombre. Intenta Obtener el objeto a almacenar.
	 * @param obj - Patron a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException {
		Patron patron = (Patron) obj;
		if (obtener(patron.getNombre()) == null) {
			db.store(patron);
			return;
		}
		throw new DatosException("(ALTA) El patron: " + patron.getNombre() + " ya existe...");
	}
	
	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param nombre - el nombre del Patron a eliminar.
	 * @return - el Patron eliminado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Patron baja(String nombrePatron) throws DatosException {
		Patron patron = obtener(nombrePatron);
		if (patron != null) {
			db.delete(patron);
			return patron;
		}
		throw new DatosException("(BAJA) El patron: " + nombrePatron + " no existe...");
	}

	/**
	 *  Actualiza datos de un Patron reemplazando el almacenado por el recibido.
	 *	@param obj - Patron con las modificaciones.
	 *  @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		Patron patron = (Patron) obj;
		Patron patronAux = obtener(patron.getNombre());
		if (patronAux != null) {
			try {
				patronAux.setNombre(patron.getNombre());
				patronAux.setEsquema(patron.getEsquema());
			} 
			catch (ModeloException e) {	}
			db.store(patronAux);
			return;
		}
		throw new DatosException("(ACTUALIZAR) El Patron: " + patron.getNombre() + " no existe...");
	}

	
	/**
	 * Obtiene el listado de todos los objetos Patron almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		ObjectSet<Patron>  result;
		Query consulta = db.query();
		consulta.constrain(Patron.class);
		result = consulta.execute();
		for (Patron patron: result) {
			listado.append("\n" + patron);
		}
		return listado.toString();
	}
	
	/**
	 * Obtiene el listado de todos los identificadores de patron almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarId() {
		StringBuilder listado = new StringBuilder();
		ObjectSet<Patron>  result;
		Query consulta = db.query();	
		consulta.constrain(Patron.class);
		result = consulta.execute();
		for (Patron patron: result) {
			if (patron != null) {
				listado.append(patron.getNombre() + "\n");
			}
		}
		return listado.toString();
	} 
	
	/**
	 * Quita todos los objetos Patron de la base de datos.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los objetos obtenidos.
		for (Patron patron: obtenerTodos()) {
			db.delete(patron);
		}
		cargarPredeterminados();
	}

} //class
