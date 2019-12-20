package warnings;

import com.bezirk.middleware.messages.Event;

public class DeleteWarningEvento extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mensagem, dataInicio, dataFim, periodicidade;

	public DeleteWarningEvento(String mensagem, String dataInicio, String dataFim, String periodicidade) {
		this.mensagem = mensagem;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.periodicidade = periodicidade;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}

}
