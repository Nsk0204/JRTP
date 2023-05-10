package in.cybage.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cybage.entity.EligibilityDtls;

public interface EligibilityDtlsRepo extends JpaRepository<EligibilityDtls, Integer> {
	
	@Query(" Select distinct(planName) from ELIGIBILTY_DETAILS ")
	public List<String> findPlanNames();
	
	@Query(" Select distinct(planStatus) from ELIGIBILTY_DETAILS ")
	public List<String> findPlanStatuses();
	
}
