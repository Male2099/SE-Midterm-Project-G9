package se.group9.gicCafe.model;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "code", unique = true)
    private String code;
    
    @Column(name = "size_R")
    private double size_R;

    @Column(name = "size_L")
    private double size_L;

    @Column(name = "size_G")
    private double size_G;

    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToOne
    @JoinColumn(name="dcid", nullable=false)
    private DrinkCategory drinkCategory;

    @OneToMany(mappedBy="drink", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail = new ArrayList<>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public DrinkCategory getDrinkCategory() {
        return drinkCategory;
    }

    public void setDrinkCategory(DrinkCategory drinkCategory) {
        this.drinkCategory = drinkCategory;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getSize_R() {
        return size_R;
    }

    public void setSize_R(double size_R) {
        this.size_R = size_R;
    }

    public double getSize_L() {
        return size_L;
    }

    public void setSize_L(double size_L) {
        this.size_L = size_L;
    }

    public double getSize_G() {
        return size_G;
    }

    public void setSize_G(double size_G) {
        this.size_G = size_G;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Drink() {
    }

    public Drink(int id, String name, String note, String code, double size_R, double size_L, double size_G,
            String image, DrinkCategory drinkCategory, List<OrderDetail> orderDetail) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.code = code;
        this.size_R = size_R;
        this.size_L = size_L;
        this.size_G = size_G;
        this.image = image;
        this.drinkCategory = drinkCategory;
        this.orderDetail = orderDetail;
    }
    
    
}
