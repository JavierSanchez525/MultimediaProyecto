package multimedia.proyecto;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;


public class extraerCampos {

	//Saca el titulo y resta los caracteres reservados para el año (7)
	public static String getTitulo (Document doc) throws IOException {
		String titulo = doc.select("div.title_wrapper").select("h1:not(a)").text();
		if (titulo.length()>7) {
			titulo = titulo.substring(0, titulo.length()-7);
		}
		if(titulo.length() > 1) {
			return titulo;
		}else {
			return null;
		}
	}
	
	//Saca el año del mismo campo que el titulo y lo pasa a int
	public static int getAño (Document doc) throws IOException {
		if(doc.select("div.title_wrapper").select("h1").select("a").text().length() > 1) {
			int año = Integer.parseInt(doc.select("div.title_wrapper").select("h1").select("a").text());
			return año;
		}else {
			return 0;
		}
		
	}
	
	public static GenerosKeyWords getGenerosYKeywords (Document doc) throws IOException { 
		//devuelve en la posicion 0 los generos y en la 1 las keywords
		//(se extraen ambos atributos en el mismo metodo para optimizar la extracción)
		
		GenerosKeyWords almacen = new GenerosKeyWords ();
		
		Elements datos = doc.select("div#titleStoryLine");
		
		//Primero obtengo los generos como un array de strings
		almacen.generos = datos.select("h4:contains(Genres) ~ a").text().split(" ");
		
		
		String urlKeyWords = datos.select("h4:contains(Plot Keywords:) ~ nobr").select("a:contains(See All)").attr("abs:href");
		if(urlKeyWords.length()>5) {
			Document paginaKeyWords =  Jsoup.connect(urlKeyWords).get();
		
			//Guarda las Keywords como un string
			almacen.keyWords = paginaKeyWords.select("tbody").select("div.sodatext").select("a").text();
		}else {
			almacen.keyWords = null;
		}
		
		return almacen;
	}
	
	//Guarda los actores (teniendo en cuenta el boton de "see full cast") como un string
	public static String getActores (Document doc) throws IOException { 
		
		String actores = null;
		Elements datos = doc.select("div#titleCast");
		String urlActores = datos.select("div.see-more").select("a:contains(See full cast)").attr("abs:href");
		if(urlActores.length()>5) {
			Document paginaCast =  Jsoup.connect(urlActores).get();
			actores = paginaCast.select("table.cast_list").select("tbody").select("tr.odd, tr.even").select("td:not(td.ellipsis)").select(" td:not(td.character)").text();
		}else {
			actores = null;
		}
		
		return actores;
	}
	
	//Guarda la descripcion en un string
	public static String getDescripcion (Document doc) throws IOException {
		String descripcion = doc.select("div.summary_text").text();
		if(descripcion.length() > 1) {
			return descripcion;
		}else {
			return null;
		}
	}
}