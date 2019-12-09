package core;

import java.util.Timer;
import java.util.TimerTask;

import i18n.I18N;
import static i18n.Messages.DEVICE_RUNNING;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

public class AirQualitySensorZirk{
    private Bezirk bezirk;

    public AirQualitySensorZirk() {
        BezirkMiddleware.initialize();
        bezirk = BezirkMiddleware.registerZirk("Air Quality Sensor Zirk");
        System.err.println("Got Bezirk instance");
    }

    public void sendAirQualityUpdate() {
    	//produces some  values since this is a mock
        final double humidity = 0.8;
        final int dustLevel = 30;
        final int pollenLevel = 1000;
        final AirQualityUpdateEvent airQualityUpdateEvent = new AirQualityUpdateEvent(humidity, dustLevel, pollenLevel);

        //sends the event
        bezirk.sendEvent(airQualityUpdateEvent);
        System.err.println("Published air quality update: " + airQualityUpdateEvent.toString());
    }

    public void sendPeriodiclyAirQualityUpdate(){
    	//publish messages periodically; also a mock but more sophisticated
        new Timer().scheduleAtFixedRate(new TimerTask() {
            private int pollenLevel = 400;
            private double humidity = 1.0;
            private int dustLevel = 17;
 
            @Override
            public void run() {
            	pollenLevel += 10;
            	humidity = humidity > 0.4 ? humidity-0.1 : humidity;
            	dustLevel++;
                AirQualityUpdateEvent airQualityUpdateEvent = 
                		new AirQualityUpdateEvent(humidity,dustLevel,pollenLevel); 
                bezirk.sendEvent(airQualityUpdateEvent);
            }
        }, 1000, 1000);
    }
    
    public static void main(String args[]) throws InterruptedException {
        AirQualitySensorZirk airQualitySensorZirk = new AirQualitySensorZirk();
        System.err.println("This product has an Air Quality Sensor");
        
        System.err.println(I18N.getString(DEVICE_RUNNING, "Air Quality Sensor"));
        airQualitySensorZirk.sendAirQualityUpdate();
        airQualitySensorZirk.sendPeriodiclyAirQualityUpdate();
     }
}