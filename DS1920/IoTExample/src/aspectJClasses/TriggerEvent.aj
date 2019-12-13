package aspectJClasses;

import java.io.IOException;
import java.util.HashMap;

import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;
import sensors.BotaoEvento;
import utilidades.Utilidades;

public aspect TriggerEvent {

	pointcut despoletaEvento(Event e): execution(* *.receiveEvent(..)) && args(e,*);

	after(Event e) : despoletaEvento(e){

		if (e instanceof BotaoEvento) {
			HashMap<String, String> contacts;
			try {
				contacts = Utilidades.populateContacts();
				for (String s : contacts.keySet()) {
					System.out.println(I18N.getString(Messages.BUTTON_CONTACTS) + " " + s + " " + contacts.get(s));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
}
