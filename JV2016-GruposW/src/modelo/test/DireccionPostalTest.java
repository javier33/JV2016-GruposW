package modelo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;

public class DireccionPostalTest {

	private DireccionPostal direccion1;
	private DireccionPostal direccion2;

	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosPrueba() {
		// Objetos para la prueba.
		try {
			direccion1 = new DireccionPostal();
			direccion2 = new DireccionPostal("Flan", "21", "88888", "Murcia");
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
		direccion1 = null;
		direccion2 = null;
	}

	// Test con DATOS VALIDOS
	@Test
	public void testDireccionPostalConvencional() {
		assertEquals(direccion2.getCalle(), "Flan");
		assertEquals(direccion2.getNumero(), "21");
		assertEquals(direccion2.getCP(), "88888");
		assertEquals(direccion2.getPoblacion(), "Murcia");
	}

	@Test
	public void testDireccionPostalDefecto() {
		assertEquals(direccion1.getCalle(), "Calle");
		assertEquals(direccion1.getNumero(), "00");
		assertEquals(direccion1.getCP(), "99999");
		assertEquals(direccion1.getPoblacion(), "Población");
	}

	@Test
	public void testDireccionPostalCopia() {
		try {
			assertEquals(direccion2, new DireccionPostal(direccion2));
		} 
		catch (ModeloException e) {	}
	}
	
	@Test
	public void testGetCalle() {
		assertEquals(direccion2.getCalle(), "Flan");
	}

	@Test
	public void testGetNumero() {
		assertEquals(direccion2.getNumero(), "21");
	}

	@Test
	public void testGetCodigoPostal() {
		assertEquals(direccion2.getCP(), "88888");
	}
	
	@Test
	public void testGetPoblacion() {
		assertEquals(direccion2.getPoblacion(), "Murcia");
	}
	
	@Test
	public void testSetCalle() {
		try {
			direccion1.setCalle("Calle");
		} catch (ModeloException e) { }	
		assertEquals(direccion1.getCalle(), "Calle");
	}
	
	@Test
	public void testSetNumero() {
		try {
			direccion1.setNumero("00");
		} catch (ModeloException e) { }	
		assertEquals(direccion1.getNumero(), "00");
	}
	
	@Test
	public void testSetCP() {
		try {
			direccion1.setCP("99999");
		} catch (ModeloException e) { }	
		assertEquals(direccion1.getCP(), "99999");
	}
	
	@Test
	public void testSetPoblacion() {
		try {
			direccion1.setPoblacion("Población");
		} catch (ModeloException e) { }
		assertEquals(direccion1.getPoblacion(), "Población");
	}
	
	@Test
	public void testToString() {
		assertEquals(direccion2.toString(), "calle: Flan\t" + "numero: 21\t" + "cp: 88888\t"  + "poblacion: Murcia\n");
	}
	
	@Test
	public void testEqualsObject() {
		try {		
			assertTrue(direccion1.equals(new DireccionPostal()));
		} 
		catch (ModeloException e) {	}
	}

	@Test
	public void testClone() {
		assertEquals(direccion2, direccion2.clone());
	}
	
	// Test con DATOS NO VALIDOS
	@Test
	public void testCorreoConvencionalCalleNull() {	

		try {
			new DireccionPostal(null, "21", "88888", "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testCorreoConvencionalNumeroNull() {	

		try {
			new DireccionPostal("Flan", null, "88888", "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCorreoConvencionalCPNull() {	

		try {
			new DireccionPostal("Flan", "21", null, "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCorreoConvencionalPoblacionNull() {	

		try {
			new DireccionPostal("Flan", "21", "88888", null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCorreoConvencionalCalleMalFormato() {	

		try {
			new DireccionPostal("flan", "21", "88888", "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testCorreoConvencionalNumeroMalFormato() {	

		try {
			new DireccionPostal("Flan", "a21", "88888", "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCorreoConvencionalCPMalFormato() {	

		try {
			new DireccionPostal("Flan", "21", "abc", "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCorreoConvencionalPoblacionMalFormato() {	

		try {
			new DireccionPostal("Flan", "21", "88888", "murcia");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetCalleNull() {
		try {
			direccion1.setCalle(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetNumeroNull() {
		try {
			direccion1.setNumero(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetCPNull() {
		try {
			direccion1.setCP(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetPoblacionNull() {
		try {
			direccion1.setPoblacion(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetCalleMalFormato() {
		try {
			direccion1.setCalle("flan");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetNumeroMalFormato() {
		try {
			direccion1.setNumero("a21");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetCPMalFormato() {
		try {
			direccion1.setCP("abc");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetPoblacionMalFormato() {
		try {
			direccion1.setPoblacion("murcia");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) {
			assertTrue(true);
		}
	}
}