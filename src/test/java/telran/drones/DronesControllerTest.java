package telran.drones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static telran.drones.api.ServiceExceptionMessages.*;
import static telran.drones.api.ValidationConstants.*;
import telran.drones.dto.*;
import telran.drones.exceptions.*;
import telran.drones.service.DronesService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

@WebMvcTest
public class DronesControllerTest {
	private static final String DRONE_NUMBER = "111";
	private static final String DRONE_WRONG_NUMBER = "1".repeat(101);
	private static final ModelType DRONE_TYPE = ModelType.Lightweight;
	private static final String MEDICATION_CODE = "123_A";
	private static final String WRONG_MEDICATION_CODE = "123_Aa";
	@MockBean
	DronesService dronesService;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	
	DroneDto droneDto = new DroneDto(DRONE_NUMBER, DRONE_TYPE);
	DroneDto droneDtoWrongNumber = new DroneDto(DRONE_WRONG_NUMBER, DRONE_TYPE);
	DroneDto droneDtoMissingNumber = new DroneDto(null, DRONE_TYPE);
	DroneMedication droneMedication = new DroneMedication(DRONE_NUMBER, MEDICATION_CODE);
	DroneMedication dmWrongCode = new DroneMedication(DRONE_NUMBER, WRONG_MEDICATION_CODE);
	DroneMedication dmMissingFields = new DroneMedication(null, null);

	@Test
	void registerDrone_serviceMethods_correct() throws Exception {
		when(dronesService.registerDrone(droneDto)).thenReturn(droneDto);
		
		String jsonDroneDto = mapper.writeValueAsString(droneDto);
		String actualJSON = mockMvc.perform(post("http://localhost:8080/drones")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonDroneDto))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		assertEquals(jsonDroneDto, actualJSON);
	}
	
	@Test
	void loadDrone_serviceMethods_correct() throws Exception {
		when(dronesService.loadDrone(droneMedication)).thenReturn(droneMedication);
		
		String jsonMedicationDto = mapper.writeValueAsString(droneMedication);
		String actualJSON = mockMvc.perform(post("http://localhost:8080/drones/load")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicationDto))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		assertEquals(jsonMedicationDto, actualJSON);
	}
	
	@Test
	void registerDrone_droneExists_exception() throws Exception  {
		when(dronesService.registerDrone(droneDto)).thenThrow(new DroneIllegalStateException());
		
		String jsonDroneDto = mapper.writeValueAsString(droneDto);
		String response = mockMvc.perform(post("http://localhost:8080/drones")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonDroneDto))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		assertEquals(DRONE_ALREADY_EXISTS, response);
	}
	
	@Test
	void loadDrone_droneNotAvailable_exception() throws Exception  {
		when(dronesService.loadDrone(droneMedication)).thenThrow(new DroneStateIllegalStateException());
		
		String jsonMedicationDto = mapper.writeValueAsString(droneMedication);
		String response = mockMvc.perform(post("http://localhost:8080/drones/load")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicationDto))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		assertEquals(DRONE_IS_NOT_AVAILABLE, response);
	}
	
	@Test
	void loadDrone_lowBattery_exception() throws Exception  {
		when(dronesService.loadDrone(droneMedication)).thenThrow(new DroneBatteryIllegalStateException());
		
		String jsonMedicationDto = mapper.writeValueAsString(droneMedication);
		String response = mockMvc.perform(post("http://localhost:8080/drones/load")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicationDto))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		assertEquals(DRONE_BATTERY_IS_LOW, response);
	}
	
	@Test
	void loadDrone_medicationNotFound_exception() throws Exception  {
		when(dronesService.loadDrone(droneMedication)).thenThrow(new MedicationNotFoundException());
		
		String jsonMedicationDto = mapper.writeValueAsString(droneMedication);
		String response = mockMvc.perform(post("http://localhost:8080/drones/load")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicationDto))
				.andExpect(status().isNotFound())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		assertEquals(MEDICATION_NOT_FOUND, response);
	}
	
	@Test
	void loadDrone_droneNotFound_exception() throws Exception  {
		when(dronesService.loadDrone(droneMedication)).thenThrow(new DroneNotFoundException());
		
		String jsonMedicationDto = mapper.writeValueAsString(droneMedication);
		String response = mockMvc.perform(post("http://localhost:8080/drones/load")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicationDto))
				.andExpect(status().isNotFound())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		assertEquals(DRONE_NOT_FOUND, response);
	}
	
	@Test
	void registerDrone_wrongNumber_validation() throws Exception {
		String jsonDroneDto = mapper.writeValueAsString(droneDtoWrongNumber);
		String response = mockMvc.perform(post("http://localhost:8080/drones").contentType(MediaType.APPLICATION_JSON)
				.content(jsonDroneDto)).andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_DRONE_NUMBER_MESSAGE, response);
	}
	
	@Test
	void loadDrone_wrongCode_validation() throws Exception {
		String jsonMedicationDto = mapper.writeValueAsString(dmWrongCode);
		String response = mockMvc.perform(post("http://localhost:8080/drones/load")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicationDto))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();
		assertEquals(WRONG_MEDICATION_CODE_MESSAGE, response);
	}
	
	@Test
	void registerDrone_missingNumber_validation() throws Exception {
		String jsonDroneDto = mapper.writeValueAsString(droneDtoMissingNumber);
		String response = mockMvc.perform(post("http://localhost:8080/drones")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonDroneDto))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();
		allFieldsMissingTest(new String[] {MISSING_DRONE_NUMBER_MESSAGE}, response);
	}
	
	@Test
	void loadDrone_missingNumber_validation() throws Exception {
		String jsonMedicationDto = mapper.writeValueAsString(dmMissingFields);
		String response = mockMvc.perform(post("http://localhost:8080/drones/load")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicationDto))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();
		allFieldsMissingTest(new String[] {MISSING_DRONE_NUMBER_MESSAGE, MISSING_MEDICATION_CODE_MESSAGE}, response);
	}
		
	private void allFieldsMissingTest(String[] expectedMessages, String response) {
		Arrays.sort(expectedMessages);
		String[] actualMessages = response.split(";");
		Arrays.sort(actualMessages);
		assertArrayEquals(expectedMessages, actualMessages);
	}

}
