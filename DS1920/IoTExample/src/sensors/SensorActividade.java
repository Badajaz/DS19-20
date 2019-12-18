package sensors;

import java.util.Date;
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
		System.out.println(I18N.getString(Messages.DIVISAO_ATIVIDADE));
		String divisao = sc.nextLine();

		SensorActividade sensor = new SensorActividade();
		Date now = new Date();
		sensor.sendActivityEvent(divisao, now);
		sc.close();

	}

	public void sendActivityEvent(String divisao, Date now) {
		ActividadeEvento ae = new ActividadeEvento(divisao, now);
		b.sendEvent(ae);
		System.out.println(I18N.getString(Messages.ACTIVIDADE_EVENT));
	}

}
