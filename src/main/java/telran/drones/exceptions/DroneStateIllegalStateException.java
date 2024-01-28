package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

public class DroneStateIllegalStateException extends IllegalStateException {
	
	public DroneStateIllegalStateException() {
		super(ServiceExceptionMessages.DRONE_IS_NOT_AVAILABLE);
	}

}
