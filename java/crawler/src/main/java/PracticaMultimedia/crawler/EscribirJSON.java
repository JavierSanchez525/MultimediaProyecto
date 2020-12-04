package multimedia.proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.json.JSONObject;

public class EscribirJSON {
	public static void escribir(JSONObject pelicula, JSONObject operacion) {
		  try {
		
		FileWriter fw = new FileWriter("Listo_para_elastic\\filename.json",true);
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