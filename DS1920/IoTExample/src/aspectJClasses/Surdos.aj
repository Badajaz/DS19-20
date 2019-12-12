package aspectJClasses;

import com.bezirk.middleware.messages.Event;

import sensors.SensorBotao;

public aspect Surdos {

	
	pointcut emiteLuz(Event e) :  execution(* *.receiveEvent(*,*)) && args(e);
	
	before(Event e): emiteLuz(e){
		
		if(e instanceof SensorBotao) {
			System.out.println("RGB(0, 0, 255) --> Butao acionado");
		}
		
		
		
	}
	
	
	
}
