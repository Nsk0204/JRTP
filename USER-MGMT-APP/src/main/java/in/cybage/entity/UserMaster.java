package in.cybage.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "USER_MASTER")
public class UserMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String fullname;

	private String email;

	private Long mobile;

	private String gender;

	private LocalDate dob;

	private Long ssn;

	private String password;

	private String accStatus;

	private LocalDate createdDate;
	
	private LocalDate updatedDate;

	private String createdBy;
	
	private String updatedBy;
}
