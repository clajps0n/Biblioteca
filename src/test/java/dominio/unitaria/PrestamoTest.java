package dominio.unitaria;

import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import dominio.Libro;
import dominio.Prestamo;
import testdatabuilder.LibroTestDataBuilder;
import testdatabuilder.PrestamoTestDataBuilder;

public class PrestamoTest
{
	public static LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
	
	private static final Date 	FECHA_SOLICITUD 		= new Date();
	private static final Libro 	LIBRO 					= libroTestDataBuilder.build();
	private static final Date 	FECHA_ENTREGA_MAXIMA 	= null;
	private static final String NOMBRE_USUARIO 			= "Pedro Navajas";
	
	@Test
	public void prestarTest() 
	{
		PrestamoTestDataBuilder prestamoTestDataBuilder = new PrestamoTestDataBuilder()
				.conFechaSolicitud(FECHA_SOLICITUD)
				.conLibro(LIBRO)
				.conFechaEntregaMaxima(FECHA_ENTREGA_MAXIMA)
				.conNombreUsuario(NOMBRE_USUARIO);
	
		Prestamo prestamo = prestamoTestDataBuilder.build();
		
		assertEquals(FECHA_SOLICITUD, prestamo.getFechaSolicitud());
		assertEquals(LIBRO, prestamo.getLibro());
		assertEquals(FECHA_ENTREGA_MAXIMA, prestamo.getFechaEntregaMaxima());
		assertEquals(NOMBRE_USUARIO, prestamo.getNombreUsuario());				
	}	
}
