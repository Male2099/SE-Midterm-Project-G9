package se.group9.gicCafe.service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import se.group9.gicCafe.model.OrderDetail;
import se.group9.gicCafe.repository.OrderDetailRepo;
import se.group9.gicCafe.repository.OrderRepo;
import se.group9.gicCafe.service.OrderDetailService;

@Service
public class OrderDetailServiceImp implements OrderDetailService{
    private OrderDetailRepo orderDetailRepo;
    public OrderDetailServiceImp(OrderDetailRepo orderDetailRepo){
        super();
        this.orderDetailRepo=orderDetailRepo;
    }
    
    @Override
    public OrderDetail saveAndFlushOrderDetail(OrderDetail orderDetail) {
       return orderDetailRepo.saveAndFlush(orderDetail);
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepo.findAll();
    }

    @Override
    public OrderDetail getOrderDetailByID(int id) {
        return orderDetailRepo.findById(id).get();
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepo.save(orderDetail);
    }

    @Override
    public void deleteOrderDetailByID(int id) {
        orderDetailRepo.deleteById(id);
    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
       return orderDetailRepo.save(orderDetail);
    }
    
}
