package edu.icet.demo.entity;

import edu.icet.demo.model.OrderDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
@Entity(name = "order_details")
public class OrderDetailsEntity {
    private String orderId;
    private String itemName;
    private int qty;
    private double amount;
    private String itemId;
}
