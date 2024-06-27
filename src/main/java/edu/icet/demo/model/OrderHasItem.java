package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderHasItem {
    private Integer id;
    private String orderId;
    private String itemId;
    private int qty;
    private double amount;
}
