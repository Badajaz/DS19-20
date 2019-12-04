package utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utilidades {

	public static void main(String[] args) throws IOException {
		writeFiles();

	}
	
	
	
	
	
	public static void writeFiles() throws IOException{
		File f = new File("warning.txt");
		if(!f.exists()) {
			f.createNewFile();
		}
		
		FileWriter fileWriter = new FileWriter("warning.txt");
	    fileWriter.write("aaa");
	    fileWriter.close();
		
	}

}
