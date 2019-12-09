package core;
import i18n.I18N;
import static i18n.Messages.HUMIDITY_MSG;
import static i18n.Messages.POLLEN_MSG;
import static i18n.Messages.DUST_MSG;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

public class AsthmaAssistantZirk{
    public AsthmaAssistantZirk() {
        BezirkMiddleware.initialize();
        final Bezirk bezirk = BezirkMiddleware.registerZirk("Asthma Assistant Zirk");
        System.err.println("Got Bezirk instance");

        final EventSet airQualityEvents = new EventSet(AirQualityUpdateEvent.class);

        airQualityEvents.setEventReceiver(new EventSet.EventReceiver() {
            @Override
            public void receiveEvent(Event event, ZirkEndPoint sender) {
                //Check if this event is of interest
                if (event instanceof AirQualityUpdateEvent) {
                    final AirQualityUpdateEvent aqUpdate = (AirQualityUpdateEvent) event;
                    System.err.println("\nReceived air quality update: " + aqUpdate.toString());
                  
                    //do something in response to this event
                    if (aqUpdate.getHumidity() > 0.7) {
                        System.out.println(I18N.getString(HUMIDITY_MSG));
                    }
                    if (aqUpdate.getDustLevel() > 20) {
                        System.out.println(I18N.getString(DUST_MSG));
                      }
                    if (aqUpdate.getPollenLevel() > 500) {
                        System.out.println(I18N.getString(POLLEN_MSG));
                     }
                }
            }
        });
        bezirk.subscribe(airQualityEvents);
    }

    public static void main(String args[]) {
        new AsthmaAssistantZirk();
        System.err.println("This product has an Asthma Assistant");
    }
}
