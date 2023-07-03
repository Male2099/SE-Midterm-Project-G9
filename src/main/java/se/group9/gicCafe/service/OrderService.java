package se.group9.gicCafe.service;

import se.group9.gicCafe.model.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderByID(int id);

    Order saveOrder(Order order);

    Order saveAndFlushOrder(Order order);

    double getTotalPrice(Order order);
}
