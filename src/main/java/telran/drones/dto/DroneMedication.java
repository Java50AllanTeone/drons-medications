package telran.drones.dto;


import static telran.drones.api.ValidationConstants.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//TODO add validation constraints
public record DroneMedication(
		@NotEmpty(message = MISSING_DRONE_NUMBER_MESSAGE) 
		@Size(max = 100, message=WRONG_DRONE_NUMBER_MESSAGE)
		String droneNumber, 
		@NotEmpty(message = MISSING_MEDICATION_CODE_MESSAGE) 
		@Pattern(regexp = MEDICATION_REGEXP, message = WRONG_MEDICATION_CODE_MESSAGE)
		String medicationCode) {

}
