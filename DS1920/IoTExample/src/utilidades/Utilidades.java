package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Utilidades {

	private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

	// private static Pattern HOUR_PATTERN =
	// Pattern.compile("(?:[01]\\\\d|2[0123]):(?:[012345]\\\\d)");

	public static void main(String[] args) throws IOException, ParseException {

		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		System.out.println(validateDeadline("09-12-2019 15:38"));

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

		FileWriter fw = new FileWriter("warning.txt", true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write(mensagem + " de " + dataInicio + " ate " + dataFim + " de " + periodicidade + " em " + periodicidade
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
	
	public static void showWarning() {
		
	}

	/**
	 * @param date
	 * @return retorna se o formato da data é v
	 */
	public static boolean validateDate(String date) {

		String[] dateAndHours = date.split(" ");
		return DATE_PATTERN.matcher(dateAndHours[0]).matches() && checkHour(dateAndHours[1]);
	}

	/**
	 * @param hour - hora presente na data
	 * @return verifica se a hora é válida
	 */
	public static boolean checkHour(String hour) {
		System.out.println("check hour: " + hour);
		String[] splitHour = hour.split(":");
		int hora = Integer.parseInt(splitHour[0]);
		int minuto = Integer.parseInt(splitHour[1]);
		return hora >= 0 && hora <= 23 && minuto >= 0 && minuto <= 59;

	}

	/**
	 * @param periodicity - duracao do periodo
	 * @return retorna se o a periodicidade eh valida
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
	 * @param number - numero de contacto
	 * @return retorna se o numero eh valido
	 */
	public static boolean validateNumberContact(String number) {
		System.out.println();
		return number.length() == 9 && validatePeriodicity(number);
	}

	/**
	 * Regista uma inatividade.
	 * 
	 * @param duracao    duracao da inatividade
	 * @param horaInicio hora de inicio da inatividade
	 * @param horaFim    hora de fim da inatividade
	 * @throws IOException
	 */
	public static void writePadraoInatividade(String duracao, String horaInicio, String horaFim) throws IOException {
		File f = new File("inatividades.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		FileWriter fw = new FileWriter(f, true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write("Inatividade durante " + duracao + " no periodo " + "[" + horaInicio + "," + horaFim + "]");
		System.out.println("Padrao de inatividade inserido com sucesso!");
		out.newLine();
		out.close();
		fw.close();

	}

	/**
	 * Regista um padrao de atividade.
	 * 
	 * @param divisao    divisao onde ocorreu a atividade
	 * @param horaInicio hora de inicio
	 * @param horaFim    hora de fim
	 * @throws IOException
	 */
	public static void writePadraoAtividade(String divisao, String horaInicio, String horaFim) throws IOException {
		File f = new File("atividades.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		FileWriter fw = new FileWriter(f, true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write("Detecao de atividade na " + divisao + " no periodo " + "[" + horaInicio + "," + horaFim + "]");
		System.out.println("Padrao de atividade inserido com sucesso!");
		out.newLine();
		out.close();
		fw.close();

	}

	/**
	 * Regista os contactos
	 * 
	 * @param nome   nome do contacto
	 * @param numero numero do contacto
	 * @throws IOException
	 */
	public static void writeContacts(String nome, String numero) throws IOException {
		File f = new File("contacts.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		FileWriter fw = new FileWriter(f, true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write(nome + ":" + numero);
		System.out.println("Contacto inserido com sucesso!");
		out.newLine();
		out.close();
		fw.close();
	}

	public static boolean checkTimeLine(String begin, String end) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date beginDate = sdf.parse(begin);

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date endDate = sdf2.parse(end);

		return beginDate.before(endDate);

	}

	/**
	 * @return popula contactos num hashmap
	 * @throws IOException
	 */
	public static HashMap<String, String> populateContacts() throws IOException {

		HashMap<String, String> contacts = new HashMap<>();

		File file = new File("contacts.txt");
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;

			while ((st = br.readLine()) != null) {
				String[] splitContact = st.split(":");
				contacts.put(splitContact[0], splitContact[1]);
			}
			br.close();
		}

		return contacts;
	}
	
	
	public static boolean isDate(String date) {
		return DATE_PATTERN.matcher(date).matches();
	
	}
	
	
	//Não está correcta esta verificação refazer TODO
	public static boolean validateDeadline(String other) {
		
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		System.out.println(fmt.format(date));
		return fmt.format(date).equals(other);
	}
	

}