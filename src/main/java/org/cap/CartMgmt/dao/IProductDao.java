package org.cap.CartMgmt.dao;

import org.cap.CartMgmt.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDao extends JpaRepository<Product, String> {

	@Query("select u from Product u where u.productId=?1")
	Product findProductBYId(Long productId);

}
