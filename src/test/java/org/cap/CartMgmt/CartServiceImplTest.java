package org.cap.CartMgmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.cap.CartMgmt.dto.AddItemToCartDto;
import org.cap.CartMgmt.entities.Cart;
import org.cap.CartMgmt.entities.Product;
import org.cap.CartMgmt.entities.User;
import org.cap.CartMgmt.exceptions.ProductNotFoundException;
import org.cap.CartMgmt.exceptions.UserNotFoundException;
import org.cap.CartMgmt.service.CartServiceImpl;
import org.cap.CartMgmt.service.ICartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@AutoConfigureTestDatabase
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import({CartServiceImpl.class})
public class CartServiceImplTest {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ICartService service;
	
	@Test
	public void testAddItemIntoCart_1() {
		User user = new User();
		System.out.println("entity Manager = "+entityManager);
		user.setUserId("vj");
		entityManager.persist(user);
		Product product = new Product();
		product.setProductId("01");
		product.setProductName("Watch");
		product.setProductPrice("100");
		entityManager.persist(product);
		AddItemToCartDto cartDto = new AddItemToCartDto();
		cartDto.setUserId("vj");
		cartDto.setProductId("01");
		cartDto.setQuantity(4);
		Cart cart = service.addItemIntoCart(cartDto);
		List<Cart> fetched =  entityManager.createQuery("from Cart").getResultList();
		Assertions.assertEquals(1, fetched.size());
		Cart foundCart = fetched.get(0);
		Map<String,Integer>productMap = foundCart.getProducts();
		Assertions.assertEquals(1, productMap.size());
		int quantity = productMap.get("01");
		Assertions.assertEquals(4, quantity);
		Assertions.assertEquals(user, foundCart.getUser());
	}
	@Test
	public void testAddItemIntoCart_02() {
		Product product = new Product();
		product.setProductId("01");
		product.setProductName("Watch");
		product.setProductPrice("100");
		entityManager.persist(product);
		AddItemToCartDto cartDto = new AddItemToCartDto();
		cartDto.setUserId("111");
		cartDto.setProductId("01");
		cartDto.setQuantity(4);
		 Executable executable = () -> service.addItemIntoCart(cartDto);
		 Assertions.assertThrows(UserNotFoundException.class, executable);
	}
	
	@Test
	public void testAddItemIntoCart_03() {
		User user = new User();
		System.out.println("entity Manager = "+entityManager);
		user.setUserId("vj");
		entityManager.persist(user);
		AddItemToCartDto cartDto = new AddItemToCartDto();
		cartDto.setUserId("vj");
		cartDto.setProductId("110");
		cartDto.setQuantity(4);
		 Executable executable = () -> service.addItemIntoCart(cartDto);
		 Assertions.assertThrows(ProductNotFoundException.class, executable);
	}
	
	@Test
	public void testRemoveItemFromCart_01() {
		User user = new User();
		System.out.println("entity Manager = "+entityManager);
		user.setUserId("vj");
		entityManager.persist(user);
		Product product = new Product();
		product.setProductId("01");
		product.setProductName("Watch");
		product.setProductPrice("100");
		entityManager.persist(product);
		Cart cart = new Cart();
		cart.setUser(user);
		Map<String, Integer> productMap = new HashMap<>();
		productMap.put(product.getProductId(), 4);
		cart.setProducts(productMap);
		entityManager.persist(cart);
		cart = service.removeItemFromCart(user.getUserId(), product.getProductId());
		Assertions.assertEquals(0, productMap.size());
		Assertions.assertEquals(productMap, cart.getProducts());
	}

	@Test
	public void testRemoveItemFromCart_02() {
		User user = new User();
		System.out.println("entity Manager = "+entityManager);
		user.setUserId("vj");
		entityManager.persist(user);
		Product product = new Product();
		product.setProductId("01");
		product.setProductName("Watch");
		product.setProductPrice("100");
		entityManager.persist(product);
		Executable executable = () -> service.removeItemFromCart("abcd",product.getProductId());
		 Assertions.assertThrows(UserNotFoundException.class, executable);
	}
	
	@Test
	public void testRemoveItemFromCart_03() {
		User user = new User();
		System.out.println("entity Manager = "+entityManager);
		user.setUserId("vj");
		entityManager.persist(user);
		Product product = new Product();
		product.setProductId("01");
		product.setProductName("Watch");
		product.setProductPrice("100");
		entityManager.persist(product);
		Executable executable = () -> service.removeItemFromCart(user.getUserId(),"abcd");
		 Assertions.assertThrows(ProductNotFoundException.class, executable);
	}
	
	
	@Test
	public void testFindCartByUserId_01() {
		User user = new User();
		System.out.println("entity Manager = "+entityManager);
		user.setUserId("vj");
		entityManager.persist(user);
		Product product = new Product();
		product.setProductId("01");
		product.setProductName("Watch");
		product.setProductPrice("100");
		entityManager.persist(product);
		Cart cart = new Cart();
		cart.setUser(user);
		Map<String, Integer> productMap = new HashMap<>();
		productMap.put(product.getProductId(), 4);
		cart.setProducts(productMap);
		entityManager.persist(cart);
		Cart fetched = service.viewMyCart(user.getUserId());
		Assertions.assertEquals(cart.getCartId(),fetched.getCartId());
		Assertions.assertEquals(productMap, cart.getProducts());
	}
	
	@Test
	public void testFindCartByUserId_02() {
		Executable executable = () -> service.viewMyCart("abcd");
		 Assertions.assertThrows(UserNotFoundException.class, executable);
	}

}
