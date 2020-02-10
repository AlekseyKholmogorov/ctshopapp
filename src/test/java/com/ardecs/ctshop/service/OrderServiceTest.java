package com.ardecs.ctshop.service;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.persistence.entity.*;
import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.repository.OrderProductRepository;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {

    User user = new User("jim", "123", "jim@example.com");
    Product product1 = new Product
            ("coffee", "india", "black coffee from india", BigDecimal.valueOf(3.0),
                    3, new Category("coffee")
            );
    Product product2 = new Product
            ("tea", "china", "black tea from china", BigDecimal.valueOf(3.0),
                    0, new Category("tea")
            );
    Order order1 = new Order(false, user);
    Order order2 = new Order(true, user);
    OrderProduct orderProduct1 = new OrderProduct(order1, product1);
    OrderProductId orderProductId = orderProduct1.getId();

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @InjectMocks
    private OrderService orderService;



    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product1.setId(1);
        product2.setId(2);
        order1.setId(1);
        order2.setId(2);
    }

    @AfterEach
    void setQuantityToDefault() {
        product1.setQuantity(3);
    }

    @Test
    void shouldThrowsNotFoundException() throws NotFoundException {
        assertThrows(NotFoundException.class, () ->
                orderService.addProductToOrder(user, Mockito.anyInt()));
    }

    @Test
    void shouldAddProductToOrder() {
        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product1));
        String result = orderService.addProductToOrder(user, Mockito.anyInt());
        assertEquals("redirect:/index", result);
    }

    @Test
    void shouldReturnNoProductInStock() {
        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product2));
        String result = orderService.addProductToOrder(user, Mockito.anyInt());
        assertEquals("/noProductInStock", result);
    }

    @Test
    void shouldFindNonPaidOrder() {
        when(orderRepository.findByIsPaid(Mockito.anyBoolean())).thenReturn(order1);
        Order nonPaidOrder = orderService.findNonPaidOrderOrCreateNew(user);
        assertNotNull(nonPaidOrder);
        assertFalse(nonPaidOrder.getIsPaid());
        assertEquals(user, nonPaidOrder.getUser());
        assertEquals(order1.getId(), nonPaidOrder.getId());
    }

    @Test
    void shouldCreateNewOrder() {
        when(orderRepository.findByIsPaid(Mockito.anyBoolean())).thenReturn(null);
        Order order = orderService.findNonPaidOrderOrCreateNew(user);
        assertNotNull(order);
        assertFalse(order.getIsPaid());
        assertEquals(user, order.getUser());
    }


    @Test
    void shouldUpdateQuantityInStockAndOrder() {
        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product1));
        when(orderProductRepository.findById(orderProductId)).thenReturn(Optional.of(orderProduct1));
        orderService.updateProductQuantityInStockAndOrder(product1, orderProduct1);
        assertEquals(2, product1.getQuantity());
        assertEquals(1, orderProduct1.getQuantityInOrder());
    }

    @Test
    void shouldAddOrderProductToOrder() {
        when(orderProductRepository.findById(orderProductId)).thenReturn(Optional.of(orderProduct1));
        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product1));
        when(orderRepository.findByIsPaid(Mockito.anyBoolean())).thenReturn(order1);
        orderService.addProductToOrder(user, Mockito.anyInt());
        Order order = orderService.findNonPaidOrderOrCreateNew(user);
        assertTrue(order.getOrderProducts().contains(orderProduct1));
    }

}