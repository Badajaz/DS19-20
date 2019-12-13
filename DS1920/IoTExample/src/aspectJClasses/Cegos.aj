package aspectJClasses;
import ui.UI;
import ui.Voz;
import utilidades.Utilidades;
import warnings.Warning;

public aspect Cegos {

	pointcut speakUI(String text) : call(* *.println(*)) && args(text) && within(UI);
	
	after(String text) : speakUI(text){
		Voz voz = new Voz();
		voz.speak(text);
	}
	
	pointcut speakWarning(String text) : call(* *.println(*)) && args(text) && within(Warning);
	
	after(String text) : speakWarning(text){
		Voz voz = new Voz();
		voz.speak(text);
	}
	
	
	pointcut speakContact(String text) : call(* *.println(*)) && args(text) && within(Utilidades); ;
	
	after(String text) : speakContact(text){
		Voz voz = new Voz();
		voz.speak(text);
	}
	
}
