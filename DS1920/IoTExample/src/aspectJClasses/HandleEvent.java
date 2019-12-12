package aspectJClasses;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet.EventReceiver;

import i18n.I18N;
import i18n.Messages;
import sensors.SensorBotao;

public class HandleEvent implements EventReceiver {

	@Override
	public void receiveEvent(Event arg0, ZirkEndPoint arg1) {
		System.out.println("entrei receive event");
		if(arg0 instanceof SensorBotao) {
			System.out.println(I18N.getString(Messages.BUTTON_RECEIVED_EVENT));
		}
		
	}

}
