package aspectJClasses;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.bezirk.middleware.messages.Event;

import sensors.ActividadeEvento;
import sensors.BotaoEvento;
import utilidades.Utilidades;
import warnings.Warning;
import warnings.WarningEvento;

public aspect TriggerEvent {

	pointcut despoletaEvento(Event e): execution(* *.receiveEvent(..)) && args(e,*);

	after(Event e) throws IOException : despoletaEvento(e){

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
		if (e instanceof WarningEvento) {
			ArrayList<Warning> ArrayWarning = Utilidades.populateWarnings();
			for (int i = 0; i < ArrayWarning.size(); i++) {
				ArrayWarning.get(i).setTimerWarning();
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
	}
}
