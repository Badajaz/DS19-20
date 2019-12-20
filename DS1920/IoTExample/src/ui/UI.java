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
import contactos.AdicionarContactoEvento;
import contactos.Contacto;
import contactos.ModificarContactoEvento;
import i18n.I18N;
import i18n.Messages;
import inatividade.ActivatedInativityEvento;
import inatividade.BeginningInativityEvento;
import inatividade.Inatividade;
import inatividade.InatividadeEvento;
import sensors.ActividadeEvento;
import sensors.BotaoEvento;
import sensors.LuzEvento;
import utilidades.Utilidades;
import warnings.AdicionarWarningEvento;
import warnings.DeleteWarningEvento;
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
		subs.add(AdicionarContactoEvento.class);
		subs.add(ModificarContactoEvento.class);
		subs.add(AdicionarWarningEvento.class);
		subs.add(DeleteWarningEvento.class);
		subs.add(InatividadeEvento.class);
		subs.add(BeginningInativityEvento.class);
		subs.add(ActivatedInativityEvento.class);
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
		boolean acabou = false;
		int input = 0;

		while (!acabou) {

			System.out.println(I18N.getString(Messages.WELCOME));
			System.out.println(I18N.getString(Messages.OPT1));
			System.out.println(I18N.getString(Messages.OPT2));
			System.out.println(I18N.getString(Messages.OPT3));
			System.out.println(I18N.getString(Messages.OPT4));
			System.out.println(I18N.getString(Messages.OPT5));
			System.out.println(I18N.getString(Messages.OPT6));
			System.out.println(I18N.getString(Messages.QUIT));
			input = sc.nextInt();

			if (input == 1) { // Criar aviso
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

				Warning warning = new Warning(mensagem + " de " + dataInicio + " ate " + dataFim + " de "
						+ periodicidade + " em " + periodicidade + " milissegundos ");
				warning.addWarningEvent(mensagem, dataInicio, dataFim, periodicidade);

			} else if (input == 2) { // Apagar aviso
				Scanner sc2 = new Scanner(System.in);
				String mensagem, dataInicio, dataFim, periodicidade;
				System.out.println(I18N.getString(Messages.MENSAGEM_AVISO));
				mensagem = sc2.nextLine();
				System.out.println(I18N.getString(Messages.DATA_INICIO_AVISO));
				dataInicio = sc2.nextLine();

				while (!Utilidades.validateDate(dataInicio)) {
					System.out.println(I18N.getString(Messages.DATA_VALIDA));
					dataInicio = sc2.nextLine();
				}

				System.out.println(I18N.getString(Messages.DATA_FIM_AVISO));
				dataFim = sc2.nextLine();

				while (!Utilidades.validateDate(dataFim)) {
					System.out.println(I18N.getString(Messages.DATA_FIM_AVISO));
					dataFim = sc2.nextLine();
				}

				try {
					while (!Utilidades.checkTimeLine(dataInicio, dataFim)) {
						System.out.println(I18N.getString(Messages.DATA_INICIO_SUPERIOR));
						System.out.println(I18N.getString(Messages.DATA_INICIO_AVISO));
						dataInicio = sc2.nextLine();
						System.out.println(I18N.getString(Messages.DATA_FIM_AVISO));
						dataFim = sc2.nextLine();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				System.out.println(I18N.getString(Messages.PERIODICIDADE_AVISO));
				periodicidade = sc2.nextLine();

				while (!Utilidades.validatePeriodicity(periodicidade)) {
					System.out.println(I18N.getString(Messages.PERIODICIDADE_AVISO));
					periodicidade = sc2.nextLine();
				}

				Warning warning = new Warning(mensagem + " de " + dataInicio + " ate " + dataFim + " de "
						+ periodicidade + " em " + periodicidade + " milissegundos ");
				warning.deleteWarningEvent(mensagem, dataInicio, dataFim, periodicidade);

			} else if (input == 3) { // Criar novo contacto
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

				Contacto c = new Contacto(nomeContacto, numContacto);
				c.sendAddContactEvent();

			} else if (input == 4) { // Criar padrao de atividade
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

			} else if (input == 5) { // Criar padrao de inatividade
				Scanner sc6 = new Scanner(System.in);

				String duracao, horaInicial, horaFinal;
				System.out.println(I18N.getString(Messages.DURACAO_INATIVIDADE));
				duracao = sc6.nextLine();
				System.out.println(I18N.getString(Messages.HORA_INICIAL));
				horaInicial = sc6.nextLine();
				System.out.println(I18N.getString(Messages.HORA_FINAL));
				horaFinal = sc6.nextLine();
				String periodo = "[" + horaInicial + "," + horaFinal + "]";
				System.out.println("periodo ui: " + periodo);

				// Criar um timer ate a duracao da inatividade. Quando for igual e dentro da
				// hora, entao faz coisas.
				Inatividade i = new Inatividade(duracao, periodo);
				i.sendInactivityEvent();

			} else if (input == 6) { // Modificar contacto
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

				Contacto c = new Contacto(nomeContacto, novoContacto);
				c.updateContactEvent();

			} else if (input == 7) {
				System.out.println(I18N.getString(Messages.GOODBYE));
				acabou = true;
				
				// acabar
				System.exit(0);
			}
		}
		sc.close();
	}
}
