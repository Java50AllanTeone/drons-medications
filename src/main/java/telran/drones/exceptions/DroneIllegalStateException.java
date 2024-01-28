package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class DroneIllegalStateException extends IllegalStateException {
	
	public DroneIllegalStateException() {
		super(ServiceExceptionMessages.DRONE_ALREADY_EXISTS);
		
	}

}
