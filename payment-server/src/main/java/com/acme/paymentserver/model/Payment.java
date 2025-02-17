package com.acme.paymentserver.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @NotNull
    @Size(min = 14, max = 16)
    @Column(name = "cred_card_number")
    private String credCardNumber;

    @NotNull
    @Column(name = "date_payment")
    private LocalDateTime date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Payment.Status status;

    public enum Status {
        PENDING, PROCESSING, COMPLETED, REFUNDED
    }
}
