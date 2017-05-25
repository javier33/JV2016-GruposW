/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO SesionUsuario utilizando base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: SesionesDAO.java 
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
import modelo.SesionUsuario;

public class SesionesDAO implements OperacionesDAO {
	
	// Requerido por el Singleton 
	private static SesionesDAO instancia = null;

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
	public static SesionesDAO getInstancia() {
		if (instancia == null) {
			instancia = new SesionesDAO();
		}
		return instancia;
	}
	
	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private SesionesDAO() {
		db = Conexion.getDB();
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
	 * Obtiene una SesionUsuario por idUsr + fecha.
	 * @param idSesion - la SesionUsuario a buscar.
	 * @return - la sesión encontrada; null si no existe.
	 */
	@Override
	public SesionUsuario obtener(String idSesion)  {
		ObjectSet<SesionUsuario> result = null;
		Query consulta = db.query();
		consulta.constrain(SesionUsuario.class);
		consulta.descend("getIdSesion()").constrain(idSesion).equal();
		result = consulta.execute();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * Obtiene una SesionUsuario dado un objeto, reenvía al método que utiliza idSesion.
	 * @param obj - la SesionUsuario a buscar.
	 * @return - la Sesion encontrada; null si no existe.
	 */
	@Override
	public SesionUsuario obtener(Object obj)  {
		return this.obtener(((SesionUsuario) obj).getIdSesion());
	}	
	
	/**
	 * Obtiene todos los objetos SesionUsuario almacenados.
	 * @return - la List de todas las encontradas.
	 */
	@Override
	public List<SesionUsuario> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(SesionUsuario.class);
		return consulta.execute();

	}
	
	/**
	 * Obtiene de todas las sesiones por IdUsr de usuario.
	 * @param idUsr - el idUsr a buscar.
	 * @return - las sesiones encontradas o null si no existe.
	 */
	public List<SesionUsuario> obtenerTodasMismoUsr(String idUsr) {
		ObjectSet<SesionUsuario> result = null;
		Query consulta = db.query();
		consulta.constrain(SesionUsuario.class);
		consulta.descend("usr").descend("idUsr").constrain(idUsr);
		result = consulta.execute();
		if (result.size() > 0) {
			return (List<SesionUsuario>) result;
		}
		return null;
	}
	
	/**
	 * Alta de una nueva SesionUsuario sin repeticiones según los campos idUsr + fecha. 
	 * @param obj - la SesionUsuario a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException {
		SesionUsuario sesion = (SesionUsuario) obj;
		if (obtener(sesion.getIdSesion()) == null) {
			//actualiza datos
			db.store(sesion);
			return;
		}
		throw new DatosException("(ALTA) La sesión: " + sesion.getIdSesion() + " ya existe...");	
	}

	/**
	 * Elimina el objeto, dado el idUsr + fecha utilizado para el almacenamiento.
	 * @param idSesion - el idUsr + fecha de la SesionUsuario a eliminar.
	 * @return - la SesionUsuario eliminada.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public SesionUsuario baja(String idSesion) throws DatosException {
		SesionUsuario sesion = obtener(idSesion);
		if (sesion != null) {
			db.delete(sesion);
			return sesion;
		}
		throw new DatosException("(BAJA) La sesión: " + idSesion + " no existe...");
	}

	/**
	 *  Actualiza datos de una SesionUsuario reemplazando el almacenado por el recibido.
	 *	@param obj - SesionUsuario con las modificaciones.
	 *  @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		SesionUsuario sesion = (SesionUsuario) obj;
		String idSesion = sesion.getUsr().getIdUsr() + sesion.getFecha().toString();
		SesionUsuario sesionAux = (SesionUsuario) obtener(idSesion);
		if(sesionAux != null) {
			sesionAux.setUsr(sesion.getUsr());
			try {
				sesionAux.setFecha(sesion.getFecha());
				sesionAux.setEstado(sesion.getEstado());
			} 
			catch (ModeloException e) { }
			db.store(sesionAux);
			return;
		}
		throw new DatosException("(ACTUALIZAR) La sesión: " + sesion.getIdSesion() + " no existe...");
	} 
	
	/**
	 * Obtiene el listado de todos las sesiones almacenadas.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		ObjectSet<SesionUsuario> result = null;
		Query consulta = db.query();
		consulta.constrain(SesionUsuario.class);	
		result = consulta.execute();	
		if (result.size() > 0) {
			for (SesionUsuario sesion: result) {
				listado.append("\n" + sesion);
			}
			return listado.toString();
		}
		return null;
	}

	/**
	 * Obtiene el listado de todos los identificadores de sesiones almacenadas.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarId() {
		StringBuilder listado = new StringBuilder();
		for (SesionUsuario sesion: obtenerTodos()) {
			if (sesion != null) {
				listado.append("\n" + sesion.getIdSesion());
			}
		}
		return listado.toString();
	}
	
	/**
	 * Quita todos los objetos SesionUsuario de la base de datos.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los objetos obtenidos
		for (SesionUsuario sesion: obtenerTodos()) {
			db.delete(sesion);
		}	
	}

}//class
