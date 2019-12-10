package botao;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;

public class Button extends Event {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		String argument = "botao";
		BezirkMiddleware.initialize();
		Bezirk b = BezirkMiddleware.registerZirk(argument);
		Button button = new Button();
		b.sendEvent(button);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(I18N.getString(Messages.BUTTON_EVENT)+" "+dtf.format(now));

	}

}
