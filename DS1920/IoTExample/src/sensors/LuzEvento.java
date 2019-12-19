package sensors;

import com.bezirk.middleware.messages.Event;

public class LuzEvento extends Event {

	private String evento;

	public LuzEvento(String evento) {
		this.evento = evento;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
