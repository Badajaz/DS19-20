package sensors;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

public class SensoresLuz {
	private Bezirk b;

	public SensoresLuz() {
		BezirkMiddleware.initialize();
		b = BezirkMiddleware.registerZirk("luz");
	}

	public void sendLightEvent(String evento) {
		LuzEvento lights = new LuzEvento(evento);
		b.sendEvent(lights);
	}
}
