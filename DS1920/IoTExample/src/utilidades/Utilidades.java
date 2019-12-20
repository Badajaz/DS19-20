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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import i18n.I18N;
import i18n.Messages;
import inatividade.Inatividade;
import warnings.Warning;

public class Utilidades {

	private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

	public static void main(String[] args) throws IOException, ParseException {
	}

	/**
	 * Escrever o warning num formato para o ficheiro
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

		int minutos = (int) ((Integer.parseInt(periodicidade) / 1000) / 60);
		int horas = (int) ((Integer.parseInt(periodicidade) / 1000) / 60) / 60;
		// 28800000 milissegundos -> 8 horas
		// 60000 milissegundos -> 1 min

		FileWriter fw = new FileWriter("warning.txt", true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write(mensagem + " de " + dataInicio + " ate " + dataFim + " de " + periodicidade + " em " + periodicidade
				+ " milissegundos ");
		System.out.println(I18N.getString(Messages.WARNING_INSERT_SUCESSO));

		out.newLine();
		out.close();
		fw.close();
	}

	/**
	 * Faz delete a um warning
	 * 
	 * @param mensagem      mensagem do warning
	 * @param dataInicio    data inicio do warning
	 * @param dataFim       data fim do warning
	 * @param periodicidade periodicidade do warning
	 * @throws IOException
	 */
	public static void deleteWarning(String mensagem, String dataInicio, String dataFim, String periodicidade)
			throws IOException {
		File f = new File("warning.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		File fAux = new File("temp.txt");
		if (!fAux.exists()) {
			fAux.createNewFile();
		}

		BufferedReader br = new BufferedReader(new FileReader(f));
		FileWriter aux = new FileWriter(fAux, true);

		String linha;
		StringBuilder sb = new StringBuilder();
		while ((linha = br.readLine()) != null) {
			if (!linha.contains(mensagem)) {
				sb.append(linha);
				aux.write(sb.toString());
				sb = new StringBuilder();
				aux.write("\n");
			} else {
				sb = new StringBuilder();
			}
		}
		System.out.println(I18N.getString(Messages.WARNING_DELETE_SUCESSO));

		br.close();
		aux.close();
		Files.delete(f.toPath());
		fAux.renameTo(new File("warning.txt"));
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
		String[] splitHour = hour.split(":");
		int hora = Integer.parseInt(splitHour[0]);
		int minuto = Integer.parseInt(splitHour[1]);
		return hora >= 0 && hora <= 23 && minuto >= 0 && minuto <= 59;
	}

	/**
	 * Valida a periodicidade
	 * 
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
	public static void writePadraoInatividade(String duracao, String periodo) throws IOException {
		File f = new File("inatividades.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		FileWriter fw = new FileWriter(f, true);
		BufferedWriter out = new BufferedWriter(fw);
		out.write("Inatividade durante " + duracao + " no periodo " + periodo);
		System.out.println(I18N.getString(Messages.PADRAO_INATIVIDADE_INSERT_SUCESSO));
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
		out.newLine();
		out.close();
		fw.close();
	}

	/**
	 * Regista um contacto
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
		System.out.println(I18N.getString(Messages.CONTACTO_INSERT_SUCESSO));
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
		BufferedReader br = null;
		File file = new File("contacts.txt");
		if (file.exists()) {
			br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null) {
				String[] splitContact = st.split(":");
				contacts.put(splitContact[0], splitContact[1]);
			}
		}
		br.close();
		return contacts;
	}

	/**
	 * Popula warnings numa arraylist
	 * 
	 * @return arraylist com warnings
	 * @throws IOException
	 */
	public static ArrayList<Warning> populateWarnings() throws IOException {
		ArrayList<Warning> ArrayWarning = new ArrayList<>();
		BufferedReader br = null;
		File r = new File("warning.txt");
		if (r.exists()) {
			try {
				br = new BufferedReader(new FileReader("warning.txt"));
				String st;
				Warning warning = null;
				while ((st = br.readLine()) != null) {
					warning = new Warning(st);
					ArrayWarning.add(warning);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		br.close();
		return ArrayWarning;
	}

	/**
	 * Verifica se a data esta num formato valido
	 * 
	 * @param date data a validar formato
	 * @return true se estiver. False caso contrario
	 */
	public static boolean isDate(String date) {
		return DATE_PATTERN.matcher(date).matches();
	}

	/**
	 * Verifica se a data AGORA eh superior a data do fim do warning. Se for,
	 * cancela o warning.
	 * 
	 * @param other data fim do warning
	 * @return
	 */
	public static boolean validateDeadline(Date other) {
		Date date = new Date(); // data AGORA
		if (date.after(other)) {
			System.out.println(I18N.getString(Messages.CANCEL_WARNING));
		}
		return date.after(other);
	}

	/**
	 * 
	 * @param before
	 * @return
	 */
	public static boolean validateBeginning(Date inicioWarning, Date fimWarning) {
		Date date = new Date(); // data AGORA
		return date.before(fimWarning);
	}

	/**
	 * Envia a mensagem para o contacto
	 * 
	 * @param s  nome do contacto
	 * @param s1 numero do contacto
	 */
	public static void sendToContact(String s, String s1) {
		System.out.println(I18N.getString(Messages.BUTTON_CONTACTS) + " " + s + " " + s1);
	}

	public static void printActivityDetected(String divisao, String hora) {
		System.out.println(I18N.getString(Messages.ACTIVIDADE_EVENT) + " - " + divisao + " - " + hora);
	}

	public static void printInActivityDetected(String divisao, String hora) {
		System.out.println(I18N.getString(Messages.INACTIVIDADE_EVENT_DETECTADA));
	}

	/**
	 * Atualiza o numero do contacto
	 * 
	 * @param nomeContacto nome cujo numero deve ser atualizado
	 * @param novoContacto numero que deve ser atualizado
	 * @throws IOException
	 */
	public static void updateContact(String nomeContacto, String novoContacto) throws IOException {
		File f = new File("contacts.txt");
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
			if (linha.contains(nomeContacto)) {
				sb.append(nomeContacto + ":" + novoContacto);
				aux.write(sb.toString());
				sb = new StringBuilder();
			} else {
				sb.append(linha);
				aux.write(sb.toString());
				sb = new StringBuilder();
			}
			aux.write("\n");
		}

		System.out.println(I18N.getString(Messages.CONTACTO_ALTERED_SUCESSO));
		in.close();
		aux.close();
		Files.delete(f.toPath());
		fAux.renameTo(new File("contacts.txt"));

	}

	/**
	 * Popula activies num hashmap
	 * 
	 * @return hashmap de activies com chave divisao e valor duracao
	 * @throws IOException
	 */
	public static HashMap<String, String> populateActivity() throws IOException {
		HashMap<String, String> activitys = new HashMap<>();

		File file = new File("atividades.txt");
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null) {
				String[] splitActivity = st.split(" ");
				activitys.put(splitActivity[4], splitActivity[splitActivity.length - 1]);
			}
			br.close();
		}

		return activitys;
	}

	/**
	 * Verifica o periodo da actividade.
	 * 
	 * @param hora    hora atual
	 * @param periodo periodo da atividade
	 * @return true se hora estiver dentro do periodo. False caso contrario
	 */
	public static boolean CheckActivityPeriod(String hora, String periodo) {
		String[] periodBounds = periodo.split(",");
		Date periodLowerBound;
		Date periodUpperBound;
		try {
			periodLowerBound = new SimpleDateFormat("HH:mm")
					.parse(periodBounds[0].substring(1, periodBounds[0].length()));
			periodUpperBound = new SimpleDateFormat("HH:mm")
					.parse(periodBounds[1].substring(0, periodBounds[1].length() - 1));
			Date hour = new SimpleDateFormat("HH:mm").parse(hora);
			return hour.after(periodLowerBound) && hour.before(periodUpperBound);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<Inatividade> populateInactivities() throws IOException {
		ArrayList<Inatividade> inactivities = new ArrayList<>();

		File file = new File("inatividades.txt");
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null) {
				String[] splitInactivity = st.split(" ");
				inactivities.add(new Inatividade(splitInactivity[2], splitInactivity[5]));
			}
			br.close();
		}

		return inactivities;

	}
}