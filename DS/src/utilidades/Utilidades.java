package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class Utilidades {

	private static Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

	// private static Pattern HOUR_PATTERN =
	// Pattern.compile("(?:[01]\\\\d|2[0123]):(?:[012345]\\\\d)");

	public static void main(String[] args) throws IOException {
		// writeFiles();
		// writeWarning("Tomar antibi�tico", "2019-10-01 08:00", "2019-10-20 20:00",
		// "8");
		// writeWarning("Deixa de tomar merdas crl", "2019-10-01 08:00", "2019-10-20
		// 20:00", "8");
		// deleteWarning("Deixa de tomar merdas crl");
		// System.out.println(validateDate("2019-10-20 20:00"));;
		// System.out.println(validateNumberContact("913885916"));
		// System.out.println(validatePeriodicity("1C"));

		System.out.println(checkHour("233:59"));
		
		writePadraoInatividade("45", "20:20", "21:05");

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

	/**
	 * permite escrever o warning num formato para o ficheiro
	 * 
	 * @param mensagem
	 * @param dataInicio
	 * @param dataFim
	 * @param periodicidade
	 * @throws IOException
	 */
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
				+ " horas ");
		out.newLine();
		out.close();
		fw.close();
	}

	/**
	 * 
	 * permite dar delete a um warning
	 * 
	 * @param mensagem - warning
	 * @throws IOException
	 */
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

		in.close();
		aux.close();
		Files.delete(f.toPath());
		fAux.renameTo(new File("warning.txt"));
	}

	/**
	 * @param date
	 * @return retorna se o formato da data é v
	 */
	public static boolean validateDate(String date) {

		// TODO verificação das horas.
		String[] dateAndHours = date.split(" ");
		return DATE_PATTERN.matcher(dateAndHours[0]).matches() && checkHour(dateAndHours[1]);
	}

	/**
	 * @param hour - hora presente na data
	 * @return verifica se a hora é válida
	 */
	public static boolean checkHour(String hour) {

		String[] splitHour = hour.split(":");
		int hora = Integer.parseInt(splitHour[0]);
		int minuto = Integer.parseInt(splitHour[1]);
		return hora >= 0 && hora <= 23 && minuto >= 0 && minuto <= 59;

	}

	/**
	 * @param periodicity - duração do período
	 * @return retorna se o a periodicidade é válida
	 */
	public static boolean validatePeriodicity(String periodicity) {

		for (int i = 0; i < periodicity.length(); i++) {
			if (periodicity.charAt(i) < '0' || periodicity.charAt(i) > '9') {
				return false;
			}
		}

		return true;

	}

	/**
	 * @param number - número de contacto
	 * @return retorna se o número é válido
	 */
	public static boolean validateNumberContact(String number) {

		return number.length() == 9 && validatePeriodicity(number);
	}

	public static void writePadraoInatividade(String duracao, String horaInicio, String horaFim) throws IOException {
		File f = new File("padraoInatividade.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		FileWriter fw = new FileWriter(f, true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write("Inatividade durante " + duracao + " no periodo " + "[" + horaInicio + "," + horaFim + "]");
		out.newLine();
		out.close();
		fw.close();

	}

}