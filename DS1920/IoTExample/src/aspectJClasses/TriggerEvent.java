package aspectJClasses;

import java.io.IOException;
import java.util.HashMap;

import com.bezirk.middleware.messages.Event;

import botao.Button;
import i18n.I18N;
import i18n.Messages;
import utilidades.Utilidades;

public aspect TriggerEvent {
	
	
	pointcut despoletaEvento(Event e): call(* *.receiveEvent(*,*)) && args(e);
	
	 before (Event e) throws IOException: despoletaEvento(e){
		
		 
		if(e instanceof Button) {
			HashMap <String,String>  contacts = Utilidades.populateContacts();
			
			for(String s :contacts.keySet()) {
				System.out.println(I18N.getString(Messages.BUTTON_CONTACTS)+" "+s+contacts.get(s));
			}
			
			
			
		}
		
	}
	

	
	
	
	
	
}
