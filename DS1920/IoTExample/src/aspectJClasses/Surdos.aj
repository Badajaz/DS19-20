package aspectJClasses;

import sensors.SensorBotao;
import sensors.SensoresLuz;

public aspect Surdos {

	pointcut emiteLuz() : call(* *.println(*)) && within(SensorBotao);

	after(): emiteLuz(){
			SensoresLuz luz = new SensoresLuz();
			luz.sendLightEvent();
	}
}