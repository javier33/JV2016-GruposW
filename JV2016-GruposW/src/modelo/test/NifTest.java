/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit de prueba automatizada de las características de la clase Nif según el modelo 2.
 * @since: prototipo2
 * @source: TestNif.java 
 * @version: 2.1 - 2017.04.25
 * @author: ajp
 */

package modelo.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.Correo;
import modelo.ModeloException;
import modelo.Nif;

public class NifTest {
	private Nif nif1; 
	private Nif nif2;

	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosPrueba() {
		try {
			// Objetos para la prueba.
			nif1 = new Nif(); 
			nif2 = new Nif("00000001R");
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After	
	public void borrarDatosPrueba() {
		nif1 = null;
	}

	// Test CON DATOS VALIDOS
	@Test
	public void testNifConvencional() {
		assertEquals(nif2.getTexto(), "00000001R");
	}

	@Test
	public void testNifDefecto() {
		assertEquals(nif1.getTexto(), "00000000T");
	}

	@Test
	public void testNifCopia() {
		try {
			assertEquals(nif2, new Nif(nif2));
		} 
		catch (ModeloException e) {	}
	}

	@Test
	public void testGetTexto() {
		assertEquals(nif2.getTexto(), "00000001R");
	}

	@Test
	public void testSetTexto() {
		try {
			nif1.setTexto("00000001R");
		} 
		catch (ModeloException e) {	}
		assertEquals(nif1.getTexto(), "00000001R");
	}

	@Test
	public void testToString() {
		assertEquals(nif2.toString(), "00000001R");
	}

	@Test
	public void testEqualsObject() {
		try {
			nif1 = new Nif("00000001R");
		} 
		catch (ModeloException e) { }
		assertTrue(nif1.equals(nif2));
	}

	@Test
	public void testClone() {
		assertNotSame(nif2, nif2.clone());
	}

	@Test
	public void testHashCode() {
		assertEquals(nif1.hashCode(), -2032408461);
	}

	// Test CON DATOS NO VALIDOS
	@Test
	public void testNifConvencionalNull() {
		try {
			String texto = null;
			nif1 = new Nif(texto);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testNifConvencionalFormato() {
		try {
			nif1 = new Nif("00000000-T");
			fail("No debe llegar aquí...");
		}
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testNifConvencionalLetra() {
		try {
			nif1 = new Nif("00000000F");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testSetTextoNull() {
		try {
			nif1.setTexto(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testSetTextoMalFormato() {
		try {
			nif1.setTexto("00000000-T");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testSetTextoLetraIncorrecta() {
		try {
			nif1.setTexto("00000000F");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

} //class
