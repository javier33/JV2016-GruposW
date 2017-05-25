/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Usuario utilizando base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: usuariosDAO.java 
 * @version: 2.2 - 2017.05.16
 * @author: ajp
 */

package accesoDatos.db4o;

import java.util.List;
import java.util.Hashtable;
import java.util.Map;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuariosDAO implements OperacionesDAO {

	// Requerido por el Singleton 
	private static UsuariosDAO instancia = null;

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
	public static UsuariosDAO getInstancia() {
		if (instancia == null) {
			instancia = new UsuariosDAO();
		}
		return instancia;
	}

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private UsuariosDAO() {
		db = Conexion.getDB();
		db.store(new Hashtable<String,String>());
		if (obtener("III1R") == null) {
			cargarPredeterminados();
		}
	}

	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		String nombreUsr = Configuracion.get().getProperty("usuario.admin");
		String password = Configuracion.get().getProperty("usuario.passwordPredeterminada");	
		Usuario usrPredeterminado = null;
		try {
			usrPredeterminado = new Usuario(new Nif("00000000T"), nombreUsr, "Admin Admin", 
					new DireccionPostal("Iglesia", "0", "30012", "Murcia"), 
					new Correo("jv.admin" + "@gmail.com"), new Fecha(), 
					new Fecha(), new ClaveAcceso(password), RolUsuario.ADMINISTRADOR);
			db.store(usrPredeterminado);
			registrarEquivalenciaId(usrPredeterminado);

			nombreUsr = Configuracion.get().getProperty("usuario.invitado");	
			usrPredeterminado = new Usuario(new Nif("00000001R"), nombreUsr, "Invitado Invitado", 
					new DireccionPostal("Iglesia", "00", "30012", "Murcia"), 
					new Correo("jv.invitado" + "@gmail.com"), new Fecha(), 
					new Fecha(), new ClaveAcceso(password), RolUsuario.INVITADO);
			db.store(usrPredeterminado);
		} catch (ModeloException e) {}
		registrarEquivalenciaId(usrPredeterminado);
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
	 * Obtiene un Usuario dado su idUsr.
	 * @param id - el idUsr de Usuario a buscar.
	 * @return - el Usuario encontrado; null si no existe.
	 */	
	@Override
	public Usuario obtener(String idUsr) {
		idUsr = obtenerEquivalencia(idUsr);
		ObjectSet<Usuario> result;
		Query consulta = db.query();
		consulta.constrain(Usuario.class);
		consulta.descend("idUsr").constrain(idUsr);
		result = consulta.execute();
		if (result.size() > 0) {
			return result.get(0);
		}	
		return null;
	}

	/**
	 * Obtiene un Usuario dado un objeto, reenvía al método que utiliza idUsr.
	 * @param obj - el Usuario a buscar.
	 * @return - el Usuario encontrado; null si no existe.
	 */
	@Override
	public Usuario obtener(Object obj)  {
		return obtener(((Usuario) obj).getIdUsr());
	}

	/**
	 * Obtiene todos los usuarios almacenados.
	 * @return - la List con todos los usuarios.
	 */
	@Override
	public List<Usuario> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(Usuario.class);
		return  consulta.execute();
	}
	
	/**
	 *  Alta de un nuevo usuario sin repeticiones según el campo idUsr. 
	 *  Si hay coincidencia de identificador hace 23 intentos de variar la última letra
	 *  procedente del NIF. Llama al generarVarianteIdUsr() de la clase Usuario.
	 *	@param obj - Objeto a almacenar.
	 *  @throws DatosException - si ya existe y están en uso todas las variantes.
	 */
	@Override
	public void alta(Object obj) throws DatosException {
		Usuario usrNuevo = (Usuario) obj;
		Usuario usrPrevio = obtener(usrNuevo.getIdUsr());
		if (usrPrevio == null) {
			db.store(usrNuevo);
			registrarEquivalenciaId(usrNuevo);
		}
		else {
			// Hay coincidencia de identificador con un usuario ya almacenado.
			// Comprueba que no haya coincidencia de Correo y Nif (ya existe)
			boolean condicion = !(usrNuevo.getCorreo().equals(usrPrevio.getCorreo())
					|| usrNuevo.getNif().equals(usrPrevio.getNif()));
			if (condicion) {
				int intentos = "ABCDEFGHJKLMNPQRSTUVWXYZ".length();				// 24 letras
				do {
					usrNuevo.generarVarianteIdUsr();
					usrPrevio = obtener(usrNuevo.getIdUsr());
					if (usrPrevio == null) {
						db.store(usrNuevo);
						registrarEquivalenciaId(usrNuevo);
						return;
					}
					intentos--;
				} while (intentos > 0);
			}
			throw new DatosException("(ALTA) El Usuario: " + usrNuevo.getIdUsr() + " ya existe...");
		}
	} 

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param id - el identificador del objeto a eliminar.
	 * @return - el Objeto eliminado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Usuario baja(String id) throws DatosException {
		Usuario usr = obtener(id);
		if (usr != null) {
			borrarEquivalenciaId(usr);
			db.delete(usr);
		}
		else {
			throw new DatosException("(BAJA) El usuario: " + id + " no existe...");
		}
		return usr;	
	} 

	/**
	 * Actualiza datos de un Usuario reemplazando el almacenado por el recibido. 
	 * Localiza la posición del almacenado por búsqueda binaria.
	 * @param obj - Usuario con los cambios.
	 * @return 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		Usuario usr = (Usuario) obj;
		Usuario usrAux = (Usuario) obtener(usr.getIdUsr());
		if(usrAux != null) {
			cambiarEquivalenciaId(usrAux, usr);
			try {
				usrAux.setNif(usr.getNif());
				usrAux.setNombre(usr.getNombre());
				usrAux.setApellidos(usr.getApellidos());
				usrAux.setDomicilio(usr.getDomicilio());
				usrAux.setCorreo(usr.getCorreo());
				usrAux.setFechaNacimiento(usr.getFechaNacimiento());
				usrAux.setFechaAlta(usr.getFechaAlta());
				usrAux.setRol(usr.getRol());
			} 
			catch (ModeloException e) { }
			db.store(usrAux);
			return;
		}
		throw new DatosException("(ACTUALIZAR) El usuario: " + usr.getIdUsr() + " no existe...");
	} 

	/**
	 * Obtiene el listado de todos los usuarios almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		for (Usuario usr: obtenerTodos()) {
			listado.append("\n" + usr);
		}
		return listado.toString();
	}
	
	/**
	 * Obtiene el listado de todos los identificadores de usuarios almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarId() {
		StringBuilder listado = new StringBuilder();
		for (Usuario usr: obtenerTodos()) {
			if (usr != null) {
				listado.append(usr.getIdUsr()+ "\n");
			}
		}
		return listado.toString();
	}
	
	/**
	 * Quita todos los objetos Usuario y vacía el mapa de equivalencias de idUsr.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los obtenidos
		for (Usuario usr: obtenerTodos()) {
			db.delete(usr);
		}
		// Quita todas las equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		mapaEquivalencias.clear();
		db.store(mapaEquivalencias);
		cargarPredeterminados();
	}

	//GESTION equivalencias id
	/**
	 * Obtiene el idUsr usado internamente a partir de otro equivalente.
	 * @param id - la clave alternativa. 
	 * @return - El idUsr equivalente.
	 */
	public String obtenerEquivalencia(String id) {
		return obtenerMapaEquivalencias().get(id);
	}

	/**
	 * Obtiene el mapa de equivalencias de id para idUsr.
	 * @return el Hashtable almacenado.
	 */
	private Map<String,String> obtenerMapaEquivalencias() {
		//Obtiene mapa de equivalencias de id de acceso
		Query consulta = db.query();
		consulta.constrain(Hashtable.class);
		ObjectSet <Hashtable<String,String>> result = consulta.execute();
		return result.get(0);	
	}

	/**
	 * Registra las equivalencias de nif y correo para un idUsr.
	 * @param usuario
	 */
	private void registrarEquivalenciaId(Usuario usuario) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Registra equivalencias 
		mapaEquivalencias.put(usuario.getIdUsr().toUpperCase(), usuario.getIdUsr().toUpperCase());
		mapaEquivalencias.put(usuario.getNif().getTexto().toUpperCase(), usuario.getIdUsr().toUpperCase());
		mapaEquivalencias.put(usuario.getCorreo().getTexto().toUpperCase(), usuario.getIdUsr().toUpperCase());
		//actualiza datos
		db.store(mapaEquivalencias);	
	}

	/**
	 * Elimina las equivalencias de nif y correo para un idUsr.
	 * @param usuario - el usuario para eliminar sus equivalencias de idUsr.
	 */
	private void borrarEquivalenciaId(Usuario usuario) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Borra equivalencias 
		mapaEquivalencias.remove(usuario.getIdUsr());
		mapaEquivalencias.remove(usuario.getNif().getTexto());
		mapaEquivalencias.remove(usuario.getCorreo().getTexto());
		//actualiza datos
		db.store(mapaEquivalencias);	
	}

	/**
	 * Actualiza las equivalencias de nif y correo para un idUsr
	 * @param usrAntiguo - usuario con id's antiguos
	 * @param usrNuevo - usuario con id's nuevos
	 */
	private void cambiarEquivalenciaId(Usuario usrAntiguo, Usuario usrNuevo) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Cambia equivalencias 
		mapaEquivalencias.replace(usrAntiguo.getIdUsr(), usrNuevo.getIdUsr().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getNif().getTexto(), usrNuevo.getIdUsr().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getCorreo().getTexto(), usrNuevo.getIdUsr().toUpperCase());
		//actualiza datos
		db.store(mapaEquivalencias);
	}

} //class