package modelo.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.Correo;
import modelo.ModeloException;

/** Proyecto: Juego de la vida.
 *  Prueba Junit4 de la clase Correo según el modelo 2.1
 *  @since: prototipo2.0
 *  @source: CorreoTest.java 
 *  @version: 2.1 - 2017/05/03
 *  @author: ajp
 *  @author: Grupo 1
 */

public class CorreoTest {
	private Correo correo1;
	private Correo correo2;

	public CorreoTest () {
		try {
			correo2 = new Correo("correo@correo.com");
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void iniciarlizarDatosPrueba() {
		try {
			correo1 = new Correo();
			correo2 = new Correo("correo@correo.com");
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@After
	public void borrarDatosPrueba() {
		correo1 = null;
	}
	
	// Test con DATOS VALIDOS
	@Test
	public void testCorreoConvencional() {
		assertEquals(correo2.getTexto(), "correo@correo.com");
	}

	@Test
	public void testCorreoDefecto() {
		assertEquals(correo1.getTexto(), "correo@correo.com");
	}

	@Test
	public void testCorreoCopia() {
		try {
			assertEquals(correo2, new Correo(correo2));
		} 
		catch (ModeloException e) {	}
	}

	@Test
	public void testGetTexto() {
		assertEquals(correo2.getTexto(), "correo@correo.com");
	}
	
	@Test
	public void testSetTexto() {
		try {
			correo2.setTexto("correo@correo.com");
		} 
		catch (ModeloException e) {	}
		assertEquals(correo2.getTexto(), "correo@correo.com");
	}

	@Test
	public void testCorreoAutentico() {
		assertTrue(correo2.correoAutentico("correo@correo.com"));
	}

	@Test
	public void testToString() {
		assertEquals("correo@correo.com", correo2.toString());
	}
	
	@Test
	public void testEqualsObject() {
		try {		
			assertTrue(correo1.equals(new Correo("correo@correo.com")));
		} 
		catch (ModeloException e) {	}
	}

	@Test
	public void testClone() {
		assertEquals(correo2, correo2.clone());
	}

	@Test
	public void testHashCode() {
		assertEquals(correo1.hashCode(), -2034667982);
	}
	
	// Test con DATOS NO VALIDOS
		@Test
		public void testCorreoConvencionalTextoNull() {	

			try {
				String texto = null;
				new Correo(texto);
				fail("No debe llegar aquí...");
			} 
			catch (AssertionError | ModeloException e) {
				assertTrue(true);
			}
		}

		@Test
		public void testCorreoConvencionalTextoMalFormato() {	
			try {
				new Correo("correocorreo.com");
				fail("No debe llegar aquí...");
			} 
			catch (ModeloException e) {
				assertTrue(true);
			}
		}

		@Test
		public void testSetTextoNull() {
			try {
				correo1.setTexto(null);
				fail("No debe llegar aquí...");
			} 
			catch (AssertionError | ModeloException e) {
				assertTrue(true);
			}
		}

		@Test
		public void testSetTextoMalFormato() {
			try {
				correo1.setTexto("correocorreo.com");
				fail("No debe llegar aquí...");
			} 
			catch (ModeloException e) {
				assertTrue(true);
			}
		}
		
} //class
