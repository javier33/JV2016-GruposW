package modelo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.DireccionPostal;
import modelo.ModeloException;

public class DireccionPostalTest {

	private DireccionPostal d1;
	private DireccionPostal d2;

	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void crearDatosPrueba() {
		// Objetos para la prueba.
		try {
			d1 = new DireccionPostal();
			d2 = new DireccionPostal("Flan", "21", "88888", "Murcia");
		} catch (ModeloException e) {
		}

	}

	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After
	public void borrarDatosPrueba() {
		d1 = null;
		d2 = null;
	}

	public void testGetCalle() {
		assertEquals(d2.getCalle(), "Flan");
	}

	@Test
	public void testSetCalle() {
		d1.getCalle();
		assertEquals(d1.getCalle(), "Calle");
		assertTrue(d1 != null);
	}

	public void testGetNumero() {
		assertEquals(d2.getNumero(), "21");
	}

	@Test
	public void testSetNumero() {
		d1.getNumero();
		assertEquals(d1.getNumero(), "00");
		assertTrue(d1 != null);
	}

	public void testGetCodigoPostal() {
		assertEquals(d2.getCodigoPostal(), "88888");
	}

	@Test
	public void testSetCP() {
		d1.getCodigoPostal();
		assertEquals(d1.getCodigoPostal(), "99999");
		assertTrue(d1 != null);
	}

	public void testGetPoblacion() {
		assertEquals(d2.getPoblacion(), "Murcia");
	}

	@Test
	public void testSetPoblacion() {
		d1.getPoblacion();
		assertEquals(d1.getPoblacion(), "Población");
		assertTrue(d1 != null);
	}
}
