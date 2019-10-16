package com.ardecs.ctshopapp.accessingdatamysql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="login", unique = true)
    private String login;

    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<U_order> u_orders = new ArrayList<>();

    public List<U_order> getU_orders() {
        return u_orders;
    }

    public void setU_orders(List<U_order> u_orders) {
        this.u_orders = u_orders;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }
}
