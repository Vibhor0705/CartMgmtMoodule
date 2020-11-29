package org.cap.CartMgmt.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.cap.CartMgmt.dao.IProductDao;
import org.cap.CartMgmt.entities.Product;
import org.cap.CartMgmt.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService implements IProductService {
	@Autowired
	IProductDao productDao;

	@Override
	public Product addProduct(Product product) {
		productDao.save(product);
		return product;
	}

	public Product findProductById(String productId) {
		Optional<Product> optionalProduct = productDao.findById(productId);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			return product;
		} else {
			throw new ProductNotFoundException("Product  not found for product id=" + productId);
		}
	}

}
