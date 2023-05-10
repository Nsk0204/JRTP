package in.cybage.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.cybage.entity.EligibilityDtls;
import in.cybage.repo.EligibilityDtlsRepo;

@Component
public class AppRunner implements ApplicationRunner{

	@Autowired
	private EligibilityDtlsRepo repo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		EligibilityDtls entity= new EligibilityDtls();
		
		entity.setEligId(1);
		entity.setName("John");
		entity.setMobile((long) 12345551);
		entity.setGender('M');
		entity.setSsn((long) 686868681);
		entity.setPlanName("SNAP");
		entity.setPlanStatus("Approved");
		repo.save(entity);
		
		EligibilityDtls entity1= new EligibilityDtls();
		entity1.setEligId(2);
		entity1.setName("Smith");
		entity1.setMobile((long) 12661243);
		entity1.setGender('M');
		entity1.setSsn((long) 688776);
		entity1.setPlanName("CCAP");
		entity1.setPlanStatus("Denied");
		repo.save(entity1);
	}

}
