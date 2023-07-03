package se.group9.gicCafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sugar")
    private int sugar;

    @Column(name = "cream")
    private boolean cream;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "size")
    private String size;

    @ManyToOne
    @JoinColumn(name="oid", nullable=false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="did")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name="fid")
    private Food food;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public boolean isCream() {
        return cream;
    }

    public void setCream(boolean cream) {
        this.cream = cream;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public OrderDetail(int id, int sugar, boolean cream, int quantity, double subtotal, String size, Order order,
            Drink drink, Food food) {
        this.id = id;
        this.sugar = sugar;
        this.cream = cream;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.size = size;
        this.order = order;
        this.drink = drink;
        this.food = food;
    }

    public OrderDetail() {
    }
    
}
