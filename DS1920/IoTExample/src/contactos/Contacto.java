package contactos;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;

public class Contacto {
	/**
	 * 
	 * Criado automaticamente
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bezirk b;
	private String nome;
	private String contacto;

	public Contacto(String nome, String contacto) {
		BezirkMiddleware.initialize();
		b = BezirkMiddleware.registerZirk("contacto");
		this.nome = nome;
		this.contacto = contacto;
	}

	public void sendAddContactEvent() {
		AdicionarContactoEvento add = new AdicionarContactoEvento(nome, contacto);
		b.sendEvent(add);
		System.out.println(I18N.getString(Messages.ADD_CONTACTO_EVENT));
	}
	
	public void updateContactEvent() {
		ModificarContactoEvento modify = new ModificarContactoEvento(nome, contacto);
		b.sendEvent(modify);
		System.out.println(I18N.getString(Messages.MODIFY_CONTACTO_EVENT));
	}
}
