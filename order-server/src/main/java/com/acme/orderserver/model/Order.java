package com.acme.orderserver.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    @NotNull
    private Long storeId;

    @NotNull
    @Size(min = 2, max = 255)
    private String address;

    @Column(name = "confirmation_date")
    private LocalDateTime confirmationDate;

    private Order.Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_order_item", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "order_item_id"))
    private List<OrderItem> items;

    public enum Status {
        CREATED, REFUNDED, PAY
    }
}
