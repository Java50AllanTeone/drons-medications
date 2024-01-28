package telran.drones.api;

public interface ValidationConstants {
	int MAX_DRONE_NUMBER_LENGTH = 100;
	String MEDICATION_REGEXP = "^[A-Z0-9_]*$";
	String MISSING_DRONE_NUMBER_MESSAGE = "Missing drone number";
	String MISSING_MEDICATION_CODE_MESSAGE = "Missing medication code";
	String MISSING_MODEL_TYPE_MESSAGE = "Missing model type";
	String WRONG_DRONE_NUMBER_MESSAGE = "Drone number must be less or equal " + MAX_DRONE_NUMBER_LENGTH;
	String WRONG_MEDICATION_CODE_MESSAGE = "Medication code can contains upper case letters, underscore, numbers";
	
	

}
