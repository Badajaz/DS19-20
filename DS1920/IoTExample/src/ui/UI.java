package ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import aspectJClasses.HandleEvent;
import i18n.I18N;
import i18n.Messages;
import sensors.ActividadeEvento;
import sensors.BotaoEvento;
import sensors.LuzEvento;
import utilidades.Utilidades;
import warnings.Warning;
import warnings.WarningEvento;

public class UI extends Thread {

	private Bezirk bezirk;
	private static final String str = "UI";

	public UI() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk(str);

		List<Class<? extends Event>> subs = new ArrayList<>();
		subs.add(BotaoEvento.class);
		subs.add(LuzEvento.class);
		subs.add(WarningEvento.class);
		subs.add(ActividadeEvento.class);
		Class<? extends Event>[] array = toArray(subs);

		final EventSet subscribedEvents = new EventSet(array);
		HandleEvent handle = new HandleEvent();
		subscribedEvents.setEventReceiver(handle);
		bezirk.subscribe(subscribedEvents);
	}

	private Class<? extends Event>[] toArray(List<Class<? extends Event>> subs) {
		Class<? extends Event>[] result = new Class[subs.size()];
		int i = 0;
		for (Class<? extends Event> c : subs) {
			result[i] = c;
			i++;
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		UI ui = new UI();

		ui.start();
	}

	@Override
	public void run() {

		Scanner sc = new Scanner(System.in);
		String input = "";

		while (!input.equals("q")) {

			System.out.println(I18N.getString(Messages.WELCOME));
			System.out.println(I18N.getString(Messages.OPT1));
			System.out.println(I18N.getString(Messages.OPT2));
			System.out.println(I18N.getString(Messages.OPT3));
			System.out.println(I18N.getString(Messages.OPT4));
			System.out.println(I18N.getString(Messages.OPT5));
			System.out.println(I18N.getString(Messages.OPT6));
			System.out.println(I18N.getString(Messages.OPT7));
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
						System.out.println(I18N.getString(Messages.DATA_INICIO_SUPERIOR));
						System.out.println(I18N.getString(Messages.DATA_INICIO_AVISO));
						dataInicio = sc1.nextLine();
						System.out.println(I18N.getString(Messages.DATA_FIM_AVISO));
						dataFim = sc1.nextLine();
					}
				} catch (ParseException e) {
					e.printStackTrace();
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
					System.out.println(I18N.getString(Messages.WARNING_INSERT_SUCESSO));
					Warning warning = new Warning(mensagem + " de " + dataInicio + " ate " + dataFim + " de "
							+ periodicidade + " em " + periodicidade + " milissegundos ");
					warning.sendWarningEvent();
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
					System.out.println(I18N.getString(Messages.WARNING_DELETE_SUCESSO));
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
					System.out.println(I18N.getString(Messages.CONTACTO_INSERT_SUCESSO));
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
					System.out.println(I18N.getString(Messages.PADRAO_INATIVIDADE_INSERT_SUCESSO));
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
						System.out.println(I18N.getString(Messages.PADRAO_ATIVIDADE_INSERT_SUCESSO));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				sc5.close();

			} else if (Integer.parseInt(input) == 6) { // Simular evento de inatividade
				Scanner sc6 = new Scanner(System.in);
				sc6.close();

			} else if (Integer.parseInt(input) == 7) { // Modificar contacto
				Scanner sc7 = new Scanner(System.in);
				String nomeContacto, novoContacto;

				System.out.println(I18N.getString(Messages.CONTACT_MODIFY_NAME));
				nomeContacto = sc7.nextLine();
				System.out.println(I18N.getString(Messages.CONTACT_MODIFY_NUMBER));
				novoContacto = sc7.nextLine();

				while (!Utilidades.validateNumberContact(novoContacto)) {
					System.out.println(I18N.getString(Messages.CONTACT_MODIFY_NUMBER));
					novoContacto = sc7.nextLine();
				}

				try {
					Utilidades.updateContact(nomeContacto, novoContacto);
					System.out.println(I18N.getString(Messages.CONTACTO_ALTERED_SUCESSO));
				} catch (IOException e) {
					e.printStackTrace();
				}
				sc7.close();
			}
		}
		sc.close();
	}
}
