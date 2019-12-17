package sensors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;

public class SensorActividade {

	
	private Bezirk b;

	public SensorActividade() {
		BezirkMiddleware.initialize();
		b = BezirkMiddleware.registerZirk("actividade");
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println(I18N.getString(Messages.ASK_DIVISAO));
		String divisao = sc.nextLine();
		
		SensorActividade sensor = newSensorActividade();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		Date now = new Date();
		sensor.sendActivityEvent(divisao, dtf.format(now));
		sc.close();
		
		
	}

	public void sendActivityEvent(String divisao, Date now ) {
		ActividadeEvento ae = new ActividadeEvento(divisao, clock);
		b.sendEvent(ae);
		System.out.println(I18N.getString(Messages.ACTIVIDADE_EVENT));
	}
	
}
