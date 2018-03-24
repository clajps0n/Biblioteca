package testdatabuilder;

import java.util.Date;

import dominio.Libro;
import dominio.Prestamo;

public class PrestamoTestDataBuilder 
{
	public static LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
	private static final Date FECHA_SOLICITUD = new Date();
	private static final Libro LIBRO = libroTestDataBuilder.build();
	private static final Date FECHA_ENTREGA_MAXIMA = null;
	private static final String NOMBRE_USUARIO = "Pedro Navajas";
	
	private Date fechaSolicitud;
	private Libro libro;
	private Date fechaEntregaMaxima;
	private String nombreUsuario;
		
	public PrestamoTestDataBuilder() 
	{		
		this.fechaSolicitud = FECHA_SOLICITUD;
		this.libro = LIBRO;
		this.fechaEntregaMaxima = FECHA_ENTREGA_MAXIMA;
		this.nombreUsuario = NOMBRE_USUARIO;
	}

	public PrestamoTestDataBuilder conFechaSolicitud(Date fechaSolicitud) 
	{
		this.fechaSolicitud = fechaSolicitud;
		return this;
	}
	

	public PrestamoTestDataBuilder conLibro(Libro libro) 
	{
		this.libro = libro;
		return this;
	}

	
	public PrestamoTestDataBuilder conFechaEntregaMaxima(Date fechaEntregaMaxima) 
	{
		this.fechaEntregaMaxima = fechaEntregaMaxima;
		return this;
	}

	
	public PrestamoTestDataBuilder conNombreUsuario(String nombreUsuario) 
	{
		this.nombreUsuario = nombreUsuario;
		return this;
	}
	
	public Prestamo build()
	{
		return new Prestamo(this.fechaSolicitud, this.libro, this.fechaEntregaMaxima, this.nombreUsuario);
	}
	
	public Prestamo buildPorDefecto()
	{
		return new Prestamo(this.libro);
	}
}
