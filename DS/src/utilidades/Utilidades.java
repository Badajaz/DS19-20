package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import i18n.Messages;

public class Utilidades {

	public static void main(String[] args) throws IOException {
		// writeFiles();
		writeWarning("Tomar antibiótico", "2019-10-01 08:00", "2019-10-20 20:00", "8");
		writeWarning("Deixa de tomar merdas crl", "2019-10-01 08:00", "2019-10-20 20:00", "8");

		writeContacto("Maria", "111222333");
	}

	public static void writeFiles() throws IOException {
		File f = new File("warning.txt"); // ficheiro dos warnings
		if (!f.exists()) {
			f.createNewFile();
		}

		File f2 = new File("contactos.txt"); // ficheiro dos contactos
		if (!f2.exists()) {
			f2.createNewFile();
		}

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
		out.write(mensagem + " de " + dataInicio + " até " + dataFim + " de " + periodicidade + " em " + periodicidade
				+ " horas conhecimento de " + "FALTA CONTACTO");
		System.out.println(Messages.SUCESSO_AVISO_ADD);
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
		if (!fAux.exists()) {
			fAux.createNewFile();
		}

		FileWriter aux = new FileWriter(fAux, true);

		BufferedReader in = new BufferedReader(new FileReader(f));
		String linha;
		StringBuilder sb = new StringBuilder();
		while ((linha = in.readLine()) != null) {
			if (!linha.contains(mensagem)) {
				sb.append(linha);
				aux.write(sb.toString());
				sb = new StringBuilder();
			} else {
				aux.write(sb.toString());
				sb = new StringBuilder();
			}
			aux.write("\n");
		}

		System.out.println(Messages.SUCESSO_AVISO_DEL);
		in.close();
		aux.close();
		Files.delete(f.toPath());
		fAux.renameTo(new File("warning.txt"));
	}

	public static void writeContacto(String nome, String contacto) throws IOException {
		File listaContactos = new File("contactos.txt");
		if (!listaContactos.exists()) {
			listaContactos.createNewFile();
		}

		FileWriter fw = new FileWriter(listaContactos, true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write(nome + ": " + contacto);
		System.out.println(Messages.SUCESSO_CONTACTO);
		out.newLine();
		out.close();
		fw.close();
	}

}