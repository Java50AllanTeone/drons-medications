package telran.drones.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import telran.drones.dto.State;

@Entity
@Table(name="event_logs")
public class EventLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Temporal(TemporalType.TIMESTAMP)
	Long id;
	LocalDateTime timestamp;
	@Column(name="drone_number")
	String droneNumber;
	
	@Enumerated(EnumType.STRING)
	State state;
	@Column(name="battery_capacity")
	int batteryCapacity;
	
	
	public EventLog(LocalDateTime timestamp, String droneNumber, State state, int batteryCapacity) {
		super();
		this.timestamp = timestamp;
		this.droneNumber = droneNumber;
		this.state = state;
		this.batteryCapacity = batteryCapacity;
	}
	
	
	

}
