package aspectJClasses;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.bezirk.middleware.messages.Event;

import contactos.AdicionarContactoEvento;
import contactos.ModificarContactoEvento;
import inatividade.BeginningInativityEvento;
import inatividade.Inatividade;
import inatividade.InatividadeEvento;
import sensors.ActividadeEvento;
import sensors.BotaoEvento;
import utilidades.Utilidades;
import warnings.AdicionarWarningEvento;
import warnings.DeleteWarningEvento;
import warnings.Warning;
import warnings.WarningEvento;

public aspect TriggerEvent {

	pointcut despoletaEvento(Event e): execution(* *.receiveEvent(..)) && args(e,*);

	after(Event e) : despoletaEvento(e){

		if (e instanceof BotaoEvento) {
			HashMap<String, String> contacts;
			try {
				contacts = Utilidades.populateContacts();
				for (String s : contacts.keySet()) {
					Utilidades.sendToContact(s, contacts.get(s));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (e instanceof AdicionarWarningEvento) {
			AdicionarWarningEvento awe = (AdicionarWarningEvento) e;
			String mensagem, dataInicio, dataFim, periodicidade;
			mensagem = awe.getMensagem();
			dataInicio = awe.getDataInicio();
			dataFim = awe.getDataFim();
			periodicidade = awe.getPeriodicidade();
			try {
				Utilidades.writeWarning(mensagem, dataInicio, dataFim, periodicidade);
				Warning warning = new Warning(mensagem + " de " + dataInicio + " ate " + dataFim + " de "
						+ periodicidade + " em " + periodicidade + " milissegundos ");
				warning.sendWarningEvent();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e instanceof DeleteWarningEvento) {
			System.out.println("trigger deleteWarningEvento");
			DeleteWarningEvento dwe = (DeleteWarningEvento) e;
			String mensagem, dataInicio, dataFim, periodicidade;
			mensagem = dwe.getMensagem();
			dataInicio = dwe.getDataInicio();
			dataFim = dwe.getDataFim();
			periodicidade = dwe.getPeriodicidade();
			try {
				Utilidades.deleteWarning(mensagem, dataInicio, dataFim, periodicidade);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (e instanceof WarningEvento) {
			ArrayList<Warning> ArrayWarning;
			try {
				ArrayWarning = Utilidades.populateWarnings();
				for (int i = 0; i < ArrayWarning.size(); i++) {
					ArrayWarning.get(i).setTimerWarning();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

		if (e instanceof ActividadeEvento) {
			ActividadeEvento ae = (ActividadeEvento) e;
			String divisao = ae.getDivisao();
			Date hour = ae.getClock();
			SimpleDateFormat dtf = new SimpleDateFormat("HH:mm");

			HashMap<String, String> actividades;
			try {
				actividades = Utilidades.populateActivity();
				for (String s : actividades.keySet()) {
					if (s.toUpperCase().equals(divisao.toUpperCase())
							&& Utilidades.CheckActivityPeriod(dtf.format(hour), actividades.get(s))) {
						Utilidades.printActivityDetected(divisao, dtf.format(hour));
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (e instanceof AdicionarContactoEvento) {
			AdicionarContactoEvento ace = (AdicionarContactoEvento) e;
			String nome = ace.getNomeContacto();
			String numero = ace.getNumContacto();
			try {
				Utilidades.writeContacts(nome, numero);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e instanceof ModificarContactoEvento) {
			ModificarContactoEvento mce = (ModificarContactoEvento) e;
			String nome = mce.getNomeContacto();
			String numero = mce.getNumContacto();
			try {
				Utilidades.updateContact(nome, numero);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (e instanceof InatividadeEvento) {
			InatividadeEvento ie = (InatividadeEvento) e;
			String duracao = ie.getDuracao();
			String periodo = ie.getPeriodo();
			try {
				Utilidades.writePadraoInatividade(duracao, periodo);
				Inatividade i = new Inatividade(duracao, periodo);
				i.sendBeginingInactivity();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

		if (e instanceof BeginningInativityEvento) {
			try {
				ArrayList<Inatividade> inactivities = Utilidades.populateInactivities();
				for (Inatividade i : inactivities) {
					i.setTimerInactivity();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
}
