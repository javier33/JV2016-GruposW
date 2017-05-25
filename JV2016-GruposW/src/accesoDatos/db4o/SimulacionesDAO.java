/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Simulacion utilizando base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: SimulacionesDAO.java 
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
import modelo.Mundo;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import util.Fecha;

public class SimulacionesDAO implements OperacionesDAO {

	// Requerido por el Singleton 
	private static SimulacionesDAO instancia = null;
	
	// Elemento de almacenamiento.
	// Base datos db4o
	private ObjectContainer db;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static SimulacionesDAO getInstancia() {
		if (instancia == null) {
			instancia = new SimulacionesDAO();
		}
		return instancia;
	}
	
	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private SimulacionesDAO() {
		db = Conexion.getDB();
		if (obtenerTodasMismoUsr("III1R") == null) {
			cargarPredeterminados();
		}
	}
	
	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		// Obtiene usuario y mundo predeterminados.
		Usuario usrPredeterminado = UsuariosDAO.getInstancia().obtener("III1R");
		Mundo mundoPredeterminado = MundosDAO.getInstancia().obtener("Demo0");
		Simulacion simulacionDemo = null;
		try {
			simulacionDemo = new Simulacion(usrPredeterminado, new Fecha(), mundoPredeterminado, EstadoSimulacion.PREPARADA);
			db.store(simulacionDemo);
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Cierra conexión.
	 */
	@Override
	public void cerrar() {
		// No hay que cerrar nada si la conexión es compartida por todos los DAO.
	}
	
	//Operaciones DAO
	/**
	 * Búsqueda binaria de Simulacion dado idUsr y fecha.
	 * @param idSimulacion - el idUsr+fecha de la Simulacion a buscar. 
	 * @return - la Simulacion encontrada; null si no existe.
	 */	
	public Simulacion obtener(String idSimulacion) {	
		ObjectSet<Simulacion> result = null;
		Query consulta = db.query();
		consulta.constrain(Simulacion.class);
		consulta.descend("getIdSimulacion()").constrain(idSimulacion).equal();
		result = consulta.execute();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * Búsqueda de Sesion dado un objeto, reenvía al método que utiliza idSesion.
	 * @param obj - la Simulacion a buscar.
	 * @return - la Simulacion encontrada; null si no existe.
	 */
	@Override
	public Simulacion obtener(Object obj)  {
		return this.obtener(((Simulacion) obj).getIdSimulacion());
	}
	
	/**
	 * Obtiene todos los objetos Simulacion almacenados.
	 * @return - la List de todas las encontradas.
	 */
	@Override
	public List<Simulacion> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(Simulacion.class);
		return consulta.execute();
	}
	
	/**
	 * Obtiene de todas las simulaciones por IdUsr de usuario.
	 * @param idUsr - el idUsr a buscar.
	 * @return - las simulaciones encontradas o null si no existe.
	 */
	public List<Simulacion> obtenerTodasMismoUsr(String idUsr) {
		ObjectSet<Simulacion> result = null;
		Query consulta = db.query();
		consulta.constrain(Simulacion.class);
		consulta.descend("usr").descend("idUsr").constrain(idUsr).equal();
		result = consulta.execute();
		if (result.size() > 0) {
			return (List<Simulacion>) result;
		}
		return null;
	}
	
	/**
	 *  Alta de una nueva Simulacion en orden y sin repeticiones según los idUsr más fecha. 
	 *  Busca previamente la posición que le corresponde por búsqueda binaria.
	 *  @param obj - Simulación a almacenar.
	 *  @throws DatosException - si ya existe.
	 */	
	public void alta(Object obj) throws DatosException {
		Simulacion simulacion = (Simulacion) obj;
		if (obtener(simulacion.getIdSimulacion()) == null) {
			db.store(simulacion);
			return;
		}
		throw new DatosException("(ALTA) La simulación: " + simulacion.getIdSimulacion() + " ya existe...");	
	}

	/**
	 * Elimina el objeto, dado el idUsr + fecha utilizado para el almacenamiento.
	 * @param idS - el idUsr + fecha de la Simulacion a eliminar.
	 * @return - la Simulacion eliminada.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Simulacion baja(String id) throws DatosException {	
		Simulacion simulacion = obtener(id);
		if (simulacion != null){
			db.delete(simulacion);
			return simulacion;
		}
		throw new DatosException("(BAJA) La simulación: " + id + " no existe...");
	}

	/**
	 *  Actualiza datos de una Simulacion reemplazando el almacenado por el recibido.
	 *	@param obj - Simulacion con las modificaciones.
	 *  @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		Simulacion simulacion = (Simulacion) obj;
		Simulacion simulacionAux = obtener(simulacion.getIdSimulacion());
		if(simulacionAux != null) {
			simulacionAux.setUsr(simulacion.getUsr());
			try {
				simulacionAux.setMundo(simulacion.getMundo());
				simulacionAux.setFecha(simulacion.getFecha());
				simulacionAux.setEstado(simulacion.getEstado());
			} 
			catch (ModeloException e) { }
			db.store(simulacionAux);
			return;
		}
		throw new DatosException("(ACTUALIZAR) La simulación: " + simulacion.getIdSimulacion() + " no existe...");
	}

	/**
	 * Obtiene el listado de todos las simulaciones almacenadas.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		ObjectSet<Simulacion> result = null;
		Query consulta = db.query();
		consulta.constrain(Simulacion.class);	
		result = consulta.execute();
		if (result.size() > 0) {
			for (Simulacion simul: result) {
				listado.append("\n" + simul);
			}
			return listado.toString();
		}
		return null;
	}

	/**
	 * Obtiene el listado de todos los identificadores de las simulaciones almacenadas.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarId() {
		StringBuilder listado = new StringBuilder();
		ObjectSet<Simulacion> result = null;
		Query consulta = db.query();
		consulta.constrain(Simulacion.class);	
		result = consulta.execute();
		if (result.size() > 0) {
			for (Simulacion s: result) {
				if (s != null) {
					listado.append(s.getIdSimulacion() + "\n");
				}
			}
			return listado.toString();
		}
		return null;
	}
	
	/**
	 * Quita todos los objetos Simulacion de la base de datos.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los objetos obtenidos
		for (Simulacion simulacion: obtenerTodos()) {
			db.delete(simulacion);
		}
		cargarPredeterminados();
	}
	
} //class
