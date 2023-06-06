package in.cybage.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.cybage.bindings.ActivateAccount;
import in.cybage.bindings.Login;
import in.cybage.bindings.User;
import in.cybage.entity.UserMaster;
import in.cybage.repo.UserMasterRepo;
import in.cybage.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	private Logger logger= LoggerFactory.getLogger(UserMgmtServiceImpl.class);
			
	@Autowired
	private UserMasterRepo userMasterRepo;

	@Autowired
	private EmailUtils emailUtils;

	private Random random = new Random();

	@Override
	public boolean saveuser(User user) {

		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);

		entity.setPassword(generateRandomPwd());
		entity.setAccStatus("In-Active");

		UserMaster save = userMasterRepo.save(entity);

		String subject = "Your Registration Success";
		String fileName = "REG-EMAIL-BODY.txt";

		String body = readEmailBody(entity.getFullname(), entity.getPassword(), fileName);
		emailUtils.sendEmail(user.getEmail(), subject, body);

		return save.getUserId() != null;
	}

	@Override
	public boolean activateuserAcc(ActivateAccount activateAcc) {
		UserMaster user = new UserMaster();
		user.setEmail(activateAcc.getEmail());
		user.setPassword(activateAcc.getTempPwd());

		Example<UserMaster> of = Example.of(user);

		List<UserMaster> findAll = userMasterRepo.findAll(of);
		if (findAll.isEmpty()) {
			return false;
		} else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(activateAcc.getNewPwd());
			userMaster.setAccStatus("Active");
			userMasterRepo.save(userMaster);
			return true;
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<UserMaster> entities = userMasterRepo.findAll();
		List<User> users = new ArrayList<>();
		for (UserMaster entity : entities) {
			User user = new User();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<UserMaster> findById = userMasterRepo.findById(userId);
		if (findById.isPresent()) {
			UserMaster userMaster = findById.get();
			User user = new User();
			BeanUtils.copyProperties(userMaster, user);
			return user;
		}
		return null;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		try {
			userMasterRepo.deleteById(userId);
			return true;
		} catch (Exception e) {
			logger.error("Exception Occured",e);
		}
		return false;
	}

	@Override
	public boolean changeStatus(Integer userId, String accStatus) {
		Optional<UserMaster> findById = userMasterRepo.findById(userId);
		if (findById.isPresent()) {
			UserMaster userMaster = findById.get();
			userMaster.setAccStatus(accStatus);
			userMasterRepo.save(userMaster);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {
//		UserMaster userMaster = new UserMaster();
//		userMaster.setEmail(login.getEmail());
//		userMaster.setPassword(login.getPassword());
//
//		Example<UserMaster> of = Example.of(userMaster);
//		List<UserMaster> findAll = userMasterRepo.findAll(of);
//		if (findAll.isEmpty()) {
//			return "Invalid credentials";
//		} else {
//			UserMaster userMaster2 = findAll.get(0);
//			if (userMaster2.getAccStatus().equals("Active")) {
//				return "Success";
//			} else {
//				return "Account not activated";
//			}
//		}

		UserMaster entity = userMasterRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		if (entity == null) {
			return "Invalid credentials";
		}
		if (entity.getAccStatus().equals("Active")) {
			return "Success";
		} else {
			return "Account not active";
		}
	}

	@Override
	public String forgotPwd(String email) {
		UserMaster entity = userMasterRepo.findByEmail(email);
		if (entity == null) {
			return "Invalid crendentials";
		} else {
			String subject = "Password recovery";
			String fileName = "RECOVER-PWD-BODY.txt";

			String body = readEmailBody(entity.getFullname(), entity.getPassword(), fileName);

			boolean sendEmail = emailUtils.sendEmail(email, subject, body);

			if (sendEmail) {
				return "Email sent successfully to registered email";
			}
		}
		return null;
	}

	private String generateRandomPwd() {

		String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
		String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
		String DIGIT = "0123456789";
		int PASSWORD_LENGTH = 20;

		String alphaNumeric = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			// produce a random order
			int index = this.random.nextInt(alphaNumeric.length());
			sb.append(alphaNumeric.charAt(index));
		}
		return sb.toString();
	}

	public String readEmailBody(String fullName, String pwd, String filename) {
//		String fileName = "REG-EMAIL-BODY.txt";
		String mailBody = null;
		String url = "";

		try (
				FileReader fr = new FileReader(filename); 
				BufferedReader br = new BufferedReader(fr);
			){
			StringBuffer buffer = new StringBuffer();

			String line = br.readLine();
			while (line != null) {

				buffer.append(line);
				line = br.readLine();
			}

			mailBody = buffer.toString();
			mailBody = mailBody.replace("{FULLNAME}", fullName);
			mailBody = mailBody.replace("{TEMP-PWD", pwd);
			mailBody = mailBody.replace("{URL}", url);
			mailBody = mailBody.replace("{PWD}", pwd);
		} catch (Exception e) {
			logger.error("Exception Occured",e);
		}
		return mailBody;
	}
}
