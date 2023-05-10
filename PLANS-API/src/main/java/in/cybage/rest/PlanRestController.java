package in.cybage.rest;

import java.util.List;
import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.cybage.constants.AppConstants;
import in.cybage.entity.Plan;
import in.cybage.props.AppProperties;
import in.cybage.service.PlanService;

@RestController
public class PlanRestController {

	private PlanService planService;
	
	private Map<String, String> messages;
	
	private PlanRestController(PlanService planService, AppProperties appProps) {
	this.planService=planService;
	this.messages=appProps.getMessages();
	System.out.println(messages);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<Map<Integer, String>> planCategories() {
		Map<Integer, String> plancategories = planService.getPlanCategories();
		return new ResponseEntity<>(plancategories, HttpStatus.OK);
	}

	@PostMapping("/plan")
	public ResponseEntity<String> savePlan(@RequestBody Plan plan) {
		String message = AppConstants.EMPTY_STR ;
//		Map<String, String> messages = appProps.getMessages();
		boolean savedPlan = planService.savePlan(plan);
		if (savedPlan) {
			message = messages.get(AppConstants.PLAN_SAVE_SUCCESS);
		} else {
			message = messages.get(AppConstants.PLAN_SAVE_FAIL);
		}
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}


	@GetMapping("/plans")
	public ResponseEntity<List<Plan>> Plans() {
		List<Plan> allPlans = planService.getAllPlans();
		return new ResponseEntity<List<Plan>>(allPlans, HttpStatus.OK);
	}
	
	@GetMapping("/plan/{planId}")
	public ResponseEntity<Plan> editPlan(@PathVariable Integer planId){
		Plan plan = planService.getPlanById(planId);
		return new ResponseEntity<>(plan, HttpStatus.OK);	
	}

	@PutMapping("/plan")
	public ResponseEntity<String> updatePlan(@RequestBody Plan plan){
		boolean updatedPlan = planService.updatePlan(plan);
		String msg=AppConstants.EMPTY_STR;
//		Map<String, String> messages = appProps.getMessages();
		if(updatedPlan) {
			msg=messages.get(AppConstants.PLAN_UPDATE_SUCCESS);
		}else {
			msg=messages.get(AppConstants.PLAN_UPDATE_FAIL);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
	@DeleteMapping("/plan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId){
		String msg=AppConstants.EMPTY_STR;
//		Map<String, String> messages = appProps.getMessages();
		boolean deletedPlan = planService.deleteByID(planId);
		if(deletedPlan) {
			msg=messages.get(AppConstants.PLAN_DELETE_SUCCESS);
		}else {
			msg=messages.get(AppConstants.PLAN_DELETE_FAIL);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
	@PutMapping("/status-change/{planId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer planId, @PathVariable String status){
		boolean changedStatus = planService.changeActiveStatus(planId, status);
		String msg=AppConstants.EMPTY_STR;
//		Map<String, String> messages = appProps.getMessages();
		if(changedStatus) {
			msg=messages.get(AppConstants.STATUS_CHANGE_SUCCESS);
		}else {
			msg=messages.get(AppConstants.STATUS_CHANGE_FAIL);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	

}
