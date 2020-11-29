package org.cap.CartMgmt.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cap.CartMgmt.dto.CartDetail;
import org.cap.CartMgmt.dto.ProductAndQuantity;
import org.cap.CartMgmt.entities.Cart;
import org.cap.CartMgmt.entities.Product;
import org.cap.CartMgmt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartUtil {
	@Autowired
	ProductService service;

	public CartDetail convertToCartDetail(Cart cart) {
		CartDetail cartDetail = new CartDetail();
		cartDetail.setCartId(cart.getCartId());
		Map<String, Integer> productsMap = cart.getProducts();
		Set<ProductAndQuantity> products = convertToProductQuantity(productsMap);
		cartDetail.setProducts(products);
		return cartDetail;
	}

	public ProductAndQuantity convertToProductQuantity(String productId, Integer quantity) {
		ProductAndQuantity productQuantity = new ProductAndQuantity();
		Product product = service.findProductById(productId);
		productQuantity.setProductId(product.getProductId());
		productQuantity.setProductName(product.getProductName());
		productQuantity.setProductPrice(product.getProductPrice());
		productQuantity.setQuantity(quantity);
		return productQuantity;
	}

	public Set<ProductAndQuantity> convertToProductQuantity(Map<String, Integer> productMap) {
		Set<ProductAndQuantity> productSet = new HashSet<>();
		if (productMap == null) {
			return productSet;
		}
		Set<String> keys = productMap.keySet();
		for (String productId : keys) {
			int quantity = productMap.get(productId);
			ProductAndQuantity productQuantity = convertToProductQuantity(productId, quantity);
			productSet.add(productQuantity);
		}
		return productSet;
	}
}
