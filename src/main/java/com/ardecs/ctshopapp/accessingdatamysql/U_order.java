package com.ardecs.ctshopapp.accessingdatamysql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class U_order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private boolean status;
    @ManyToOne
    @JoinTable(name = "id")
    private User user;

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns        = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
