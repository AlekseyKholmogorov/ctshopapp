package com.ardecs.ctshop.persistence.entity;

import com.ardecs.ctshop.persistence.Views;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@JsonIdentityInfo(
        generator= ObjectIdGenerators.PropertyGenerator.class,
        property="id")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.OverviewInformation.class)
    private Integer id;
    @Size(min=2, max=20)
    @JsonView(Views.OverviewInformation.class)
    private String name;
    @Size(min=2, max=20)
    private String region;
    @Size(min=2)
    private String description;
    @JsonView(Views.OverviewInformation.class)
    private BigDecimal price = BigDecimal.ZERO;
    private Integer quantity = 0;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonView(Views.OverviewInformation.class)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonIgnore
    private Set<OrderProduct> orderProducts = new HashSet<>(0);

    public Product() {

    }

    public Product(String name, String region, String description, BigDecimal price, Integer quantity, Category category) {
        this.name = name;
        this.region = region;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
