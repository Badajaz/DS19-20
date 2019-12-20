package aspectJClasses;

import contactos.Contacto;
import inatividade.Inatividade;
import sensors.SensorActividade;
import sensors.SensorBotao;
import sensors.SensoresLuz;
import warnings.Warning;

public aspect Deaf {

	pointcut emiteLuzBotao() : call(* *.println(*)) && within(SensorBotao);

	after(): emiteLuzBotao(){
		SensoresLuz luz = new SensoresLuz();
		luz.sendLightEvent("botao");
	}

	pointcut emiteLuzWarning() :  call(* *.println(*)) && within(Warning);

	after(): emiteLuzWarning(){
		SensoresLuz luz = new SensoresLuz();
		luz.sendLightEvent("warning");
	}
	
	pointcut emiteLuzActividade() :  execution(* *.sendActivityEvent(..)) && args(*,*) && within(SensorActividade);

	after(): emiteLuzActividade(){
		SensoresLuz luz = new SensoresLuz();
		luz.sendLightEvent("actividade");
	}
	
	pointcut emiteLuzContactos() :  call(* *.println(*)) && within(Contacto);

	after(): emiteLuzContactos(){
		SensoresLuz luz = new SensoresLuz();
		luz.sendLightEvent("contacto");
	}
	
	pointcut emiteLuzInatividade() :  call(* *.println(*)) && within(Inatividade);

	after(): emiteLuzInatividade(){
		SensoresLuz luz = new SensoresLuz();
		luz.sendLightEvent("inatividade");
	}
	
}