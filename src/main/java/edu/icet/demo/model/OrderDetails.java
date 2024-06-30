package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private Integer id;//cart Num
    private String orderId;
    private String itemId;
    private int qty;
    private double amount;

    public OrderDetails(Object o, String oId, String itemCode, Integer qty) {
    }
}
