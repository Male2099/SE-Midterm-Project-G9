package se.group9.gicCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.group9.gicCafe.model.OrderDetail;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer>{
    
}
