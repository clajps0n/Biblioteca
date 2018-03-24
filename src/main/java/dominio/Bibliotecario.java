package dominio;



import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario 
{
	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String LOS_PALINDROMOS_SOLO_SE_USAN_EN_BIBLIOTECA = "los libros palíndromos solo se pueden utilizar en la biblioteca";
	
	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;
	
	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) 
	{
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;
	}
	
	public void prestar(String isbn, String usuario) 
	{	
		Date fechaEntrega = null;		 
		Libro libro = null;
		Prestamo prestamo = null;
		Date fechaSolicitud = new Date();
			
		//Si el libro está prestado
		if (esPrestado(isbn)) 
		{
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}//Si no está prestado
		else
		{
			//Si el isbn es palíndromo
			if (esPalindromo(isbn)) 
			{
				throw new PrestamoException(LOS_PALINDROMOS_SOLO_SE_USAN_EN_BIBLIOTECA);
			}else
			{
				fechaEntrega = calcularFechaEntrega(sumaDigitosISBN(isbn), fechaSolicitud);					
			}				
			libro = this.repositorioLibro.obtenerPorIsbn(isbn);
			
			prestamo = new Prestamo(new Date(), libro, fechaEntrega, usuario);
			
			repositorioPrestamo.agregar(prestamo);
		}
				
	}

	public boolean esPalindromo(String cadena) 
	{		
		String inverso = "";
		
		for (int i = cadena.length()-1; i >= 0; i--) 
		{
			inverso += cadena.charAt(i);
		}
		
		return inverso.equals(cadena);	
	}
	
		
	public int sumaDigitosISBN(String cadena)
	{
		int suma = 0;
		for (int i = 0; i < cadena.length(); i++) 
		{
			try 
			{
				suma = suma + Integer.parseInt(String.valueOf(cadena.charAt(i)));
			} catch (Exception e) 
			{
				e.getMessage();
			}
		}	
		
		return suma;
	}
	
	public Date calcularFechaEntrega(int sumaISBN, Date fechaSolicitud)
	{
		Date fechaEntregaMaxima = null;
			
		if (sumaISBN > 30) 
		{
			Calendar calendar = Calendar.getInstance();			
			calendar.setTime(fechaSolicitud);						
			
			int cont = 1;
			while(cont < 15)
			{
			    calendar.add(Calendar.DAY_OF_MONTH, 1);
			    if(!calendar.getTime().toString().contains("Sun"))
			    {
			        cont++;
			    }
			}			
			
			fechaEntregaMaxima = calendar.getTime();			
		}
		return fechaEntregaMaxima;
	}

	public boolean esPrestado(String isbn) 
	{
		if (this.repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn) != null) 
		{
			return true;
		}
		return false;
	}
}
