package in.cybage.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cybage.entity.Plan;

public interface PlanRepo extends JpaRepository<Plan, Integer> {

}
