package org.cap.CartMgmt.dao;

import org.cap.CartMgmt.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartDao extends JpaRepository<Cart, Long> {
	@Query("from Cart where user.userId=:userId")
	Cart findCartByUserId(@Param("userId") String userId);


}
