package com.ardecs.ctshop.service;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.exceptions.PaidOrderCannotBeDeletedException;
import com.ardecs.ctshop.persistence.entity.*;
import com.ardecs.ctshop.persistence.repository.OrderProductRepository;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public Map<Product, Integer> getProductsInOrder(Order order) {
        return order.getOrderProducts().stream()
                .collect(Collectors.toMap(OrderProduct::getProduct, OrderProduct::getQuantityInOrder));
    }

    public BigDecimal getTotalSum(Order order) {

        return order.getOrderProducts().stream()
                .map(p -> p.getProduct().getPrice().multiply(BigDecimal.valueOf(p.getQuantityInOrder())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deleteOrder(Order order) {

        if (order.getIsPaid()) {
            throw new PaidOrderCannotBeDeletedException("Paid order cannot be deleted");
        }

        order.getOrderProducts().forEach(
                p -> p.getProduct().setQuantity(p.getProduct().getQuantity() + p.getQuantityInOrder())
        );

        orderRepository.delete(order);
    }

    @Transactional
    public synchronized String addProductToOrder(User user, Integer id) {

        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);

        if (isProductNotAvailable(product)) {
            return "/noProductInStock";
        }

        Order order = findNonPaidOrderOrCreateNew(user);

        OrderProductId orderProductId = new OrderProductId(order.getId(), product.getId());
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElse(new OrderProduct(order, product));

        updateProductQuantityInStockAndOrder(product, orderProduct);

        orderProductRepository.save(orderProduct);
        order.getOrderProducts().add(orderProduct);

        return "redirect:/index";

    }

    private boolean isProductNotAvailable(Product product) {
        return product.getQuantity() == 0;
    }


    Order findNonPaidOrderOrCreateNew(User user) {

        Order order = orderRepository.findByIsPaid(false);

        if (order == null) {
            return createNewOrder(user);
        }

        return order;
    }

    private Order createNewOrder(User user) {
        Order order = new Order(false, user);
        orderRepository.save(order);
        user.getOrders().add(order);
        return order;
    }

    void updateProductQuantityInStockAndOrder(Product product, OrderProduct orderProduct) {

        product.setQuantity(product.getQuantity() - 1);
        orderProduct.setQuantityInOrder(orderProduct.getQuantityInOrder() + 1);

    }

}
