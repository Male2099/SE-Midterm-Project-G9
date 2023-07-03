package se.group9.gicCafe.service.Implementation;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import se.group9.gicCafe.model.Order;
import se.group9.gicCafe.model.OrderDetail;
import se.group9.gicCafe.repository.OrderRepo;
import se.group9.gicCafe.service.OrderService;

@Service
public class OrderServiceImp implements OrderService {

    private OrderRepo orderRepo;

    public OrderServiceImp(OrderRepo orderRepo) {
        super();
        this.orderRepo=orderRepo;
        }

    @Override
    public Order getOrderByID(int id) {
        return orderRepo.findById(id).get();
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order saveOrder(Order order) {
       return orderRepo.save(order);
    }

    @Override
    public double getTotalPrice(Order order) {
        List<OrderDetail> orderDetails=order.getOrderDetail();
        double sum=0;
        for(OrderDetail orderD : orderDetails){
            sum+=orderD.getSubtotal();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        return Double.parseDouble(decimalFormat.format(sum));
    }

    @Override
    public Order saveAndFlushOrder(Order order) {
       return orderRepo.saveAndFlush(order);
    }
}
