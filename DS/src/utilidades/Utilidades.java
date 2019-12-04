package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Utilidades {

	public static void main(String[] args) throws IOException {
		//writeFiles();
		writeWarning("Tomar antibiótico", "2019-10-01 08:00", "2019-10-20 20:00", "8");
		writeWarning("Deixa de tomar merdas crl", "2019-10-01 08:00", "2019-10-20 20:00", "8");
		
		//deleteWarning("Deixa de tomar merdas crl");
	}

	public static void writeFiles() throws IOException {
		File f = new File("warning.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		FileWriter fileWriter = new FileWriter("warning.txt");
		fileWriter.write("aaa");
		fileWriter.close();

	}

	public static void writeWarning(String mensagem, String dataInicio, String dataFim, String periodicidade)
			throws IOException {
		File f = new File("warning.txt");
		if (!f.exists()) {
			f.createNewFile();
		}
		
		String horaInicio = dataInicio.substring(11); // inicio da hora na string dataInicio
		
		FileWriter fw = new FileWriter("warning.txt", true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write(mensagem + " de " + dataInicio + " até " + dataFim + " de " + periodicidade + " em " + periodicidade + " horas ");
		out.newLine();
		out.close();
		fw.close();
	}
	
	public static void deleteWarning(String mensagem) throws IOException {
		File f = new File("warning.txt");
		if (!f.exists()) {
			f.createNewFile();
		}
		
		File fAux = new File("temp.txt");
		if(!fAux.exists()) {
			fAux.createNewFile();
		}
		
		FileWriter aux = new FileWriter(fAux, true);
		
		BufferedReader in = new BufferedReader(new FileReader(f));
		String linha;
		StringBuilder sb = new StringBuilder();
		while((linha = in.readLine()) != null) {
			if(!linha.contains(mensagem)) {
				sb.append(linha);
				aux.write(sb.toString());
				sb = new StringBuilder();
			} else {
				aux.write(sb.toString());
				sb = new StringBuilder();
			}
			aux.write("\n");
		}
		
		in.close();
		aux.close();
		Files.delete(f.toPath());
		fAux.renameTo(new File("warning.txt"));
	}

}