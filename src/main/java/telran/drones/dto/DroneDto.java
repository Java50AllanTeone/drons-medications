package telran.drones.dto;


import static telran.drones.api.ValidationConstants.*;
import jakarta.validation.constraints.*;

public record DroneDto(
		@NotEmpty(message = MISSING_DRONE_NUMBER_MESSAGE) 
		@Size(max = 100, message=WRONG_DRONE_NUMBER_MESSAGE)
		String number, 
		ModelType modelType) {
	

}
