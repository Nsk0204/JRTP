package in.cybage.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name= "PLAN_MASTER")
public class Plan {

	@Id
	@GeneratedValue
	@Column(name= "PLAN_ID")
	private Integer planId;
	
	@Column(name="PLAN_NAME")
	private String planName;

	@Column(name="ACTIVE_SW")
	private String activeSw;
	
	@Column(name="CATEGORY_ID")
	private Integer categoryId;
	
	@Column(name="PLAN_START_DATE")
	private LocalDate planStartDate;
	
	@Column(name="PLAN_END_DATE")
	private LocalDate planEndDate;
	
	@Column(name = "CREATED_DATE", updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;
	
	@Column(name = "UPDATED_DATE", insertable = false)
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
 
	
}
