/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit de prueba automatizada de las características de la clase Persona según el modelo 2.
 * @since: prototipo2
 * @source: TestPersona.java 
 * @version: 2.1 - 2017.04.25
 * @author: ajp
 */

package modelo.test;

import static org.junit.Assert.*;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Persona;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Fecha;

public class PersonaTest {
	private Persona persona1; 
	private Persona persona2; 

	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosPrueba() {
		try {
			// Objetos para la prueba.
			persona1 = new Usuario(); 
			persona2 = new Usuario(new Nif("00000001R"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21),
					new Fecha(2017,05,12), new ClaveAcceso(), RolUsuario.NORMAL);
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
		persona1 = null;
	}

	// Test CON DATOS VALIDOS
	@Test
	public void testPersonaConvencional() {
		try {
			assertEquals(persona2.getNif(), new Nif("00000001R"));
			assertEquals(persona2.getNombre(), "Luis");
			assertEquals(persona2.getApellidos(), "Pérez Ruiz");
			assertEquals(persona2.getDomicilio(), new DireccionPostal("Roncal", "10", "30130", "Murcia"));
			assertEquals(persona2.getCorreo(), new Correo("luis@gmail.com"));
			assertEquals(persona2.getFechaNacimiento(), new Fecha(2000, 03, 21));
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testPersonaDefecto() {
		try {
			assertEquals(persona1.getNif(), new Nif("00000000T"));
			assertEquals(persona1.getNombre(), "Nombre");
			assertEquals(persona1.getApellidos(), "Apellidos1 Apellido2");
			assertEquals(persona1.getDomicilio(), new DireccionPostal());
			assertEquals(persona1.getCorreo(), new Correo());
			assertEquals(persona1.getFechaNacimiento(), new Fecha());
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testGetNif() {
		try {
			assertEquals(persona2.getNif(), new Nif("00000001R"));
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testGetNombre() {
		assertEquals(persona2.getNombre(), "Luis");
	}

	@Test
	public void testGetApellidos() {
		assertEquals(persona2.getApellidos(), "Pérez Ruiz");
	}

	@Test
	public void testGetDomicilio() {
		try {
			assertEquals(persona2.getDomicilio(), new DireccionPostal("Roncal", "10", "30130", "Murcia"));
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testGetFechaNacimiento() {
		assertEquals(persona2.getFechaNacimiento(), new Fecha(2000, 03, 21));
	}

	@Test
	public void testGetCorreo() {
		try {
			assertEquals(persona2.getCorreo(), new Correo("luis@gmail.com"));
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testSetNif() {
		try {
			persona1.setNif(new Nif("00000001R"));
			assertEquals(persona1.getNif(), new Nif("00000001R"));
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testSetNombre() {
		try {
			persona1.setNombre("Luis");
		} 
		catch (ModeloException e) { }
		assertEquals(persona1.getNombre(), "Luis");
	}

	@Test
	public void testSetCorreo() {
		try {
			persona1.setCorreo(new Correo("luis@gmail.com"));
			assertEquals(persona1.getCorreo(), new Correo("luis@gmail.com"));
		} 
		catch (ModeloException e) { }
	}

	@Test
	public void testSetApellidos() {
		try {
			persona1.setApellidos("Sánchez Azul");
		} 
		catch (ModeloException e) { }
		assertEquals(persona1.getApellidos(), "Sánchez Azul");
	}

	@Test
	public void testSetDomicilio() {
		try {
			persona1.setDomicilio(new DireccionPostal("Roncal", "10", "30130", "Murcia"));
		} 
		catch (ModeloException e) { }
		assertEquals(persona1.getDomicilio(), persona2.getDomicilio());
	}

	@Test
	public void testSetFechaNacimiento() {
		try {
			persona1.setFechaNacimiento(new Fecha(2000, 03, 21));
		} 
		catch (ModeloException e) { }
		assertEquals(persona1.getFechaNacimiento(), persona2.getFechaNacimiento());
	}

	@Test
	public void testToString() {
		assertNotNull(persona2.toString());
	}

	@Test
	public void testEqualsObject() {
		try {
			persona1 = new Usuario(new Nif("00000001R"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21),
					new Fecha(2017,05,12), new ClaveAcceso(), RolUsuario.NORMAL);
		} 
		catch (ModeloException e) { }
		assertTrue(persona1.equals(persona2));
	}

	public void testHashCode() {
		assertEquals(persona2.hashCode(), -2032408461);
	}

	// Test CON DATOS NO VALIDOS
	@Test
	public void testSetNifNull() {
		try {
			persona1.setNif(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetNombreNull() {
		try {
			persona1.setNombre(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testSetApellidosNull() {
		try {
			persona1.setApellidos(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testSetDomicilioNull() {
		try {
			persona1.setDomicilio(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testSetCorreoNull() {
		try {
			persona1.setCorreo(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
			assertTrue(true);
		}
	}

	@Test
	public void testSetFechaNacimientoNull() {
		try {
			persona2.setFechaNacimiento(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
			assertTrue(true);
		}
	}

} //class
