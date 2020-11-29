package org.cap.CartMgmt.service;

import org.cap.CartMgmt.dto.AddItemToCartDto;
import org.cap.CartMgmt.entities.Cart;
import org.springframework.stereotype.Service;

@Service
public interface ICartService {
	
	Cart removeItemFromCart(String userId, String productId);

	Cart addItemIntoCart(AddItemToCartDto cartDto);

	Cart viewMyCart(String userId);
}
