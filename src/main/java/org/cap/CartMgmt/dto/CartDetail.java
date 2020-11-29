package org.cap.CartMgmt.dto;

import java.util.Set;

public class CartDetail {
	private Long cartId;
	private Set<ProductAndQuantity> products;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Set<ProductAndQuantity> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductAndQuantity> products) {
		this.products = products;
	}

}
