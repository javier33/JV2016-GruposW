package modelo.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Nif;
import modelo.Simulacion;
import modelo.Usuario;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario.RolUsuario;
import util.Fecha;
/** Proyecto: Juego de la vida.
 *  Prueba Junit4 de la clase Simulacion según el modelo 2.1
 *  @since: prototipo2.0
 *  @source: CorreoTest.java 
 *  @version: 2.1 - 2017/05/14
 *  @author: ajp
 *  @author: Grupo 1
 */
public class SimulacionTest {
	private Simulacion simulacion1;
	private Simulacion simulacion2;
	private Usuario usr;
	private Fecha fecha;
	private Mundo mundo;


	public SimulacionTest() {
		try {
			usr = new Usuario(new Nif("00000001R"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21),
					new Fecha(2015,05,12), new ClaveAcceso(), RolUsuario.NORMAL);
			fecha = new Fecha(2017, 5, 12, 10, 35, 2);
			mundo = new Mundo();		
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void iniciarlizarDatosPrueba() {	
		try {
			simulacion1 = new Simulacion();
			simulacion2 = new Simulacion(usr, fecha, mundo, EstadoSimulacion.PREPARADA);
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@After
	public void borrarDatosPrueba() {	
		simulacion1 = null;
	}

	// Test con DATOS VALIDOS
	@Test
	public void testSimulacionConvencional() {	
		assertEquals(simulacion2.getUsr(), usr);
		assertEquals(simulacion2.getFecha(), fecha);
		assertEquals(simulacion2.getMundo(), mundo);
		assertEquals(simulacion2.getEstado(), EstadoSimulacion.PREPARADA);
	}

	@Test
	public void testSimulacionDefecto() {
		try {
			assertEquals(simulacion1.getUsr(), new Usuario());
			assertEquals(simulacion1.getFecha(), new Fecha());
			assertEquals(simulacion1.getMundo(), new Mundo());
			assertEquals(simulacion1.getEstado(), EstadoSimulacion.PREPARADA);
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testSimulacionCopia() {
		try {
			assertEquals(simulacion2, new Simulacion(simulacion2));
		} 
		catch (ModeloException e) {	}
	}

	@Test
	public void testGetUsr() {
		assertEquals(simulacion2.getUsr(), usr);
	}

	@Test
	public void testGetMundo() {
		assertEquals(simulacion2.getMundo(), mundo);
	}

	@Test
	public void testGetFecha() {
		assertEquals(simulacion2.getFecha(), fecha);
	}

	@Test
	public void testGetEstado() {
		assertEquals(simulacion2.getEstado(), EstadoSimulacion.PREPARADA);
	}

	@Test
	public void testGetIdSimulacion() {
		assertEquals(simulacion2.getIdSimulacion(), "LPR1R:20170512103502");
	}

	@Test
	public void testSetUsr() {
		simulacion1.setUsr(usr);
		assertEquals(simulacion1.getUsr(), usr);
	}

	@Test
	public void testSetMundo() {
		simulacion1.setMundo(mundo);
		assertEquals(simulacion1.getMundo(), mundo);
	}

	@Test
	public void testSetFecha() {
		try {
			simulacion1.setFecha(fecha);
		} 
		catch (ModeloException e) { }
		assertEquals(simulacion1.getFecha(), fecha);
	}

	@Test
	public void testSetEstado() {
		simulacion1.setEstado(EstadoSimulacion.PREPARADA);
		assertEquals(simulacion1.getEstado(), EstadoSimulacion.PREPARADA);
	}

	@Test
	public void testToString() {
		assertNotNull(simulacion1.toString());
	}

	@Test
	public void testEquals() {
		try {		
			assertTrue(simulacion2.equals(new Simulacion(usr, fecha, mundo, EstadoSimulacion.PREPARADA)));
		} 
		catch (ModeloException e) {	}
	}

	@Test
	public void testClone() {
		assertEquals(simulacion2, simulacion2.clone());
	}

	@Test
	public void testHashCode() {
		assertEquals(simulacion2.hashCode(), 1316541442);
	}

} // class
