package multimedia.proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.json.JSONObject;

public class EscribirJSON {
	public static void escribir(JSONObject pelicula, JSONObject operacion) {
		  try {
		//Escribe en el JSON el id de la pelicula y su informacion
		FileWriter fw = new FileWriter("Listo_para_elastic\\archivoJSON.json",true);
        BufferedWriter bw = new BufferedWriter(fw);
       
        operacion.write(bw);
        bw.write("\n");
        bw.write("\n");
        pelicula.write(bw);
        bw.write("\n");
        bw.write("\n");
        bw.write("\n");
        
        bw.close();
		  } catch (Exception e) {
	           e.printStackTrace();
	       }
	}
}