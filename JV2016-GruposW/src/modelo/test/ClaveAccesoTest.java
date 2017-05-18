/** 
 * Proyecto: Juego de la vida.
 * Prueba Junit4 de la clase Claveacceso según el modelo 2.
 * @since: prototipo 2.0
 * @source: ClaveAccesoTest.java 
 * @version: 2.1 - 2017.05.12
 * @author: ajp
 */

package modelo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.ClaveAcceso;
import modelo.ModeloException;
import util.Criptografia;

public class ClaveAccesoTest {
	private ClaveAcceso clave1;
	private ClaveAcceso clave2 ;
	
	
	@Before
	public void iniciarlizarDatosPrueba() {	
		try {
			clave1 = new ClaveAcceso();
			clave2 = new ClaveAcceso("Miau#2");
		} 
		catch (ModeloException e) {	}
	}
	
	@After
	public void borrarDatosPrueba() {	
		clave1 = null;
	}
	
	// Test con DATOS VALIDOS
	@Test
	public void testClaveAccesoConvencional() {	
		assertEquals(clave2.getTexto(), Criptografia.cesar("Miau#2"));
	}

	@Test
	public void testClaveAccesoDefecto() {
		assertEquals(clave1.getTexto(), Criptografia.cesar("Miau#0"));
	}

	@Test
	public void testClaveAccesoCopia() {
		try {
			assertEquals(clave2, new ClaveAcceso(clave2));
		} 
		catch (ModeloException e) {	}
		
	}

	@Test
	public void testGetTexto() {
		assertEquals(clave2.getTexto(), Criptografia.cesar("Miau#2"));
	}

	@Test
	public void testSetTexto() {
		try {
			clave1.setTexto("Miau#1");
			assertEquals(clave1.getTexto(), Criptografia.cesar("Miau#1"));
		} 
		catch (ModeloException e) {	}
		
	}

	@Test
	public void testToString() {
		assertEquals(clave2.toString(), Criptografia.cesar("Miau#2"));
	}

	@Test
	public void testEquals() {
		try {
			assertTrue(clave2.equals(new ClaveAcceso("Miau#2")));
		} 
		catch (ModeloException e) { }
		
	}

	@Test
	public void testClone() {
		assertEquals(clave2, clave2.clone());
	}

	@Test
	public void testHashCode() {
		assertEquals(clave2.hashCode(), -1900842130);
	}

	// Test con DATOS NO VALIDOS
	@Test
	public void testClaveAccesoConvencionalTextoNull() {	
		try {
			String texto = null;
			new ClaveAcceso(texto);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}
	
	@Test
	public void testClaveAccesoConvencionalTextoMalFormato() {	
		try {
			new ClaveAcceso("hola");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetTextoNull() {
		try {
			clave1.setTexto(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetTextoMalFormato() {
		try {
			clave1.setTexto("hola");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}
	
} //class
