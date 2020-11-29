package org.cap.CartMgmt.dao;

import org.cap.CartMgmt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User, String> {

	@Query("select u from User u where u.userId=?1")
	User findUserBYId(Long userId);

}
