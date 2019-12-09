package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import i18n.I18N;
import i18n.Messages;
import utilidades.Utilidades;
import warnings.Warning;

public class UI extends Thread {

	private Timer t;
	private List<Warning> ArrayWarning;

	public static void main(String[] args) throws IOException {
		UI ui = new UI();
		ui.start();
	}

	@Override
	public void run() {

		Scanner sc = new Scanner(System.in);
		String input = "";
		ArrayWarning = new ArrayList<Warning>();
		

		while (!input.equals("q")) {
			BufferedReader br;
			File r = new File("warning.txt");
			if (r.exists()) {

				try {
					br = new BufferedReader(new FileReader("warning.txt"));
					String st;

					while ((st = br.readLine()) != null) {
						ArrayWarning.add(new Warning(st));
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				for (int i = 0; i < ArrayWarning.size(); i++) {

					ArrayWarning.get(i).setTimerWarning();

				}

			}

			System.out.println(I18N.getString(Messages.WELCOME));
			System.out.println(I18N.getString(Messages.OPT1));
			System.out.println(I18N.getString(Messages.OPT2));
			System.out.println(I18N.getString(Messages.OPT3));
			System.out.println(I18N.getString(Messages.OPT4));
			System.out.println(I18N.getString(Messages.OPT5));
			System.out.println(I18N.getString(Messages.OPT6));
			input = sc.nextLine();

			if (Integer.parseInt(input) == 1) { // Criar aviso
				Scanner sc1 = new Scanner(System.in);
				String mensagem, dataInicio, dataFim, periodicidade;

				System.out.println(I18N.getString(Messages.MENSAGEM_AVISO));
				mensagem = sc1.nextLine();
				System.out.println(I18N.getString(Messages.DATA_INICIO_AVISO));
				dataInicio = sc1.nextLine();

				while (!Utilidades.validateDate(dataInicio)) {
					System.out.println(I18N.getString(Messages.DATA_VALIDA));
					dataInicio = sc1.nextLine();
				}

				System.out.println(I18N.getString(Messages.DATA_FIM_AVISO));
				dataFim = sc1.nextLine();

				while (!Utilidades.validateDate(dataFim)) {
					System.out.println(I18N.getString(Messages.DATA_FIM_AVISO));
					dataFim = sc1.nextLine();
				}

				try {
					while (!Utilidades.checkTimeLine(dataInicio, dataFim)) {
						System.out.println(I18N.getString(Messages.DATA_INICIO_SUPERIOR_FIM));
						System.out.println(I18N.getString(Messages.DATA_INICIO_AVISO));
						dataInicio = sc1.nextLine();
						System.out.println(I18N.getString(Messages.DATA_FIM_AVISO));
						dataFim = sc1.nextLine();
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				System.out.println(I18N.getString(Messages.PERIODICIDADE_AVISO));
				periodicidade = sc1.nextLine();

				while (!Utilidades.validatePeriodicity(periodicidade)) {
					System.out.println(I18N.getString(Messages.PERIODICIDADE_AVISO));
					periodicidade = sc1.nextLine();
				}

				String horaInicio = dataInicio.substring(11); // inicio da hora na string dataInicio
				String horaFim = dataFim.substring(11); // inicio da hora na string dataInicio

				try {
					Utilidades.writeWarning(mensagem, dataInicio, dataFim, periodicidade);
				} catch (IOException e) {
					e.printStackTrace();
				}

				sc1.close();
			} else if (Integer.parseInt(input) == 2) { // Apagar aviso
				Scanner sc2 = new Scanner(System.in);
				String mensagem;
				System.out.println(I18N.getString(Messages.MENSAGEM_AVISO));
				mensagem = sc2.nextLine();

				try {
					Utilidades.deleteWarning(mensagem);
				} catch (IOException e) {
					e.printStackTrace();
				}

				sc2.close();
			} else if (Integer.parseInt(input) == 3) { // Criar novo contacto
				Scanner sc3 = new Scanner(System.in);
				String nomeContacto, numContacto;

				System.out.println(I18N.getString(Messages.NOME_CONTACTO));
				nomeContacto = sc3.nextLine();
				System.out.println(I18N.getString(Messages.TEL_CONTACTO));
				numContacto = sc3.nextLine();

				while (!Utilidades.validateNumberContact(numContacto)) {
					System.out.println(I18N.getString(Messages.TEL_CONTACTO));
					numContacto = sc3.nextLine();
				}

				try {
					Utilidades.writeContacts(nomeContacto, numContacto);
				} catch (IOException e) {
					e.printStackTrace();
				}

				sc3.close();
			} else if (Integer.parseInt(input) == 4) { // Criar padrao de inatividade
				Scanner sc4 = new Scanner(System.in);
				String duracao, horaInicio, horaFim;
				System.out.println(I18N.getString(Messages.DURACAO));
				duracao = sc4.nextLine();
				System.out.println(I18N.getString(Messages.HORA_INICIAL));
				horaInicio = sc4.nextLine();
				System.out.println(I18N.getString(Messages.HORA_FINAL));
				horaFim = sc4.nextLine();

				try {
					Utilidades.writePadraoInatividade(duracao, horaInicio, horaFim);
				} catch (IOException e) {
					e.printStackTrace();
				}

				sc4.close();
			} else if (Integer.parseInt(input) == 5) { // Criar padrao de atividade
				Scanner sc5 = new Scanner(System.in);

				String divisao, horaInicio, horaFim;
				System.out.println(I18N.getString(Messages.DIVISAO_ATIVIDADE));
				divisao = sc5.nextLine();
				System.out.println(I18N.getString(Messages.HORA_INICIAL));
				horaInicio = sc5.nextLine();
				System.out.println(I18N.getString(Messages.HORA_FINAL));
				horaFim = sc5.nextLine();

				try {
					if (Utilidades.checkHour(horaInicio) && Utilidades.checkHour(horaFim)) {
						Utilidades.writePadraoAtividade(divisao, horaInicio, horaFim);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				sc5.close();

			} else if (Integer.parseInt(input) == 6) { // Simular evento de inatividade
				Scanner sc6 = new Scanner(System.in);
				sc6.close();

			}

		}
	}

}
