package sensors;

import java.util.Date;

import com.bezirk.middleware.messages.Event;

public class ActividadeEvento extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divisao;
	private Date clock;
	
	public ActividadeEvento(String divisao, Date clock) {
		this.divisao = divisao;
		this.clock = clock;
	}

	public String getDivisao() {
		return divisao;
	}

	public Date getClock() {
		return clock;
	}
}
