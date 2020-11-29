package org.cap.CartMgmt.dto;

import org.cap.CartMgmt.entities.Product;

public class ViewCartDto {
	private Product product;
	private Integer quantity;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ViewCartDto() {
	}

	public ViewCartDto(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

}
