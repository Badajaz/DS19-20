package aspectJClasses;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet.EventReceiver;

import contactos.AdicionarContactoEvento;
import contactos.ModificarContactoEvento;
import i18n.I18N;
import i18n.Messages;
import sensors.ActividadeEvento;
import sensors.BotaoEvento;
import sensors.LuzEvento;
import warnings.AdicionarWarningEvento;
import warnings.DeleteWarningEvento;
import warnings.WarningEvento;

public class HandleEvent implements EventReceiver {

	@Override
	public void receiveEvent(Event arg0, ZirkEndPoint arg1) {
		if (arg0 instanceof BotaoEvento) {
			System.err.println(I18N.getString(Messages.BUTTON_RECEIVED_EVENT));
		}
		if (arg0 instanceof LuzEvento) {
			LuzEvento e = (LuzEvento) arg0;
			if (e.getEvento().equals("botao")) {
				System.err.println("RGB(0, 0, 255) --> Botao acionado");
			}
			if (e.getEvento().equals("warning")) {
				System.err.println("RGB(255, 255, 255) --> Warning acionado");
			}
			if (e.getEvento().equals("actividade")) {
				System.err.println("RGB(255, 0, 0) --> Actividade acionado");
			}
			if (e.getEvento().equals("contacto")) {
				System.err.println("RGB(0, 0, 255) --> Contacto acionado");
			}
		}
		if (arg0 instanceof WarningEvento) {
			System.err.println(I18N.getString(Messages.WARNING_RECEIVED_EVENT));
		}
		if (arg0 instanceof ActividadeEvento) {
			System.err.println(I18N.getString(Messages.ACTIVITY_RECEIVED_EVENT));
		}
		if (arg0 instanceof AdicionarContactoEvento) {
			System.err.println(I18N.getString(Messages.ADICIONAR_CONTACTO_EVENT));
		}
		if (arg0 instanceof ModificarContactoEvento) {
			System.err.println(I18N.getString(Messages.MOD_CONTACTO_EVENT));
		}
		if (arg0 instanceof AdicionarWarningEvento) {
			System.err.println(I18N.getString(Messages.ADD_WARNING_EVENT));
		}
		
		if (arg0 instanceof DeleteWarningEvento) {
			System.err.println(I18N.getString(Messages.DEL_WARNING_EVENT));
		}
	}
}
