package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class DroneBatteryIllegalStateException extends IllegalStateException {
	
	public DroneBatteryIllegalStateException() {
		super(ServiceExceptionMessages.DRONE_BATTERY_IS_LOW);
		
	}

}
