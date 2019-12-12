package sensors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;

public class SensorBotao extends Event {

	/**
	 * 
	 * Criado automaticamente
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bezirk b;

	public SensorBotao() {
		BezirkMiddleware.initialize();
		b = BezirkMiddleware.registerZirk("botao");
	}

	public static void main(String[] args) {

		SensorBotao sensor = new SensorBotao();
		sensor.sendEvent();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(I18N.getString(Messages.BUTTON_EVENT) + " " + dtf.format(now));
	}

	public void sendEvent() {
		SensorBotao button = new SensorBotao();
		b.sendEvent(button);

	}

}
