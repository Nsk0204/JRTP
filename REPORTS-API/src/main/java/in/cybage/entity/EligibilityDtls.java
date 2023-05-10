package in.cybage.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ELIGIBILTY_DETAILS")
public class EligibilityDtls {
	@Id
	private Integer eligId;
	private String name;
	private Long mobile;
	private String email;
	private Character gender;
	private Long ssn;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private LocalDate createDate;
	private LocalDate updateDate;
	private String createdBy;
	private String updatedBy;

}
