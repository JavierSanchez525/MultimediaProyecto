package PracticaMultimedia.crawler;

import java.io.File;
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

	public static String[] leerURLs() throws IOException, InvalidFormatException {
        OPCPackage pkg = OPCPackage.open(new File("src\\MovieGenreIGC_v3.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        String[] urls = new String[rows];
        
        for (int i = 1; i <= 6/*rows*/; i++) {
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
		for (int i = 0; i < 5; i++) {
			
	        JSONObject myObject = new JSONObject();
	        
	        Document doc = Jsoup.connect(pelicula[i]).get();
	        /*
	        // Sacamos el titulo
	        
	        myObject.put("titulo",  );
	        
	        // Sacamos el año
	        myObject.put("año", );

			*/
			String titulo = escribirJSON.getTitulo(doc);
			int año = escribirJSON.getAño(doc);
			String [] GenerosYKeyWords = new String [2];
			GenerosYKeyWords = escribirJSON.getGenerosYKeywords(doc);
			String actores = escribirJSON.getActores(doc);
			String descripcion = escribirJSON.getDescipcion(doc);
			System.out.println(pelicula[i]);
			System.out.println(titulo);
			System.out.println(año);
			System.out.println(GenerosYKeyWords[0]);
			System.out.println(GenerosYKeyWords[1]);
			System.out.println(actores);
			System.out.println(descripcion);
			System.out.println();
			System.out.println();
			
		}	
		
		
	}
}
