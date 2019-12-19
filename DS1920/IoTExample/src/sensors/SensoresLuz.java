package sensors;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

public class SensoresLuz {
	/**
	 * 
	 * Criado automaticamente
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
