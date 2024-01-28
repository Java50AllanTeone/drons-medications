package telran.drones.model;

import jakarta.persistence.*;
import lombok.*;
import telran.drones.dto.*;

@Entity
@Table(name="medications")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Medication {
	
	String name;
	Integer weight;
	@Id
	@Column(name="medication_code")
	String code;

	static public Medication of(DroneMedication droneMedication) {
		return new Medication(null, null, droneMedication.medicationCode());
	}
	
	public DroneMedication build() {
		return new DroneMedication(null, code);
	}

}
