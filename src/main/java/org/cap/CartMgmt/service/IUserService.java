package org.cap.CartMgmt.service;

import org.cap.CartMgmt.entities.User;

public interface IUserService {
	User addUser(User user);
	User signIn(User user);
}
