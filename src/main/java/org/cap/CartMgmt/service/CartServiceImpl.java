package org.cap.CartMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;

import org.cap.CartMgmt.controller.CartRestController;
import org.cap.CartMgmt.dao.ICartDao;
import org.cap.CartMgmt.dao.IProductDao;
import org.cap.CartMgmt.dao.IUserDao;
import org.cap.CartMgmt.dto.AddItemToCartDto;
import org.cap.CartMgmt.entities.Cart;
import org.cap.CartMgmt.entities.Product;
import org.cap.CartMgmt.entities.User;
import org.cap.CartMgmt.exceptions.ProductNotFoundException;
import org.cap.CartMgmt.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartServiceImpl implements ICartService {
	private static final Logger Log = LoggerFactory.getLogger(CartRestController.class);
	@Autowired
	private ICartDao cartDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IProductDao productDao;

	/*
	 * Function Name : addItemIntoCart Input Parameters : AddItemToCartDto Return
	 * Type : Cart Description : add existing Items into users cart
	 */
	@Override
	public Cart addItemIntoCart(AddItemToCartDto cartDto) {
		Log.info("inside Add Item Into Cart Method");
		boolean isUserExist = userDao.existsById(cartDto.getUserId());
		if (!isUserExist) {
			Log.error("User Not Found for user id : " + cartDto.getUserId());
			throw new UserNotFoundException("User Not found for userId :" + cartDto.getUserId());
		}
		boolean isProductExist = productDao.existsById(cartDto.getProductId());
		if (!isProductExist) {
			Log.error("Product Not Found for product id : " + cartDto.getProductId());
			throw new ProductNotFoundException("Product Not found for productId :" + cartDto.getProductId());
		}
		Cart cart = cartDao.findCartByUserId(cartDto.getUserId());
		if (cart == null) {
			cart = new Cart();
			User user = findUserById(cartDto.getUserId());
			cart.setUser(user);
			cart = cartDao.save(cart);
		}

		Map<String, Integer> products = cart.getProducts();
		if (products == null) {
			products = new HashMap<>();
			cart.setProducts(products);
		}
		products.put(cartDto.getProductId(), cartDto.getQuantity());
		cart.setProducts(products);
		cart = cartDao.save(cart);
		return cart;
	}

	/*
	 * Function Name : removeItemFromCart Input Parameters : String userId,String
	 * productId Return Type : Cart Description : Remove existing Items from users
	 * cart
	 */
	@Override
	public Cart removeItemFromCart(String userId, String productId) {
		Log.info("inside Remove Item from Cart Method");
		boolean isUserExist = userDao.existsById(userId);
		if (!isUserExist) {
			Log.error("User Not Found for user id : " + userId);
			throw new UserNotFoundException("User Not found for userId :" + userId);
		}
		boolean isProductExist = productDao.existsById(productId);
		if (!isProductExist) {
			Log.error("Product Not Found for product id : " + productId);
			throw new ProductNotFoundException("Product Not found for productId :" + productId);
		}
		Cart cart = cartDao.findCartByUserId(userId);
		Map<String, Integer> products = cart.getProducts();
		if (products != null) {
			products.remove(productId);
			cartDao.save(cart);
		}
		return cart;
	}

	/*
	 * Function Name : viewMyCart Input Parameters : String userId Return Type :
	 * Cart Description : View Users Cart
	 */
	@Override
	public Cart viewMyCart(String userId) {
		Log.info("Inside Find Cart By User Id");
		boolean isUserExist = userDao.existsById(userId);
		if (!isUserExist) {
			Log.error("User Not Found for user id : " + userId);
			throw new UserNotFoundException("User Not found for userId :" + userId);
		}
		return cartDao.findCartByUserId(userId);
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

	/*
	 * Function Name : findProductById Input Parameters : String productId Return
	 * Type : Product Description : Find Product by their productId
	 */
	private Product findProductById(String productId) {
		Optional<Product> optionalProduct = productDao.findById(productId);
		if (optionalProduct.isPresent()) {
			return optionalProduct.get();
		} else {
			throw new ProductNotFoundException("Product  not found for product id=" + productId);
		}
	}

}
