package in.cybage.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cybage.entity.UserMaster;

public interface UserMasterRepo extends JpaRepository<UserMaster, Integer>{

	public UserMaster findByEmailAndPassword( String email, String password);
	
	public UserMaster findByEmail(String email);
}
