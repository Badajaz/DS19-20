package core;
import com.bezirk.middleware.messages.Event;

public class AirQualityUpdateEvent extends Event {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final double
            humidity /* decimal, e.g., 0.5 */,
            dustLevel /* milligrams per cubic meter. Above 20 is high. */,
            pollenLevel /* grams per cubic meter. Above 500 is high. */;

    public AirQualityUpdateEvent(double humidity, double dustLevel, double pollenLevel) {
        this.humidity = humidity;
        this.dustLevel = dustLevel;
        this.pollenLevel = pollenLevel;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getDustLevel() {
        return dustLevel;
    }

    public double getPollenLevel() {
        return pollenLevel;
    }

    public String toString() {
        return String.format("humidity: %s, dustLevel: %s, pollenLevel: %s", humidity,
                dustLevel, pollenLevel);
    }
}
