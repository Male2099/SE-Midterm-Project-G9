package se.group9.gicCafe.service;

import java.util.List;

import se.group9.gicCafe.model.OrderDetail;

public interface OrderDetailService {
    public OrderDetail saveAndFlushOrderDetail(OrderDetail orderDetail);

    public OrderDetail saveOrderDetail(OrderDetail orderDetail);

    public List<OrderDetail> getAllOrderDetails();

    public OrderDetail getOrderDetailByID(int id);

    public OrderDetail updateOrderDetail(OrderDetail orderDetail);

    public void deleteOrderDetailByID(int id);

    
}
