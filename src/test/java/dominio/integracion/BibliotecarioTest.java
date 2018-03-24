package dominio.integracion;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.Prestamo;
import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.sistema.SistemaDePersistencia;
import testdatabuilder.LibroTestDataBuilder;
import testdatabuilder.PrestamoTestDataBuilder;

public class BibliotecarioTest 
{

	private static final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";
	private static final String ISBN_PALINDROMO = "AX1221XA";	
	private static final String NOMBRE_USUARIO = "Bayardo San Rom�n";
	
	
	private SistemaDePersistencia sistemaPersistencia;
	
	private RepositorioLibro repositorioLibros;
	private RepositorioPrestamo repositorioPrestamo;

	@Before
	public void setUp() 
	{		
		sistemaPersistencia = new SistemaDePersistencia();
		
		repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();
		
		sistemaPersistencia.iniciar();
	}
	

	@After
	public void tearDown() 
	{
		sistemaPersistencia.terminar();
	}

	@Test
	public void prestarLibroTest() 
	{
		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);
		
		Prestamo prestamo = new PrestamoTestDataBuilder().conNombreUsuario("Bayardo San Rom�n").build();

		// act
		blibliotecario.prestar(libro.getIsbn(), prestamo.getNombreUsuario());

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));
	}

	@Test
	public void prestarLibroNoDisponibleTest() 
	{
		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		
		repositorioLibros.agregar(libro);
		
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);
		
		// act
		blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);

		try 
		{			
			blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
			fail();
			
		} catch (PrestamoException e) 
		{
			// assert
			Assert.assertEquals(Bibliotecario.EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE, e.getMessage());
		}
	}
	
	@Test
	public void prestarLibroISBNpalindromo() 
	{
		// arrange
		Libro libro = new LibroTestDataBuilder().conIsbn(ISBN_PALINDROMO).build();
		
		repositorioLibros.agregar(libro);
		
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);
				
		try 
		{			
			blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
			fail();
			
		} catch (PrestamoException e) 
		{
			// assert
			Assert.assertEquals(Bibliotecario.LOS_PALINDROMOS_SOLO_SE_USAN_EN_BIBLIOTECA, e.getMessage());
		}
	}
}
