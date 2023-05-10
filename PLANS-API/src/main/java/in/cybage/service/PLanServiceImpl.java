package in.cybage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cybage.entity.Plan;
import in.cybage.entity.PlanCategory;
import in.cybage.repo.PlanCategoryRepo;
import in.cybage.repo.PlanRepo;

@Service
public class PLanServiceImpl implements PlanService {

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private PlanCategoryRepo categoryRepo;

	@Override
	public Map<Integer, String> getPlanCategories() {
		List<PlanCategory> categories = categoryRepo.findAll();
		Map<Integer, String> categoryMap = new HashMap<>();
		categories.forEach(category -> {
			categoryMap.put(category.getCategoryID(), category.getCategoryName());
		});
		return categoryMap;
	}

	@Override
	public boolean savePlan(Plan plan) {
		Plan savedPlan = planRepo.save(plan);
		return savedPlan.getPlanId() != null;
	}

	@Override
	public List<Plan> getAllPlans() {
		return planRepo.findAll();
	}

	@Override
	public Plan getPlanById(Integer pId) {
		Optional<Plan> planByID = planRepo.findById(pId);
		if (planByID.isPresent()) {
			return planByID.get();
		}
		return null;
	}

	@Override
	public boolean updatePlan(Plan plan) {
		Plan updatedPlan = planRepo.save(plan);
		return updatedPlan.getPlanId() != null;
	}

	@Override
	public boolean deleteByID(Integer pId) {
		boolean status = false;
		try {
			planRepo.deleteById(pId);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean changeActiveStatus(Integer pId, String Status) {
		Optional<Plan> findPlanById = planRepo.findById(pId);
		if (findPlanById.isPresent()) {
			Plan plan = findPlanById.get();
			plan.setActiveSw(Status);
			planRepo.save(plan);
			return true;
		}
		return false;
	}

}
