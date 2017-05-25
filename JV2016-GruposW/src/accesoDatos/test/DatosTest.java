/** 
 * Proyecto: Juego de la vida.
 *  Clase JUnit 4 para pruebas de GestionDatos.
 *  @since: prototipo2.1
 *  @source: DatosTest.java 
 * @version: 2.1 - 2017/04/16 
 *  @author: ajp
 */

package accesoDatos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Hashtable;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Nif;
import modelo.Patron;
import modelo.Posicion;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class DatosTest {

	private Datos fachada;
	private Usuario usrPrueba;
	private SesionUsuario sesionPrueba;
	private Simulacion simulacionPrueba;
	private Mundo mundoPrueba;
	private Patron patronPrueba;

	public DatosTest () {
		fachada = new Datos();
	}

	@Before
	public void crearDatosPrueba() {
		try {
			// Usuario con idUsr "PMA8P"
			usrPrueba =  new Usuario(new Nif("00000008P"), "Pepe",
					"Márquez Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("pepe@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2014, 12, 3), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
			sesionPrueba = new SesionUsuario(fachada.obtenerUsuario("III1R"), new Fecha(), EstadoSesion.EN_PREPARACION);
			mundoPrueba = fachada.obtenerMundo("Demo0");
			simulacionPrueba = new Simulacion(fachada.obtenerUsuario("III1R"), new Fecha(), new Mundo(), EstadoSimulacion.PREPARADA);
			patronPrueba = fachada.obtenerPatron("Demo0");
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@After
	public void borraDatosPrueba() {
		fachada.borrarTodosUsuarios();
		fachada.borrarTodasSesiones();
		fachada.borrarTodosMundos();
		fachada.borrarTodosPatrones();
		fachada.borrarTodasSimulaciones();
		usrPrueba = null;
		sesionPrueba = null;
		mundoPrueba = null;
		simulacionPrueba = null;
		patronPrueba = null;
	}

	@Test
	public void testObtenerUsuarioId() {
		assertEquals(fachada.obtenerUsuario("III1R").getIdUsr(), "III1R");
	}

	@Test
	public void testObtenerUsuario() {
		try {
			fachada.altaUsuario(usrPrueba);
		} 
		catch (DatosException e) { }
		// Busca el mismo Usuario almacenado.
		assertSame(usrPrueba, fachada.obtenerUsuario(usrPrueba));
	}

	@Test
	public void testAltaUsuario() {
		try {
			// Usuario nuevo, que no existe.
			fachada.altaUsuario(usrPrueba);
		} 
		catch (DatosException e) { }
		// Busca el mismo Usuario almacenado.
		assertSame(usrPrueba, fachada.obtenerUsuario(usrPrueba));
	}

	@Test
	public void testBajaUsuario() {
		try {
			fachada.altaUsuario(usrPrueba);
			// Baja del mismo Usuario almacenado.
			assertSame(usrPrueba, fachada.bajaUsuario(usrPrueba.getIdUsr()));
		} 
		catch (DatosException e) { }
	}

	@Test
	public void testActualizarUsuario() {
		try {
			// Usuario nuevo, que no existe.
			fachada.altaUsuario(usrPrueba);
			usrPrueba.setApellidos("Ramírez Pinto");
			fachada.actualizarUsuario(usrPrueba);
		} 
		catch (DatosException | ModeloException e) { }
		assertEquals(fachada.obtenerUsuario(usrPrueba).getApellidos(), "Ramírez Pinto");
	}

	@Test
	public void testToStringDatosUsuarios() {
		assertNotNull(fachada.toStringDatosUsuarios());
	}

	@Test
	public void testGetEquivalenciaId() {
		try {		
			// Usuario nuevo, que no existe.
			fachada.altaUsuario(usrPrueba);
		} 
		catch (DatosException e) { }
		assertEquals(fachada.obtenerUsuario("PMA8P").getIdUsr(), "PMA8P");
		assertEquals(fachada.obtenerUsuario("00000008P").getIdUsr(), "PMA8P");
		assertEquals(fachada.obtenerUsuario("pepe@gmail.com").getIdUsr(), "PMA8P");
	}

	@Test
	public void testObtenerSesionId() {
		try {
			fachada.altaSesion(sesionPrueba);
		} 
		catch (DatosException e) { }
		// Busca la misma sesion almacenada.
		assertSame(sesionPrueba, fachada.obtenerSesion(sesionPrueba.getIdSesion()));
	}

	@Test
	public void testObtenerSesion() {
		try {
			fachada.altaSesion(sesionPrueba);
		} 
		catch (DatosException e) { }
		// Busca la misma sesion almacenada.
		assertSame(sesionPrueba, fachada.obtenerSesion(sesionPrueba));
	}

	@Test
	public void testAltaSesion() {
		try {
			fachada.altaSesion(sesionPrueba);
		} 
		catch (DatosException e) { }
		assertSame(sesionPrueba, fachada.obtenerSesion(sesionPrueba));
	}

	@Test
	public void testBajaSesionUsuario() {
		try {
			fachada.altaSesion(sesionPrueba);
			// Baja de la misma sesion almacenada.
			assertSame(sesionPrueba, fachada.bajaSesion(sesionPrueba.getIdSesion()));
		} 
		catch (DatosException e) { }
	}

	@Test
	public void testActualizarSesion() {
		try {
			fachada.altaSesion(sesionPrueba);
			sesionPrueba.setEstado(EstadoSesion.CERRADA);
			fachada.actualizarSesion(sesionPrueba);
		} 
		catch (DatosException e) { }
		assertEquals(fachada.obtenerSesion(sesionPrueba).getEstado(), EstadoSesion.CERRADA);
	}

	@Test
	public void testToStringDatosSesiones() {
		assertNotNull(fachada.toStringDatosSesiones());
	}

	@Test
	public void testObtenerSimulacionId() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerSimulacion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAltaSimulacion() {
		fail("Not yet implemented");
	}

	@Test
	public void testBajaSimulacion() {
		fail("Not yet implemented");
	}

	@Test
	public void testActualizarSimulacion() {	
		Simulacion simulacionNueva = null;
		try {
			simulacionNueva = new Simulacion(simulacionPrueba);
			fachada.altaSimulacion(simulacionPrueba);
			simulacionNueva.setEstado(EstadoSimulacion.COMPLETADA);
			fachada.actualizarSimulacion(simulacionNueva);
		} 
		catch (DatosException | ModeloException e) { }
		assertEquals(fachada.obtenerSimulacion(simulacionPrueba), simulacionNueva);
	}

	@Test
	public void testToStringDatosSimulaciones() {
		assertNotNull(fachada.toStringDatosSimulaciones());
	}

	@Test
	public void testObtenerMundoString() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerMundoMundo() {
		fail("Not yet implemented");
	}

	@Test
	public void testAltaMundo() {
		fail("Not yet implemented");
	}

	@Test
	public void testBajaMundo() {
		fail("Not yet implemented");
	}

	@Test
	public void testActualizarMundo() {
		Map<Patron, Posicion> distribucionPrueba = new Hashtable<Patron, Posicion>();
		Mundo mundoNuevo = null;
		try {
			mundoNuevo = new Mundo(mundoPrueba);
			distribucionPrueba.put(new Patron(), new Posicion(3,5));
			fachada.altaMundo(mundoPrueba);
			mundoNuevo.setDistribucion(distribucionPrueba);
			fachada.actualizarMundo(mundoNuevo);
		} 
		catch (DatosException | ModeloException e) { }
		assertEquals(fachada.obtenerMundo(mundoNuevo), mundoNuevo);
	}

	@Test
	public void testToStringDatosMundos() {
		assertNotNull(fachada.toStringDatosMundos());
	}

	@Test
	public void testObtenerPatronString() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerPatronPatron() {
		fail("Not yet implemented");
	}

	@Test
	public void testAltaPatron() {
		fail("Not yet implemented");
	}

	@Test
	public void testBajaPatron() {
		fail("Not yet implemented");
	}

	@Test
	public void testActualizarPatron() {
		byte[][] esquemaPrueba = new byte[5][4];
		try {
			fachada.altaPatron(patronPrueba);
			patronPrueba.setEsquema(esquemaPrueba);
			fachada.actualizarMundo(mundoPrueba);
		} 
		catch (DatosException | ModeloException e) { }
		assertSame(fachada.obtenerPatron(patronPrueba).getEsquema(), esquemaPrueba);
	}

	@Test
	public void testToStringDatosPatrones() {
		assertNotNull(fachada.toStringDatosPatrones());
	}

}
