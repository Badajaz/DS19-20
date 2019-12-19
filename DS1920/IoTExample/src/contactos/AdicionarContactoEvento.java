package contactos;

import com.bezirk.middleware.messages.Event;

public class AdicionarContactoEvento extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String nomeContacto;
	String numContacto;
	
	public AdicionarContactoEvento(String nome, String num) {
		this.nomeContacto = nome;
		this.numContacto = num;
	}
	
	public String getNomeContacto() {
		return nomeContacto;
	}
	
	public String getNumContacto() {
		return numContacto;
	}
}
