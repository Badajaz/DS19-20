package inatividade;

import com.bezirk.middleware.messages.Event;

public class InatividadeEvento extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String duracao, periodo;

	public InatividadeEvento(String duracao, String periodo) {
		this.duracao = duracao;
		this.periodo = periodo;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
}
