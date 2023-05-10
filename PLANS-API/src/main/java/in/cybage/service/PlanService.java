package in.cybage.service;

import java.util.List;
import java.util.Map;

import in.cybage.entity.Plan;

public interface PlanService {

	public Map<Integer,String> getPlanCategories();
	
	public boolean savePlan(Plan plan);
	
	public List<Plan> getAllPlans();
	
	public Plan getPlanById( Integer pId);
	
	public boolean updatePlan(Plan plan);
	
	public boolean deleteByID(Integer pId);
	
	public boolean changeActiveStatus( Integer pId, String Status);
	
}
