package dominio.unitaria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioTest 
{
	@Test
	public void esPrestadoTest() 
	{		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertTrue(esPrestado);
	}
	
	@Test
	public void libroNoPrestadoTest() 
	{
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertFalse(esPrestado);
	}
	
	
	@Test
	public void isbnEsPalindromoTest()
	{
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder().conIsbn("1A5885A1");
				
		Libro libro = libroTestDataBuilder.build();
		
		Bibliotecario bibliotecario = mock(Bibliotecario.class);
						
		when(bibliotecario.esPalindromo(libro.getIsbn())).thenReturn(true);
		
		boolean esPalindromo = bibliotecario.esPalindromo(libro.getIsbn());
		
		//assert
		assertTrue(esPalindromo);
	}
	
	@Test
	public void isbnNoEsPalindromoTest()
	{
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder().conIsbn("1A5845B9");
				
		Libro libro = libroTestDataBuilder.build();
		
		Bibliotecario bibliotecario = mock(Bibliotecario.class);
		
		boolean esPalindromo = bibliotecario.esPalindromo(libro.getIsbn());
		
		//assert
		assertFalse(esPalindromo);
	}
	
	@Test
	public void sumaDigitosISBNTest()
	{
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder().conIsbn("5A5555B55");
		
		Libro libro = libroTestDataBuilder.build();
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		int sumaDigitos = bibliotecario.sumaDigitosISBN(libro.getIsbn());
		
		//assert
		assertEquals(35, sumaDigitos);
	}
	
	@Test
	public void calcularFechaEntregaTest() throws ParseException
	{
		// arrange		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		Calendar myCal = Calendar.getInstance();
		myCal.set(Calendar.YEAR, 2018);
		myCal.set(Calendar.MONTH, 3);
		myCal.set(Calendar.DAY_OF_MONTH, 24);
		Date fechaSolicitud = myCal.getTime();
				
		myCal.set(Calendar.MONTH, 4);
		myCal.set(Calendar.DAY_OF_MONTH, 10);
		Date fechaEntregaEsperada = myCal.getTime();
				
		Date fechaEntregaCalculada = bibliotecario.calcularFechaEntrega(31, fechaSolicitud);
				
		System.out.println("fechaSolicitud: "+fechaSolicitud);
		System.out.println("fechaEntregaEsperada: "+fechaEntregaEsperada);
		System.out.println("fechaEntregaCalculada: "+fechaEntregaCalculada);
		
		
		//assert
		assertNotNull(fechaEntregaCalculada);
		assertEquals(fechaEntregaEsperada, fechaEntregaCalculada);		
	}
	
	@Test
	public void calcularFechaEntregaCaeDomingoTest() throws ParseException
	{
		// arrange		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		Calendar myCal = Calendar.getInstance();
		myCal.set(Calendar.YEAR, 2018);
		myCal.set(Calendar.MONTH, 3);
		myCal.set(Calendar.DAY_OF_MONTH, 23);
		Date fechaSolicitud = myCal.getTime();
				
		myCal.set(Calendar.MONTH, 4);
		myCal.set(Calendar.DAY_OF_MONTH, 9);
		Date fechaEntregaEsperada = myCal.getTime();
				
		Date fechaEntregaCalculada = bibliotecario.calcularFechaEntrega(31, fechaSolicitud);
				
		System.out.println("fechaSolicitud: "+fechaSolicitud);
		System.out.println("fechaEntregaEsperada: "+fechaEntregaEsperada);
		System.out.println("fechaEntregaCalculada: "+fechaEntregaCalculada);
				
		//assert
		assertNotNull(fechaEntregaCalculada);
		assertEquals(fechaEntregaEsperada, fechaEntregaCalculada);		
	}
	
	@Test
	public void calcularFechaEntregaNull() throws ParseException
	{
		// arrange		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
				
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
						
		Date fechaSolicitud = new Date(); 
		
		Date fechaEntregaCalculada = bibliotecario.calcularFechaEntrega(29, fechaSolicitud);
		Date fechaEntregaEsperada = null;
		
		//assert
		assertNull(fechaEntregaCalculada);
		assertEquals(fechaEntregaEsperada, fechaEntregaCalculada);
	}

}






