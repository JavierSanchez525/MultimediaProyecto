package PracticaMultimedia.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;



public class escribirJSON {

	
	public static String getTitulo (Document doc) throws IOException {
		String titulo = doc.select("div.title_wrapper").select("h1").text().replace('(', '-').split("-")[0];
		return titulo;
	}
	
	public static int getAño (Document doc) throws IOException {
		int año = Integer.parseInt(doc.select("div.title_wrapper").select("h1").text().replace('(', '-').split("-")[1].replace(")", ""));
		return año;
	}
	
	public static String[] getGenerosYKeywords (Document doc) throws IOException { 
		//devuelve en la posicion 0 los generos y en la 1 las keywords
		//(se extraen ambos atributos en el mismo metodo para optimizar la extracción)
		String [] GenerosKeywords = new String [2];
		Elements datos = doc.select("div#titleStoryLine");
		
		//Primero obtengo los generos (SOLO OBTIENE LOS GENEROS DE LA PAGINA EN CASO DE "SEE MORE" HAY QUE CAMBIAR CODIGO)
		GenerosKeywords[0] = datos.select("h4:contains(Genres) ~ a").text();
		
		
		String urlKeyWords = datos.select("h4:contains(Plot Keywords:) ~ nobr").select("a:contains(See All)").attr("abs:href");
		
		Document paginaKeyWords =  Jsoup.connect(urlKeyWords).get();
		// (CUIDADO YA QUE AQUELLOS KEYWORDS QUE SEAN COMPUESTOS ES DECIR QUE SEAN MAS DE UNA PALABRA SE SEPARARAN) 
		GenerosKeywords[1] = paginaKeyWords.select("tbody").select("div.sodatext").select("a").text();
		//select("tbody").select("div.sodatext").
		
		return GenerosKeywords;
	}
	
	public static String getActores (Document doc) throws IOException { 
		
		
		Elements datos = doc.select("div#titleCast");
		String urlActores = datos.select("div.see-more").select("a:contains(See full cast)").attr("abs:href");
		Document paginaCast =  Jsoup.connect(urlActores).get();
		String actores = paginaCast.select("table.cast_list").select("tbody").select("tr.odd, tr.even").select("td:not(td.ellipsis)").select(" td:not(td.character)").text();
		
		
		return actores;
	}
	
	public static String getDescipcion (Document doc) throws IOException {
		String descripcion = doc.select("div.summary_text").text();
		return descripcion;
	}
	
}
