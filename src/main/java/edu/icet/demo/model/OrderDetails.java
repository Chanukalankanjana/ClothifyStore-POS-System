package edu.icet.demo.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private Integer id;
    private String orderId;
    private String itemName;
    private int qty;
    private double amount;
    private String itemId;
}
