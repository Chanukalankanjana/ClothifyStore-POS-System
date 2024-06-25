package edu.icet.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_has_items")
@Table(name = "order_has_items")
public class OrderHasItemEntity {
    private Integer id;
    private String orderId;
    private String itemId;
    private int qty;
    private double amount;
}
