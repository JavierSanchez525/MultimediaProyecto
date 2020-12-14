package multimedia.proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class leerExcel {
	
	//Metodo que lee las URL del Excel
	public static String[] leerURLs() throws IOException, InvalidFormatException {
        OPCPackage pkg = OPCPackage.open(new File("src\\MovieGenreIGC_v3.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        String[] urls = new String[rows];
        
        for (int i = 1; i <= rows; i++) {
        	XSSFRow row = sheet.getRow(i);
        	
        	XSSFCell url = row.getCell(1);
        	String titulo_url = url.getStringCellValue();
        	urls[i-1] = titulo_url+'/';
        	
        }
        wb.close();
        return urls;
    }
	
	public static void main(String[] args) throws IOException, InvalidFormatException{
		
		String[] pelicula = leerURLs();
		
		//Crea el archivo JSON si no existe
		 try {
		   		
	           String ruta = "Listo_para_elastic\\archivoJSON.json";
	          
	           File file = new File(ruta);
	           
	           if (file.exists()) {
	        	   file.delete();
	               
	           }
	           else {
	        	   file.createNewFile();
	           }
	           
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		 
		 Id idIncremental = new Id();
		 
		 //Bucle que recorre todo el array de URLs y saca toda informacion de las mismas
		for (int i = 0; i < pelicula.length; i++) {
			
			//System.out.println(i);
			
	        JSONObject datosExtraidos = new JSONObject();
	        JSONObject id = new JSONObject();
	        JSONObject operacion = new JSONObject();
	        if (i!=15925) { //15925 tiene una url que da error 404 not found
	        	Document doc = Jsoup.connect(pelicula[i]).get();
	        	String titulo = extraerCampos.getTitulo(doc);
				int anio = extraerCampos.getAño(doc);
				
				GenerosKeyWords datos = new GenerosKeyWords();
				String descripcion = extraerCampos.getDescripcion(doc);
				datos.generos  = extraerCampos.getGenerosYKeywords(doc).generos;
				datos.keyWords  = extraerCampos.getGenerosYKeywords(doc).keyWords;
				String actores = extraerCampos.getActores(doc);
				
				datosExtraidos.put("anio", anio );
				datosExtraidos.put("generos", datos.generos );
				datosExtraidos.put("keyWords", datos.keyWords );
				datosExtraidos.put("actores", actores );
				datosExtraidos.put("descripcion", descripcion );
				datosExtraidos.put("titulo", titulo );
				id.put("id", idIncremental.incremental);
				operacion.put("index", id);
			    idIncremental.incremental ++;
			   	
				EscribirJSON.escribir(datosExtraidos,operacion);
	        }    
		}
	}
}