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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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


    @Autowired
    MockMvc mockMvc;

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
        Throwable throwable = assertThrows(NotFoundException.class, () -> productRepository
                .findById(Mockito.anyInt()).orElseThrow(NotFoundException::new));
        assertNull(throwable.getMessage());
    }

    @Test
    void shouldFindProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));
        assertEquals(product1.getId(), 1);
        assertEquals(product1.getName(), "coffee");
        assertEquals(product1.getPrice(), BigDecimal.valueOf(3.0));
        assertEquals(product1.getQuantity(), 3);
    }

    @Test
    @Disabled
    void shouldReturnNoProductInStock() throws Exception {
        when(productRepository.findById(1)).thenReturn(Optional.of(product2));
        assertEquals(product2.getId(),2);
        assertEquals(product2.getQuantity(),0);
        this.mockMvc.perform(get("src/main/resources/templates/noProductInStock.html")).andDo(print())
                .andExpect(content().string(containsString("We dont have this product")));
    }

    @Test
    void shouldFindNonPaidOrder() {
        when(orderRepository.findByIsPaid(false)).thenReturn(order1);
        assertFalse(order1.getIsPaid());
        assertEquals(order1.getUser(), user);
    }

    @Test
    void shouldCreateNewOrder() {
        when(orderRepository.findByIsPaid(true)).thenReturn(order2);
        assertTrue(order2.getIsPaid());
        assertEquals(order2.getUser(), user);
        if (order2.getIsPaid()) {
            Order order = new Order(false, user);
            assertFalse(order.getIsPaid());
            assertEquals(order.getUser(), user);
        }
    }

    @Test
    void shouldFindOrderProductById() {
        when(orderProductRepository.findById(orderProductId)).thenReturn(Optional.ofNullable(orderProduct1));
        assertEquals(orderProduct1.getProduct(), product1);
        assertEquals(orderProduct1.getOrder(), order1);
        assertEquals(orderProduct1.getId(), orderProductId);
    }

    @Test
    void shouldUpdateQuantityInStockAndOrder() {
        product1.setQuantity(product1.getQuantity() - 1);
        assertEquals(product1.getQuantity(), 2);
        orderProduct1.setQuantityInOrder(orderProduct1.getQuantityInOrder() + 1);
        assertEquals(orderProduct1.getQuantityInOrder(), 1);
    }


}