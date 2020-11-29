package org.cap.CartMgmt.controller;

import org.cap.CartMgmt.dto.AddItemToCartDto;
import org.cap.CartMgmt.dto.CartDetail;
import org.cap.CartMgmt.entities.Cart;
import org.cap.CartMgmt.entities.Product;
import org.cap.CartMgmt.entities.User;
import org.cap.CartMgmt.exceptions.ProductNotFoundException;
import org.cap.CartMgmt.exceptions.UserNotFoundException;
import org.cap.CartMgmt.service.ICartService;
import org.cap.CartMgmt.service.IProductService;
import org.cap.CartMgmt.service.IUserService;
import org.cap.CartMgmt.util.CartUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartRestController {
	private static final Logger Log = LoggerFactory.getLogger(CartRestController.class);
	@Autowired
	private ICartService service;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProductService productService;
	@Autowired
	private CartUtil util;

	// Adding new Items into Users Cart by passing AddItemIntoCartDto
	@PostMapping("/addItemIntoCart")
	ResponseEntity<CartDetail> addItemIntoCart(@RequestBody AddItemToCartDto cartDto) {
		Cart cart = service.addItemIntoCart(cartDto);
		CartDetail cartDetail = util.convertToCartDetail(cart);
		ResponseEntity<CartDetail> responce = new ResponseEntity<>(cartDetail, HttpStatus.OK);
		return responce;
	}

	// View Users Cart by passing their userId
	@GetMapping("/viewMyCart/{userId}")
	ResponseEntity<CartDetail> viewMyCart(@PathVariable String userId) {
		Cart cart = service.viewMyCart(userId);
		CartDetail cartDetail = util.convertToCartDetail(cart);
		ResponseEntity<CartDetail> responce = new ResponseEntity<>(cartDetail, HttpStatus.OK);
		return responce;
	}

	// Remove Items From Users Cart by passing their userId & productId
	@GetMapping("/removeProductByUserId/{userId}/{productId}")
	ResponseEntity<CartDetail> removeItemFromCart(@PathVariable String userId, @PathVariable String productId) {
		Cart cart = service.removeItemFromCart(userId, productId);
		CartDetail cartDetail = util.convertToCartDetail(cart);
		ResponseEntity<CartDetail> responce = new ResponseEntity<>(cartDetail, HttpStatus.OK);
		return responce;
	}

	// Handle UserNotFoundException
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		Log.error("User Not Found Exception " + ex);
		String msg = ex.getMessage();
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
		return response;
	}

	// Handle ProductNotFoundException
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
		Log.error("Product Not Found Exception " + ex);
		String msg = ex.getMessage();
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
		return response;
	}

	// Handle all other Exception if caught
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleAll(Throwable ex) {
		Log.error("Exception caught");
		String msg = ex.getMessage();
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}

	@PostMapping("/addProduct")
	ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product addedProduct = productService.addProduct(product);
		ResponseEntity<Product> responce = new ResponseEntity<>(addedProduct, HttpStatus.OK);
		return responce;
	}

	@PostMapping("/addUser")
	ResponseEntity<User> addUser(@RequestBody User user) {
		User addedUser = userService.addUser(user);
		ResponseEntity<User> responce = new ResponseEntity<>(addedUser, HttpStatus.OK);
		return responce;
	}

}
