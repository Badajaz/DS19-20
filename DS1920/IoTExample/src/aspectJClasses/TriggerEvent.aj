package aspectJClasses;

import java.io.IOException;
import java.util.HashMap;

import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;
import sensors.BotaoEvento;
import sensors.LuzEvento;
import utilidades.Utilidades;

public aspect TriggerEvent {

	pointcut despoletaEvento(Event e): execution(* *.receiveEvent(..)) && args(e,*);

	after(Event e) : despoletaEvento(e){

		if (e instanceof BotaoEvento) {
			HashMap<String, String> contacts;
			try {
				contacts = Utilidades.populateContacts();
				for (String s : contacts.keySet()) {
					Utilidades.sendToContact(s,contacts.get(s));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
}
