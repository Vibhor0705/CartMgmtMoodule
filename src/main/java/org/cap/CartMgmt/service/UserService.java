package org.cap.CartMgmt.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.cap.CartMgmt.dao.IUserDao;
import org.cap.CartMgmt.entities.User;
import org.cap.CartMgmt.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements IUserService {
	@Autowired
	IUserDao userDao;

	@Override
	public User addUser(User user) {
		userDao.save(user);
		return user;
	}
	
	@Override
	public User signIn(User user) {
			User user1 = findUserById(user.getUserId());
			if(user1.getUserId()==user.getUserId() && user1.getUserRole().equals(user.getUserRole())&&user1.getUserPassword().equals(user.getUserPassword())) {
				return user1;
			}
			throw new UserNotFoundException("Incorrect login id or password ");
	}
	
	/*
	 * Function Name : findUserById Input Parameters : String userId Return Type :
	 * User Description : Find User by their userId
	 */
	private User findUserById(String userId) {
		Optional<User> optional = userDao.findById(userId);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new UserNotFoundException("User not found for user name=" + userId);
		}
	}


}
