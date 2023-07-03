package se.group9.gicCafe.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    @Column(name = "order_date")
    private Date order_date;

    @Column(name = "total")
    private double total;

    @Column(name = "cash_received")
    private double cash_received;

    @Column(name = "changed")
    private double changed;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name="uid", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="tid")
    private Tables tables;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail = new ArrayList<>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getChanged() {
        return changed;
    }

    public void setChanged(double changed) {
        this.changed = changed;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public double getCash_received() {
        return cash_received;
    }

    public void setCash_received(double cash_received) {
        this.cash_received = cash_received;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order(int id, Date order_date, double total, double cash_received, double changed,
            String status, User user, Tables tables, List<OrderDetail> orderDetail) {
        this.id = id;
        this.order_date = order_date;
        this.total = total;
        this.cash_received = cash_received;
        this.changed = changed;
        this.status = status;
        this.user = user;
        this.tables = tables;
        this.orderDetail = orderDetail;
    }

    public Order() {
    }  
    
    
}
