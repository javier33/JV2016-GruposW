/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit de prueba automatizada de las características de la clase Mundo según el modelo 2.
 * @since: prototipo2
 * @source: MundoTest.java 
 * @version: 2.1 - 2017.04.25
 * @author: ajp
 */

package modelo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.Mundo;
import modelo.Patron;
import modelo.Posicion;
import modelo.ModeloException;
import util.*;

public class MundoTest {
	
	private Mundo mundo1;
	private Mundo mundo2;
	
	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosPrueba() {	
		try {
			this.mundo1 = new Mundo();
			ArrayList<Integer> constantesMundo = new ArrayList<Integer>();
			Map<Patron, Posicion> distribucionPatrones = new Hashtable<Patron, Posicion>();
			distribucionPatrones.put(new Patron(), new Posicion());
			distribucionPatrones.put(new Patron(), new Posicion());
			byte[][] espacio = new byte[100][100];
			this.mundo2 = new Mundo("Mundo2", constantesMundo, distribucionPatrones, espacio);
		} 
		catch (ModeloException e) {	}
	}
	
	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After
	public void borrarDatosPrueba() {	
		mundo1 = null;
		mundo2 = null;
	}
	
	@Test
	public void testMundoConvencional(){
		assertNotNull(mundo1);
	}
	
	@Test
	public void testMundoMundo(){
		
		Mundo mundoCopia = null;
		try {
			mundoCopia = new Mundo(mundo1);
		} catch (ModeloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(mundo1, mundoCopia);
	}
	
	@Test
	public void testMundo(){
		
		Mundo mundoPrueba = null;
		try {
			mundoPrueba = new Mundo();
		} catch (ModeloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(mundoPrueba);
	}
	
	@Test
	public void testMundoConvencionalNull(){
		try {
			Mundo mundoPrueba = new Mundo(null, null, null, null);
			fail("No debe llegar aquí...");
		} catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetNombre() {	
		try {
			mundo1.setNombre("Mundo1");
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		assertEquals(mundo1.getNombre(), ("Mundo1"));
	}
	
	@Test
	public void testSetConstantes(){
		List<Integer> ls = new ArrayList<Integer>();
		
		try {
			mundo1.setConstantes(ls);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		assertEquals(mundo1.getConstantes(), ls);
	}
	
	@Test
	public void testSetDistribucion(){
		
		Map<Patron,Posicion> mp = new Hashtable<Patron, Posicion>();
		
		try {
			mp.put(new Patron("Prueba1", new byte[][]{{1,0},{0,1}}), new Posicion(0,0));
			mundo1.setDistribucion(mp);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		assertEquals(mundo1.getDistribucion(), mp);
	}
	
	@Test
	public void testSetEspacio(){
		
		byte[][] hola = new byte[5][5];
		
		try {
			
			hola = new byte[][]{
				{0,1,0,1,0},
				{0,1,0,0,1},
				{0,0,1,0,1},
				{1,0,0,1,0},
				{0,1,1,0,1}
			};
			
			mundo1.setEspacio(hola);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
		assertSame(mundo1.getEspacio(), hola);
	}
	
	
	@Test
	public void testSetNombreNull(){
		try {
			mundo1.setNombre(null);
			fail("No deberia llegar aqui.");
		} catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetConstantesNull(){
		try {
			mundo1.setConstantes(null);
			fail("No deberia llegar aqui.");
		} catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	public void TestSetDistribucionNull(){
		try {
			mundo1.setDistribucion(null);
			fail("No deberia llegar aqui.");
		} catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void TestSetEspacioNull(){
		try {
			mundo1.setEspacio(null);
			fail("No deberia llegar aqui.");
		} catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testToString(){
		assertNotNull(mundo1.toString());
	}
	
	@Test
	public void testClone(){
		assertNotSame(mundo1, mundo1.clone());
	}
	
	@Test
	public void testHashCode(){
		assertEquals(mundo1.hashCode(), 812670444);
	}
}
